package com.andersonlfeitosa.mavendependencyanalyzer.graphviz;

public class OutputType {

	private String name;
	private String filePath;
	
	OutputType(String name) { 
		this.name = name;
		this.filePath = "default." + name;
	}

	public void toFilePath(String filePath){ 
		this.filePath = filePath;
	}
	
	public String name() {
		return this.name;
	}
	
	public String filePath() {
		return this.filePath;
	}

	public void name(String name) {
		this.name = name;
	}
	
}
