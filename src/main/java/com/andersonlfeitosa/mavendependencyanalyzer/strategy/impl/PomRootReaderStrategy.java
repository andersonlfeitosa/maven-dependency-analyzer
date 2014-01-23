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
	
	private Map<String, Property> properties = new HashMap<String, Property>();

	@Override
	public Map<String, Project> read(File file) { 
		readPom(XStreamConfigurator.createXStream(), null, file);
		return poms;
	}

	private void readPom(XStream xstream, Project parent, File file) {
		logger.info("processing pom file " + file.getAbsolutePath());
		try {
			String sfile = FileUtils.readFileToString(file);
			Project project = (Project) xstream.fromXML(sfile);
			
			if (project.getProperties() != null) {
				properties.putAll(project.getProperties());
			}
			
			setGroupId(project, parent);
			setVersion(project, parent);
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

	private void replaceVariablesOfProject(Project project) {
		project.setArtifactId(replaceVariable(project.getArtifactId()));
		project.setVersion(replaceVariable(project.getVersion()));
		project.setGroupId(replaceVariable(project.getGroupId()));

		if (project.getDependencies() != null) {
			for (Dependency dependency : project.getDependencies()) {
				dependency.setArtifactId(replaceVariable(dependency.getArtifactId()));
				dependency.setGroupId(replaceVariable(dependency.getGroupId()));
				dependency.setVersion(replaceVariable(dependency.getVersion()));
			}
		}
		
		if (project.getDependencyManagement() != null && project.getDependencyManagement().getDependencies() != null) {
			for (Dependency dependency : project.getDependencyManagement().getDependencies()) {
				dependency.setArtifactId(replaceVariable(dependency.getArtifactId()));
				dependency.setGroupId(replaceVariable(dependency.getGroupId()));
				dependency.setVersion(replaceVariable(dependency.getVersion()));
			}
		}
	}

	public String replaceVariable(String text) {
		String replaced = text;

		if (text != null) {
			Matcher variable = PATTERN_VARIABLE.matcher(replaced);
			List<String> variables = new ArrayList<String>();
			while (variable.find()) {
				variables.add(variable.group(1));
			}
			
			Property property = null;
			for (String propertyName : variables) {
				property = properties.get(property);
				if (property != null) {
					String value = property.getValue();
					replaced = replaced.replaceAll("\\$\\{" + propertyName + "\\}", StringEscapeUtils.escapeJava(value));
					logger.debug("replace variable " + text + " for " + replaced);
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
			}
		}
	}
	
	private void setVersion(Project project, Project parent) {
		if (project.getVersion() == null) {
			if (parent != null && project.getParent().getVersion() == null) {
				logger.warn("setting version " + parent.getVersion() + " from the parent pom.xml to artifactId " + project.getArtifactId());
				project.setVersion(parent.getVersion());
			} else {
				logger.warn("setting version " + project.getParent().getVersion() + " from the parent tag to artifactId " + project.getArtifactId());
				project.setVersion(project.getParent().getVersion());
			}
		}
	}

	private void setGroupId(Project project, Project parent) {
		if (project.getGroupId() == null) {
			if (parent != null && project.getParent().getGroupId() == null) {
				logger.warn("setting groupId " + parent.getGroupId() + " from the parent pom.xml to artifactId " + project.getArtifactId());
				project.setGroupId(parent.getGroupId());
			} else {
				logger.warn("setting groupId " + project.getParent().getGroupId() + " from the parent tag to artifactId " + project.getArtifactId());
				project.setGroupId(project.getParent().getGroupId());
			}
		}
	}

}
