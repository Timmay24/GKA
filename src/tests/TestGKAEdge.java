package tests;

import static org.junit.Assert.*;
import main.graphs.GKAEdge;

import org.junit.Test;

public class TestGKAEdge {

	@Test
	public void testEquals() {
		GKAEdge e1 = GKAEdge.valueOf("test", 1);
		GKAEdge e2 = GKAEdge.valueOf("test", 1);
		GKAEdge e3 = GKAEdge.valueOf("test2", 2, 1);
		GKAEdge e4 = GKAEdge.valueOf("test3", 2, 2);
		GKAEdge e5 = GKAEdge.valueOf("test3", 2, 2);
		GKAEdge e6 = GKAEdge.valueOf("test3", null, 2);
		GKAEdge e7 = GKAEdge.valueOf("test3", 3, 2);
		
		assertEquals(e1,e2);
		assertEquals(e4,e5);
		assertNotEquals(e1, e3);
		assertNotEquals(e1, e4);
		assertNotEquals(e5, e6);
		assertNotEquals(e5, e7);
	}

}
