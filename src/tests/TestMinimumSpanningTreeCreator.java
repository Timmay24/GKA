package tests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.algorithms.tsp.MinimumSpanningTreeCreator;

import org.junit.Test;

public class TestMinimumSpanningTreeCreator {

	@Test
	public void testGetSortedListOf() {
		int id = 1;
		GKAEdge e1 = GKAEdge.valueOf("e" + id, 4, id++);
		GKAEdge e2 = GKAEdge.valueOf("e" + id, 8, id++);
		GKAEdge e3 = GKAEdge.valueOf("e" + id, 1, id++);
		GKAEdge e4 = GKAEdge.valueOf("e" + id, 2, id++);
		
		Set<GKAEdge> edges = new HashSet<>(Arrays.asList(e1,e2,e3,e4));
		MinimumSpanningTreeCreator mst = new MinimumSpanningTreeCreator();
		
		assertEquals(Arrays.asList(e3,e4,e1,e2), mst.getSortedListOf(edges));
	}
	
	@Test
	public void testApplyMinimumSpanningTree() {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		GKAVertex a,b,c,d,e,f;
		GKAEdge ab,ac,ad,ae,af,
				bc,bd,be,bf,
				cd,ce,cf,
				de,df,
				ef;
		
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
		
		new MinimumSpanningTreeCreator().applyMinimumSpanningTreeTo(g);
		
		Set<GKAEdge> desiredEdges = new HashSet<>(Arrays.asList());
		
		
	}

}
