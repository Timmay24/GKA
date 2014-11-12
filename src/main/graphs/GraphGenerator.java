package main.graphs;


public class GraphGenerator implements Runnable {

	GKAGraph g;
	GraphType graphType;
	int desiredVertexCount;
	int desiredEdgeCount;
	
	/**
	 * KONSTRUKTOR
	 * 
	 * @param g
	 * @param graphType
	 * @param desiredVertexCount
	 * @param desiredEdgeCount
	 */
	public GraphGenerator(GKAGraph g, GraphType graphType, int desiredVertexCount, int desiredEdgeCount) {
		this.g = g;
		if (graphType.isDirected()) {
			this.graphType = GraphType.DIRECTED_WEIGHTED;
		} else {
			this.graphType = GraphType.UNDIRECTED_WEIGHTED;
		}
		this.desiredVertexCount = desiredVertexCount;
		this.desiredEdgeCount = desiredEdgeCount;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		final 	String 	PREFIX_EDGE = "e";
		final 	String 	PREFIX_VERTEX = "v";
		final 	int		minWeight = 1;
				int		edgeIndex = 0;
				long	startTime = System.nanoTime();
		
		
		g.sendMessage("/gph");
		// Ladebalken mit max Wert fuettern
		g.sendMessage("/pbi " + desiredEdgeCount);
				
		// Neuen Graph erzeugen
		g.newGraph(graphType);
		
//		// 
//		for (int i = 0; i < desiredVertexCount; i++) {
//			g.addVertex(PREFIX_VERTEX + String.valueOf(i));
//		}
		
		/* Es folgt die Generierung der gewuenschten Anzahl von Knoten.
		 * Innenbegriffen ist die Generierung zweier Wege (damit auch tatsaechlich Wege existieren),
		 * die vorerst nicht zusammenhaengen.
		 */
		
		// 1. Weg (beginnend mit v0)
		for (int i = 0; i < (desiredVertexCount / 2) - 1; i++) {
			g.addEdge(PREFIX_VERTEX + String.valueOf(i), PREFIX_VERTEX + String.valueOf(i + 1), GKAEdge.valueOf(PREFIX_EDGE + String.valueOf(i), minWeight), false);
			edgeIndex++;
		}
		
		// 2. Weg (beginnend mit v(desiredVertexCount / 2))
		for (int i = (desiredVertexCount / 2); i < desiredVertexCount; i++) {
			g.addEdge(PREFIX_VERTEX + String.valueOf(i), PREFIX_VERTEX + String.valueOf(i + 1), GKAEdge.valueOf(PREFIX_EDGE + String.valueOf(i), minWeight), false);
			edgeIndex++;
		}
		
		// Zufaelliges Verknuepfen von Knoten, bis gewuenschte Kantenanzahl erreicht
		for (int i = edgeIndex; i < desiredEdgeCount; i++) {
			int target = (int) (Math.random() * (desiredVertexCount - 1));
			int source = (int) (Math.random() * (desiredVertexCount - 1));
			double weight = ((Math.random() * desiredEdgeCount) + ((desiredVertexCount / 2) + 1) * minWeight);
			g.addEdge( PREFIX_VERTEX + String.valueOf(source), PREFIX_VERTEX + String.valueOf(target), GKAEdge.valueOf(PREFIX_EDGE + String.valueOf(++edgeIndex), (int) weight), false );
			g.sendMessage("/pbu " + i);
		}
		
		g.sendMessage("/pbe");
		g.sendMessage("/gps");
		g.sendMessage("ERFOLG: Zufallsgraph generiert in " + String.valueOf((System.nanoTime() - startTime) / 1E9D) + " Sekunden.");
		g.sendMessage("Knoten: " + desiredVertexCount);
		g.sendMessage("Kanten: " + desiredEdgeCount);
		g.setLayout();
	}
}
