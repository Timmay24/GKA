package main.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.graphs.interfaces.PathFinder;

import com.google.common.base.Preconditions;

public class Dijkstra implements PathFinder {

	/**
	 * @param g GraphWrapper des Graphen, auf dem der Weg gesucht werden soll
	 * @param startNode Startknoten
	 * @param endNode Zielknoten
	 * @return Liste des kuerzesten Weges zwischen Start- und Zielknoten
	 * @throws IllegalArgumentException
	 */
	public static List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws IllegalArgumentException {
		Long startTime = System.nanoTime();
		
		// Precondition TODO DOC
		// Graph muss gewichtet sein
		if (!g.isWeighted()) {
			throw new IllegalArgumentException("Der Graph muss gewichtet sein.\n" + "g.isWeighted() returned false");
		}
		
		if (startNode == endNode) {
			System.out.println("Startknoten == Zielknoten.");
			g.sendStats(("X"), Double.valueOf((System.nanoTime() - startTime) / 1000000D).toString());
			return Arrays.asList(startNode);
		}
		
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
		startNode.setWeight(0);
		startNode.setParent(startNode);
		
		// Aktueller Knoten - am Anfang der Startknoten
		GKAVertex currentNode = startNode;

		// Hauptschleife
		do {
			// Unbesuchten Knoten mit der aktuell geringsten Distanz holen und auf besucht setzen
			// bzw. aus dem Set nodes entfernen
			currentNode = minNode(nodes);
			nodes.remove(currentNode);
			System.out.println(currentNode + " besucht und aus nodes entfernt.");
			
			
			// Set bilden, in dem alle, ausser den bereits besuchten, Nachbarknoten enthalten sind
			Set<GKAVertex> unvisitedAdjacents = new HashSet<>(g.getAllAdjacentsOf(currentNode));
			unvisitedAdjacents.retainAll(nodes); // nur adjazente Knoten behalten, die noch im Set nodes enthalten sind
			
			// Fuer alle unbesuchten Nachbarn des aktuellen Knotens:
			for (GKAVertex adj : unvisitedAdjacents) {
				
				// eigene Distanz und Kantengewicht, der Kante zwischen aktuellem Knoten und Nachbarn, addieren
				GKAEdge adjEdge = g.getEdge(currentNode, adj);
				Integer distSum;
				if (adjEdge != null) {
					distSum = currentNode.getWeight() + adjEdge.getWeight();
					System.out.println("Distanz von " + currentNode + " ("+currentNode.getWeight()+") + Kantengewicht ("+ adjEdge.getName() +") == " + distSum);
				} else {
					throw new NullPointerException("Kante wurde nicht gefunden.\n ==> " + currentNode + " -- " + adj);
				}
				
				// Falls Distanz-Summe niedriger ist, als die aktuelle Distanz des Nachbarn
				if (adj.getWeight() > distSum) {
					System.out.println("Nachbars ("+adj+") Distanz ist größer (" + adj.getWeight() +  " > " + distSum + ") => " + distSum + " ist die neue Distanz für "+adj+".");
					// Distanz des Nachbarn aktualisieren
					adj.setWeight(distSum);
					// und aktuellen Knoten als Vorgaenger des Nachbarn setzen
					adj.setParent(currentNode);
					System.out.println("Setze " + currentNode + " als Vorgänger von " + adj + ".\n");
				} else {
					System.out.println("Nachbars Distanz ist kleiner oder gleich. (" + adj.getWeight() +  " < " + distSum + ")\n");
				}
			}
			
			// wtf it work.s
			if (currentNode == endNode) {
				nodes.clear();
			}
			
		} while (!nodes.isEmpty()); // solange es noch unbesuchte Knoten gibt
		System.out.println("Alle Knoten besucht (nodes geleert)\n");
		
		// Weg ueber alle Vorgaenger rekonstruieren
		while (currentNode.getParent() != currentNode) {
			resultWay.add(currentNode);
			System.out.println(currentNode + " zum Weg hinzugefügt");
			currentNode = currentNode.getParent();
		}
		// die Schleife wird verlassen, sobald man am Startknoten
		// angekommen ist (wenn ein Knoten sein eigener Vorgaenger ist).
		// dann muss abschliessend der Startknoten manuell der Wegliste hinzugefuegt werden,
		// da dies, durch die Abbruchbedingung in der Schleife, nicht mehr geschieht.
		resultWay.add(currentNode);
		System.out.println(currentNode + " zum Weg hinzugefügt");
		
		// Stats an die GUI melden
		g.sendStats(("X"), Double.valueOf((System.nanoTime() - startTime) / 1000000D).toString());
		
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