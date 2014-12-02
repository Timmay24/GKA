package tests;

import static org.junit.Assert.*;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.algorithms.flow.EdmondKarp;
import main.graphs.algorithms.flow.FordFulkerson;
import main.graphs.algorithms.interfaces.FlowCalculator;

import org.junit.Test;

public class TestEdmondKarp {
	
	// Example Graph from
	// http://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm#Example

	@Test
	public void testEdmondKarp() {
		GKAGraph graph = GKAGraph.valueOf();
		graph.openGraph("./src/ressources/wiki_flow_exmpl.gka");
		
		GKAVertex a = graph.getVertex("a");
		GKAVertex g = graph.getVertex("g");
		
		FlowCalculator edmond = new EdmondKarp();
		
		edmond.getMaxFlow(graph, a, g);
		
		assertEquals(new Integer(5), edmond.getMaxFlow());
		
	}
	
	@Test
	public void testFordFulkerson() {
		GKAGraph graph = GKAGraph.valueOf();
		graph.openGraph("./src/ressources/wiki_flow_exmpl.gka");
		
		GKAVertex a = graph.getVertex("a");
		GKAVertex g = graph.getVertex("g");
		
		FlowCalculator ford = new FordFulkerson();
		
		ford.getMaxFlow(graph, a, g);
		
		assertEquals(new Integer(5), ford.getMaxFlow());
		
	}

}
