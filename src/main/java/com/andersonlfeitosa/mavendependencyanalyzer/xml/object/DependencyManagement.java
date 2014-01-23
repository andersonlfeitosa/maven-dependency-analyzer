package com.andersonlfeitosa.mavendependencyanalyzer.xml.object;

import java.util.List;

public class DependencyManagement {
	
	private List<Dependency> dependencies;

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	
}
