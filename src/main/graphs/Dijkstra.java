package main.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.graphs.interfaces.PathFinder;

public class Dijkstra implements PathFinder {

	public static List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws IllegalArgumentException {
		List<GKAVertex> resultWay = new ArrayList<>();
		
		// Set aller zu besuchenden Knoten (wird aufgebraucht)
		Set<GKAVertex> nodes = new HashSet<>(g.getGraph().vertexSet());
		
		/* Vorarbeiten 1
		 * Jede Distanz auf unendlich bzw. Integer.max setzen
		 * (Alle Knoten unbesucht setzen und alle Vorgaenger null setzen => durch GKAVertex impl. bereits erfolgt)
		 */
		for (GKAVertex v : g.getGraph().vertexSet()) {
			v.setWeight(Integer.MAX_VALUE);
		}
		
		/* Vorarbeiten 2
		 * Vorgaenger vom Startknoten auf sich selbst setzen
		 * Distanz vom Startknoten auf 0 setzen
		 * Startknoten als besucht markieren
		 */
		startNode.setParent(startNode);
		
		// Aktueller Knoten - am Anfang der Startknoten
		GKAVertex currentNode = startNode;

		// Hauptschleife
		do {
			// Unbesuchten Knoten mit der aktuell geringsten Distanz holen und auf besucht setzen
			// bzw. aus nodes entfernen
			currentNode = minNode(nodes);
			nodes.remove(currentNode);
			
			// Fuer alle unbesuchten Nachbarn des aktuellen Knotens:
			Set<GKAVertex> unvisitedAdjacents = new HashSet<>(g.getAllAdjacentsOf(currentNode));
			unvisitedAdjacents.retainAll(nodes);
			
			for (GKAVertex adj : unvisitedAdjacents) {
				// eigene Distanz und Kantengewicht, der Kante zwischen aktuellem Knoten und Nachbarn, addieren
				Integer distSum = currentNode.getWeight() + g.getEdge(currentNode, adj).getWeight();
				
				// Falls Distanz-Summe niedriger ist, als die Distanz des Nachbarns
				if (adj.getWeight() > distSum) {
					// Distanz des Nachbarn aktualisieren
					adj.setWeight(distSum);
					// und aktuellen als Vorgaenger des Nachbarns setzen
					adj.setParent(currentNode);
					System.out.println(currentNode + " <--parent-- " + adj);
				}
				
				
			}
			
		} while (!nodes.isEmpty());
		
		while (currentNode.getParent() != currentNode) {
			resultWay.add(currentNode);
			currentNode = currentNode.getParent();
		}
		resultWay.add(currentNode);
		
		return Utils.reverse(resultWay);
	}
	
	public static GKAVertex minNode(Collection<GKAVertex> vertices) {
		GKAVertex minNode = null;
		
		for (GKAVertex v : vertices) {
			if (minNode == null) {
				minNode = v;
			} else {
				if (minNode.getWeight() > v.getWeight()) {
					minNode = v;
				}
			}
		}
		
		return minNode;
	}
}