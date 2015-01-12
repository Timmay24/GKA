package main.graphs.algorithms.interfaces;

import java.util.List;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public interface TSPAlgorithm extends GKAAlgorithm, Runnable {
	
	public void injectReferences(GKAGraph g, GKAVertex startNode);
	
	public List<GKAVertex> getRoute();
	
}
