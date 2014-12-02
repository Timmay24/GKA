package main.graphs.algorithms.path;

import static main.graphs.Utils.reverse;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.Matrix;
import main.graphs.algorithms.interfaces.PathFinder;

/**
 * @author Louisa
 *
 */
public class FloydWarshall implements PathFinder {
	
	// Konstante fuer Unendlich
	private static final int INFINITY = Integer.MAX_VALUE/2;
	
	/**
	 * @param g
	 * @param startNode
	 * @param endNode
	 * @return
	 * @throws IllegalStateException
	 * //TODO update doc tag 
	 */
	@Override
	public List<GKAVertex> findShortestWay(GKAGraph g, GKAVertex startNode, GKAVertex endNode) throws IllegalStateException {
		
		Preconditions.checkNotNull(g);
		Preconditions.checkNotNull(startNode);
		Preconditions.checkNotNull(endNode);
		
		long startTime = System.nanoTime();
		long hitcount = 0;
		List<GKAVertex> returnList = new ArrayList<>();
		
		
		// Precondition TODO DOC
		// Graph muss gewichtet sein
		if (!g.isWeighted()) {
			hitcount++;
			throw new IllegalStateException("Der Graph muss gewichtet sein.\n"
					+ "g.isWeighted() returned false");
		}
		
		
		
		//Matrix fuer den kuerzesten Abstand zwischen Knoten
		Matrix<GKAVertex, GKAVertex, Integer> distanceTable = new Matrix<>();
		
		//Matrix fuer den kuerzesten Weg
		Matrix<GKAVertex, GKAVertex, GKAVertex> sequenceTable = new Matrix<>();
		
		
		
		
		
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
		 * Matix Elemente: Integerwerte(Kantengewichtung), Null und Integerwert fuer unendlich
		 * Ist Spalten und zeilenname eines Feldes in der Matrix identisch so wird in dieses Feld Null hereingeschrieben,
		 * weil man es fuer den weiteren Algorithmus nicht verwenden muss. 
		 * Gibt es keine Verbindung zwischen Knoten des Spaltennamens und Knoten des Zeilennamens so wird in das Feld
		 * eine Zahl die 'unendlich' repraesentiert eigefuegt.
		 * Ansonstens wird die Kantengewicht eingefuegt.
		 */
		for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < list.size(); columnIndex++) {
				GKAVertex firstVertex = list.get(rowIndex);
				GKAVertex secondVertex = list.get(columnIndex);
				
				if (firstVertex != secondVertex) {
					if (firstVertex.hasEdgeTo(secondVertex, g)) {
						hitcount++;
						distanceTable.setValueAt(firstVertex, secondVertex, (g.getEdge(firstVertex, secondVertex).getWeight()));
						hitcount++;
					} else {
						distanceTable.setValueAt(firstVertex, secondVertex, INFINITY);
					}
					sequenceTable.setValueAt(firstVertex, secondVertex, secondVertex);
				} else if (firstVertex == secondVertex) {
					distanceTable.setValueAt(firstVertex, secondVertex, null);
					sequenceTable.setValueAt(firstVertex, secondVertex, null);
				}
			}
		}
		
		
		
		
		
		/**
		 * Aenderung der Matritzen:
		 * i = Zeilenname
		 * j = Spaltenname
		 * k = Iterationsdurchgang
		 * distanceTable = Matrix fuer den kuerzeste Strecke
		 * sequenceTable = Matrix fuer den Weg 
		 * Zeilenname, Spaltenname und Iterationsdurchgang (name) duerfen nicht identische sein.
		 * Ist der Wert des Feldes von i und j groeßer als der Wert des Feldes von i und k 
		 * plus der Wert von k und j (dij > (dik +dkj)), so werden die Matritzen modifiziert und der Wert des Feldes veraendert.  
		 */
		for (int k = 0; k < list.size(); k++) {
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.size(); j++) {
					GKAVertex ivertx = list.get(i);
					GKAVertex jvertx = list.get(j);
					GKAVertex kvertx = list.get(k);
					if (i != j && i != k && j != k) {
						if (distanceTable.getValueAt(ivertx, jvertx) >
								(distanceTable.getValueAt(ivertx, kvertx) + distanceTable.getValueAt(kvertx, jvertx))){
							distanceTable.setValueAt(ivertx, jvertx, (distanceTable.getValueAt(ivertx, kvertx) + distanceTable.getValueAt(kvertx, jvertx)));
							//Matrix fuer den kuerzesten Weg modifizieren
							sequenceTable.setValueAt(ivertx, jvertx, kvertx);
						}
					}
				}
			}
		}
		
		
		
		
		//damit startNode und endNode nicht geaendert werden koennen
		GKAVertex firstNode = startNode;
		GKAVertex lastNode = endNode;
		
		
		List<GKAVertex> reverseReturnList = new ArrayList<>();
		//erster Knoten des Weges wird hinzugefuegt -> firstNode == startNode
		reverseReturnList.add(endNode);
		
		
		
		
		GKAVertex oneVertexInWay;
		
		//solange der "Spaltenname"(lastNode) ungleich dem "Zeilen-Spalten-Inhalt"(oneVertexInWay) ist, fuege diesen zur Rueckgabeliste hinzu
		while((oneVertexInWay = sequenceTable.getValueAt(firstNode, lastNode)) != lastNode){
			reverseReturnList.add(oneVertexInWay);
			lastNode = oneVertexInWay;
		}
		
		
		
		//fuege den Zielknoten zur Rueckgabeliste hinzu
		reverseReturnList.add(startNode);
		returnList = reverse(reverseReturnList);
		
		
		// Stats uebermitteln
		try {
			g.sendStats(this.getClass().newInstance(), "Floyd-Wars.", String.valueOf((System.nanoTime() - startTime) / 1E6D), String.valueOf(returnList.size() - 1), String.valueOf(hitcount));
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return returnList;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
