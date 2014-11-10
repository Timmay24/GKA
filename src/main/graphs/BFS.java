package main.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.graphs.interfaces.PathFinder;
import static main.graphs.Utils.reverse;

/**
 * @author Louisa und Tim
 *
 */
public class BFS implements PathFinder {
	
	/**
	 * Finds a shortest way from given startNode to given endNode within g, if it exists. 
	 * 
	 * @param g is the Graph
	 * @param startNode is the startvertex
	 * @param endNode is the endvertex
	 * @return the sortest way from the start vertex to the end vertex
	 */
	public static List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws IllegalArgumentException {
		
		long startTime = System.nanoTime();
		
		// reset relevant attributes of each vertex and edge of the graph
		for (GKAVertex v : g.getGraph().vertexSet()) {
			v.setParent(null);
			v.setVisited(false);
		}
		
		// hit-counter for graph accesses
		Integer hitcount = 0;
	
		// queue for the vertices
		List<GKAVertex> queue = new ArrayList<>();
				
		// write start node in queue
		queue.add(startNode);
		
		// a list of all visited vertices
		List<GKAVertex> visitedVertices = new ArrayList<>();
		
		// as long as the queue is not empty an the end vertex is not in the queue 
		// add all possible vertices for the way in queue
		while (!queue.isEmpty() && (queue.get((queue.size()) - 1) != endNode)) {

			GKAVertex firstNode = queue.get(0);

			List<GKAVertex> adjacents = new ArrayList<>();
			// if the vertex is in the queue
			if (g.containsVertex(firstNode)) {
				// get a list of all adjacent vertices of the current looking
				// Vertex
				adjacents.addAll(g.getAllAdjacentsOf(firstNode));
				hitcount += adjacents.size();
			} else {
				System.out.println("Es existiert kein Weg zum Zielknoten.");
			}

			// mark the first vertex as visited
			firstNode.setVisited(true);

			hitcount += 1;

			// take the next adjacent Vertex to be visited
			for (int i = 0; i < adjacents.size(); i++) {
				GKAVertex currentVertex = adjacents.get(i);
				if ((!currentVertex.isVisited())) {

					// mark it as visited
					currentVertex.setVisited(true);

					// set current node-weight of the start Vertex one higher
					currentVertex.setWeight(firstNode.getWeight() + 1);

					// set the parent
					currentVertex.setParent(firstNode);

					// add the child-vertex (current vertex) to the end of the
					// list
					queue.add(currentVertex);

					/*
					 * if the current vertex is the endNode then stop the
					 * for-loop and go on with the next commmand
					 */
					if (currentVertex == endNode) {
						i = adjacents.size();
						// queue.add(currentVertex);
					}
				}
			}

			// done with the start vertex, added all its children to the queue

			// add current vertex to the list of visited vertices
			visitedVertices.add(firstNode);
			// Done with first vertex. Remove it from queue.
			queue.remove(0);

		}
		
		
		if (queue.isEmpty() && !visitedVertices.get(visitedVertices.size() - 1).equals(endNode)) {
			throw new IllegalArgumentException("Es existiert kein Weg zwischen Start- und Zielknoten.");
		} else {
			visitedVertices.addAll(queue);
		}
		
		
		//#####################################################
		/* 
		 * find the shortest way from the startNode to the endNode
		 * */
		
			
		//reverse list with vertices to go the queue way back
		List<GKAVertex> reverseList = reverse(visitedVertices);
		
		List<GKAVertex> reverseReturnList = new ArrayList<GKAVertex>();
		
		//put the first element (the endNode of the way) in the return list
		reverseReturnList.add(reverseList.get(0));
		
		/* gives the shortest way in a list
		 * 
		 *starts at the end vertex and finds the parent vertex with a nodeweight one less than the end vertex, if found
		 *takes the current parent vertex as the new child vertex and find the parent vertex of this one...
		 *stops when start vertex is found
		 */
		GKAVertex tmpVertex = reverseList.get(0); // dummy: first vertex of the way
		for (GKAVertex v : reverseList) {
			if ((tmpVertex.getWeight() == (v.getWeight() + 1)) && (tmpVertex.getParent().equals(v))) {
				reverseReturnList.add(v);
				tmpVertex = v;
			}
		}
		
		// Number of edges in shortest way	
		int tmp = (reverseReturnList.size() - 1);
		Integer anzahlInInt = new Integer(tmp); 
		String anzahl = anzahlInInt.toString();
		
		
		// Number of edges in the way
		System.out.println("Der Weg hat " + anzahl + " Kanten");
		System.out.println("Es wurde " + hitcount.toString() + " Mal auf den Graphen zugegriffen");
		System.out.println("Der Weg ist: ");	
		
		// reverse the reverseList to get the way from the StartVertex to the EndVertex
		List<GKAVertex> returnList = reverse(reverseReturnList); 
		
		for(int i=0 ; i<returnList.size() ; i++){
			String tmp2 = returnList.get(i).getName().toString();
			System.out.println(tmp2);
		}
		
//		Map<String,String> stats = new HashMap<>();
//		stats.put("hitcount", hitcount.toString());
//		stats.put("timeelapsed", Double.valueOf((System.nanoTime() - startTime) / 1000000D).toString());
		
		// transmitting stats to graph wrapper for further processment
		g.sendStats(hitcount.toString(), Double.valueOf((System.nanoTime() - startTime) / 1000000D).toString());
		
		return returnList;
		
	}
}
