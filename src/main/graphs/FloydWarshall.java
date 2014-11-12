package main.graphs;

import static main.graphs.Utils.reverse;

import java.util.ArrayList;
import java.util.List;

import main.graphs.interfaces.PathFinder;

public class FloydWarshall implements PathFinder {
	
	//Konstante f�r Unendlich
	private static final int INFINITY = Integer.MAX_VALUE/2;
	
	public static List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws IllegalArgumentException {

		long hitcount = 0;
		
		long startTime = System.nanoTime();
		
		List<GKAVertex> returnList = new ArrayList<>();
		
		//Matrix f�r den k�rzesten Abstand zwischen Knoten
		Matrix<GKAVertex, GKAVertex, Integer> distanceTable = new Matrix<>();
		
		//Matrix f�r den k�rzesten Weg
		Matrix<GKAVertex, GKAVertex, GKAVertex> sequenceTable = new Matrix<>();
		
		/**
		 * Aufbau der Matritzen:
		 * Alle Knoten im Graphen werden in eine Liste geschrieben. 
		 * Dabei wird ein Knoten nur eingef�gt, wenn er noch 
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
		 * Matix Elemente: Integerwerte(Kantengewichtung), Null und Integerwert f�r unendlich
		 * Ist Spalten und zeilenname eines Feldes in der Matrix identisch so wird in dieses Feld Null hereingeschrieben,
		 * weil man es f�r den weiteren Algorithmus nicht verwenden muss. 
		 * Gibt es keine Verbindung zwischen Knoten des Spaltennamens und Knoten des Zeilennamens so wir in das Feld
		 * eine Zahl die Unendlich repr�sentiert eigef�gt.
		 * Ansonstens wird die Kantengewicht eingef�gt.
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
		 * �nderung der Matritzen:
		 * i = Zeilenname
		 * j = Spaltenname
		 * k = Iterationsdurchgang
		 * distanceTable = Matrix f�r den k�rzeste Strecke
		 * sequenceTable = Matrix f�r den Weg 
		 * Zeilenname, Spaltenname und Iterationsdurchgang (name) d�rfen nicht identische sein.
		 * Ist der Wert des Feldes von i und j gr��er als der Wert des Feldes von i und k 
		 * plus der Wert von k und j (dij > (dik +dkj)), so werden die Matritzen modifiziert und der Wert des Feldes ver�ndert.  
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
							//Matrix f�r den k�rzesten Weg modifizieren
							sequenceTable.setValueAt(ivertx, jvertx, kvertx);
						}
					}
				}
			}
		}
		
		//damit startNode und endNode nicht ge�ndert werden k�nnen
		GKAVertex firstNode = startNode;
		GKAVertex lastNode = endNode;
		
		List<GKAVertex> reverseReturnList = new ArrayList<>();
		//erster Knoten des Weges wird hinzugef�gt -> firstNode == startNode
		reverseReturnList.add(endNode);
		
		GKAVertex oneVertexInWay;
		
		//solange der "Spaltenname"(lastNode) ungleich dem "Zeilen-Spalten-Inhalt"(oneVertexInWay) ist, f�ge diesen zur R�ckgabeliste hinzu
		while((oneVertexInWay = sequenceTable.getValueAt(firstNode, lastNode)) != lastNode){
			reverseReturnList.add(oneVertexInWay);
			lastNode = oneVertexInWay;
		}
		
		//f�ge den Zielknoten zur R�ckgabeliste hinzu
		reverseReturnList.add(startNode);
		returnList = reverse(reverseReturnList);
		
		//Anzahl der Kanten im k�rzesten Weg	
		int tmp = (returnList.size() - 1);
		Integer anzahlInInt = new Integer(tmp); 
		String anzahl = anzahlInInt.toString();
		System.out.println("Der Weg hat " + anzahl + " Kanten");
		

		g.sendStats("Floyd-Wars.", String.valueOf((System.nanoTime() - startTime) / 1E6D), String.valueOf(reverseReturnList.size() - 1), String.valueOf(hitcount));

		return returnList;


				
	}

}
