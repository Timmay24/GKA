package main.graphs.algorithms.tsp;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.algorithms.GKAAlgorithmBase;

public class MSTHeuristicTour extends GKAAlgorithmBase {

	protected 	List<GKAVertex> 	tourList;
	protected 	int 				tourLength;
	protected	int					mstLength;
	
	public MSTHeuristicTour() {
		reset();
	}
	
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
		
		reset();
		
		startTimeMeasurement();
		
		// MST vom Ausgangsgraphen g erzeugen und in einen gerichteten + gewichteten MST konvertieren,
		// in dem zu jeder Kante eine R�ckw�rtskante existiert. (null bewirkt, dass nicht direkt der Graph g ver�ndert wird)
		MinimumSpanningTreeCreator mstCreator = new MinimumSpanningTreeCreator();
		GKAGraph mstNoDups = mstCreator.applyMinimumSpanningTreeTo(g, null);
		mstLength = mstCreator.getMSTLength();
		
		GKAGraph mst = getGraphCopyWithBackEdges(mstNoDups);
		GKAGraph tour = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		
		/**
		 * Ab hier folgen die Schritte, in der eine Eulertour �ber den MST erzeugt werden soll,
		 * um die Reihenfolge zu berechnen, in der die Rundreise stattfinden soll.
		 * Dabei wird jede Kante, �ber die gegangen wird, aus mst gel�scht und fortgefahren,
		 * bis man zur�ck am Startknoten angelangt ist.
		 * 
		 * Anschlie�end werden aus der erstellen Liste "doppelte" Knoten entfernt
		 * und entstandene Folge von Knoten als Weg in der Tour im Graphen tour angewendet.
		 */
		List<GKAVertex> vertexlist = new ArrayList<GKAVertex>();
		
		vertexlist.add(startNode);
		
		//Liste f�r den Weg einer Tour erstellen �ber mehrfachkanten
		int vertexlistIndex = 0;
		while (vertexlistIndex < vertexlist.size()) {
			GKAVertex vertex = vertexlist.get(vertexlistIndex);
			List<GKAVertex> list = new ArrayList<GKAVertex>(mst.getAllAdjacentsOf(vertex));
			if (!list.isEmpty()) {
				if (list.size() > 1) {
					list.removeAll(vertexlist);
				}
				
				GKAVertex adjacentVertex = list.get(0);

				// Gefundenen adjazenten Knoten in die tourliste hinzuf�gen
				vertexlist.add(adjacentVertex);

				// Kante zwischen startNode und elem (adjazenten Knoten) l�schen
				mst.removeEdge(vertex, adjacentVertex);
			}
			vertexlistIndex++;
		}
		
		
		List<GKAVertex> tourlist = new ArrayList<>();
		
		//tourlist ohne duplikate aus der vertexlist erzeugen
		for (GKAVertex nextVertex : vertexlist) {
			if (!tourlist.contains(nextVertex)) {
				tourlist.add(nextVertex);
			}
		}
		
		
		/* Die Kanten zu einem neuen Graphen tour hinzuf�gen */
		for (int index = 0; index < tourlist.size(); index++) {
			GKAVertex source;
			GKAVertex target;

			if (index != tourlist.size() - 1) {
				source = tourlist.get(index);
				target = tourlist.get(index + 1);

			} else {
				source = tourlist.get(index);
				target = tourlist.get(0);
			}

			GKAEdge edge = g.getEdge(source, target);
			String edgeName = edge.getName();
			int edgeWeight = edge.getWeight();
			tourLength += edgeWeight;
			tour.addEdge(source, target, edgeName, edgeWeight);
		}
		
		tourlist.add(startNode);
		tourList = tourlist;
		
		stopTimeMeasurement();
		
		return tour;
	}
	
	/**
	 * Erzeugt ein Abbild von graph mit je einer R�ckw�rtskante pro normaler Kante
	 * 
	 * @param graph Ausgangsgraph
	 * @return Abbildgraphen + R�ckw�rtskanten
	 */
	public GKAGraph getGraphCopyWithBackEdges(GKAGraph graph) {
		GKAGraph resultGraph = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
		
		for (GKAEdge edge : graph.getGraph().edgeSet()) {
			String source = ((GKAVertex) edge.getSource()).toString();
			String target = ((GKAVertex) edge.getTarget()).toString();
			String edgeName = edge.getName();
			Integer weight = edge.getWeight();
			
			// Vorw�rtskante (normal)
			resultGraph.addEdge(source, target, edgeName, weight);
			// R�ckw�rtskante
			resultGraph.addEdge(target, source, edgeName + "_Rev", weight);
		}
		
		return resultGraph;
	}

	public List<GKAVertex> getTourList() {
		return tourList;
	}

	public int getTourLength() {
		return tourLength;
	}
	
	public int getMSTLength() {
		return mstLength;
	}
	
	private void reset() {
		tourList = new ArrayList<>();
		tourLength = 0;
		mstLength = 0;
		hc = 0;
	}
	
}
