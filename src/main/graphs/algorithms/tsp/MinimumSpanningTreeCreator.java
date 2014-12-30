package main.graphs.algorithms.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.algorithms.GKAAlgorithmBase;
import main.graphs.algorithms.interfaces.TSPAlgorithm;

public class MinimumSpanningTreeCreator extends GKAAlgorithmBase implements TSPAlgorithm {

	public void applyMinimumSpanningTreeTo(GKAGraph g) {
		GKAGraph minimalGraph = GKAGraph.valueOf(g.getGraphType());
		
		for (GKAVertex v : g.getGraph().vertexSet()) {
			minimalGraph.addVertex(v);
		}
		
		
		
		// Alle Kanten von g, aufsteigend sortiert, in einer Liste speichern, die abgearbeitet wird
		List<GKAEdge> remainingEdges = getSortedListOf(g.getGraph().edgeSet());
		
		// Hauptschleife
		while (!remainingEdges.isEmpty()) {
			GKAEdge currentEdge = remainingEdges.remove(0);
			
			minimalGraph.addEdge((GKAVertex)currentEdge.getSource(), (GKAVertex)currentEdge.getTarget(), currentEdge.getName());
			
			// Falls die hinzugefuegte Kante einen Zyklus verursacht, wird sie wieder entfernt
			if (hasCycle(minimalGraph)) {
				minimalGraph.removeEdge(currentEdge.getName());
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
	
	private boolean hasCycle(GKAGraph g) {
		
		return false;
	}
	
}
