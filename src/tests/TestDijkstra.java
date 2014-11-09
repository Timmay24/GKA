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
	
	List<List<GKAVertex>> shortestWays;

	@Before
	public void setUp() throws Exception {
		shortestWays = new ArrayList<>();
	}

	@Ignore
	@Test
	public void testFindShortestWay() {
		GKAGraph graph = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		GKAVertex a = GKAVertex.valueOf("a");
		GKAVertex b = GKAVertex.valueOf("b");
		GKAVertex c = GKAVertex.valueOf("c");
		GKAVertex d = GKAVertex.valueOf("d");
		GKAVertex e = GKAVertex.valueOf("e");
		GKAVertex f = GKAVertex.valueOf("f");
		
		GKAEdge vAB = GKAEdge.valueOf("vAB", 4);
		GKAEdge vAC = GKAEdge.valueOf("vAC", 2);
		GKAEdge vAE = GKAEdge.valueOf("vAE", 4);
		GKAEdge vBC = GKAEdge.valueOf("vBC", 1);
		GKAEdge vCD = GKAEdge.valueOf("vCD", 3);
		GKAEdge vCE = GKAEdge.valueOf("vCE", 5);
		GKAEdge vDF = GKAEdge.valueOf("vDF", 2);
		GKAEdge vEF = GKAEdge.valueOf("vEF", 1);
		
		graph.addEdge(a, b, vAB);
		graph.addEdge(a, c, vAC);
		graph.addEdge(a, e, vAE);
		graph.addEdge(b, c, vBC);
		graph.addEdge(c, d, vCD);
		graph.addEdge(c, e, vCE);
		graph.addEdge(d, f, vDF);
		graph.addEdge(e, f, vEF);
		
		shortestWays.add(Arrays.asList(a,e,f));
		
		GKAVertex startNode = a;
		GKAVertex endNode = f;
		
		assertEquals(Arrays.asList(a,e,f), Dijkstra.findShortestWay(graph, startNode, endNode));
	}
	
	@Test
	public void testFindShortestWay2() {
		GKAGraph graph = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		GKAVertex q = GKAVertex.valueOf("q");
		GKAVertex w = GKAVertex.valueOf("w");
		GKAVertex s = GKAVertex.valueOf("s");
		GKAVertex o = GKAVertex.valueOf("o");
		GKAVertex p = GKAVertex.valueOf("p");
		GKAVertex l = GKAVertex.valueOf("l");
		
		GKAEdge qw = GKAEdge.valueOf("qw", 2);
		GKAEdge qs = GKAEdge.valueOf("qs", 1);
		GKAEdge ws = GKAEdge.valueOf("ws", 3);
		GKAEdge sp = GKAEdge.valueOf("sp", 5);
		GKAEdge so = GKAEdge.valueOf("so", 2);
		GKAEdge sl = GKAEdge.valueOf("sl", 8);
		GKAEdge pl = GKAEdge.valueOf("pl", 1);
		
		graph.addEdge(q, w, qw);
		graph.addEdge(q, s, qs);
		graph.addEdge(w, s, ws);
		graph.addEdge(s, p, sp);
		graph.addEdge(s, o, so);
		graph.addEdge(s, l, sl);
		graph.addEdge(p, l, pl);
		
		GKAVertex startNode = w;
		GKAVertex endNode = l;
		
		assertEquals(Arrays.asList(w,s,p,l), Dijkstra.findShortestWay(graph, startNode, endNode));
		
		startNode = q;
		endNode = l;
		
		assertEquals(Arrays.asList(q,s,p,l), Dijkstra.findShortestWay(graph, startNode, endNode));
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
