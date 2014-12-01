package main.graphs.algorithms.flow;

import static com.google.common.base.Preconditions.*;

import java.util.HashSet;
import java.util.Set;

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
	
	protected int maxFlow = 0;
	
	
	/**
	 * Standart-Konstruktor, der von jeder Kindklasse zuerst aufgerufen werden muss,
	 * damit die Initialisierung nicht in jeder Kindklasse von Hand gemacht werden muss
	 */
	protected FlowCalculatorBase() {
		initFields();
	}
	
	/**
	 * Initialisiert alle, zur Flussberechnung notwendigen, Variablen
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
		checkNotNull(sourceNode);
		checkNotNull(sinkNode);
		
		startTimeMeasurement();
		
		Set<GKAVertex> vertices = new HashSet<>(graph.getGraph().vertexSet());
		
		capacities = new Matrix<>(vertices, vertices);	
		currentFlows = new Matrix<>(vertices, vertices, Integer.MAX_VALUE);
		
		// Aufruf, der die Ausfuehrung des Algorithmus, wie von der Kindklasse implementiert, anstoesst
		maxFlow = getMaxFlow_(graph, sourceNode, sinkNode);
		
		
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
	protected abstract Integer getMaxFlow_(GKAGraph graph, GKAVertex sourceNode, GKAVertex sinkNode);
	
	
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
