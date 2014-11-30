package main.graphs.algorithms.flow;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.algorithms.interfaces.FlowCalculator;

public class FordFulkerson implements FlowCalculator {

	@Override
	public Integer getMaxFlow(GKAGraph graph, GKAVertex sourceNode, GKAVertex sinkNode) throws RuntimeException {
		return new Integer(0);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
