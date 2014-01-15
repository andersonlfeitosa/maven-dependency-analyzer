package com.andersonlfeitosa.mavendependencyanalyzer.strategy;

public interface IPomFileReader {
	
	/**
	 * This method read all pom.xml files contained in file specified as parameter.
	 * @param file directory will be read.
	 */
	void read(String file);

}
