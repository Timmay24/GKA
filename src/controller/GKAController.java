package controller;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GraphType;
import main.graphs.GKAVertex;

import org.jgrapht.ListenableGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import controller.interfaces.AdapterUpdateSender;
import controller.interfaces.CellSender;
import controller.interfaces.MessageSender;
import controller.interfaces.NodeSender;

public interface GKAController extends MessageSender, CellSender<mxCell>, AdapterUpdateSender, NodeSender {
	
	/**
	 * @return Instanz der Graphen-Wrapperklasse.
	 */
	public ListenableGraph<GKAVertex, GKAEdge> getGraph();
	
	
	/**
	 * @return Interne Komponente des Graphen.
	 */
	public mxGraphComponent getGraphComponent();
	
	
	/**
	 * @return Die Wrapperklasse des GKA-Graphen
	 */
	public GKAGraph getGraphWrapper();
	
	
	/**
	 * Laesst den Wrapper einen neuen Graphen erzeugen.
	 * 
	 * @param graphType
	 */
	public void newGraph(GraphType graphType);
	
	
	/**
	 * Zum Laden eines Graphen aus einer Datei.
	 */
	public void openGraph();
	
	
	/**
	 * Zum Speichern eines Graphen in einer Datei.
	 * Zwingt zur Wahl des Dateipfades. 
	 */
	public void saveGraph(String filePath);
	
	
	/**
	 * Zum Speichern eines Graphen in einer Datei.
	 * Benutzt ggf. vorhandenen Dateipfad, sonst --> Pfadwahl.
	 */
	public void saveGraph();
	
	
	/**
	 * Fuegt einen Knoten hinzu.
	 * 
	 * @param vertexName
	 */
	public void addVertex(String vertexName);
	
	/**
	 * Fuegt Kanten hinzu.
	 * 
	 * @param sourceVertex
	 * @param targetVertex
	 * @param edge
	 */
	public void addEdge(String sourceVertex, String targetVertex, GKAEdge edge);
	
	
	/**
	 * Positioniert den Graphen in Form eines Kreises.
	 */
	public void setCircleLayout();
	
	
	/**
	 * Passt die Einstellungen fuer parallele Kanten an.
	 */
	public void setParallelEdgeLayout();

}