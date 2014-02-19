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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artifactId == null) ? 0 : artifactId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (artifactId == null) {
			if (other.artifactId != null)
				return false;
		} else if (!artifactId.equals(other.artifactId))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [groupId=" + groupId + ", artifactId=" + artifactId
				+ ", version=" + version + "]";
	}

}
