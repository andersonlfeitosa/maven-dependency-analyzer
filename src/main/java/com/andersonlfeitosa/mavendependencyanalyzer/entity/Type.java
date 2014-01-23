package com.andersonlfeitosa.mavendependencyanalyzer.entity;

public enum Type {
	
	POM,
	
	JAR,
	
	WAR,
	
	EAR,
	
	EJB_CLIENT,
	
	SWF,
	
	SWC, 
	
	RB_SWC;
	
	public static Type getType(String type) {
		for (Type t : Type.values()) {
			if (t.name().equalsIgnoreCase(type)) {
				return t;
			} else if (type != null && type.equalsIgnoreCase("ejb-client")) {
				return t.EJB_CLIENT;
			} else if (type.equalsIgnoreCase("rb.swc")) {
				return t.RB_SWC;
			}
		}
		
		return null;
	}
	

}
