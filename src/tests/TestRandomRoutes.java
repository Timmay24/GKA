package tests;

import java.util.ArrayList;
import java.util.List;

import main.graphs.CompleteGraphGenerator;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.algorithms.tsp.MSTHeuristicTour;
import main.graphs.algorithms.tsp.NearestNeighbourHeuristicSearcher;

import org.junit.Test;

public class TestRandomRoutes {

	private int run = 1;
	
	@Test
	public void testRandomRoutes() {
		
		// 25 Knoten
		for (int i = 0; i < 10; i++) {
			doIteration(25, 5);
		}
		run = 1;
		// 50 Knoten
		for (int i = 0; i < 10; i++) {
			doIteration(50, 5);
		}
		run = 1;
		// 100 Knoten
		for (int i = 0; i < 10; i++) {
			doIteration(100, 5);
		}
	}
	
	private void doIteration(int vertexCount, int minWeight) {
		// Zufälligen Graphen generieren und...
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		new CompleteGraphGenerator(g, g.getGraphType(), vertexCount, minWeight).run();
		// ...Startknoten auswählen
		GKAVertex startNode = new ArrayList<GKAVertex>(g.getGraph().vertexSet()).get((int)(Math.random() * g.getGraph().vertexSet().size()));
		
		MSTHeuristicTour msth = new MSTHeuristicTour();
		msth.getTour(g, startNode);
		List<GKAVertex> resultMSTH = msth.getTourList();
		long runtimeMSTH = (long) (msth.getRuntime() / 1E6D);
		int lengthMSTH = msth.getTourLength();
		String wayMSTH = resultMSTH.toString();
		int lengthMST = msth.getMSTLength();
		
		NearestNeighbourHeuristicSearcher nnhs = new NearestNeighbourHeuristicSearcher();
		nnhs.injectReferences(g, startNode);
		nnhs.run();
		List<GKAVertex> resultNNHS = nnhs.getRoute();
		long runtimeNNHS = (long) (nnhs.getRuntime() / 1E6D);
		int lengthNNHS = nnhs.getRouteLength();
		String wayNNHS = resultNNHS.toString();
		
		System.out.println("### Durchlauf " + run++);
		System.out.println("Knotenanzahl: " + vertexCount);
		System.out.println("MST Tour: " + wayMSTH);
		System.out.println("NN  Tour: " + wayNNHS);
		System.out.println("----------------------------------");
		System.out.println("             MST | NN");
		System.out.println("Tourlänge:   " + gl(lengthMSTH, lengthNNHS));
		System.out.println("Länge MSTx2: " + lengthMST * 2);
		System.out.println("Laufzeit:    " + gl(runtimeMSTH, runtimeNNHS) + " ms");
		System.out.println("--------------------------------------------------------------\n");
	}
	
	private String gl(long a, long b) {
		return (a >= b)?(a + " > " + b):(a + " < " + b);
	}
	
}