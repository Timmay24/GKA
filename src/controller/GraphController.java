package controller;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GraphType;
import main.graphs.Vertex;

import org.jgrapht.ListenableGraph;

import com.mxgraph.swing.mxGraphComponent;

public class GraphController implements GKAController {

	private		GKAGraph	gkaGraph;
	
	
	/**
	 * Konstruktor
	 */
	public GraphController() {
		// Zum Start einen Beispielgraphen erzeugen.
		setGraphWrapper(GKAGraph.valueOf());
	}
	
	/**
	 * @return Gibt die Instanz der Graphen-Wrapperklasse zurueck.
	 */
	public ListenableGraph<Vertex, GKAEdge> getGraph() {
		return getGraphWrapper().getGraph();
	}
	
	public void addMessageListener(MessageListener ml) {
		getGraphWrapper().addMessageListener(ml);
	}
	
	/**
	 * @param graphType
	 * @return Erzeugt einen neuen Graphenwrapper, der einen Graphen des gewuenschten Typs graphType enthaelt.
	 * 			Der Rueckgabewert (die Graphenkomponente) kann direkt in der GUI eingebunden werden, um den Graphen anzuzeigen.
	 */
	public mxGraphComponent newGraph(GraphType graphType) {
		setGraphWrapper(GKAGraph.valueOf(graphType));
		return getGraphWrapper().getGraphComponent();
	}
	
	/**
	 * @return 
	 * @throws Exception: Wird geworfen, falls noch keine Graphenkomponente, mangels Graphen, erzeugt wurde.
	 */
	public mxGraphComponent getGraphComponent() {
		return getGraphWrapper().getGraphComponent();
	}
	
	/**
	 * @return Die Wrapperklasse des GKA-Graphen
	 */
	public GKAGraph getGraphWrapper() {
		return gkaGraph;
	}
	
	private void setGraphWrapper(GKAGraph gkaGraph) {
		this.gkaGraph = gkaGraph;
	}
	
	public void setCircleLayout() {
		getGraphWrapper().setCircleLayout();
	}
	
	public void setParallelEdgeLayout() {
		getGraphWrapper().setParallelEdgeLayout();
	}
}
