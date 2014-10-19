package main.graphs;
import org.jgrapht.graph.DefaultEdge;


public class Edge extends DefaultEdge {

	private Vertex start;
	private Vertex end;
	private int edgeWeight;
	
	
	
	
	public int edgeWeight(){
		return this.edgeWeight;
	}
	
	public void edgeWeight(int edgeWeight){
		this.edgeWeight = edgeWeight;
	}
	
	public Edge() {
	    super();
	}

	
	
}
