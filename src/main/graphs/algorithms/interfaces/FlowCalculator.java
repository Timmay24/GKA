package main.graphs.algorithms.interfaces;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public interface FlowCalculator {
	
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
	 * @pre graph darf nicht null sein und muss gerichtet sein
	 *      	  ({@code isDirected() == true})
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

	/**
	 * Startet die Messung der Laufzeit des Algorithmus
	 */
	public void startTimeMeasurement();
	
	/**
	 * Stoppt die Messung der Laufzeit des Algorithmus und berechnet die Differenz zwischen Start- und Endzeit
	 */
	public void stopTimeMeasurement();
	
	/**
	 * @return Die benoetigte Rechenzeit fuer den Algorithmus
	 */
	public long getRuntime();

	/**
	 * @return Anzahl Zugriffe auf den Graphen bei der Nutzung des Algorithmus
	 */
	public long getHitCounter();
	
	/**
	 * @return true, wenn Algorithmus noch arbeitet und Berechnung noch nicht abgeschlossen ist
	 */
	public boolean isRunning();
	
	/**
	 * @return Bezeichnung der Algorithmus-Klasse
	 */
	@Override
	public String toString();

}
