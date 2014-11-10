package main.graphs;

import static main.graphs.Utils.reverse;

import java.util.ArrayList;
import java.util.List;

import main.graphs.interfaces.PathFinder;

public class FloydWarshall implements PathFinder {
	
	//Konstante für Unendlich
	private static final int INFINITY = 99999999;
	
	public static List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws IllegalArgumentException {

		long startTime = System.nanoTime();
		
		List<GKAVertex> returnList = new ArrayList<>();
		
		//Matrix für den kürzesten Abstand zwischen Knoten
		Matrix<GKAVertex, GKAVertex, Integer> distanceTable = new Matrix<>();
		
		//Matrix für den kürzesten Weg
		Matrix<GKAVertex, GKAVertex, GKAVertex> sequenceTable = new Matrix<>();
		
		//
		List<GKAVertex> list = new ArrayList<>();
		list.add(startNode);
		for(int i = 0; i < list.size(); i++){
			for(GKAVertex v : g.getAllAdjacentsOf(list.get(i))){
				if(!list.contains(v)){
					list.add(v);
				}
			}
		}
		for(int rowIndex=0; rowIndex<list.size(); rowIndex++){
			for(int columnIndex=0; columnIndex<list.size(); columnIndex++){
				GKAVertex firstVertex = list.get(rowIndex);
				GKAVertex secondVertex = list.get(columnIndex);
				
				if(firstVertex != secondVertex){
					if(firstVertex.hasEdgeTo(secondVertex, g)){
						distanceTable.setValueAt(firstVertex, secondVertex, (g.getEdge(firstVertex, secondVertex).getWeight()));
					}else{
						distanceTable.setValueAt(firstVertex, secondVertex, INFINITY);
					}
					sequenceTable.setValueAt(firstVertex, secondVertex, secondVertex);
				}else if(firstVertex == secondVertex){
					distanceTable.setValueAt(firstVertex, secondVertex, null);
					sequenceTable.setValueAt(firstVertex, secondVertex, null);
				}
			}
		}

		for(int k=0; k<list.size(); k++){
			for(int i=0; i<list.size(); i++){
				for(int j=0; j<list.size(); j++){
					GKAVertex ivertx = list.get(i);
					GKAVertex jvertx = list.get(j);
					GKAVertex kvertx = list.get(k);
					if(i!=j && i!=k && j!=k){
						//if dij > (dik +dkj)
						if(distanceTable.getValueAt(ivertx, jvertx) > 
								(distanceTable.getValueAt(ivertx, kvertx) + distanceTable.getValueAt(kvertx, jvertx))){
							distanceTable.setValueAt(ivertx, jvertx, (distanceTable.getValueAt(ivertx, kvertx) + distanceTable.getValueAt(kvertx, jvertx)));
							//Matrix für den kürzesten Weg modifizieren
							sequenceTable.setValueAt(ivertx, jvertx, kvertx);
						}
					}
				}
			}
		}
		
		//damit startNode und endNode nicht geändert werden können
		GKAVertex firstNode = startNode;
		GKAVertex lastNode = endNode;
		
		List<GKAVertex> reverseReturnList = new ArrayList<>();
		//erster Knoten des Weges wird hinzugefügt -> firstNode == startNode
		reverseReturnList.add(firstNode);
		
		GKAVertex oneVertexInWay;
		
		//solange der "Spaltenname"(lastNode) ungleich dem "Zeilen-Spalten-Inhalt"(oneVertexInWay) ist, füge diesen zur Rückgabeliste hinzu
		while((oneVertexInWay = sequenceTable.getValueAt(firstNode, lastNode)) != lastNode){
			reverseReturnList.add(oneVertexInWay);
			lastNode = oneVertexInWay;
		}
		
		//füge den Zielknoten zur Rückgabeliste hinzu
		reverseReturnList.add(endNode);
		
		// Number of edges in shortest way	
		int tmp = (reverseReturnList.size() - 1);
		Integer anzahlInInt = new Integer(tmp); 
		String anzahl = anzahlInInt.toString();
		System.out.println("Der Weg hat " + anzahl + " Kanten");
		
		g.sendStats("42", Double.valueOf((System.nanoTime() - startTime) / 1000000D).toString());
//		returnList = reverse(reverseReturnList); 
		return reverseReturnList;
//		return returnList;
				
	}

}
