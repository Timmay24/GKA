package main.graphs.algorithms.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;

public class EdmondKarp extends FlowCalculatorBase {

	/**
	 * Standart-Konstruktor
	 */
	public EdmondKarp() {
		super();
	}
	
	//TODO: Benennung...
	
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.flow.FlowCalculatorBase#getMaxFlow_(main.graphs.GKAGraph, main.graphs.GKAVertex, main.graphs.GKAVertex)
	 */
	@Override
	protected Integer getMaxFlow_(GKAVertex sourceNode, GKAVertex sinkNode, int _maxFlow) throws RuntimeException {
		// Preconditions checked in super class
		
		Map<GKAVertex, GKAVertex> parentMap = new HashMap<>();
		List<GKAVertex> alreadyReached = new ArrayList<>();
		
		Queue<GKAVertex> nodesToProcess = new LinkedList<GKAVertex>();
		nodesToProcess.offer(sourceNode);
		
		while (!nodesToProcess.isEmpty()) {
			GKAVertex activeNode = nodesToProcess.poll();
			alreadyReached.add(activeNode);
			
			// Fuer alle 
			for (GKAVertex adjacentNode : forwardingVerticesOf(activeNode)) {
				
				// Verbleibende Flusskapazitaet zwischen aktivem und benachbartem Knoten berechnen
				int maxInterFlow = getRemainingCapacityBetween(activeNode, adjacentNode);
				
				// Falls ein Fluss besteht und der benachbarte Knoten wurde noch nicht abgearbeitet
				if (maxInterFlow > 0 && !alreadyReached.contains(adjacentNode)) {
					// Aktiven Knoten als Vorgaenger des benachbarten Knotens setzen
					parentMap.put(adjacentNode, activeNode);
					
					// 
					_maxFlow = Integer.min(_maxFlow, maxInterFlow);
					
					// Wenn der Nachbarknoten nicht die Senke ist
					if (adjacentNode != sinkNode) {
						nodesToProcess.offer(adjacentNode); // Nachbarknoten als naechsten zu bearbeitenden Knoten einreihen
					} else {
						// sonst: Solange es zu adjacentNode einen Vorgaenger gibt
						while (parentMap.get(adjacentNode) != null) {
							
							// 
							currentFlows.setValueAt(parentMap.get(adjacentNode) , adjacentNode, currentFlows.getValueAt(parentMap.get(adjacentNode) , adjacentNode) + _maxFlow);
							currentFlows.setValueAt(adjacentNode, parentMap.get(adjacentNode) , currentFlows.getValueAt(adjacentNode, parentMap.get(adjacentNode) ) - _maxFlow);
							
							// Vorgaenger durchruecken, um mit dem naechsten Iterationsschritt fortzufahren
							adjacentNode = parentMap.get(adjacentNode);
						}
						return _maxFlow;
					}
				}
			}
		}
		return 0;
	}

}
