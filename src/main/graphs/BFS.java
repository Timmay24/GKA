package main.graphs;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;




public class BFS {

	
	private Graph graph;
	private List queue;
	
	
	//distance infinity
	private int distance = Integer.MAX_VALUE;
	
		
	
	
	private BFS(Graph g){
		this.graph = g;
		this.queue = new ArrayList<>();
	}
		
	
	public BFS valueOf(Graph g){
		return new BFS(g); 
	}
	
	
	public void findShortestWay(Graph g, Vertex startNode, Vertex endNode){
		//start node has no parents
		startNode.nodeWeight(0);
		
		//start node was visited but not finished yet
		startNode.color("grey");
		
		//write start node in queue
		queue.add(startNode);
		
		while (! queue.isEmpty()){
		
		}
		
		
	}
	







}
