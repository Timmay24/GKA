package tests;

import static org.junit.Assert.*;
import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.algorithms.path.Dijkstra;
import main.graphs.algorithms.tsp.MinimumSpanningTreeCreator;
import main.graphs.algorithms.tsp.NearestNeighbourHeuristicSearcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestNearestNeighbourHeuristicSearcher {
	
	List<GKAVertex> shortestWays = new ArrayList<>();
	List<GKAVertex> shortestWays1 = new ArrayList<>();
	List<GKAVertex> shortestWays2 = new ArrayList<>();
	List<GKAVertex> shortestWays3 = new ArrayList<>();
	List<GKAVertex> shortestWays4 = new ArrayList<>();
	
	GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
	GKAGraph g1 = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
	
	GKAVertex a,b,c,d,e,f;
	GKAEdge ab,ac,ad,ae,af,
			bc,bd,be,bf,
			cd,ce,cf,
			de,df,
			ef;

	@Before
	public void setUp() throws Exception {
		
		
		g.addEdge("a", "b", "ab", 15);
		g.addEdge("a", "c", "ac", 20);
		g.addEdge("a", "d", "ad", 15);
		g.addEdge("a", "e", "ae", 11);
		g.addEdge("a", "f", "af", 18);
		g.addEdge("b", "c", "bc", 12);
		g.addEdge("b", "d", "bd", 10);
		g.addEdge("b", "e", "be", 14);
		g.addEdge("b", "f", "bf", 15);
		g.addEdge("c", "d", "cd", 22);
		g.addEdge("c", "e", "ce", 23);
		g.addEdge("c", "f", "cf", 24);
		g.addEdge("d", "e", "de", 21);
		g.addEdge("d", "f", "df", 24);
		g.addEdge("e", "f", "ef", 8);
		
		
		a = g.getVertex("a");
		b = g.getVertex("b");
		c = g.getVertex("c");
		d = g.getVertex("d");
		e = g.getVertex("e");
		f = g.getVertex("f");
		
		ab = g.getEdge("ab");
		ac = g.getEdge("ac");
		ad = g.getEdge("ad");
		ae = g.getEdge("ae");
		af = g.getEdge("af");
		bc = g.getEdge("bc");
		bd = g.getEdge("bd");
		be = g.getEdge("be");
		bf = g.getEdge("bf");
		cd = g.getEdge("cd");
		ce = g.getEdge("ce");
		cf = g.getEdge("cf");
		de = g.getEdge("de");
		df = g.getEdge("df");
		ef = g.getEdge("ef");
	}

	
	@Test
	public void testNearestNeighbourGraphG() {
		
		shortestWays.add(a);
		shortestWays.add(e);
		shortestWays.add(f);
		shortestWays.add(b);
		shortestWays.add(d);
		shortestWays.add(c);
		shortestWays.add(a);
		
		for (int i = 0; i < 100; i++) {
			assertEquals(shortestWays, g.findRoute(a));
		}
		
		shortestWays1.add(b);
		shortestWays1.add(d);
		shortestWays1.add(a);
		shortestWays1.add(e);
		shortestWays1.add(f);
		shortestWays1.add(c);
		shortestWays1.add(b);
		
		assertEquals(shortestWays1, g.findRoute(b));
		
		shortestWays2.add(e);
		shortestWays2.add(f);
		shortestWays2.add(b);
		shortestWays2.add(d);
		shortestWays2.add(a);
		shortestWays2.add(c);
		shortestWays2.add(e);
		
		assertEquals(shortestWays2, g.findRoute(e));
		
	}
	
	
	
		
		

}
