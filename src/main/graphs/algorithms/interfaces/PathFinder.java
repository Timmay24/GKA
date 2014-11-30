package main.graphs.algorithms.interfaces;

import java.util.ArrayList;
import java.util.List;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public interface PathFinder {
	
	List<GKAVertex> resultWay = new ArrayList<GKAVertex>();

	public List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws RuntimeException;
	
	@Override
	public String toString();
	
}