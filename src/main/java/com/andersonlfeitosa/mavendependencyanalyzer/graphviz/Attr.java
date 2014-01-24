package com.andersonlfeitosa.mavendependencyanalyzer.graphviz;

public class Attr {

	private Attrs attrs;
	private String name;
	private AttrType value;
	
	public Attr(Attrs attributes, String name) { 
		this.attrs = attributes;
		this.name = name;
	}
	
	public Attr(Attrs attributes, String name, String value) { 
		this(attributes,name);
		this.value = new EscString(value);
	}
	
	public void value(String value) {
		if (value == null) { 
			this.remove();
		} else { 
			this.value = new EscString(value);
			this.attrs.set(name,this);
		}
	}

	public void remove() {
		this.attrs.remove(name);
	}

	public String name() {
		return name;
	}
	
	public AttrType value() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attr other = (Attr) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
