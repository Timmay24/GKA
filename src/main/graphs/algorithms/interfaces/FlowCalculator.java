package main.graphs.algorithms.interfaces;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public interface FlowCalculator {
	
	public Integer getMaxFlow(GKAGraph graph, GKAVertex sourceNode, GKAVertex sinkNode) throws RuntimeException;
	
	@Override
	public String toString();

}
