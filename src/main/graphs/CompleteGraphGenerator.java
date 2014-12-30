package main.graphs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;


public class CompleteGraphGenerator implements Runnable {

	protected GKAGraph 	g;
	protected GraphType graphType;
	protected long		desiredVertexCount;
	
	/**
	 * KONSTRUKTOR
	 * 
	 * @param g
	 * @param graphType
	 */
	public CompleteGraphGenerator(GKAGraph g, GraphType graphType, long desiredVertexCount) {
		checkNotNull(g);
		checkNotNull(graphType);
		
		this.g = g;
		if (graphType.isDirected()) {
			this.graphType = GraphType.DIRECTED_WEIGHTED;
		} else {
			this.graphType = GraphType.UNDIRECTED_WEIGHTED;
		}
		this.desiredVertexCount = desiredVertexCount;
		System.err.println("desiredVertexCount:" + desiredVertexCount);
	}

	@Override
	public void run() {
		final 	String 	PREFIX_EDGE = "e";
		final 	String 	PREFIX_VERTEX = "v";
				long	edgeIndex = 1;
				long	startTime = System.nanoTime();
				
				
		// Neuen Graph erzeugen
		g.newGraph(graphType);
				
		// Benotigte Anzahl Knoten hinzufuegen
		for (long i = 0; i < desiredVertexCount; i++) {
			g.addVertexWithoutChecks(PREFIX_VERTEX + (i + 1));
//			g.addVertex(PREFIX_VERTEX + (i + 1));
		}
		
		List<GKAVertex> vertices = new ArrayList<>(g.getGraph().vertexSet());
		
		long targetEdgeCount = (desiredVertexCount * (desiredVertexCount-1))/2;

		
		g.sendMessage("/gphide");
		// Ladebalken mit max Wert fuettern
		g.sendMessage("/pbinit " + targetEdgeCount);
		
		while (!vertices.isEmpty()) {
			GKAVertex source = vertices.remove(0);
			
			for (GKAVertex target : vertices) {
				int newEdgeWeight = getProperEdgeWeight(source, target);
				
				g.addEdgeWithoutChecks(source, target, PREFIX_EDGE + edgeIndex++, newEdgeWeight);
//				g.addEdge(source, target, PREFIX_EDGE + edgeIndex++, newEdgeWeight);
				g.sendMessage("/pbupdate " + edgeIndex);
			}
//			vertices.clear();
		}
		
		
		long actualEdgeCount = g.getGraph().edgeSet().size();
		System.err.println("targetEdgeCount: " + targetEdgeCount);
		System.err.println("actualEdgeCount: " + actualEdgeCount);
		
		g.sendMessage("/pbend");
		g.sendMessage("/gpshow");
		g.sendMessage("ERFOLG: Vollständiger Graph generiert in " + String.valueOf((System.nanoTime() - startTime) / 1E9D) + " Sekunden.");
		g.sendMessage("Kanten: " + actualEdgeCount);
		g.setLayout();
	}
	
	private int getProperEdgeWeight(GKAVertex source, GKAVertex target) {
		// mocked
		// Beruecksichtigung der Dreiecksungleichung noetig!
		return (int) (Math.random() * 19 + 1);
	}
	
}
