package main.graphs;

import static main.graphs.Utils.reverse;

import java.util.ArrayList;
import java.util.List;

import main.graphs.interfaces.PathFinder;

public class FloydWarshall implements PathFinder {
	
	//Konstante für Unendlich
	private static final int INFINITY = Integer.MAX_VALUE/2;
	
	public static List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws IllegalArgumentException {

		long hitcount = 0;
		long startTime = System.nanoTime();
		
		// Precondition TODO DOC
		// Graph muss gewichtet sein
		if (!g.isWeighted()) { hitcount++;
			throw new IllegalArgumentException("Der Graph muss gewichtet sein.\n" + "g.isWeighted() returned false");
		}
		
		List<GKAVertex> returnList = new ArrayList<>();
		
		//Matrix für den kürzesten Abstand zwischen Knoten
		Matrix<GKAVertex, GKAVertex, Integer> distanceTable = new Matrix<>();
		
		//Matrix für den kürzesten Weg
		Matrix<GKAVertex, GKAVertex, GKAVertex> sequenceTable = new Matrix<>();
		
		/**
		 * Aufbau der Matritzen:
		 * Alle Knoten im Graphen werden in eine Liste geschrieben. 
		 * Dabei wird ein Knoten nur eingefügt, wenn er noch 
		 * nicht in der Liste vohanden ist
		 */
		List<GKAVertex> list = new ArrayList<>();
		list.add(startNode);
		for(int i = 0; i < list.size(); i++){
			for(GKAVertex v : g.getAllAdjacentsOf(list.get(i))){
				hitcount++;
				if(!list.contains(v)){
					list.add(v);
				}
			}
		}
		/**
		 * Aufbau der Matritzen:
		 * Die Knotennamen sind Spalten und Zeilennamen. 
		 * Matix Elemente: Integerwerte(Kantengewichtung), Null und Integerwert für unendlich
		 * Ist Spalten und zeilenname eines Feldes in der Matrix identisch so wird in dieses Feld Null hereingeschrieben,
		 * weil man es für den weiteren Algorithmus nicht verwenden muss. 
		 * Gibt es keine Verbindung zwischen Knoten des Spaltennamens und Knoten des Zeilennamens so wir in das Feld
		 * eine Zahl die Unendlich repräsentiert eigefügt.
		 * Ansonstens wird die Kantengewicht eingefügt.
		 */
		for(int rowIndex=0; rowIndex<list.size(); rowIndex++){
			for(int columnIndex=0; columnIndex<list.size(); columnIndex++){
				GKAVertex firstVertex = list.get(rowIndex);
				GKAVertex secondVertex = list.get(columnIndex);
				
				if(firstVertex != secondVertex){
					if(firstVertex.hasEdgeTo(secondVertex, g)){
						hitcount++;
						distanceTable.setValueAt(firstVertex, secondVertex, (g.getEdge(firstVertex, secondVertex).getWeight()));
						hitcount++;
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
		/**
		 * Änderung der Matritzen:
		 * i = Zeilenname
		 * j = Spaltenname
		 * k = Iterationsdurchgang
		 * distanceTable = Matrix für den kürzeste Strecke
		 * sequenceTable = Matrix für den Weg 
		 * Zeilenname, Spaltenname und Iterationsdurchgang (name) dürfen nicht identische sein.
		 * Ist der Wert des Feldes von i und j größer als der Wert des Feldes von i und k 
		 * plus der Wert von k und j (dij > (dik +dkj)), so werden die Matritzen modifiziert und der Wert des Feldes verändert.  
		 */
		for(int k=0; k<list.size(); k++){
			for(int i=0; i<list.size(); i++){
				for(int j=0; j<list.size(); j++){
					GKAVertex ivertx = list.get(i);
					GKAVertex jvertx = list.get(j);
					GKAVertex kvertx = list.get(k);
					if(i!=j && i!=k && j!=k){
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
		reverseReturnList.add(endNode);
		
		GKAVertex oneVertexInWay;
		
		//solange der "Spaltenname"(lastNode) ungleich dem "Zeilen-Spalten-Inhalt"(oneVertexInWay) ist, füge diesen zur Rückgabeliste hinzu
		while((oneVertexInWay = sequenceTable.getValueAt(firstNode, lastNode)) != lastNode){
			reverseReturnList.add(oneVertexInWay);
			lastNode = oneVertexInWay;
		}
		
		//füge den Zielknoten zur Rückgabeliste hinzu
		reverseReturnList.add(startNode);
		returnList = reverse(reverseReturnList);
		
		//Anzahl der Kanten im kürzesten Weg	
		int tmp = (returnList.size() - 1);
		Integer anzahlInInt = new Integer(tmp); 
		String anzahl = anzahlInInt.toString();
		System.out.println("Der Weg hat " + anzahl + " Kanten");
		

		g.sendStats("Floyd-Wars.", String.valueOf((System.nanoTime() - startTime) / 1E6D), String.valueOf(reverseReturnList.size() - 1), String.valueOf(hitcount));

		return returnList;


				
	}

}
