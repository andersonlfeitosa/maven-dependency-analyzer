package com.andersonlfeitosa.mavendependencyanalyzer.manager;

public class MavenDependencyAnalyzer {
	
	private static final MavenDependencyAnalyzer instance = new MavenDependencyAnalyzer();
	
	private MavenDependencyAnalyzer() {}
	
	public static MavenDependencyAnalyzer getInstance() {
		return instance;
	}

	public void execute(String directory) {
		initResources();
		// process
		closeResources();
	}

	private void closeResources() {
		
	}

	private void initResources() {
		
	}
	
}
