package com.andersonlfeitosa.mavendependencyanalyzer.xml.object;

import java.util.Set;

public class DependencyManagement {
	
	private Set<Dependency> dependencies;

	public Set<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	
}
