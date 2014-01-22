package com.andersonlfeitosa.mavendependencyanalyzer.util;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Dependency;
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

	public static String gavToString(ArtifactEntity artifact) {
		StringBuilder sb = new StringBuilder();
		
		if (artifact != null) {
			sb.append(artifact.getGroupId());
			sb.append(GAV_SEPARATOR);
			sb.append(artifact.getArtifactId());
			sb.append(GAV_SEPARATOR);
			sb.append(artifact.getVersion());
		}
		
		return sb.toString();
	}

	public static Object gavToString(Dependency dependency) {
		StringBuilder sb = new StringBuilder();
		
		if (dependency != null) {
			sb.append(dependency.getGroupId());
			sb.append(GAV_SEPARATOR);
			sb.append(dependency.getArtifactId());
			sb.append(GAV_SEPARATOR);
			sb.append(dependency.getVersion());
		}
		
		return sb.toString();
	}
	
	

}
