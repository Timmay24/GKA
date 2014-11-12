package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import main.graphs.Dijkstra;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphGenerator;
import main.graphs.GraphType;
import main.graphs.exceptions.NoWayException;

import org.junit.Test;

public class TestSamePath {
	
	@Test
	public void testSamePath() throws IllegalArgumentException, NoWayException {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		new GraphGenerator(g, g.getGraphType(), 100, 6000).run();
		
		List<List<GKAVertex>> ways = new ArrayList<>();
		long time = System.nanoTime();
			
		for (int i = 0; i < 100; i++) {
			ways.add(Dijkstra.findShortestWay(g, g.getVertex("v5"), g.getVertex("v49")));
		}
		
		for (List<GKAVertex> way : ways) {
			for (List<GKAVertex> way2 : ways) {
				if (!way.equals(way2)) 	// falls zwei Wege mal nicht übereinstimmen
					fail();				// Test fehlschlagen lassen
			}
		}
		
		assertTrue(true);				// Laeuft der Test bis hier hin durch, sind alle Wege gleich.
		
		double timeElapsed = (System.nanoTime() - time) / 1E9D;
		System.err.println("Vergangene Zeit: " + timeElapsed + " Sekunden.");
	}

}
