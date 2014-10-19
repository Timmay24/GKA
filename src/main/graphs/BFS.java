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
	public static List<Vertex> allAdjNode(Graph<Vertex, Edge> g, Vertex startNode){
			
		//alle Kanten des Startknotens holen
		Set<Edge> edges = g.edgesOf(startNode);
			
		//konvertieren vom Set zu einer List, sodass man ein nach dem anderen Element herausnehmen kann
		List<Edge> tmpEdgeList = new ArrayList<>(edges);
				
		//Rückgabeliste erstellen
		List<Vertex> adjacentTarget = new ArrayList<>();
			
		while(! tmpEdgeList.isEmpty()){
			//zu der ersten zugehörigen Kante den Knoten holen
			Edge tmp = tmpEdgeList.get(0);
			//Zielknoten in die Liste aller Zielknoten des Startknotens schreiben
			adjacentTarget.add(Vertex.valueOf(g.getEdgeTarget(tmp).toString()));
			tmpEdgeList.remove(tmp);
		}
		
		return adjacentTarget;
			
	}
	
	
	
	public static List<Vertex> findShortestWay(Graph<Vertex, Edge> g, Vertex startNode, Vertex endNode){
	
		//start node has no parents
		startNode.nodeWeight(0);
		
		//start node was visited but not finished yet
		startNode.color("grey");
		
		//write start node in queue
		queue.add(startNode);
		
		Vertex firstNode = startNode;
		
		//a list of all visited vertices
		List<Vertex> visitedVertices = new ArrayList<>();
		
		while (! queue.isEmpty() && (queue.get(queue.size()-1) != endNode) ){
			
			//get a list of all adjacent vertices of the current looking Vertex
			List<Vertex> list = allAdjNode(g, firstNode);
			
			//take the next adjacent Vertex to be visited -> turn it grey
			for(int i=0; i<list.size(); i++){
				Vertex currentVertex = list.get(i);
				if ((currentVertex.color()=="white")){
					
				    //mark it as visited (color grey)
					currentVertex.color("grey");
					
					//set current node-weight of the start Vertex one higher
					currentVertex.nodeWeight(firstNode.nodeWeight() + 1);
					
					//add the child-vertex (current vertex) to the end of the list 
					queue.add(currentVertex);
					
					/* if the current vertex is the endNode then stop the 
					for-loop and go on with the next commmand */
					if (currentVertex == endNode){
						i = list.size();
					}
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
		
		
		visitedVertices.addAll(queue);
		
		
		
		//#####################################################
		/* 
		 * find the shortest way from the startNode to the endNode
		 * */
		
		
	
		//reverse list with vertices to go the queue way back
		List<Vertex> reverseList = reverse(visitedVertices);
		
		
		List<Vertex> returnList = new ArrayList<Vertex>();
		
		//put the first element (the endNode of the way) in the return list
		returnList.add(reverseList.get(0));
		
		//gives the shortest way in a list
		for(int i=0 ; i<reverseList.size(); i++){
			
			Vertex first = reverseList.get(i);
			Vertex second = reverseList.get(i+1);
			
			/*if the nodeweight of the first element of the reverse list 
			one higher than the second element then the second element is 
			the adjacent vertex and a vertex of the shortest way  */
			if (first.nodeWeight() == (second.nodeWeight() + 1)){
				returnList.add(second);
//				if(second != startNode){
//					reverseList.remove(second);
//				}
			}
		}
		
		
		return returnList;
		
	}
	


	//Help method to reverse List with Vertices
	public static List<Vertex> reverse(List<Vertex> l){
		
		List<Vertex> reverseList = new ArrayList<>();
			for (int i=l.size()-1 ; i>=0 ; i--){
				Vertex lastElem = l.get(i);
				reverseList.add(lastElem);
			}
		return reverseList;
	}





}
