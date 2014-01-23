
package com.andersonlfeitosa.mavendependencyanalyzer.strategy;

import java.io.File;
import java.util.Map;

import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Project;

public interface IPomReader {
	
	Map<String, Project> read(File file);
	
}
