package main.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hamcrest.core.IsEqual;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;




public class BFS {

	
	private static  Graph graph;
	private static List queue;
	private  Vertex startNode;
	private  Vertex endNode;
	
	//distance infinity
	private int distance = Integer.MAX_VALUE;
	
		
	
	
	private BFS(Graph g){
		this.graph = g;
		this.queue = new ArrayList<>();
	}
		
	
	public BFS valueOf(Graph g){
		return new BFS(g); 
	}
	
//	//getter und setter
//	public void startNode(Vertex startNode){
//		this.startNode = startNode;
//	}
//	
//	public Vertex startNode(){
//		return this.startNode;
//	}
	
	
	// adjazente Knoten holen
	//allAdjNode := Vertex -> List<Vertex>
	public static List<Vertex> allAdjNode(Graph g, Vertex startNode){
				
		//alle Kanten des Startknotens holen
//		Vertex adjNode;
		Set<Edge> edges = g.edgesOf(startNode);
			
		//konvertieren vom Set zu einer List, sodass man ein nach dem anderen Element herausnehmen kann
		List<Edge> tmpEdgeList = (List) edges;
				
		//Rückgabeliste erstellen
		List<Vertex> adjacentTarget = new ArrayList<>();
			
		while(! edges.isEmpty()){
			//zu der ersten zugehörigen Kante den Knoten holen
			Edge tmp = tmpEdgeList.get(1);
			//Zielknoten in die Liste aller Zielknoten des Startknotens schreiben
			adjacentTarget.add((Vertex)g.getEdgeTarget(tmp));
		}
		
		return adjacentTarget;
			
	}
	
	
	
	public static List<Vertex> findShortestWay(Graph g, Vertex startNode, Vertex endNode){
	
		//start node has no parents
		startNode.nodeWeight(0);
		
		//start node was visited but not finished yet
		startNode.color("grey");
		
		//write start node in queue
		queue.add(startNode);
		
		Vertex firstNode = startNode;
		
		List<Vertex> visitedVertices = new ArrayList<>();
		
		while (! queue.isEmpty()){
			
			List<Vertex> list = allAdjNode(g, firstNode);
			
			//take the next adjacent Vertex to be visited -> turn it grey
			for(int i=1; i<=list.size(); i++){
				Vertex currentVertex = list.get(i);
				if (currentVertex.color()=="white"){
					
				    //mark it as visited (color grey)
					currentVertex.color("grey");
					
					//set current node-weight of the current Vertex one higher
					currentVertex.nodeWeight(firstNode.nodeWeight() + 1);
					
					//add the child-vertex (current vertex) to the end of the list 
					queue.add(currentVertex);
				}
			}
			//done with the startNode, added all its children to the queue -> turn into black
			firstNode.color("black");
			//add current vertex to the list of visited vertices
			visitedVertices.add(firstNode);
			//remove current visited vertex from the queue
			queue.remove(firstNode);
			//next vertex in the queue becomes the new startNode
			firstNode = (Vertex) queue.get(0);
			
		}
	
		
		//TODO
		List<Vertex> l = new ArrayList<Vertex>();
		l.add(Vertex.valueOf("a"));
		return l;
		
	}
	







}
