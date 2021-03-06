package controller;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GraphType;
import main.graphs.GKAVertex;
import main.graphs.algorithms.interfaces.FlowCalculator;
import main.graphs.algorithms.interfaces.PathFinder;

import org.jgrapht.ListenableGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import controller.interfaces.AdapterUpdateListener;
import controller.interfaces.CellListener;
import controller.interfaces.MessageListener;
import controller.interfaces.NodeListener;
import controller.interfaces.StatsListener;

public class GraphController implements GKAController {

	private		GKAGraph	gkaGraph;
	
	
	/**
	 * Konstruktor
	 */
	public GraphController() {
		// Zum Start einen Beispielgraphen erzeugen.
		setGraphWrapper(GKAGraph.valueOf());
	}
	
	
	/* (non-Javadoc)
	 * @see controller.GKAController#getGraph()
	 */
	@Override
	public ListenableGraph<GKAVertex, GKAEdge> getGraph() {
		return getGraphWrapper().getGraph();
	}
	
	/* (non-Javadoc)
	 * @see controller.MessageSender#addMessageListener(controller.MessageListener)
	 */
	@Override
	public void addMessageListener(MessageListener ml) {
		getGraphWrapper().addMessageListener(ml);
	}
	
	
	/* (non-Javadoc)
	 * @see controller.GKAController#newGraph(main.graphs.GraphType)
	 */
	@Override
	public void newGraph(GraphType graphType) {
		getGraphWrapper().newGraph(graphType);
	}
	
	/* (non-Javadoc)
	 * @see controller.GKAController#saveGraph(java.lang.String)
	 */
	@Override
	public void saveGraph(String filePath) {
		getGraphWrapper().saveGraph(filePath);
	}
	
	/* (non-Javadoc)
	 * @see controller.GKAController#saveGraph()
	 */
	@Override
	public void saveGraph() {
		getGraphWrapper().saveGraph();
	}
	
	/* (non-Javadoc)
	 * @see controller.GKAController#getGraphComponent()
	 */
	@Override
	public mxGraphComponent getGraphComponent() {
		return getGraphWrapper().getGraphComponent();
	}
	
	
	/* (non-Javadoc)
	 * @see controller.GKAController#getGraphWrapper()
	 */
	@Override
	public GKAGraph getGraphWrapper() {
		return gkaGraph;
	}
	
	/**
	 * @param gkaGraph
	 */
	private void setGraphWrapper(GKAGraph gkaGraph) {
		this.gkaGraph = gkaGraph;
	}
	
	/* (non-Javadoc)
	 * @see controller.GKAController#setCircleLayout()
	 */
	@Override
	public void setCircleLayout() {
		getGraphWrapper().setCircleLayout();
	}
	
	/* (non-Javadoc)
	 * @see controller.GKAController#setParallelEdgeLayout()
	 */
	@Override
	public void setParallelEdgeLayout() {
		getGraphWrapper().setParallelEdgeLayout();
	}

	/* (non-Javadoc)
	 * @see controller.GKAController#openGraph()
	 */
	@Override
	public void openGraph() {
		getGraphWrapper().openGraph();
	}

	/* (non-Javadoc)
	 * @see controller.CellSender#addCellListener(controller.CellListener)
	 */
	public void addCellListener(CellListener<mxCell> cellListener) {
		getGraphWrapper().addCellListener(cellListener);
	}


	/* (non-Javadoc)
	 * @see controller.AdapterUpdateSender#addAdapterUpdateListener(controller.AdapterUpdateListener)
	 */
	@Override
	public void addAdapterUpdateListener(AdapterUpdateListener adapterUpdateListener) {
		getGraphWrapper().addAdapterUpdateListener(adapterUpdateListener);
	}
	
	/* (non-Javadoc)
	 * @see controller.NodeSender#addNodeListener(controller.NodeListener)
	 */
	@Override
	public void addNodeListener(NodeListener NodeListener) {
		getGraphWrapper().addNodeListener(NodeListener);
	}

	@Override
	public void addVertex(String vertexName) {
		getGraphWrapper().addVertex(vertexName);
	}
	
	public void addVertex(GKAVertex vertex) {
		getGraphWrapper().addVertex(vertex);
	}
	

	public void addStatsListener(StatsListener statsListener) {
		getGraphWrapper().addStatsListener(statsListener);
	}


//	private void addEdge(String sourceVertex, String targetVertex, GKAEdge edge, boolean verbose) {
//		getGraphWrapper().addEdge(sourceVertex, targetVertex, edge, verbose);
//	}
//	
//	@Override
//	private void addEdge(String sourceVertex, String targetVertex, GKAEdge edge) {
//		getGraphWrapper().addEdge(sourceVertex, targetVertex, edge, true);
//	}
	
	@Override
	public void addEdge(String sourceVertex, String targetVertex, String newEdgeName) {
		getGraphWrapper().addEdge(sourceVertex, targetVertex, newEdgeName, true);
	}
	
	public void findShortestWay(PathFinder algorithm, String startVertex, String goalVertex) {
		getGraphWrapper().findShortestWay(algorithm, startVertex, goalVertex);
	}
	
	public Integer calculateMaxFlow(FlowCalculator algorithm, String sourceVertex, String sinkVertex) {
		return getGraphWrapper().calculateMaxFlow(algorithm, sourceVertex, sinkVertex);
	}
	
	public boolean isWeighted() {
		return getGraphWrapper().isWeighted();
	}
	
	public boolean isDirected() {
		return getGraphWrapper().isDirected();
	}
}
