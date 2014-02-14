package com.andersonlfeitosa.mavendependencyanalyzer.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.DependencyEntity;

public class HTMLReporter implements IReport {

	@Override
	public void print(List<ArtifactEntity> artifacts) {
		StringBuilder sb = new StringBuilder();
		
		for (ArtifactEntity a : artifacts) {
			
			sb.append("{\"name\":");
			sb.append("\"");
			sb.append(a.getArtifactId());
			sb.append("\",\"size\":");
			sb.append(a.getDependencies().size());
			sb.append(",\"imports\":[");
			
			if (a.getDependencies() != null) {
				for (int i = 0; i < a.getDependencies().size(); i++) {
					
					DependencyEntity dependency = a.getDependencies().get(i);
					
					sb.append("\"");
					sb.append(dependency.getDependency().getArtifactId());
					sb.append("\"");
					
					if (i < a.getDependencies().size()-1) {
						sb.append(",");
					}
				}
			}
			
			sb.append("]},");
			sb.append("\n");
		}
		
		String data = sb.toString();
		data = data.substring(0, data.length()-2);

		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					getClass().getClassLoader().getResourceAsStream("maven-graph-dependency.html")));
			
			String text = null;
			String line = buf.readLine();
			
			while (line != null) {
				text += line;
				line = buf.readLine();
			}
			
			text = text.replace("${PROJECT_DATA}", data);
			FileUtils.writeStringToFile(new File("result.html"), text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
