package main.graphs.algorithms.path;

import java.util.ArrayList;
import java.util.List;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.algorithms.interfaces.PathFinder;

public abstract class PathFinderBase implements PathFinder, Runnable {

	protected GKAGraph g;
	protected GKAVertex startNode;
	protected GKAVertex endNode;
	protected List<GKAVertex> resultWay = new ArrayList<GKAVertex>();
	
	protected abstract List<GKAVertex> findShortestWay() throws RuntimeException;

	@Override
	public void run() {
//		g.sendMessage("/pbindeterminate");
//		g.sendMessage("/pbshow");
//		g.sendMessage("/gphide");
		
		
		resultWay = findShortestWay();
	}
	
	@Override
	public void injectReferences(GKAGraph g, GKAVertex startNode, GKAVertex endNode) {
		this.g         = g;
		this.startNode = startNode;
		this.endNode   = endNode;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public List<GKAVertex> getResultWay() {
		return this.resultWay;
	}

}
