package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import main.graphs.GKAEdge;
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

}
