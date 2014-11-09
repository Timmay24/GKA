package main.graphs;

import java.util.HashMap;
import java.util.Map;

public class Algorithms {
	
	public static Object getAlgorithm(Integer code) {
		Map<Integer, Object> algorithms = new HashMap<>();
		algorithms.put(0, BFS.class);
		algorithms.put(1, Dijkstra.class);
		algorithms.put(2, FloydWarshall.class);
		return algorithms.get(code);
	}

}
