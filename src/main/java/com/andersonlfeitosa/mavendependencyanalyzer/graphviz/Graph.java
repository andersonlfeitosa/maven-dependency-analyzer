package com.andersonlfeitosa.mavendependencyanalyzer.graphviz;

import java.util.List;

public interface Graph extends Component {

	Node node();
	
	Edge edge();
	
	List<Node> nodes();
	
	List<Edge> edges();
	
	String getType();
	
	Node addNode(String name);
	
	Edge addEdge(Node nodeFrom, Node nodeTo);
	
	boolean containsNode(Node node);
}
