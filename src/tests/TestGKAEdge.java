package tests;

import static org.junit.Assert.*;
import main.graphs.GKAEdge;

import org.junit.Test;

public class TestGKAEdge {

	@Test
	public void testEquals() {
		GKAEdge e1 = GKAEdge.valueOf("test");
		GKAEdge e2 = GKAEdge.valueOf("test");
		GKAEdge e3 = GKAEdge.valueOf("test2");
		GKAEdge e4 = GKAEdge.valueOf("test3", 2);
		GKAEdge e5 = GKAEdge.valueOf("test3", 2);
		GKAEdge e6 = GKAEdge.valueOf("test3", null);
		GKAEdge e7 = GKAEdge.valueOf("test3", 3);
		
		assertEquals(e1,e2);
		assertEquals(e4,e5);
		assertNotEquals(e1, e3);
		assertNotEquals(e1, e4);
		assertNotEquals(e5, e6);
		assertNotEquals(e5, e7);
	}

}
