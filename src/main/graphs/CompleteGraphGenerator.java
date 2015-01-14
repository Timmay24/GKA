package main.graphs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class CompleteGraphGenerator implements Runnable {

	protected GKAGraph 	g;
	protected GraphType graphType;
	protected long		desiredVertexCount;
	protected int		minWeight;
	protected int		maxWeight;
	protected int		triangleInequalityBuffer;
	
	/**
	 * KONSTRUKTOR
	 * 
	 * @param g
	 * @param graphType
	 */
	public CompleteGraphGenerator(GKAGraph g, GraphType graphType, long desiredVertexCount) {
		this(g, graphType, desiredVertexCount, 5);
	}
	
	public CompleteGraphGenerator(GKAGraph g, GraphType graphType, long desiredVertexCount, int minWeight) {
		checkNotNull(g);
		checkNotNull(graphType);
		
		this.g = g;
		if (graphType.isDirected()) {
			this.graphType = GraphType.DIRECTED_WEIGHTED;
		} else {
			this.graphType = GraphType.UNDIRECTED_WEIGHTED;
		}
		this.desiredVertexCount = desiredVertexCount;
		this.minWeight = minWeight;
	}

	@Override
	public void run() {
		final 	String 	PREFIX_EDGE = "e";
		final 	String 	PREFIX_VERTEX = "v";
				long	edgeIndex = 1;
				long	startTime = System.nanoTime();
				
		
		// Neuen Graph erzeugen
		g.newGraph(graphType);
		
		// Abfangen, dass wenigstens 2 Knoten generiert werden
		if (desiredVertexCount < 2) {
			desiredVertexCount = 2;
			g.sendMessage("HINWEIS: Es wurden weniger als 2 Knoten gewuenscht - es werden 2 Knoten generiert.");
		}
				
		// Benötigte Anzahl Knoten hinzufügen
		for (long i = 0; i < desiredVertexCount; i++) {
			g.addVertexWithoutChecks(PREFIX_VERTEX + (i + 1));
		}
		
		List<GKAVertex> vertices = new ArrayList<>(g.getGraph().vertexSet());
		
		// Vorausrechnung der Anzahl von Kanten, die generiert werden
		long targetEdgeCount = (desiredVertexCount * (desiredVertexCount-1))/2;
		
		g.sendMessage("/gphide");
		// Ladebalken mit max Wert fuettern
		g.sendMessage("/pbinit " + targetEdgeCount);
		
		while (!vertices.isEmpty()) {
			// Nächsten, in der Liste stehenden, Knoten entnehmen,
			// von dem aus Kanten zu den verbleibenden Knoten generiert werden sollen
			GKAVertex source = vertices.remove(0);
			
			for (GKAVertex target : vertices) {
				int edgeWeight = getRandomWeight();
				
				g.addEdgeWithoutChecks(source, target, PREFIX_EDGE + edgeIndex++, edgeWeight);
				g.sendMessage("/pbupdate " + edgeIndex);
			}
		}
		
		long actualEdgeCount = g.getGraph().edgeSet().size();
		
		g.sendMessage("/pbend");
		g.sendMessage("/gpshow");
		g.sendMessage("ERFOLG: Vollständiger Graph generiert in " + String.valueOf((System.nanoTime() - startTime) / 1E9D) + " Sekunden.");
		g.sendMessage("Kanten: " + actualEdgeCount);
		g.setLayout();
	}
	
	private int getRandomWeight() {
		return (int) (Math.random() * minWeight + minWeight);
	}
	
}
