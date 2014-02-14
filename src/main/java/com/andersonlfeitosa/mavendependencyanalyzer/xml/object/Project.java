package com.andersonlfeitosa.mavendependencyanalyzer.xml.object;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {

	private String modelVersion;
	private String groupId;
	private String artifactId;
	private String packaging;
	private String version;
	private String name;
	private Parent parent;
	private List<String> modules;
	private List<Dependency> dependencies;
	private Map<String, Property> properties;
	private DependencyManagement dependencyManagement;

	/*
	 * transient attributes
	 */
	private Project aggregatorProject;
	private Project parentProject;
	private File pom;

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public List<String> getModules() {
		return modules;
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public Map<String, Property> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Property> properties) {
		this.properties = properties;
	}

	public DependencyManagement getDependencyManagement() {
		return dependencyManagement;
	}

	public void setDependencyManagement(DependencyManagement dependencyManagement) {
		this.dependencyManagement = dependencyManagement;
	}

	public Project getAggregatorProject() {
		return aggregatorProject;
	}

	public void setAggregatorProject(Project aggregatorProject) {
		this.aggregatorProject = aggregatorProject;
	}

	public Project getParentProject() {
		return parentProject;
	}

	public void setParentProject(Project parentProject) {
		this.parentProject = parentProject;
	}

	public File getPom() {
		return pom;
	}

	public void setPom(File pom) {
		this.pom = pom;
	}
	
	public void addProperty(String key, Property property) {
		if (getProperties() == null) {
			setProperties(new HashMap<String, Property>());
		}
		
		getProperties().put(key, property);
	}

}
