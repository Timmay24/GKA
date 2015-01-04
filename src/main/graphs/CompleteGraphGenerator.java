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
		this(g, graphType, desiredVertexCount, 1, 10);
	}
	
	public CompleteGraphGenerator(GKAGraph g, GraphType graphType, long desiredVertexCount, int minWeight, int maxWeight) {
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
		this.maxWeight = maxWeight;
		this.triangleInequalityBuffer = 1;
//		System.err.println("desiredVertexCount:" + desiredVertexCount);
	}

	@Override
	public void run() {
		final 	String 	PREFIX_EDGE = "e";
		final 	String 	PREFIX_VERTEX = "v";
				long	edgeIndex = 1;
				long	startTime = System.nanoTime();
				
		
		// Neuen Graph erzeugen
		g.newGraph(graphType);
		
		//TODO
		// Abfangen, wenn weniger als 2 Knoten gewuenscht werden, dass trotzdem mindestens 2 Knoten erstellt werden
		
				
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
		
		/////BERECHNUNG DER KANTENGEWICHTE INNERHALB MATRIX/////
		// Matrix fuer Kantengewichte
		Matrix<GKAVertex, GKAVertex, Integer> edgeWeights = new Matrix<>(vertices, vertices);
		
		//Erste Zeile für ersten Knoten mit Random Zahlen füllen
		for (GKAVertex vertex : vertices) {
			edgeWeights.setValueAt(vertices.get(0), vertex, getRandomWeight());
		}

		int row = 1;
		
		while (row < vertices.size()) {
			
			for (int i = row; i < vertices.size(); i++) {
				int src_opt = edgeWeights.getValueAt(vertices.get(row-1), vertices.get(row));
				int src_tgt = edgeWeights.getValueAt(vertices.get(row-1), vertices.get(i));
				int newEdgeWeight = Math.abs(src_tgt - src_opt) + 1;
				
				edgeWeights.setValueAt(vertices.get(row), vertices.get(i), newEdgeWeight);
			}
			
			row++;
		}
		
		
		System.err.println(edgeWeights);
		
		
		
		while (!vertices.isEmpty()) {
			GKAVertex source = vertices.remove(0);
			
			for (GKAVertex target : vertices) {
				int edgeWeight = edgeWeights.getValueAt(source, target);
				
				g.addEdgeWithoutChecks(source, target, PREFIX_EDGE + edgeIndex++, edgeWeight);
//				g.addEdge(source, target, PREFIX_EDGE + edgeIndex++, newEdgeWeight);
				g.sendMessage("/pbupdate " + edgeIndex);
			}
//			vertices.clear();
		}
		
		
		long actualEdgeCount = g.getGraph().edgeSet().size();
//		System.err.println("targetEdgeCount: " + targetEdgeCount);
//		System.err.println("actualEdgeCount: " + actualEdgeCount);
		
		g.sendMessage("/pbend");
		g.sendMessage("/gpshow");
		g.sendMessage("ERFOLG: Vollständiger Graph generiert in " + String.valueOf((System.nanoTime() - startTime) / 1E9D) + " Sekunden.");
		g.sendMessage("Kanten: " + actualEdgeCount);
		g.setLayout();
	}
	
//	List<Integer> mock = Arrays.asList(5,5,4,6,2);
//	int mi = 0;
	
	private int getRandomWeight() {
//		return mock.get((mi++ % mock.size()));
		return (int) (Math.random() * (maxWeight - minWeight) + minWeight);
	}
	
}
