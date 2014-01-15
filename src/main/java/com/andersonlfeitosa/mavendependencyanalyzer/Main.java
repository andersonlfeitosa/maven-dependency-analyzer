package com.andersonlfeitosa.mavendependencyanalyzer;

import com.andersonlfeitosa.mavendependencyanalyzer.manager.MavenDependencyAnalyzer;

public class Main {
	
	public static void main(String[] args) {
		MavenDependencyAnalyzer.getInstance().execute(args[0]);
	}

}
