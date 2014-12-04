package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphGenerator;
import main.graphs.GraphType;
import main.graphs.algorithms.path.Dijkstra;
import main.graphs.algorithms.path.FloydWarshall;
import main.graphs.exceptions.NoWayException;

import org.junit.Ignore;
import org.junit.Test;

public class TestSamePath {
	
	@Ignore
	@Test
	public void testSamePath() throws IllegalArgumentException, NoWayException {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		new GraphGenerator(g, g.getGraphType(), 100, 6000).run();
		
		List<List<GKAVertex>> ways = new ArrayList<>();
		long time = System.nanoTime();
			
		for (int i = 0; i < 25; i++) { //TODO set back to 100 times
			ways.add(g.findShortestWay(new Dijkstra(), g.getVertex("v5"), g.getVertex("v49")));
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
	
	@Test
	public void testDeqFW() throws IllegalStateException, NoWayException {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		g.openGraph("./src/ressources/d.gka");
		
		List<GKAVertex> dijWay = new ArrayList<>();
		List<GKAVertex> floWay = new ArrayList<>();
		long time = System.nanoTime();
		
		dijWay = g.findShortestWay(new Dijkstra(), g.getVertex("a"), g.getVertex("e"));
		floWay = g.findShortestWay(new FloydWarshall(), g.getVertex("a"), g.getVertex("e"));
		
		assertEquals(dijWay, floWay);
		
		double timeElapsed = (System.nanoTime() - time) / 1E6D;
		System.err.println("Vergangene Zeit: " + timeElapsed + " Millisekunden.");
	}

}
