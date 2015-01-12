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

	/**
	 * Errechnet eine möglichst kurze Rundreise innerhalb des Graphen g ab dem Startknoten startNode
	 * 
	 * @param g Ausgangsgraph, innerhalb dessen die Rundreise errechnet werden soll
	 * @param startNode Startknoten der Rundreise
	 * @return Graph, der der Rundreise entspricht
	 */
	public GKAGraph getTour(GKAGraph g, GKAVertex startNode) {
		checkNotNull(g);
		checkNotNull(startNode);
		
		// MST vom Ausgangsgraphen g erzeugen und in einen gerichteten + gewichteten MST konvertieren,
		// in dem zu jeder Kante eine Rückwärtskante existiert. (null bewirkt, dass nicht direkt der Graph g verändert wird)
		GKAGraph mst = getGraphCopyWithBackEdges(new MinimumSpanningTreeCreator().applyMinimumSpanningTreeTo(g, null));
		
		// Festlegung, in welchem Graphen die Tour abgebildet werden soll
		GKAGraph gclone = g.clone();
		GKAGraph tour = g;
		
		// Neuen Graphen (ungerichtet + gewichtet) erzeugen
		tour.newGraph(GraphType.UNDIRECTED_WEIGHTED);
		
		
		/**
		 * Ab hier folgen die Schritte, in der eine Eulertour über den MST erzeugt werden soll,
		 * um die Reihenfolge zu berechnen, in der die Rundreise stattfinden soll.
		 * Dabei wird jede Kante, über die gegangen wird, aus mst gelöscht und fortgefahren,
		 * bis man zurück am Startknoten angelangt ist.
		 * 
		 * Anschließend werden aus der erstellen Liste "doppelte" Knoten entfernt
		 * und entstandene Folge von Knoten als Weg in der Tour im Graphen tour angewendet.
		 */
		List<GKAVertex> vertexlist = new ArrayList<GKAVertex>();
		
		vertexlist.add(startNode);
		
		//Liste für den Weg einer Tour erstellen über mehrfachkanten
		int vertexlistIndex = 0;
		while(vertexlistIndex < vertexlist.size()){
			GKAVertex vertex = vertexlist.get(vertexlistIndex);
			List<GKAVertex> list = new ArrayList<GKAVertex>(mst.getAllAdjacentsOf(vertex));
			if(!list.isEmpty()){
				if(list.size() > 1){
					list.removeAll(vertexlist);
				}
				GKAVertex adjacentVertex = list.get(0);
				
				// Gefundenen adjazenten Knoten in die tourliste hinzufügen
				vertexlist.add(adjacentVertex);
				
				//Kante zwischen startNode und elem (adjazenten Knoten) löschen
				mst.removeEdge(vertex, adjacentVertex);
			}
			vertexlistIndex++;
		}
		
		
		List<GKAVertex> tourlist = new ArrayList<GKAVertex>();
		
		//tourlist ohne duplikate aus der vertexlist erzeugen
		for ( Iterator<GKAVertex> iterator = vertexlist.iterator(); iterator.hasNext();){
			GKAVertex nextVertex = iterator.next();
			
			if(!tourlist.contains(nextVertex)){
				tourlist.add(nextVertex);
			}
		}
		
		
		/* Die Kenten zu einem neuen Graphen tour hinzufügen */
		for(int index=0; index<tourlist.size();index++){
			GKAVertex source;
			GKAVertex target;
			
			if (index != tourlist.size() - 1) {
				source = tourlist.get(index);
				target = tourlist.get(index + 1);
				
			} else {
				source = tourlist.get(index);
				target = tourlist.get(0);
			}
			
			GKAEdge edge = gclone.getEdge(source, target);
			String edgeName = edge.getName();
			int edgeWeight = edge.getWeight();
			tour.addEdge(source, target, edgeName, edgeWeight);
		}
		
		
		return tour;
	}
	
	/**
	 * Erzeugt ein Abbild von graph mit je einer Rückwärtskante pro norm. Kante
	 * 
	 * @param graph Ausgangsgraph
	 * @return Abbildgraphen + Rückwärtskanten
	 */
	public GKAGraph getGraphCopyWithBackEdges(GKAGraph graph) {
		GKAGraph resultGraph = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
		
		for (GKAEdge edge : graph.getGraph().edgeSet()) {
			String source = ((GKAVertex) edge.getSource()).toString();
			String target = ((GKAVertex) edge.getTarget()).toString();
			String edgeName = edge.getName();
			Integer weight = edge.getWeight();
			
			// Vorwärtskante (normal)
			resultGraph.addEdge(source, target, edgeName, weight);
			// Rückwärtskante
			resultGraph.addEdge(target, source, edgeName + "_Rev", weight);
		}
		
		return resultGraph;
	}
	
}
