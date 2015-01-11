package main.graphs.algorithms.tsp;

import static com.google.common.base.Preconditions.checkNotNull;
import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.algorithms.GKAAlgorithmBase;

public class MSTHeuristicTour extends GKAAlgorithmBase {

	/**
	 * Errechnet eine m�glichst kurze Rundreise innerhalb des Graphen g ab dem Startknoten startNode
	 * 
	 * @param g Ausgangsgraph, innerhalb dessen die Rundreise errechnet werden soll
	 * @param startNode Startknoten der Rundreise
	 * @return Graph, der der Rundreise entspricht
	 */
	public GKAGraph getTour(GKAGraph g, GKAVertex startNode) {
		checkNotNull(g);
		checkNotNull(startNode);
		
		// MST vom Ausgangsgraphen g erzeugen und in einen gerichteten + gewichteten MST konvertieren,
		// in dem zu jeder Kante eine R�ckw�rtskante existiert. (null bewirkt, dass nicht direkt der Graph g ver�ndert wird)
		GKAGraph mst = getGraphCopyWithBackEdges(new MinimumSpanningTreeCreator().applyMinimumSpanningTreeTo(g, null));
		
		// Festlegung, in welchem Graphen die Tour abgebildet werden soll
		GKAGraph tour = g;
		
		// Neuen Graphen (ungerichtet + gewichtet) erzeugen
		tour.newGraph(GraphType.UNDIRECTED_WEIGHTED);
		
		
		/**
		 * Ab hier folgen die Schritte, in der eine Eulertour �ber den MST erzeugt werden soll,
		 * um die Reihenfolge zu berechnen, in der die Rundreise stattfinden soll.
		 * Dabei wird jede Kante, �ber die gegangen wird, aus mst gel�scht und fortgefahren,
		 * bis man zur�ck am Startknoten angelangt ist.
		 * 
		 * Anschlie�end werden aus der erstellen Liste "doppelte" Knoten entfernt
		 * und entstandene Folge von Knoten als Weg in der Tour im Graphen tour angewendet.
		 */
		
		
		return tour;
	}
	
	/**
	 * Erzeugt ein Abbild von graph mit je einer R�ckw�rtskante pro norm. Kante
	 * 
	 * @param graph Ausgangsgraph
	 * @return Abbildgraphen + R�ckw�rtskanten
	 */
	protected GKAGraph getGraphCopyWithBackEdges(GKAGraph graph) {
		GKAGraph resultGraph = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
		
		for (GKAEdge edge : graph.getGraph().edgeSet()) {
			String source = ((GKAVertex) edge.getSource()).toString();
			String target = ((GKAVertex) edge.getTarget()).toString();
			String edgeName = edge.getName();
			Integer weight = edge.getWeight();
			
			// Vorw�rtskante (normal)
			graph.addEdge(source, target, edgeName, weight);
			// R�ckw�rtskante
			graph.addEdge(target, source, edgeName + "_Rev", weight);
		}
		
		return resultGraph;
	}
	
}
