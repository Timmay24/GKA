package main.graphs.algorithms.flow;

import java.util.Stack;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public class FordFulkerson extends FlowCalculatorBase {

	/* (non-Javadoc)
	 * @see main.graphs.algorithms.flow.FlowCalculatorBase#getMaxFlow_(main.graphs.GKAGraph, main.graphs.GKAVertex, main.graphs.GKAVertex)
	 */
	@Override
	protected Integer getMaxFlow_(GKAGraph graph, GKAVertex sourceNode, GKAVertex sinkNode) {
		//TODO implement
		// Preconditions checked in super class
		
		Stack<GKAVertex> nodesToProcess = new Stack<GKAVertex>();
		
		return new Integer(maxFlow);
	}
	
}
