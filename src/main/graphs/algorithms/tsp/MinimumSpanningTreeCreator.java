package main.graphs.algorithms.tsp;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.algorithms.GKAAlgorithmBase;
import main.graphs.algorithms.interfaces.PathFinder;

public class MinimumSpanningTreeCreator extends GKAAlgorithmBase { 
	
	/**
	 * @param graph Ausgangsgraph
	 * @return Referenz auf den Zielgraphen (in diesem Fall == graph)
	 */
	public GKAGraph applyMinimumSpanningTreeTo(GKAGraph graph) {
		checkNotNull(graph);
		return applyMinimumSpanningTreeTo(graph, graph);
	}
	
	/**
	 * Erzeugt einen minimalen Spannbaum in targetGraph anhand von sourceGraph.
	 * Sind sourceGraph und targetGraph gleich, wird der Ausgangsgraph modifiziert.
	 * 
	 * @param sourceGraph Ausgangsgraph
	 * @param targetGraph Zielgraph
	 * @return Referenz auf den Zielgraphen
	 */
	public GKAGraph applyMinimumSpanningTreeTo(GKAGraph sourceGraph, GKAGraph targetGraph) {
		checkNotNull(sourceGraph);
		
		long startTime = System.nanoTime();
		
		// Alle Kanten von g, aufsteigend sortiert, in einer Liste speichern, die abgearbeitet wird
		List<GKAEdge> remainingEdges = getSortedListOf(sourceGraph.getGraph().edgeSet());
		
		targetGraph = GKAGraph.valueOf(sourceGraph.getGraphType());
		
		// Hauptschleife
		while (!remainingEdges.isEmpty()) {
			// Zu bearbeitende Kante,
			GKAEdge currentEdge = remainingEdges.remove(0);
			// Inzidente Knoten und
			String sourceNode = ((GKAVertex)currentEdge.getSource()).getName();
			String targetNode = ((GKAVertex)currentEdge.getTarget()).getName();
			// Zur Kante gehörige Informationen holen
			String edgeName = currentEdge.getName();
			Integer edgeWeight = currentEdge.getWeight();
			
			// Falls source oder target im Zielgraphen nicht vorhanden sind
			if (!targetGraph.containsVertex(sourceNode) || !targetGraph.containsVertex(targetNode)) {
				// kann die Kante eingetragen werden
				targetGraph.addEdge(sourceNode, targetNode, edgeName, edgeWeight, false);
				
			} else {
				PathFinder bfs = new BFS();
				bfs.injectReferences(targetGraph, targetGraph.getVertex(sourceNode), targetGraph.getVertex(targetNode));
				// Prüfen, ob es bereits einen Weg zwischen source und target Knoten gibt
				bfs.run();
				
				// Falls es keinen Weg gibt (=> Wegliste leer)
				if (bfs.getResultWay().isEmpty()) {
					// kann die Kante eingetragen werden
					targetGraph.addEdge(sourceNode, targetNode, edgeName, edgeWeight, false);
				}
			}
		}
		sourceGraph.sendMessage("MST erzeugt in " + (System.nanoTime() - startTime) / 1E9D + " Sekunden");
		
		// Referenz zum Zielgraphen zurückgeben
		return targetGraph;
	}
	
	
	/**
	 * Sortiert eine Collection von Kanten aufsteigend nach Kantengewichtung.
	 * 
	 * @param edges Collection von Kanten
	 * @return Aufsteigend sortierte Liste von Kanten
	 */
	public List<GKAEdge> getSortedListOf(Collection<GKAEdge> edges) {
		checkNotNull(edges);
		
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
