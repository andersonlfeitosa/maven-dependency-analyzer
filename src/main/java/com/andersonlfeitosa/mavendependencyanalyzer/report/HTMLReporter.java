package com.andersonlfeitosa.mavendependencyanalyzer.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity;

public class HTMLReporter implements IReport {

	@Override
	public void print(List<ArtifactEntity> artifacts) {
		String data = artifactsToJSON(artifacts);
		writeFile(data);
	}

	private void writeFile(String data) {
//		FileUtils.readFileToString(new File());
		
		
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					getClass().getClassLoader().getResourceAsStream("maven-graph-dependency.html")));
			
			String text = null;
			String line = buf.readLine();
			
			while (line != null) {
				text += line;
				text += "\n";
				line = buf.readLine();
			}
			
			text = text.replace("${PROJECT_DATA}", data);
			FileUtils.writeStringToFile(new File("result.html"), text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	String artifactsToJSON(List<ArtifactEntity> artifacts) {
		StringBuilder json = new StringBuilder();
		Iterator<ArtifactEntity> it = artifacts.iterator();
		while (it.hasNext()) {
			json.append(it.next().toJSON());
			if (it.hasNext()) {
				json.append(",");
			}
		}
		json.append("\n");
		return json.toString();
	}

}
