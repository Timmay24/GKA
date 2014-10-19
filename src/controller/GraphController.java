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

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;



public class GraphController {
	
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
		createSampleSetup();
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
		
	}
	
	public void createSampleSetup() {
		
		String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add some sample data (graph manipulated via JGraphX)
        getGraph().addVertex(v1);
        getGraph().addVertex(v2);
        getGraph().addVertex(v3);
        getGraph().addVertex(v4);

//        getGraph().addEdge(v1, v2);
//        getGraph().addEdge(v2, v3);
//        getGraph().addEdge(v3, v1);
//        getGraph().addEdge(v4, v3);
        
//        addEdge(v1, v2);
//        addEdge(v2, v3);
//        addEdge(v3, v1);
//        addEdge(v4, v3);
        
//        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
//        layout.execute(jgxAdapter.getDefaultParent());
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
	
//	public mxGraph getMxGraph() {
//		return jgxAdapter;
//	}
	
	public void addEdge(String source, String target) {
//		GKAEdge newEdge = new GKAEdge(null);
//		newEdge.setSource(source);
//		newEdge.setTarget(target);
		edges.add( getGraph().addEdge(source, target) );
	}
	
	public boolean removeEdge(String source, String target) {
		return getGraph().removeEdge(getGraph().getEdge(source, target));
	}
	
	public boolean addVertex(String name) {
		if (getGraph().addVertex(name)) {
			vertices.add(name);
			return true;
		}
		return false;
	}
	
	public boolean removeVertex(String vertexName) {
		//TODO: unklar, ob das entfernen aus der liste so klappt, oder ob gesucht werden muss.
		
		if (getGraph().removeVertex(vertexName)) {
			vertices.remove(vertexName);
			return true;
		};
		return false;
	}
	
}
