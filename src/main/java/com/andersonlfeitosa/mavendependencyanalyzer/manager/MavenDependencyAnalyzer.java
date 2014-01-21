package com.andersonlfeitosa.mavendependencyanalyzer.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.andersonlfeitosa.mavendependencyanalyzer.log.Log;
import com.andersonlfeitosa.mavendependencyanalyzer.util.GAVFormatter;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Dependency;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Parent;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Project;
import com.thoughtworks.xstream.XStream;

public class MavenDependencyAnalyzer {

	private static final MavenDependencyAnalyzer instance = new MavenDependencyAnalyzer();
	
	private static final Log logger = Log.getInstance();
	
	private Map<String, Project> poms = new HashMap<String, Project>();

	private MavenDependencyAnalyzer() {
	}

	public static MavenDependencyAnalyzer getInstance() {
		return instance;
	}

	public void execute(String directory) {
		readPom(createXStream(), null, createFile(directory, "/pom.xml"));
	}
	
	private File createFile(String... path) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < path.length; i++) {
			sb.append(path[i]);
			sb.append("/");
		}
		
		File f = new File(sb.toString());
		logger.debug("open file " + f.getAbsolutePath());
		return f;
	}

	private XStream createXStream() {
		XStream xstream = new XStream();
		xstream.alias("project", Project.class);
		xstream.alias("parent", Parent.class);
		xstream.alias("module", String.class);
		xstream.alias("dependency", Dependency.class);
		
		xstream.omitField(Project.class, "scm");
		xstream.omitField(Project.class, "properties");
		xstream.omitField(Project.class, "dependencyManagement");
		xstream.omitField(Project.class, "build");
		xstream.omitField(Parent.class, "relativePath");
		xstream.omitField(Dependency.class, "exclusions");
		
		return xstream;
	}

	private void readPom(XStream xstream, Project parent, File file) {
		logger.info("processing file " + file.getAbsolutePath());
		try {
			String sfile = FileUtils.readFileToString(file);
			Project project = (Project) xstream.fromXML(sfile);
			setGroupId(project, parent);
			setVersion(project, parent);
			poms.put(GAVFormatter.gavToString(project), project);
			if (project.getModules() != null && !project.getModules().isEmpty()) {
				for (String module : project.getModules()) {
					readPom(xstream, project, createFile(file.getParent(), "/", module, "/pom.xml"));
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void setVersion(Project project, Project parent) {
		if (project.getVersion() == null) {
			if (project.getParent().getVersion() == null) {
				project.setVersion(parent.getVersion());
			} else {
				project.setVersion(project.getParent().getVersion());
			}
		}
	}

	private void setGroupId(Project project, Project parent) {
		if (project.getGroupId() == null) {
			if (project.getParent().getGroupId() == null) {
				project.setGroupId(parent.getGroupId());
			} else {
				project.setGroupId(project.getParent().getGroupId());
			}
		}
	}

}
