package com.andersonlfeitosa.mavendependencyanalyzer.graphviz;

public interface Component {

	Attr attr(String name);

	Attrs attrs();
	
	String name();
	
	String output();
	
}
