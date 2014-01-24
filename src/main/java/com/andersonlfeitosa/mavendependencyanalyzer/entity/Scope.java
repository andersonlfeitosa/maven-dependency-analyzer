package com.andersonlfeitosa.mavendependencyanalyzer.entity;

public enum Scope {
	
	COMPILE ("compile"),
	
	TEST ("test"),
	
	PROVIDED ("provided"),
	
	RUNTIME ("runtime"),
	
	SYSTEM ("system"),
	
	EXTERNAL ("external");
	
	private String value;
	
	Scope(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static Scope getScope(String value) {
		Scope result = null;
		for (Scope t : Scope.values()) {
			if (t.getValue().equalsIgnoreCase(value)) {
				result = t;
				break;
			}
		}
		
		return result;
	}

}
