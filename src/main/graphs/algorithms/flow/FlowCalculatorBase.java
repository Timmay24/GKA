package main.graphs.algorithms.flow;

import static com.google.common.base.Preconditions.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.Matrix;
import main.graphs.algorithms.interfaces.FlowCalculator;

public abstract class FlowCalculatorBase implements FlowCalculator {
	
	// Fuer Laufzeitmessung
	protected long startTime = 0;
	protected long timeElapsed = 0;
	protected long hitCounter = 0;
	protected boolean running = false;
	
	protected Matrix<GKAVertex, GKAVertex, Integer> capacities;
	protected Matrix<GKAVertex, GKAVertex, Integer> currentFlows;
	
	protected GKAGraph _graph; // Hilfsvariable mit Referenz auf den Arbeitsgraphen
	
	protected int maxFlow = 0;
	
	
	/**
	 * Standart-Konstruktor, der von jeder Kindklasse zuerst aufgerufen werden muss,
	 * damit die Initialisierung nicht in jeder Kindklasse von Hand gemacht werden muss
	 */
	protected FlowCalculatorBase() {
		initFields();
	}
	
	/**
	 * Initialisiert alle, zur Flussberechnung notwendige, Variablen
	 */
	protected void initFields() {
		//TODO Initialisierungen
		// mal sehen, ob das hier ueberhaupt gebraucht wird
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
		
		_graph = graph;
		
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
		
		// Matrizen einrichten //
		// Kapazitaeten zwischen adjazenten Knoten berechnen
		capacities = new Matrix<>(vertices, vertices); // evtl. ueberfluessige zeile, da for loop komplett fuellung uebernehmen sollte
		for (GKAVertex source : vertices) {
			for (GKAVertex sink : vertices) {
				capacities.setValueAt(source, sink, getCapacityBetween(source, sink));
			}
		}
		
		System.out.println(capacities);
		
		// Matrix, die aktuelle Fluesse halten soll, mit Nullen fuellen
		currentFlows = new Matrix<>(vertices, vertices, 0);
		
		// Falls aus der Quelle heraus kein Fluss entstehen kann => Abbruch, kein Fluss moeglich
		if (forwardingVerticesOf(sourceNode).isEmpty()) {
			stopTimeMeasurement();
			return 0;
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		// Aufruf, der die Ausfuehrung des Algorithmus, wie von der Kindklasse implementiert, anstoesst
		int resultFlow = 0;
		do {
			resultFlow = getMaxFlow_(sourceNode, sinkNode, Integer.MAX_VALUE);
			maxFlow += resultFlow;
		} while (resultFlow != 0);

		stopTimeMeasurement();
		
		return maxFlow;
	}
	
	/**
	 * Hilfsmethode, in der die Kindklasse den eigentlichen Algorithmus implementiert
	 * 
	 * @param graph
	 * @param sourceNode
	 * @param sinkNode
	 * @return
	 */
	protected abstract Integer getMaxFlow_(GKAVertex sourceNode, GKAVertex sinkNode, int maxFlow);
	
	
	/**
	 * Ermittelt den maximalen Fluss ueber alle Kanten zwischen zwei adjazenten Knoten
	 * 
	 * @param source Quell-Knoten
	 * @param sink Senken-Knoten
	 * @return Maximaler Fluss ueber alle Kanten zwischen zwei adjazenten Knoten
	 */
	protected Integer getCapacityBetween(GKAVertex source, GKAVertex sink) {
		checkNotNull(_graph);
		
		int totalCapacity = 0;
		
		// Fuer alle ausgehenden Kanten von source
		for (GKAEdge edge : _graph.outgoingEdgesOf(source))
		{
			// pruefen, ob das Target einer Kante der sink-Knoten ist
			if (sink.equals((GKAVertex) edge.getTarget()))
			{
				// Kapazitaet der Kante der Gesamtkapazitaet hinzufuegen
				totalCapacity += edge.getWeight();
			}
		}
		return totalCapacity;
	}
	
	/**
	 * Ermittelt die Restkapazitaet zwischen zwei adjazenten Knoten
	 * 
	 * @param source Quelle
	 * @param sink Senke
	 * @return Restkapazitaet zwischen zwei adjazenten Knoten
	 */
	protected Integer getRemainingCapacityBetween(GKAVertex source, GKAVertex sink) {
		checkNotNull(capacities);
		checkNotNull(currentFlows);
		
		return capacities.getValueAt(source, sink) - currentFlows.getValueAt(source, sink);
	}
	
	
	/**
	 * Ermittelt adjazente Knoten zu source, zu denen noch Restkapazitaeten fuer den Fluss vorhanden sind
	 * 
	 * @param source Quelle
	 * @return Set von adjazenten Knoten, zu denen noch Restkapazitaeten fuer den Fluss vorhanden sind
	 */
	protected Set<GKAVertex> forwardingVerticesOf(GKAVertex source) {
		Set<GKAVertex> vertices = new HashSet<>();
		
		for (GKAVertex vertex : capacities.getColumnKeys()) {
			
			// Wenn noch Restkapazitaet zwischen beiden Knoten vorhanden ist
			if (getRemainingCapacityBetween(source, vertex) > 0) {
				
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
		return hitCounter;
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
