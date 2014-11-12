package main.graphs.exceptions;

import main.graphs.GKAVertex;

public class NoWayException extends Exception {

	private static final long serialVersionUID = -8468728089378970529L;

	public NoWayException(String startNode, String endNode) {
		super("Es existiert kein Weg zwischen " + startNode + " und " + endNode);
	} 
	
	public NoWayException(GKAVertex startNode, GKAVertex endNode) {
		this(startNode.getName(), endNode.getName());
	}
}
