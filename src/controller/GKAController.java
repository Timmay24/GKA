package controller;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GraphType;
import main.graphs.Vertex;

import org.jgrapht.ListenableGraph;

import com.mxgraph.swing.mxGraphComponent;

public interface GKAController extends MessageSender {
	
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
	 * @param graphType
	 * @return Erzeugt einen neuen Graphenwrapper, der einen Graphen des gewuenschten Typs graphType enthaelt.
	 * 			Der Rueckgabewert (die Graphenkomponente) kann direkt in der GUI eingebunden werden, um den Graphen anzuzeigen.
	 */
	public mxGraphComponent newGraph(GraphType graphType);
	
	
	/**
	 * Zum Laden eines Graphen aus einer Datei.
	 */
	public void openGraph();
	
	
	/**
	 * Zum Speichern eines Graphen in einer Datei.
	 */
	public void saveGraph();
	
	/**
	 * siehe saveGraph(). Waehlt zusaetzlich einen neuen Dateipfad aus.
	 */
	public void saveGraphAs();
	
	
	/**
	 * Positioniert den Graphen in Form eines Kreises.
	 */
	public void setCircleLayout();
	
	/**
	 * Passt die Einstellungen fuer parallele Kanten an.
	 */
	public void setParallelEdgeLayout();
	
}