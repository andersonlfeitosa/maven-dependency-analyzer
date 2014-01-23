package com.andersonlfeitosa.mavendependencyanalyzer.xml.object;

public class Property {

	private String name;
	private String value;

	public Property(String nodeName, String value) {
		this.name = nodeName;
		this.value = value;
	}

	public Property() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Property [name=" + name + ", value=" + value + "]";
	}
}
