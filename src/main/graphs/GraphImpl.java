package main.graphs;

import main.graphs.interfaces.Graph;

public class GraphImpl implements Graph{

	//SELECTORS
	private int edges;
	private int vertices;
	private String vertexColor;
	
	//INITIALIZATION
	private GraphImpl(int edges, int vertices){
		this.edges = edges;
		this.vertices = vertices;
		this.vertexColor = "White";
	}
	
	public GraphImpl valueOf(int edges, int vertices){
		return new GraphImpl(edges, vertices);
	}
		
	
	
	
	// PREDICATES
	
	@Override
	public boolean hasUnknownNeighbour() {
		// TODO Auto-generated method stub
		/* 1
		 * 
		 * */
		return false;
	}
	
	
	@Override
	public boolean isDirected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUndirected() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
