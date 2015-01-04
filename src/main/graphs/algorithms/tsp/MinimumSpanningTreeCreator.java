package main.graphs.algorithms.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.algorithms.GKAAlgorithmBase;
import main.graphs.algorithms.path.BFS;

public class MinimumSpanningTreeCreator extends GKAAlgorithmBase { 
	
	public void applyMinimumSpanningTreeTo(GKAGraph g) {
		// Alle Kanten von g, aufsteigend sortiert, in einer Liste speichern, die abgearbeitet wird
		List<GKAEdge> remainingEdges = getSortedListOf(g.getGraph().edgeSet());
		
		// Aktuellen Graphen zuruecksetzen
		g.newGraph(g.getGraphType());
		
		// Hauptschleife
		while (!remainingEdges.isEmpty()) {
			GKAEdge currentEdge = remainingEdges.remove(0);
			String sourceNode = ((GKAVertex)currentEdge.getSource()).getName();
			String targetNode = ((GKAVertex)currentEdge.getTarget()).getName();
			String edgeName = currentEdge.getName();
			Integer edgeWeight = currentEdge.getWeight();
			
			if (!g.containsVertex(targetNode) || !g.containsVertex(sourceNode)) {
				g.addEdge(sourceNode, targetNode, edgeName, edgeWeight);
				
			} else if (g.findShortestWay(new BFS(), sourceNode, targetNode).isEmpty()) {
				g.addEdge(sourceNode, targetNode, edgeName, edgeWeight);
			}
		}
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
