package main.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.graphs.exceptions.NoWayException;
import main.graphs.interfaces.PathFinder;

import com.google.common.base.Preconditions;

public class Dijkstra implements PathFinder {

	/**
	 * @param g GraphWrapper des Graphen, auf dem der Weg gesucht werden soll
	 * @param startNode Startknoten
	 * @param endNode Zielknoten
	 * @return Liste des kuerzesten Weges zwischen Start- und Zielknoten
	 * @throws IllegalStateException
	 * @throws NoWayException
	 */
	public static List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws IllegalStateException, NoWayException {
		Long startTime = System.nanoTime();
		List<GKAVertex> resultWay = new ArrayList<>();
		long hitcount = 0;
		boolean searchSuccessful = false;
		
		
		// Precondition TODO DOC
		// Graph muss gewichtet sein
		if (!g.isWeighted()) { hitcount++;
			throw new IllegalStateException("Der Graph muss gewichtet sein.\n" + "g.isWeighted() returned false");
		}
		
		
		
		
		if (startNode == endNode) { // Falls Start- und Zielknoten identisch sind, kann abgebrochen werden.
			System.out.println("Startknoten == Zielknoten.");
			g.sendStats(("Dijkstra"), String.valueOf((System.nanoTime() - startTime) / 1E6D),"s==t", String.valueOf(hitcount));
			return Arrays.asList(startNode);
		}
		
		

		
		// Set aller zu besuchenden Knoten (wird aufgebraucht)
		Set<GKAVertex> nodes = new HashSet<>(g.getGraph().vertexSet()); hitcount++;
		
		
		
		/* Vorarbeiten 1
		 * Jede Distanz auf unendlich bzw. Integer.max setzen
		 * (Alle Knoten unbesucht setzen und alle Vorgaenger null setzen => durch GKAVertex impl. bereits erfolgt)
		 */
		hitcount++; for (GKAVertex v : g.getGraph().vertexSet()) {
			v.setWeight(Integer.MAX_VALUE);
		}
		
		
		
		
		/* Vorarbeiten 2
		 * Vorgaenger vom Startknoten auf sich selbst setzen
		 * Distanz vom Startknoten auf 0 setzen
		 * Startknoten als besucht markieren (technisch gesehen, erst @Zeile )
		 */
		startNode.setWeight(0);
		startNode.setParent(startNode);
		
		
		
		GKAVertex currentNode = startNode; // Aktuellen Knoten setzen - am Anfang der Startknoten

		
		
		// Hauptschleife
		do {
			// Unbesuchten Knoten mit der aktuell geringsten Distanz holen und auf besucht setzen
			// bzw. aus dem Set nodes entfernen
			currentNode = minNode(nodes);
			nodes.remove(currentNode);
			
			
			
			
			// Set bilden, in dem alle, ausser den bereits besuchten, Nachbarknoten enthalten sind
			Set<GKAVertex> unvisitedAdjacents = new HashSet<>(g.getAllAdjacentsOf(currentNode)); hitcount++; // zuerst alle Adjazenten speichern
			unvisitedAdjacents.retainAll(nodes); // danach bereits besuchte Adjazenten rausschmeissen (es bleiben die, die noch in nodes ent
			

			
			
			for (GKAVertex adj : unvisitedAdjacents) { // Fuer alle unbesuchten Nachbarn des aktuellen Knotens:
				
				// eigene Distanz + Kantengewicht der inzidenten Kante (currentNode <--> Nachbarn) addieren
				GKAEdge adjEdge = g.getEdge(currentNode, adj); hitcount++;
				Integer distSum;
				if (adjEdge != null) {
					distSum = currentNode.getWeight() + adjEdge.getWeight();
				} else {
					throw new NullPointerException("Kante wurde nicht gefunden.\n ==> " + currentNode + " -- " + adj);
				}
				
				
				
				if (adj.getWeight() > distSum) { // Falls Distanz-Summe niedriger, als die aktuelle Distanz des Nachbarn
					adj.setWeight(distSum); 	 // Distanz des Nachbarn aktualisieren
					adj.setParent(currentNode);  // und aktuellen Knoten als Vorgaenger des Nachbarn setzen
				}
			}
			
			
			
			if (currentNode == endNode) {   // Zu currentNode wurden zu diesem Zeitpunkt alle inzidenten Kanten untersucht
				nodes.clear();				// Ist currentNode gleichzeitig das Ziel, kann abgebrochen werden
				searchSuccessful = true;	// Flag setzen, dass Suche erfolgreich war
			}
			
			
		} while (!nodes.isEmpty()); // Solange es noch unbesuchte Knoten gibt
		
		
		
		// Weg ueber alle Vorgaenger rekonstruieren
		while (searchSuccessful && currentNode != null && currentNode.getParent() != currentNode) {
			resultWay.add(currentNode);
			currentNode = currentNode.getParent();
		}
		// die Schleife wird verlassen, sobald man am Startknoten
		// angekommen ist (currentNode == currentNode.getParent())
		
		
		
		
		if (currentNode == null) // Existiert kein Weg, wird irgendwann der Parent 'null' auftreten und currentNode 'null' gesetzt.
			throw new NoWayException(startNode, endNode);
		
		resultWay.add(currentNode);
		// dann muss abschliessend der Startknoten manuell der Wegliste hinzugefuegt werden,
		// da dies, durch die Abbruchbedingung in der Schleife, nicht mehr geschieht.
		
		
		
		
		// Stats an die GUI melden
		g.sendStats("Dijkstra", String.valueOf((System.nanoTime() - startTime) / 1E6D), String.valueOf(resultWay.size() - 1), String.valueOf(hitcount));
		
		// Da der Weg ueber die Vorgaenger rekonstruiert wurde,
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
				if (minNode.getWeight() > v.getWeight())
					minNode = v;
			}
		}
		return minNode;
	}
}