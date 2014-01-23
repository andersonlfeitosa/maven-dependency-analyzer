
package com.andersonlfeitosa.mavendependencyanalyzer.strategy;

import java.io.File;

public interface IPomReader {
	
	/**
	 * This method read all pom.xml files contained in file specified as parameter.
	 * @param file directory will be read.
	 */
	void read(File file);

}
