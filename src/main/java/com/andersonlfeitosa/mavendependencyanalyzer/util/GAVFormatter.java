package com.andersonlfeitosa.mavendependencyanalyzer.util;

import com.andersonlfeitosa.mavendependencyanalyzer.xml.Project;

public class GAVFormatter {

	private static final String GAV_SEPARATOR = ":";

	public static String gavToString(Project project) {
		StringBuilder sb = new StringBuilder();
		
		if (project != null) {
			sb.append(project.getGroupId());
			sb.append(GAV_SEPARATOR);
			sb.append(project.getArtifactId());
			sb.append(GAV_SEPARATOR);
			sb.append(project.getVersion());
		}
		
		return sb.toString();
	}
	
	

}
