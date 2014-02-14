package com.andersonlfeitosa.mavendependencyanalyzer.strategy.impl;

import java.io.File;
import java.util.Map;

import com.andersonlfeitosa.mavendependencyanalyzer.strategy.IPomReader;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Project;

public class DirectoryPomReaderStrategy implements IPomReader {

	@Override
	public Map<String, Project> read(File file) {
		// TODO to be implemented
		return null;
	}

}
