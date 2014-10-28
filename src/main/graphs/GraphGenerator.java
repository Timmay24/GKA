package main.graphs;


public class GraphGenerator {

	public static void createRandomGraph(GKAGraph graph, GraphType type, int desiredVertexCount, int desiredEdgeCount) {
		final String PREFIX_EDGE = "e";
		final String PREFIX_VERTEX = "v";
		
		// Neuen Graph erzeugen
		graph.newGraph(type);
		
	}
}
