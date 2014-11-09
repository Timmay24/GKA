package main.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.graphs.interfaces.PathFinder;

public class Dijkstra implements PathFinder {

	/**
	 * @param g GraphWrapper des Graphen, auf dem der Weg gesucht werden soll
	 * @param startNode Startknoten
	 * @param endNode Zielknoten
	 * @return Liste des kuerzesten Weges zwischen Start- und Zielknoten
	 * @throws IllegalArgumentException
	 */
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
			// bzw. aus dem Set nodes entfernen
			currentNode = minNode(nodes);
			nodes.remove(currentNode);
			
			
			// Set bilden, in dem alle, ausser den bereits besuchten, Nachbarknoten enthalten sind
			Set<GKAVertex> unvisitedAdjacents = new HashSet<>(g.getAllAdjacentsOf(currentNode));
			unvisitedAdjacents.retainAll(nodes); // nur adjazente Knoten behalten, die noch im Set nodes enthalten sind
			
			// Fuer alle unbesuchten Nachbarn des aktuellen Knotens:
			for (GKAVertex adj : unvisitedAdjacents) {
				
				// eigene Distanz und Kantengewicht, der Kante zwischen aktuellem Knoten und Nachbarn, addieren
				Integer distSum = currentNode.getWeight() + g.getEdge(currentNode, adj).getWeight();
				
				// Falls Distanz-Summe niedriger ist, als die aktuelle Distanz des Nachbarn
				if (adj.getWeight() > distSum) {
					// Distanz des Nachbarn aktualisieren
					adj.setWeight(distSum);
					// und aktuellen Knoten als Vorgaenger des Nachbarn setzen
					adj.setParent(currentNode);
					
//					System.out.println(currentNode + " <--parent-- " + adj);
				}
			}
			
		} while (!nodes.isEmpty()); // solange es noch unbesuchte Knoten gibt
		
		// Weg ueber Vorgaenger rekonstruieren
		while (currentNode.getParent() != currentNode) {
			resultWay.add(currentNode);
			currentNode = currentNode.getParent();
		}
		// die Schleife wird verlassen, sobald man am Startknoten
		// angekommen ist (wenn ein Knoten sein eigener Vorgaenger ist).
		// dann muss abschliessend der Startknoten manuell der Wegliste hinzugefuegt werden,
		// da dies, durch die Abbruchbedingung in der Schleife, nicht mehr geschieht.
		resultWay.add(currentNode);
		
		// da der Weg ueber die Vorgaenger rekonstruiert wurde,
		// entspricht die Liste resultWay dem umgekehrten Weg
		// und muss gedreht zurueckgegeben werden.
		return Utils.reverse(resultWay);
	}
	
	/**
	 * Ermittelt den Knoten mit der kleinsten Gewichtung aus der
	 * gegebenen Collection.
	 * 
	 * @param vertices Collection von Knoten
	 * @return Knoten mit der kleinsten Gewichtung
	 */
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