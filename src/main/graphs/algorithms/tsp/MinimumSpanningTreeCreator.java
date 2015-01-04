package main.graphs.algorithms.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.algorithms.GKAAlgorithmBase;
import main.graphs.algorithms.path.BFS;

public class MinimumSpanningTreeCreator extends GKAAlgorithmBase { 
	
	public void applyMinimumSpanningTreeTo(GKAGraph g) {
		GKAGraph minimalGraph = GKAGraph.valueOf(g.getGraphType());
		
		for (GKAVertex v : g.getGraph().vertexSet()) {
			System.out.println(v);
		}
		
		
		// Alle Kanten von g, aufsteigend sortiert, in einer Liste speichern, die abgearbeitet wird
		List<GKAEdge> remainingEdges = getSortedListOf(g.getGraph().edgeSet());
		
		for (GKAEdge e : remainingEdges) {
			System.out.println(e);
		}
		
		// Hauptschleife
		while (!remainingEdges.isEmpty()) {
			GKAEdge currentEdge = remainingEdges.remove(0);
			String sourceNode = ((GKAVertex)currentEdge.getSource()).getName();
			String targetNode = ((GKAVertex)currentEdge.getTarget()).getName();
			String edgeName = currentEdge.getName();
			Integer edgeWeight = currentEdge.getWeight();
			
			System.out.println(sourceNode);
			System.out.println(targetNode);
			System.out.println(edgeName + "\n");
			
			if (!minimalGraph.containsVertex(targetNode) || !minimalGraph.containsVertex(sourceNode)) {
				System.out.println(minimalGraph.addEdge(sourceNode, targetNode, edgeName, edgeWeight));
				
			} else if (minimalGraph.findShortestWay(new BFS(), sourceNode, targetNode).isEmpty()) {
				System.out.println(minimalGraph.addEdge(sourceNode, targetNode, edgeName, edgeWeight));
			}
			
		}

		
//		//DEBUG
//		System.out.println("edges im minimalgraph");
//		for (GKAEdge e : minimalGraph.getGraph().edgeSet()) {
//			System.out.println(e.getName());
//		}
//		
		
	}
	
	public List<GKAEdge> getSortedListOf(Collection<GKAEdge> edges) {
		List<GKAEdge> result = new ArrayList<>(edges);
		
		result.sort(new Comparator<GKAEdge>() {
			@Override
			public int compare(GKAEdge o1, GKAEdge o2) {
				return o1.getWeight().compareTo(o2.getWeight());
			}
		});
		
		return result;
	}
	
}
