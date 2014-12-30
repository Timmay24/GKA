package main.graphs.algorithms.interfaces;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public interface FlowCalculator extends GKAAlgorithm {
	
	/**
	 * Berechnet fuer graph den MaxFlow (maximalen Fluss) zwischen Quelle
	 * (sourceNode) und Senke (sinkNode).
	 * 
	 * @param graph
	 *            Graph, in dem der Fluss berechnet werden soll
	 * @param sourceNode
	 *            Knoten, der als Quelle fungiert
	 * @param sinkNode
	 *            Knoten, der als Senke fungiert
	 * @return Der maximale Fluss zwischen Quelle (sourceNode) und Senke
	 *         (sinkNode)
	 * @throws RuntimeException
	 *            falls ein Laufzeitfehler eintritt //TODO: konkretisieren,
	 *            wenn klar, welche Fehlertypen geworfen werden koennen.
	 *            
	 * @pre graph darf nicht null sein, muss gerichtet und gewichtet sein
	 *      	  ({@code isDirected() == true}
	 *      	   {@code isWeighted() == true})
	 *      
	 * @pre sourceNode darf nicht null sein
	 * 
	 * @pre sinkNode darf nicht null sein
	 */
	public Integer getMaxFlow(GKAGraph graph, GKAVertex sourceNode, GKAVertex sinkNode) throws RuntimeException;
	
	/**
	 * @return Liefert den MaxFlow Wert aus dem letzten Berechnungsdurchgang
	 */
	public Integer getMaxFlow();

}
