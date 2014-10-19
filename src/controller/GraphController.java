/*
 * TODO:
 * Hashmap für vertices und edges anlegen um verwaltung zu vereinfachen
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.Pseudograph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;



public class GraphController implements ListenableMessages {
	
	private static 		GraphController 					instance;
	
	private				ListenableGraph<String, GKAEdge> 	jGraph;
	
	private				JGraphXAdapter<String, GKAEdge> 	jgxAdapter;
	private 			mxGraphComponent 					graphComponent;
	private 			List<String>						vertices;
	private 			List<GKAEdge>						edges;
	

	private GraphController() {
		createGraph();
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
//		createSampleSetup();
	}

	public static GraphController getInstance() {
		if (instance == null) {
			instance = new GraphController();
		}
		return instance;
	}
	
	private void createGraph() {
		// create an undirected graph
		jGraph = new ListenableUndirectedGraph<String, GKAEdge>(new Pseudograph<String, GKAEdge>(GKAEdge.class));
		
		/**
		 * // or a directed graph
		 * jGraph = new ListenableDirectedGraph<String, GKAEdge>(new DirectedPseudograph<String, GKAEdge>(GKAEdge.class));
		 */
		
		jgxAdapter = new JGraphXAdapter<String, GKAEdge>(getGraph());
		
		graphComponent = new mxGraphComponent(jgxAdapter);
		graphComponent.setSize(774, 326);
		
	}
	
	public void createSampleSetup() {
		
		String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add some sample data (graph manipulated via JGraphX)
        addVertex(v1);
        addVertex(v2);
        addVertex(v3);
        addVertex(v4);

        addEdge(v1, v2, new GKAEdge("Test", 1.0));
        addEdge(v2, v3, new GKAEdge("Test", 1.0));
        addEdge(v3, v1, new GKAEdge("Test", 1.0));
        addEdge(v4, v3, new GKAEdge("Test", 1.0));
        
        setGraphConfig();
        setLayout();
        getGraphComponent().setConnectable(false);
		getGraphComponent().setDragEnabled(false);
	}

	public void setLayout(){
		mxCircleLayout layout1 = new mxCircleLayout(getAdapter());
        layout1.execute(getAdapter().getDefaultParent());
		mxParallelEdgeLayout layout = new mxParallelEdgeLayout(getAdapter(), 50);
        layout.execute(getAdapter().getDefaultParent());
	}
	
	private void setGraphConfig(){
		getAdapter().setAllowDanglingEdges(false);
		getAdapter().setCellsDisconnectable(false);
		getAdapter().setDisconnectOnMove(false);
		getAdapter().setCellsEditable(false);
		getAdapter().setVertexLabelsMovable(false);
		getAdapter().setEdgeLabelsMovable(false);
		getAdapter().setConnectableEdges(false);
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
	
	public void addEdge(String source, String target, GKAEdge newEdge) {
		getGraph().addEdge(source, target, newEdge);
		sendMessage(source + " : " + target);
	}
	
	public boolean removeEdge(String source, String target) {
		return getGraph().removeEdge(getGraph().getEdge(source, target));
	}
	
	public boolean addVertex(String name) {
		return getGraph().addVertex(name);
	}
	
	public boolean removeVertex(String vertexName) {
		if (getGraph().removeVertex(vertexName)) {
			vertices.remove(vertexName);
			return true;
		};
		return false;
	}
	
    List<MessageListener> msgListerner = new ArrayList<>();
	@Override
	public void addMessageListener(MessageListener messageListener) {
		msgListerner.add(messageListener);
	}
	
	private void sendMessage(String message) {
		for (MessageListener ml : msgListerner) {
			ml.giveMessage(message);
		}
	}
}
