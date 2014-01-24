package com.andersonlfeitosa.mavendependencyanalyzer.entity;

public enum Type {
	
	POM ("pom"),
	
	JAR ("jar"),
	
	WAR ("war"),
	
	EAR ("ear"),
	
	EJB_CLIENT ("ejb-client"),
	
	SWF ("swf"),
	
	SWC ("swc"),  
	
	RB_SWC ("rb.swc");
	
	private String value;
	
	Type(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static Type getType(String value) {
		Type result = null;
		for (Type t : Type.values()) {
			if (t.getValue().equalsIgnoreCase(value)) {
				result = t;
				break;
			}
		}
		
		return result;
	}
}
