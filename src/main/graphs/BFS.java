package main.graphs;

import java.util.ArrayList;
import java.util.List;





/**
 * @author Louisa und Tim
 *
 */
public class BFS {

	
	
	/**
	 * @param g is the Graph
	 * @param startNode is the startvertex
	 * @param endNode is the endvertex
	 * @return the sortest way from the start vertex to the end vertex
	 */
	public static List<Vertex> findShortestWay(GKAGraph g, Vertex startNode, Vertex endNode){
	
		//queue for the vertices
		List<Vertex> queue = new ArrayList<>();
				
		//write start node in queue
		queue.add(startNode);
		
		//a list of all visited vertices
		List<Vertex> visitedVertices = new ArrayList<>();
		
		//as long as the queue is not empty an the end vertex is not in the queue 
		//add all possible vertices for the way in queue
		while(!queue.isEmpty() && (queue.get((queue.size())-1) != endNode)){
			
					Vertex firstNode = queue.get(0);
				 				
					List<Vertex> list = new ArrayList<>();
					// if the vertex is in the queue
					if (g.containsVertex(firstNode)){
						//get a list of all adjacent vertices of the current looking Vertex
						list.addAll(g.getAllAdjacentsOf(firstNode));
					}else{
						System.out.println(" Dieser Knoten existiert im Graphen nicht ");
					}
					
					//mark the first vertex as visited 
					firstNode.setVisited(true);
					
					//take the next adjacent Vertex to be visited 
					for(int i=0; i<list.size(); i++){
						Vertex currentVertex = list.get(i);
						if ((!currentVertex.isVisited())){
							
						    //mark it as visited
							currentVertex.setVisited(true);
							
							//set current node-weight of the start Vertex one higher
							currentVertex.setNodeWeight(firstNode.getNodeWeight() + 1);
							
							//set the parent 
							currentVertex.setParent(firstNode.toString());
							
							//add the child-vertex (current vertex) to the end of the list 
							queue.add(currentVertex);
							
							/* if the current vertex is the endNode then stop the 
							for-loop and go on with the next commmand */
							if (currentVertex == endNode){
								i = list.size();
//								queue.add(currentVertex);
							}
						}
					}
				
				//done with the start vertex, added all its children to the queue 
				
				//add current vertex to the list of visited vertices
				visitedVertices.add(firstNode);
				//Done with first vertex. Remove it from queue. 
				queue.remove(0);
				
				}
		
		
		if(queue.isEmpty() && !visitedVertices.get(visitedVertices.size()-1).equals(endNode)){
			throw new IllegalArgumentException(" Der Endknoten ist nicht im Teilgraphen enthalten ");
		}
		else{
			visitedVertices.addAll(queue);
		}
		
		
		//#####################################################
		/* 
		 * find the shortest way from the startNode to the endNode
		 * */
		
			
		//reverse list with vertices to go the queue way back
		List<Vertex> reverseList = reverse(visitedVertices);
		
		List<Vertex> reverseReturnList = new ArrayList<Vertex>();
		
		//put the first element (the endNode of the way) in the return list
		reverseReturnList.add(reverseList.get(0));
		
		//gives the shortest way in a list
		/*
		 *starts at the end vertex and finds the parent vertex with a nodeweight one less than the end vertex, if found
		 *takes the current parent vertex as the new child vertex and find the parent vertex of this one...
		 *stops when start vertex is found
		 */
		Vertex tmpVertex = reverseList.get(0); // dummy: first vertex of the way
		for(Vertex v : reverseList){
			if ((tmpVertex.getNodeWeight() == (v.getNodeWeight() + 1)) && (tmpVertex.getParent().equals(v.getName()))){
				reverseReturnList.add(v);
				tmpVertex = v;
			}
		}
		
		//Number of edges in shortest way	
		int tmp = (reverseReturnList.size() - 1);
		Integer anzahlInInt = new Integer(tmp); 
		String anzahl = anzahlInInt.toString();
		
		
		//Number of edges in the way
		System.out.println("Der Weg hat " + anzahl + " Kanten");
		System.out.println("Der Weg ist: ");	
		
		//reverse the reverseList to get the way from the StartVertex to the EndVertex
		List<Vertex> returnList = reverse(reverseReturnList); 
		
		for(int i=0 ; i<returnList.size() ; i++){
			String tmp2 = returnList.get(i).getName().toString();
			System.out.println(tmp2);
		}
		
		return returnList;
		
}
	

	
	
	
	

	/**
	 * Help method 
	 * 
	 * @param l: a list with vertices
	 * @return the reversed list of the input list
	 * 
	 * Reverse input list of vertices.
	 * If list is empty, return list is also empty.
	 * If list has one element l and returnList are the same
	 * If list is null then NullPointerException
	 */
	public static List<Vertex> reverse(List<Vertex> l){
		if (l == null){throw new NullPointerException("the input is null");}
		
		List<Vertex> reverseList = new ArrayList<>();
			for (int i=l.size()-1 ; i>=0 ; i--){
				Vertex lastElem = l.get(i);
				reverseList.add(lastElem);
			}
			
		return reverseList;
	}





}
