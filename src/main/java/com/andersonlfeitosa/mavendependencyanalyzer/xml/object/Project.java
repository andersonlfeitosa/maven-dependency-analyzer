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
	public String toString() {
		return "Project [groupId=" + groupId + ", artifactId=" + artifactId
				+ ", version=" + version + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((aggregatorProject == null) ? 0 : aggregatorProject
						.hashCode());
		result = prime * result
				+ ((artifactId == null) ? 0 : artifactId.hashCode());
		result = prime * result
				+ ((dependencies == null) ? 0 : dependencies.hashCode());
		result = prime
				* result
				+ ((dependencyManagement == null) ? 0 : dependencyManagement
						.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result
				+ ((modelVersion == null) ? 0 : modelVersion.hashCode());
		result = prime * result + ((modules == null) ? 0 : modules.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((packaging == null) ? 0 : packaging.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((parentProject == null) ? 0 : parentProject.hashCode());
		result = prime * result + ((pom == null) ? 0 : pom.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
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
		if (aggregatorProject == null) {
			if (other.aggregatorProject != null)
				return false;
		} else if (!aggregatorProject.equals(other.aggregatorProject))
			return false;
		if (artifactId == null) {
			if (other.artifactId != null)
				return false;
		} else if (!artifactId.equals(other.artifactId))
			return false;
		if (dependencies == null) {
			if (other.dependencies != null)
				return false;
		} else if (!dependencies.equals(other.dependencies))
			return false;
		if (dependencyManagement == null) {
			if (other.dependencyManagement != null)
				return false;
		} else if (!dependencyManagement.equals(other.dependencyManagement))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (modelVersion == null) {
			if (other.modelVersion != null)
				return false;
		} else if (!modelVersion.equals(other.modelVersion))
			return false;
		if (modules == null) {
			if (other.modules != null)
				return false;
		} else if (!modules.equals(other.modules))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (packaging == null) {
			if (other.packaging != null)
				return false;
		} else if (!packaging.equals(other.packaging))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (parentProject == null) {
			if (other.parentProject != null)
				return false;
		} else if (!parentProject.equals(other.parentProject))
			return false;
		if (pom == null) {
			if (other.pom != null)
				return false;
		} else if (!pom.equals(other.pom))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
