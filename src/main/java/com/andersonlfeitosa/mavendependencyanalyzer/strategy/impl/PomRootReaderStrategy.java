package com.andersonlfeitosa.mavendependencyanalyzer.strategy.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.Scope;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.Type;
import com.andersonlfeitosa.mavendependencyanalyzer.log.Log;
import com.andersonlfeitosa.mavendependencyanalyzer.strategy.IPomReader;
import com.andersonlfeitosa.mavendependencyanalyzer.util.GAVFormatter;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.XStreamConfigurator;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Dependency;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Project;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Property;
import com.thoughtworks.xstream.XStream;

public class PomRootReaderStrategy implements IPomReader {
	
	private static final Pattern PATTERN_VARIABLE = Pattern.compile("\\$\\{(.*?)\\}");
	
	private static final Log logger = Log.getInstance();
	
	private Map<String, Project> poms = new HashMap<String, Project>();
	
	private Project root;
	
	@Override
	public Map<String, Project> read(File file) { 
		readPom(XStreamConfigurator.createXStream(), null, file);
		return poms;
	}

	private void readPom(XStream xstream, Project aggregatorProject, File file) {
		logger.info("processing pom file " + file.getAbsolutePath());
		
		try {
			String sfile = FileUtils.readFileToString(file);
			Project project = (Project) xstream.fromXML(sfile);
			project.setPom(file);
			deduce(project);
			
			if (aggregatorProject == null) {
				logger.info("setting the root pom file");
				this.root = project;
			} else {
				project.setAggregatorProject(aggregatorProject);
				Project projectParent = poms.get(GAVFormatter.gavToString(project.getParent()));
				project.setParentProject(projectParent);
			}
			
			setInformationForDependencies(project);
			replaceVariablesOfProject(project);
			
			poms.put(GAVFormatter.gavToString(project), project);
			if (project.getModules() != null && !project.getModules().isEmpty()) {
				for (String module : project.getModules()) {
					readPom(xstream, project, new File(file.getParent() + "/" + module + "/pom.xml"));
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void deduce(Project project) {
		if ((project.getGroupId() == null || project.getGroupId().equals("${project.groupId}")) && project.getParent().getGroupId() != null) {
			logger.warn("setting groupId " + project.getParent().getGroupId() + " from the parent tag to pom " + project.getPom().getAbsolutePath());
			project.setGroupId(project.getParent().getGroupId());
		}
		
		if ((project.getArtifactId() == null || project.getArtifactId().equals("${project.artifactId}")) && project.getParent().getArtifactId() != null)  {
			logger.warn("setting artifactId " + project.getParent().getArtifactId() + " from the parent tag to pom " + project.getPom().getAbsolutePath());
			project.setArtifactId(project.getParent().getArtifactId());
		}
		
		if ((project.getVersion() == null || project.getVersion().equals("${project.version}")) && project.getParent().getVersion() != null)  {
			logger.warn("setting version " + project.getParent().getVersion() + " from the parent tag to pom " + project.getPom().getAbsolutePath());
			project.setVersion(project.getParent().getVersion());
		}
		
		if (project.getProperties() == null) {
			if (project.getParentProject() != null) {
				logger.info("setting properties to project");
				project.setProperties(project.getParentProject().getProperties());
			}
		} else {
			if (project.getParentProject() != null && project.getParentProject().getProperties() != null) {
				logger.info("adding properties to project");
				project.getProperties().putAll(project.getParentProject().getProperties());
				
			}
		}
		
		if (project.getDependencyManagement() == null) {
			logger.info("setting ");
		}
	}

	private void replaceVariablesOfProject(Project project) {
		project.setArtifactId(replaceVariable(project.getArtifactId(), project.getProperties()));
		project.setVersion(replaceVariable(project.getVersion(), project.getProperties()));
		project.setGroupId(replaceVariable(project.getGroupId(), project.getProperties()));

		if (project.getDependencies() != null) {
			for (Dependency dependency : project.getDependencies()) {
				dependency.setArtifactId(replaceVariable(dependency.getArtifactId(), project.getProperties()));
				dependency.setGroupId(replaceVariable(dependency.getGroupId(), project.getProperties()));
				dependency.setVersion(replaceVariable(dependency.getVersion(), project.getProperties()));
			}
		}
		
		if (project.getDependencyManagement() != null && project.getDependencyManagement().getDependencies() != null) {
			for (Dependency dependency : project.getDependencyManagement().getDependencies()) {
				dependency.setArtifactId(replaceVariable(dependency.getArtifactId(), project.getProperties()));
				dependency.setGroupId(replaceVariable(dependency.getGroupId(), project.getProperties()));
				dependency.setVersion(replaceVariable(dependency.getVersion(), project.getProperties()));
			}
		}
	}

	public String replaceVariable(String text, Map<String, Property> properties) {
		String replaced = text;

		if (text != null) {
			Matcher variable = PATTERN_VARIABLE.matcher(replaced);
			List<String> variables = new ArrayList<String>();
			while (variable.find()) {
				variables.add(variable.group(1));
			}
			
			Property property = null;
			for (String propertyName : variables) {
				if (properties != null) {
					property = properties.get(property);
					if (property != null) {
						String value = property.getValue();
						replaced = replaced.replaceAll("\\$\\{" + propertyName + "\\}", StringEscapeUtils.escapeJava(value));
						logger.debug("replace variable " + text + " for " + replaced);
					}
				}
			}
			
		}
		
		return replaced;
	}
	
	private void setInformationForDependencies(Project project) {
		if (project.getDependencies() != null) {
			for (Dependency dependency : project.getDependencies()) {
				
				if (dependency.getScope() == null) {
					dependency.setScope(Scope.COMPILE.name());
				}
				
				if (dependency.getType() == null) {
					dependency.setType(Type.JAR.name());
				} 
				
				if (dependency.getClassifier() != null && dependency.getClassifier().equalsIgnoreCase("client")) {
					dependency.setType(Type.EJB_CLIENT.name());
				}
			}
		}
	}
	
	@Override
	public Project getRoot() {
		return root;
	}

}
