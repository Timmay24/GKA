package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.algorithms.path.Dijkstra;
import main.graphs.exceptions.NoWayException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestDijkstra {
	
	List<List<GKAVertex>> shortestWays;

	@Before
	public void setUp() throws Exception {
		shortestWays = new ArrayList<>();
	}

	
	@Test
	public void testFindShortestWay() throws IllegalArgumentException, NoWayException {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		g.addEdge("a", "b", "vAB", 4);
		g.addEdge("a", "c", "vAC", 2);
		g.addEdge("a", "e", "vAE", 4);
		g.addEdge("b", "c", "vBC", 1);
		g.addEdge("c", "d", "vCD", 3);
		g.addEdge("c", "e", "vCE", 5);
		g.addEdge("d", "f", "vDF", 2);
		g.addEdge("e", "f", "vEF", 1);
		
		GKAVertex a = g.getVertex("a");
		GKAVertex b = g.getVertex("b");
		GKAVertex c = g.getVertex("c");
		GKAVertex d = g.getVertex("d");
		GKAVertex e = g.getVertex("e");
		GKAVertex f = g.getVertex("f");
		
		assertNotNull(a);
		assertNotNull(b);
		assertNotNull(c);
		assertNotNull(d);
		assertNotNull(e);
		assertNotNull(f);
		
		GKAEdge vAB = g.getEdge("vAB");
		GKAEdge vAC = g.getEdge("vAC");
		GKAEdge vAE = g.getEdge("vAE");
		GKAEdge vBC = g.getEdge("vBC");
		GKAEdge vCD = g.getEdge("vCD");
		GKAEdge vCE = g.getEdge("vCE");
		GKAEdge vDF = g.getEdge("vDF");
		GKAEdge vEF = g.getEdge("vEF");
		
		assertNotNull(vAB);
		assertNotNull(vAC);
		assertNotNull(vAE);
		assertNotNull(vBC);
		assertNotNull(vCD);
		assertNotNull(vCE);
		assertNotNull(vDF);
		assertNotNull(vEF);
		
		shortestWays.add(Arrays.asList(a,e,f));
		
		GKAVertex startNode = a;
		GKAVertex endNode = f;
		
		assertEquals(Arrays.asList(a,e,f), g.findShortestWay(new Dijkstra(), startNode, endNode));
	}
	
	@Test
	public void testFindShortestWay2() throws IllegalArgumentException, NoWayException {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		g.addVertex("q");
		g.addVertex("w");
		g.addVertex("s");
		g.addVertex("o");
		g.addVertex("p");
		g.addVertex("l");
		
		GKAVertex q = g.getVertex("q");
		GKAVertex w = g.getVertex("w");
		GKAVertex s = g.getVertex("s");
		GKAVertex o = g.getVertex("o");
		GKAVertex p = g.getVertex("p");
		GKAVertex l = g.getVertex("l");
		
		g.addEdge("q", "w", "qw", 2);
		g.addEdge("q", "s", "qs", 1);
		g.addEdge("w", "s", "ws", 3);
		g.addEdge("s", "p", "sp", 5);
		g.addEdge("s", "o", "so", 2);
		g.addEdge("s", "l", "sl", 8);
		g.addEdge("p", "l", "pl", 1);
		
		GKAEdge qw = g.getEdge("qw");
		GKAEdge qs = g.getEdge("qs");
		GKAEdge ws = g.getEdge("ws");
		GKAEdge sp = g.getEdge("sp");
		GKAEdge so = g.getEdge("so");
		GKAEdge sl = g.getEdge("sl");
		GKAEdge pl = g.getEdge("pl");
		
		GKAVertex startNode = w;
		GKAVertex endNode = l;
		
		assertEquals(Arrays.asList(w,s,p,l), g.findShortestWay(new Dijkstra(), startNode, endNode));
		
		startNode = q;
		endNode = l;
		
		assertEquals(Arrays.asList(q,s,p,l), g.findShortestWay(new Dijkstra(), startNode, endNode));
//		assertEquals(Arrays.asList(q,s,p,l), new Dijkstra().findShortestWay(g, startNode, endNode));
		
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
