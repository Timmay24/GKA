package main.graphs.algorithms.tsp;

import java.util.ArrayList;
import java.util.List;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.Matrix;
import main.graphs.algorithms.GKAAlgorithmBase;
import main.graphs.algorithms.interfaces.TSPAlgorithm;

public class NearestNeighbourHeuristicSearcher extends GKAAlgorithmBase implements TSPAlgorithm {

	protected GKAGraph g;
	protected GKAVertex startNode;
	protected List<GKAVertex> resultRoute;
	
	public List<GKAVertex> findRoute() {
		
		int hitcount = 0;
		
		List<GKAVertex> resultRoute = new ArrayList<>();

		
		
		//Matrix fuer den kuerzesten Abstand zwischen Knoten
		Matrix<GKAVertex, GKAVertex, Integer> distanceTable = new Matrix<>();
		
		
		/**
		 * Aufbau der Matritzen:
		 * Alle Knoten im Graphen werden in eine Liste geschrieben. 
		 * Dabei wird ein Knoten nur eingefuegt, wenn er noch 
		 * nicht in der Liste vohanden ist
		 */
		List<GKAVertex> list = new ArrayList<>();
		list.add(startNode);
		for (int i = 0; i < list.size(); i++) {
			for (GKAVertex v : g.getAllAdjacentsOf(list.get(i))) {
				hitcount++;
				if (!list.contains(v)) {
					list.add(v);
				}
			}
		}
		
		
		
		/**
		 * Aufbau der Matritzen:
		 * Die Knotennamen sind Spalten und Zeilennamen. 
		 * Matix Elemente: Integerwerte(=> Kantengewichtung)
		 * Ist Spalten und zeilenname eines Feldes in der Matrix identisch so wird in dieses Feld Null hereingeschrieben,
		 * weil man es fuer den weiteren Algorithmus nicht verwenden muss. 
		 */
		for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < list.size(); columnIndex++) {
				GKAVertex firstVertex = list.get(rowIndex);
				GKAVertex secondVertex = list.get(columnIndex);
				
				if (firstVertex != secondVertex) {
					//Kantenlänge holen
					distanceTable.setValueAt(firstVertex, secondVertex, (g.getEdge(firstVertex, secondVertex).getWeight()));
//					System.out.println(g.getEdge(firstVertex, secondVertex).getWeight());
				} else if (firstVertex == secondVertex) {
					distanceTable.setValueAt(firstVertex, secondVertex, null);
				}
			}
		}
		
		
		
		/*Startknoten zur Rundgangliste hinzufügen*/
		resultRoute.add(startNode);
		
		/*kürzeste Distance von einem zum anderen Knoten*/
		int shortest_distance = Integer.MAX_VALUE;
		
		/*Knoten der den kürzesten Abstand zu einem bestimmten anderen Knoten hat*/
		GKAVertex current_vertex_with_shortest_distance = null;
		
		/**
		 * kürzesten Weg finden
		 */
		for (int resultRouteindex = 0; resultRouteindex < list.size(); resultRouteindex++){
		
			//Schlüssel (Knoten) der Matrixzeile
			GKAVertex line_vertex = resultRoute.get(resultRouteindex);
			
			for (int listindex = 0; listindex < list.size(); listindex++){
				//Schlüssel (Knoten) der Matrixspalte
				GKAVertex column_vertex = list.get(listindex);
				
				//Entfernung von einem Knoten zum anderen
				int distance = distanceTable.getValueAt(line_vertex, column_vertex);
				
				/*Wenn ein kürzerer Abstand von source zu Target gefunden wurde,
				 * dann übernehme die Distance und speichere den Knoten*/
				if(distance < shortest_distance && !resultRoute.contains(column_vertex)){
					shortest_distance = distance;
					current_vertex_with_shortest_distance = column_vertex;
				}
			}
			
			/*Wenn der Knoten gefunden wurde, der am dichtesten vom line_vertex liegt,
			 * wird dieser zur Rundgangliste hinzugefügt*/
			if(current_vertex_with_shortest_distance != null){
				resultRoute.add(current_vertex_with_shortest_distance);
			}
			
		}
		
		/*Am Ende des Rundgangs wird der Startknoten hinzugefügt, damit es eine Rundereise ist
		 * Der Startknoten ist also zweimal in der Liste vorhanden.*/
		resultRoute.add(startNode);
		
		System.out.println(resultRoute.toString());
		
		return resultRoute;
	}

	@Override
	public void run() {
		this.resultRoute = findRoute();
	}

	public List<GKAVertex> getRoute() {
		return this.resultRoute;
	}
	
	@Override
	public void injectReferences(GKAGraph g, GKAVertex startNode) {
		this.g = g;
		this.startNode = startNode;
	}
	
}






























