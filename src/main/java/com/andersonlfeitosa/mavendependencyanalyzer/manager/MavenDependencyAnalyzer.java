package com.andersonlfeitosa.mavendependencyanalyzer.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.andersonlfeitosa.mavendependencyanalyzer.xml.Dependency;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Parent;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Project;
import com.thoughtworks.xstream.XStream;

public class MavenDependencyAnalyzer {

	private static final MavenDependencyAnalyzer instance = new MavenDependencyAnalyzer();

	private MavenDependencyAnalyzer() {
	}

	public static MavenDependencyAnalyzer getInstance() {
		return instance;
	}

	public void execute(String directory) {
		XStream xstream = new XStream();
		xstream.alias("project", Project.class);
		xstream.alias("parent", Parent.class);
		xstream.alias("module", String.class);
		xstream.alias("dependency", Dependency.class);
		
		xstream.omitField(Project.class, "scm");
		xstream.omitField(Project.class, "properties");
		xstream.omitField(Project.class, "dependencyManagement");
		xstream.omitField(Parent.class, "relativePath");

		try {
			BufferedReader br = new BufferedReader(new FileReader(directory + "/pom.xml"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			Project project = (Project) xstream.fromXML(sb.toString());
			System.out.println(project);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		// IPomFileReader reader = new PomFileReaderDataBaseStrategy();
		// reader.read(new File(directory));
	}

}
