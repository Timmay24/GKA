package main.graphs.algorithms.flow;

import java.util.LinkedList;
import java.util.Queue;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public class EdmondKarp extends FlowCalculatorBase {

	/**
	 * Standart-Konstruktor
	 */
	public EdmondKarp() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.flow.FlowCalculatorBase#getMaxFlow_(main.graphs.GKAGraph, main.graphs.GKAVertex, main.graphs.GKAVertex)
	 */
	@Override
	protected Integer getMaxFlow_(GKAGraph graph, GKAVertex sourceNode, GKAVertex sinkNode) throws RuntimeException {
		// Preconditions checked in super class
		
		Queue<GKAVertex> nodesToProcess = new LinkedList<GKAVertex>();
		
		
		return new Integer(maxFlow);
	}

}
