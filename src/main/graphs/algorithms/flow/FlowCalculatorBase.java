package main.graphs.algorithms.flow;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.Matrix;
import main.graphs.algorithms.interfaces.Batch;
import main.graphs.algorithms.interfaces.FlowCalculator;

public abstract class FlowCalculatorBase implements FlowCalculator {
	
	// Fuer Laufzeitmessung
	protected long startTime = 0;
	protected long timeElapsed = 0;
	protected long hc = 0;
	protected boolean running = false;
	
	protected Matrix<GKAVertex, GKAVertex, Integer> capacities;
	protected Matrix<GKAVertex, GKAVertex, Integer> currentFlows;
	
	protected Batch<GKAVertex> nodesToProcess;
	
	protected GKAGraph _graph; // Hilfsvariable mit Referenz auf den Arbeitsgraphen
	
	protected int maxFlow = 0;
	
	
	/**
	 * Standart-Konstruktor, der von jeder Kindklasse zuerst aufgerufen werden muss,
	 * um den Container der abzuarbeitenden Knoten zu initialisieren
	 */
	protected FlowCalculatorBase(Batch<GKAVertex> nodesToProcessContainer) {
		this.nodesToProcess = nodesToProcessContainer;
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#getMaxFlow(main.graphs.GKAGraph, main.graphs.GKAVertex, main.graphs.GKAVertex)
	 */
	@Override
	public Integer getMaxFlow(GKAGraph graph, GKAVertex sourceNode, GKAVertex sinkNode) throws RuntimeException {
		checkNotNull(graph);
		checkState(graph.isDirected(), "Der Graph muss gerichtet sein.");
		checkState(graph.isWeighted(), "Der Graph muss gewichtet sein.");
		checkNotNull(sourceNode);
		checkNotNull(sinkNode);
		
		hc = 0;			// Hitcouter Zaehlvariable
		maxFlow = 0;	// Akkumulator fuer das Endergebnis
		_graph = graph;	// Referenz auf den Graphen fuer Hilfsfunktionen
		
		startTimeMeasurement();
		
		// Falls Quelle und Senke identisch sind => "unbegrenzter" Fluss
		if (sourceNode.equals(sinkNode)) {
			stopTimeMeasurement();
			return Integer.MAX_VALUE;
		}
		
		
		  /////////////////////
		 // Initialisierung //
		/////////////////////
		
		// Alle vorhandenen Knoten aus dem Graphen holen 
		Set<GKAVertex> vertices = new HashSet<>(graph.getGraph().vertexSet());
		hc++;
		
		// Matrizen einrichten //
		// Kapazitaeten zwischen adjazenten Knoten berechnen
		capacities = new Matrix<>();
		for (GKAVertex source : vertices) {
			for (GKAVertex sink : vertices) {
				capacities.setValueAt(source, sink, getTotalCapacityBetween(source, sink));
			}
		}
		
		System.out.println(capacities); //debug
		
		// Matrix, die aktuelle Fluesse halten soll, mit Nullen fuellen
		currentFlows = new Matrix<>(vertices, vertices, 0);
		
		// Falls aus der Quelle heraus kein Fluss entstehen kann => Abbruch, kein Fluss moeglich
		if (getAccessibleAdjacentsFrom(sourceNode).isEmpty()) {
			stopTimeMeasurement();
			return 0;
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// "Messschleife", bei der die ergebenden Fluesse aufaddiert werden,
		// solange welche gefunden werden (resultFlow > 0)
		int resultFlow = 0;
		do {
			resultFlow = getMaxFlow_(sourceNode, sinkNode);
			maxFlow += resultFlow;
		} while (resultFlow > 0);

		stopTimeMeasurement();
		
		return maxFlow;
	}
	
	/**
	 * Unteraufruf von getMaxFlow, in dem der eigentliche Algorithmus implementiert wird.
	 * Die Funktion liefert den, fuer die aktuelle Belegung der currentFlows-Matrix (aktuelle Fluesse),
	 * maximalen Fluss zwischen Quelle und Senke.
	 * Wenn kein Fluss (mehr) ermittelt werden kann, liefert die Funktion 0 zurueck.
	 * 
	 * @param graph Graph, in dem gesucht wird
	 * @param sourceNode Quelle
	 * @param sinkNode Senke
	 * @return Den ermittelten maximalen Fluss fuer die aktuellen Belegung der Aktuell-Flussmatrix (currentFlows)
	 * 		   (maxFlow == 0: kein Fluss moeglich)
	 */
	protected Integer getMaxFlow_(GKAVertex sourceNode, GKAVertex sinkNode) throws RuntimeException {
		
		int _maxFlow = Integer.MAX_VALUE;
		
		Map<GKAVertex, GKAVertex> parentMap = new HashMap<>();
		List<GKAVertex> processedNodes = new ArrayList<>();
		
		nodesToProcess.clear();
		
		nodesToProcess.add(sourceNode);
		
		// Hauptschleife
		while (!nodesToProcess.isEmpty()) {
			GKAVertex activeNode = nodesToProcess.remove();
			processedNodes.add(activeNode);
			
			// Fuer alle benachbarten Knoten, zu denen es noch Restkapazitaet(en) gibt
			for (GKAVertex adjacentNode : getAccessibleAdjacentsFrom(activeNode)) {
				
				// Verbleibende Flusskapazitaet zwischen aktivem und benachbartem Knoten berechnen
				int maxInterFlow = getRemainingCapacityBetween(activeNode, adjacentNode);
				
				// Falls ein (zusaetzlicher) Fluss zwischen aktivem und benachbartem Knoten moeglich ist
				// und der benachbarte Knoten noch nicht abgearbeitet wurde
				if (maxInterFlow > 0 && !processedNodes.contains(adjacentNode)) {
					
					// Aktiven Knoten als Vorgaenger des benachbarten Knotens setzen
					parentMap.put(adjacentNode, activeNode);
					
					// Minimum aller Restkapazitaeten zwischen den,
					// auf dem Pfad liegenden, Knoten ermitteln
					_maxFlow = Integer.min(_maxFlow, maxInterFlow);
					
					// Wenn der Nachbarknoten nicht die Senke ist,
					if (adjacentNode != sinkNode) {
						nodesToProcess.add(adjacentNode); // dann den Nachbarknoten als naechsten zu bearbeitenden Knoten einreihen
					} else {
						// sonst: Solange es zu adjacentNode einen Vorgaenger gibt
						while (parentMap.get(adjacentNode) != null) {
							
							// Auf dem Rueckweg, das ermittelte Minimum aus allen Zwischenfluessen auf dem Pfad
							// zu allen auf dem Pfad liegenden aktuellen Fluessen aufaddieren
							currentFlows.setValueAt(
									parentMap.get(adjacentNode),
									adjacentNode,
									currentFlows.getValueAt(parentMap.get(adjacentNode), adjacentNode) + _maxFlow
									);
							
							// Rueckwaertskanten ebenfalls aktualisieren
							currentFlows.setValueAt(adjacentNode,
									parentMap.get(adjacentNode),
									currentFlows.getValueAt(adjacentNode, parentMap.get(adjacentNode)) - _maxFlow
									);
							
							// Vorgaenger durchruecken, um mit dem naechsten Iterationsschritt fortzufahren
							adjacentNode = parentMap.get(adjacentNode);
						}
						return _maxFlow;
					}
				}
			}
		}
		return 0;
	}
	
	
	/**
	 * Ermittelt den maximalen Fluss ueber alle Kanten zwischen zwei adjazenten Knoten
	 * 
	 * @param source Quell-Knoten
	 * @param sink Senken-Knoten
	 * @return Maximaler Fluss ueber alle Kanten zwischen zwei adjazenten Knoten
	 */
	protected Integer getTotalCapacityBetween(GKAVertex source, GKAVertex sink) {
		checkNotNull(_graph);
		
		int totalCapacity = 0;
		
		// Fuer alle ausgehenden Kanten von source
		for (GKAEdge edge : _graph.outgoingEdgesOf(source))
		{
			hc += _graph.getGraph().edgesOf(source).size();
			
			// Pruefen, ob das Target einer Kante der sink-Knoten ist
			if (sink.equals((GKAVertex) edge.getTarget()))
			{
				// Kapazitaet der Kante der Gesamtkapazitaet hinzufuegen
				totalCapacity += edge.getWeight();
			}
		}
		return totalCapacity;
	}
	
	/**
	 * Ermittelt die (Rest-)Kapazitaet zwischen zwei adjazenten Knoten
	 * 
	 * @param source Quelle
	 * @param sink Senke
	 * @return Restkapazitaet zwischen zwei adjazenten Knoten
	 */
	protected Integer getRemainingCapacityBetween(GKAVertex source, GKAVertex sink) {
		checkNotNull(capacities);
		checkNotNull(currentFlows);
		
		// Maximale Kapazitaet minus Aktueller Fluss
		return capacities.getValueAt(source, sink) - currentFlows.getValueAt(source, sink);
	}
	
	
	/**
	 * Ermittelt adjazente Knoten zu source, zu denen noch Restkapazitaeten fuer den Fluss vorhanden sind
	 * 
	 * @param source Quelle
	 * @return Set von adjazenten Knoten, zu denen noch Restkapazitaeten fuer den Fluss vorhanden sind
	 */
	protected Set<GKAVertex> getAccessibleAdjacentsFrom(GKAVertex source) {
		Set<GKAVertex> vertices = new HashSet<>();
		
		for (GKAVertex vertex : capacities.getColumnKeys()) {
			
			// Wenn noch Restkapazitaet zwischen beiden Knoten vorhanden ist
			if (getRemainingCapacityBetween(source, vertex) > 0) {
				// umgekehrten fall pruefen und ggf. negativen wert vom pos. abziehen
				// zur korrektur...
				
				// Zum benachbarten Knoten sind noch Restkapazitaeten uebrig => zum Set hinzufuegen
				vertices.add(vertex);
			}
		}
		return vertices;
	}
	
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#getMaxFlow()
	 */
	@Override
	public Integer getMaxFlow() {
		return this.maxFlow;
	}
	
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#startTimeMeasurement()
	 */
	@Override
	public void startTimeMeasurement() {
		startTime = System.nanoTime();
		running = true;
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#stopTimeMeasurement()
	 */
	@Override
	public void stopTimeMeasurement() {
		timeElapsed = System.nanoTime() - startTime;
		running = false;
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#getRuntime()
	 */
	@Override
	public long getRuntime() {
		return timeElapsed;
	}

	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#hitCounter()
	 */
	@Override
	public long getHitCounter() {
		return hc;
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return running;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
