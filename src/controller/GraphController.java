/*
 * TODO:
 * Hashmap für vertices und edges anlegen um verwaltung zu vereinfachen
 */

package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import main.graphs.GraphType;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.Pseudograph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphComponent.mxGraphControl;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;



public class GraphController implements ListenableMessages, GKAController {
	
	private static 		GraphController 					instance;
	private				ListenableGraph<String, GKAEdge> 	jGraph;
	private				JGraphXAdapter<String, GKAEdge> 	jgxAdapter;
	private 			mxGraphComponent 					graphComponent;
	private 			List<MessageListener>				msgListener;
	

	private GraphController() {
		msgListener = new ArrayList<>();
	}

	public static GraphController getInstance() {
		if (instance == null) {
			instance = new GraphController();
		}
		return instance;
	}
	
	public mxGraphComponent newGraph(GraphType graphType) {

		/**
		 * Neuen Graphen erstellen. Graphentyp wird durch den Enum graphType übermittelt
		 */
		if (graphType.isDirected()) {
			jGraph = new ListenableDirectedGraph<>(new DirectedPseudograph<String, GKAEdge>(GKAEdge.class));
		} else {
			jGraph = new ListenableUndirectedGraph<>(new Pseudograph<String, GKAEdge>(GKAEdge.class));
		}
		
		jgxAdapter = new JGraphXAdapter<String, GKAEdge>(getGraph());
		
		if (graphType.isDirected()) {
			jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_ENDARROW, "none");
		}
		
		createGraphComponent(jgxAdapter);
		
		setGraphConfig();
		
		return graphComponent;
	}
	
	private void createGraphComponent(JGraphXAdapter<String, GKAEdge> jgxAdapter) {
		graphComponent = new mxGraphComponent(jgxAdapter);
		addMouseAdapter();
	}
	
	public mxGraphComponent createSampleSetup() {
		
		mxGraphComponent mxGC = newGraph(GraphType.DIRECTED_WEIGHTED);
		
		String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        addVertex(v1);
        addVertex(v2);
        addVertex(v3);
        addVertex(v4);

        addEdge(v1, v2, new GKAEdge("Edge 1", 1.0));
        addEdge(v2, v3, new GKAEdge("Edge 2", 2.0));
        addEdge(v3, v1, new GKAEdge("Edge 3", 3.0));
        addEdge(v4, v3, new GKAEdge("Edge 4", 4.0));
        
        sendMessage("Beispiel-Graph erstellt");
        
        setLayout();
        
        return mxGC;
	}

	private void setGraphConfig() {
		getAdapter().setAllowDanglingEdges(false);
		getAdapter().setCellsDisconnectable(false);
		getAdapter().setDisconnectOnMove(false);
		getAdapter().setCellsEditable(false);
		getAdapter().setVertexLabelsMovable(false);
		getAdapter().setEdgeLabelsMovable(false);
		getAdapter().setConnectableEdges(false);
		
		setLayout();
		
		getGraphComponent().setSize(774, 326);
		getGraphComponent().setConnectable(false);
		getGraphComponent().setDragEnabled(false);
		
		
	}
	
	public void setLayout() {
		setCircleLayout();
		setParallelEdgeLayout();
	}
	
	public void setCircleLayout() {
		mxCircleLayout layout1 = new mxCircleLayout(getAdapter());
        layout1.execute(getAdapter().getDefaultParent());
	}
	
	public void setParallelEdgeLayout() {
		mxParallelEdgeLayout layout = new mxParallelEdgeLayout(getAdapter(), 50);
        layout.execute(getAdapter().getDefaultParent());
	}
	
	public mxGraphComponent getGraphComponent() {
		return graphComponent;
	}
	
	public ListenableGraph<String, GKAEdge> getGraph() {
		return jGraph;
	}
	
	public mxGraph getAdapter() {
		return jgxAdapter;
	}
	
	public boolean addEdge(String source, String target, GKAEdge newEdge) {
		if (getGraph().addEdge(source, target, newEdge)) {
			sendMessage("Kante erstellt: " + source + " : " + target);
			return true;
		} else {
			sendMessage("Kante konnte nicht erstellt werden (" + source + " : " + target + ")");
			return false;
		}
	}
	
	public boolean removeEdge(String source, String target) {
		if (getGraph().removeEdge(getGraph().getEdge(source, target))) {
			sendMessage("Kante entfernt (" + source + " : " + target + ")");
			return true;
		} else {
			sendMessage("Kante konnte nicht entfernt werden (" + source + " : " + target + ")");
			return false;
		}
	}
	
	public boolean addVertex(String name) {
		if (getGraph().addVertex(name)) {
			sendMessage("Knoten " + name + " hinzugefügt");
			return true;
		} else {
			sendMessage("Knoten " + name + " konnte nicht hinzugefügt werden");
			return false;
		}
	}
	
	public boolean removeVertex(String name) {
		if (getGraph().removeVertex(name)) {
			sendMessage("Knoten " + name + " entfernt");
			return true;
		} else {
			sendMessage("Knoten " + name + " konnte nicht entfernt werden");
			return false;
		}
	}
	
	@Override
	public void addMessageListener(MessageListener messageListener) {
		msgListener.add(messageListener);
	}
	
	private void sendMessage(String message) {
		for (MessageListener ml : msgListener) {
			ml.giveMessage(message);
		}
	}
	
	public void printAllEdges() {
		sendMessage("Alle Kanten:");
		for (GKAEdge e : getGraph().edgeSet()) {
			sendMessage(e.toString());
		}
	}
	
	public mxGraphControl getGraphControl() {
		return getGraphComponent().getGraphControl();
	}
	
	public void addMouseAdapter() {
		getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Object cell = getGraphComponent().getCellAt(e.getX(), e.getY());
				
				if (cell != null && cell instanceof mxCell) {
					System.out.println(((mxCell) cell).getValue());
					sendMessage("Markiert: " + ((mxCell) cell).getValue());
//					System.out.println(((mxCell)cell).isEdge());
//					System.out.println(((mxCell)cell).isVertex());
				}
			}
		});
	}
	
}
