package controller;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GraphType;
import main.graphs.Vertex;

import org.jgrapht.ListenableGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

public interface GKAController extends MessageSender, CellSender<mxCell>, AdapterUpdateSender {
	
	/**
	 * @return Instanz der Graphen-Wrapperklasse.
	 */
	public ListenableGraph<Vertex, GKAEdge> getGraph();
	
	
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
	 * Positioniert den Graphen in Form eines Kreises.
	 */
	public void setCircleLayout();
	
	
	/**
	 * Passt die Einstellungen fuer parallele Kanten an.
	 */
	public void setParallelEdgeLayout();
	
}