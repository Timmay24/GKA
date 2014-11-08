package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.graphs.Dijkstra;
import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestDijkstra {
	
	GKAGraph graph;
	GKAVertex a,b,c,d,e,f;
	GKAEdge vAB, vAC, vAE, vBC, vCD, vCE, vDF, vEF;
	List<List<GKAVertex>> shortestWays;

	@Before
	public void setUp() throws Exception {
		
		graph = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		shortestWays = new ArrayList<>();
		
		a = GKAVertex.valueOf("a");
		b = GKAVertex.valueOf("b");
		c = GKAVertex.valueOf("c");
		d = GKAVertex.valueOf("d");
		e = GKAVertex.valueOf("e");
		f = GKAVertex.valueOf("f");
		
		vAB = GKAEdge.valueOf("vAB", 4);
		vAC = GKAEdge.valueOf("vAC", 2);
		vAE = GKAEdge.valueOf("vAE", 4);
		vBC = GKAEdge.valueOf("vBC", 1);
		vCD = GKAEdge.valueOf("vCD", 3);
		vCE = GKAEdge.valueOf("vCE", 5);
		vDF = GKAEdge.valueOf("vDF", 2);
		vEF = GKAEdge.valueOf("vEF", 1);
		
		graph.addEdge(a, b, vAB);
		graph.addEdge(a, c, vAC);
		graph.addEdge(a, e, vAE);
		graph.addEdge(b, c, vBC);
		graph.addEdge(c, d, vCD);
		graph.addEdge(c, e, vCE);
		graph.addEdge(d, f, vDF);
		graph.addEdge(e, f, vEF);
		
		shortestWays.add(Arrays.asList(a,e,f));
	}

	@Test
	public void testFindShortestWay() {
		GKAVertex startNode = a;
		GKAVertex endNode = f;
		
		List<GKAVertex> shortestWay = Arrays.asList(a,e,f);
		
//		assertTrue(shortestWays.contains(Dijkstra.findShortestWay(graph, startNode, endNode)));
		assertEquals(shortestWay, Dijkstra.findShortestWay(graph, startNode, endNode));
	}
	
	
	@Test
	public void testMinNode() {
		Set<GKAVertex> nodes = new HashSet<>();
		
		GKAVertex minNode = GKAVertex.valueOf("min", 2);
		
		nodes.add(GKAVertex.valueOf("1",5));
		nodes.add(minNode); // min
		nodes.add(GKAVertex.valueOf("3",8));
		nodes.add(GKAVertex.valueOf("4",3));
		nodes.add(GKAVertex.valueOf("5",10));
		
		assertEquals(minNode, Dijkstra.minNode(nodes));
	}

}
