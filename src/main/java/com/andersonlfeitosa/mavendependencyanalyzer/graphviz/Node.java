package com.andersonlfeitosa.mavendependencyanalyzer.graphviz;

public class Node implements Component {

	private Graph graph;
	private String name;
	private Attrs attrs;

	private Node(String name) { 
		this.name = name;
		this.attrs = new Attrs(this);
	}
	
	public Node(String name, Graph graph) {
		this(name, name, graph);
	}
	
	public Node(String label, String id, Graph graph) {
		this(id);
		this.graph = graph;
		this.attr("label").value(label);
	}
	
	@Override
	public Attr attr(String name) {
		return this.attrs.get(name);
	}

	@Override
	public Attrs attrs() {
		return this.attrs;
	}
	
	public String name() {
		return this.name;
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
		Node other = (Node) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String output() {
		
		StringBuilder xOut = new StringBuilder(this.name);
	    StringBuilder xAttr = new StringBuilder("");
	    String xSeparator = "";
	    
	    for (Attr attrs : this.attrs.list()) {   
		      if  ("html".equals(attrs.name())) {
			      xAttr.append(xSeparator + "label = " + attrs.value().toGv());
		      }else {
	          xAttr.append(xSeparator + attrs.name() + " = " + attrs.value().toGv());
		      }
	        xSeparator = ", ";
	    }
	      if (xAttr.length() > 0) {
	        xOut.append(" [" + xAttr.toString() + "]");
			}
	      xOut.append(";");

	      return(xOut.toString());
		
	}

	static Node getDefault(String name) {
		return new Node(name);
	}
	
	public Graph graph() { 
		return graph;
	}

}
