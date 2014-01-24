package com.andersonlfeitosa.mavendependencyanalyzer.entity;

public enum Packaging {
	
	POM ("pom"), 
	
	JAR ("jar"),
	
	WAR ("war"),
	
	EAR ("ear"),
	
	EJB ("ejb"),
	
	SWF ("swf"),
	
	SWC ("swc");
	
	private String value;
	
	Packaging(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static Packaging getPackaging(String value) {
		Packaging result = null;
		for (Packaging t : Packaging.values()) {
			if (t.getValue().equalsIgnoreCase(value)) {
				result = t;
				break;
			}
		}
		
		return result;
	}
	
}
