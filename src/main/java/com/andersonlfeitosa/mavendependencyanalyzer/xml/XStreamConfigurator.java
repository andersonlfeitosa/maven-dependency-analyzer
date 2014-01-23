package com.andersonlfeitosa.mavendependencyanalyzer.xml;

import com.andersonlfeitosa.mavendependencyanalyzer.xml.converter.PropertyConverter;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Dependency;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Parent;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Project;
import com.thoughtworks.xstream.XStream;

public class XStreamConfigurator {
	
	public static XStream createXStream() {
		XStream xstream = new XStream();
		
		xstream.alias("project", Project.class);
		xstream.alias("parent", Parent.class);
		xstream.alias("module", String.class);
		xstream.alias("dependency", Dependency.class);

		xstream.registerLocalConverter(Project.class, "properties", new PropertyConverter());
		
		xstream.omitField(Project.class, "scm");
		xstream.omitField(Project.class, "build");
		xstream.omitField(Parent.class, "relativePath");
		xstream.omitField(Dependency.class, "exclusions");

		return xstream;
	}

}
