package main.graphs;

import java.util.HashMap;
import java.util.Map;

import main.graphs.algorithms.interfaces.PathFinder;
import main.graphs.algorithms.path.BFS;
import main.graphs.algorithms.path.Dijkstra;
import main.graphs.algorithms.path.FloydWarshall;

public class Algorithms {
	
	public static PathFinder getAlgorithm(Integer code) {
		Map<Integer, PathFinder> algorithms = new HashMap<>();
		algorithms.put(0, new BFS());
		algorithms.put(1, new Dijkstra());
		algorithms.put(2, new FloydWarshall());
		return algorithms.get(code);
	}

}
