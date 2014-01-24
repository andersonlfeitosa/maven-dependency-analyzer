package com.andersonlfeitosa.mavendependencyanalyzer.graphviz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attrs {

	Component component;
	private Map<String, Attr> data;
	
	public Attrs(Component component) { 
		this.component = component;
		this.data = new HashMap<String, Attr>();
	}

	public Attr get(String name) {
		Attr attr = this.data.get(name);
		if (attr == null)
			attr = new Attr(this,name);
		return attr;
	}

	public void set(String name, Attr attribute) {
		this.data.put(name, attribute);
	}

	public int total() {
		return this.data.size();
	}

	public void remove(String name) {
		this.data.remove(name);
	}
	
	public List<Attr> list() { 
		return new ArrayList<Attr>(data.values());
	}
}
