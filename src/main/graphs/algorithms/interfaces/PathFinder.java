package main.graphs.algorithms.interfaces;

import java.util.List;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public interface PathFinder extends Runnable {
	
	public void injectReferences(GKAGraph g, GKAVertex startNode, GKAVertex endNode);
	
	@Override
	public String toString();
	
	@Override
	public void run();
	
	public List<GKAVertex> getResultWay();
	
}