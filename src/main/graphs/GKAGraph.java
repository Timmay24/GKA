package main.graphs;

import static com.google.common.base.Preconditions.*;
import gui.GraphPopUp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import main.graphs.algorithms.interfaces.FlowCalculator;
import main.graphs.algorithms.interfaces.PathFinder;
import main.graphs.exceptions.NoWayException;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.Pseudograph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphComponent.mxGraphControl;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import controller.FileHandler;
import controller.interfaces.AdapterUpdateListener;
import controller.interfaces.AdapterUpdateSender;
import controller.interfaces.CellListener;
import controller.interfaces.CellSender;
import controller.interfaces.MessageListener;
import controller.interfaces.MessageSender;
import controller.interfaces.NodeListener;
import controller.interfaces.NodeSender;
import controller.interfaces.SetListener;
import controller.interfaces.SetSender;
import controller.interfaces.StatsListener;
import controller.interfaces.StatsSender;


public class GKAGraph implements MessageSender, CellSender<mxCell>, AdapterUpdateSender, StatsSender, SetSender, NodeSender {
	
	private			ListenableGraph<GKAVertex, GKAEdge> jGraph;
	private			JGraphXAdapter<GKAVertex, GKAEdge> 	jgxAdapter;
	private 		mxGraphComponent 					graphComponent 			= null;
	private 		List<MessageListener>				msgListeners 			= new ArrayList<>();          
	private			List<CellListener<mxCell>>			cellListeners 			= new ArrayList<>();         
	private			List<AdapterUpdateListener>			adapterUpdateListeners 	= new ArrayList<>();
	private 		List<StatsListener> 				statsListeners 			= new ArrayList<>();        
	private 		List<NodeListener>					nodeListeners 			= new ArrayList<>();
	private			GraphType							graphType;

	private			long								edgeIdCounter = 0;
	private 		String								currentFilePath = null;
	public	final	String								UNDIRECTED_SYMBOL 	 = "--";
	public	final	String								DIRECTED_SYMBOL 	 = "->";
	public	final 	String 								COLOR_NEUTRAL_EDGE 	 = "6482b9";
	public	final 	String 								COLOR_NEUTRAL_VERTEX = "c3d9ff";
	public	final 	String 								COLOR_RED 			 = "ff0000";
	public	final 	String 								COLOR_YELLOW 		 = "ffff00";
	public	final 	String 								COLOR_GREEN			 = "00ff00";

	
	//TODO DEBUG UTIL
	private List<SetListener> setListeners = new ArrayList<>();
	
	
	/**
	 * Konstruktor mit Angabe des Graphentypen
	 * 
	 * @param graphType
	 */
	private GKAGraph(GraphType graphType) {
		newGraph(graphType);
	}

	/**
	 * Standart-Konstruktor
	 */
	private GKAGraph() {}

	/**
	 * Factory-Methode
	 * 
	 * @param graphType
	 * @return Neue Instanz der Klasse
	 */
	public static GKAGraph valueOf(GraphType graphType) {
		return new GKAGraph(graphType);
	}
	
	public static GKAGraph valueOf() {
		return new GKAGraph();
	}

	/**
	 * Erzeugt einen neuen Graphen. Der Graphentyp wird durch graphType
	 * uebermittelt.
	 * 
	 * @param graphType Legt den Typ des erzeugenden Graphen fest.
	 *                  Wird null uebergeben, wird ein Beispielgraph erzeugt.
	 * @return Automatisch erzeugte, zum Graphen gehoerige Graphenkomponente
	 */
	public void newGraph(GraphType graphType) {

		if (graphType == null) {
			createSampleGraph();
		} else {
		
			this.graphType = graphType;
	
			if (graphType.isDirected()) {
				jGraph = new ListenableDirectedGraph<>( new DirectedPseudograph<GKAVertex, GKAEdge>(GKAEdge.class));
			} else {
				jGraph = new ListenableUndirectedGraph<>( new Pseudograph<GKAVertex, GKAEdge>(GKAEdge.class));
			}
	
			jgxAdapter = new JGraphXAdapter<GKAVertex, GKAEdge>(jGraph);
	
			// Der Kantenstyle wird gesondert angepasst, sollte es sich um einen
			// gerichteten Graphen handeln
			if (!graphType.isDirected()) {
				jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_ENDARROW, "none");
			}
	
			createGraphComponent(jgxAdapter);
			setGraphConfig();
			
			// just to know...
//			for (String key : getAdapter().getStylesheet().getDefaultEdgeStyle().keySet()) {
//				System.out.println(key + " => " + getAdapter().getStylesheet().getDefaultEdgeStyle().get(key));
//			}
		}
	}
	
	/**
	 * BASEFUNC
	 * Generiert einen zufaelligen Graphen, mit variabler Anzahl
	 * von Knoten und Kanten.
	 * 
	 * @param desiredVertexCount Gewuenschte Knotenanzahl.
	 * @param desiredEdgeCount Gewuenschte Kantenanzahl.
	 */
	public void createRandomGraph(int desiredVertexCount, int desiredEdgeCount) {
		String desiredVertexCountString = (String) JOptionPane.showInputDialog(null, "Anzahl zu generierender Knoten:", "Zufallsgraph", JOptionPane.PLAIN_MESSAGE, null, null, desiredVertexCount);
		if (!desiredVertexCountString.matches("\\d+"))
			return;
		
		String desiredEdgeCountString = JOptionPane.showInputDialog("Anzahl zu generierender Kanten:", desiredEdgeCount);
		if (!desiredEdgeCountString.matches("\\d+"))
			return;
		
		GraphGenerator generator = new GraphGenerator(this, graphType, Integer.parseInt(desiredVertexCountString), Integer.parseInt(desiredEdgeCountString));
		new Thread(generator).start();
	}
	
	
	/**
	 * Standartkonfiguration fuer den Zufallsgraphen.
	 */
	public void createRandomGraph() {
		createRandomGraph(100, 6000);
	}
	
	
	/**
	 * Erzeugt eine GraphComponente die spaeter in der GUI eingebunden werden 
	 * kann
	 * 
	 * @param jgxAdapter JGraphXAdapter-Objekt
	 */
	private void createGraphComponent(JGraphXAdapter<GKAVertex, GKAEdge> jgxAdapter) {
		graphComponent = new mxGraphComponent(jgxAdapter);
		addMouseAdapter();
		sendAdapterUpdate(getGraphComponent()); // Gibt Listenern Bescheid, dass der Adapter erneuert wurde.
	}

	/**
	 * Erzeugt einen Beispielgraphen (gerichtet + gewichtet)
	 * 
	 * @return mxGraphComponent
	 */
	public void createSampleGraph() {

		if (true) {
			newGraph(GraphType.DIRECTED_UNWEIGHTED);
	
			addEdge("v1", "v2", "e1", false);
			addEdge("v1", "v3", "e2", false);
			addEdge("v1", "v3", "e4", false);
			addEdge("v4", "v1", "e3", false);
		}
			
		sendMessage("FERTIG: Beispiel-Graph erstellt!");
		sendMessage("--------------------------------\n");
		setLayout();
	}

	/**
	 * Grundeinstellungen fuer Adapter, Layout und Graphenkomponente
	 */
	private void setGraphConfig() {
		getAdapter().setAllowDanglingEdges(false);
		getAdapter().setCellsDisconnectable(false);
		getAdapter().setDisconnectOnMove(false);
		getAdapter().setCellsEditable(false); // true setzen, falls Einzelteile geaendert werden duerfen sollen
		getAdapter().setVertexLabelsMovable(false);
		getAdapter().setEdgeLabelsMovable(false);
		getAdapter().setConnectableEdges(false);

		setLayout();

		getGraphComponent().setSize(774, 326);
		getGraphComponent().setConnectable(false);
		getGraphComponent().setDragEnabled(false);
	}
	
	/**
	 * @param locked
	 */
	private void setElementsLocked(boolean locked) {
		getAdapter().setCellsLocked(locked);
	}

	/**
	 * ...Abkuerzung, da beide Layoutanpassungen meist zusammen und an mehreren
	 * Stellen aufgerufen werden.
	 */
	public void setLayout() {
		setCircleLayout();
//		setHierarchyLayout();
	}
	
	/**
	 * Ordnet den Graphen in einem kreisfoermigen Layout an.
	 */
	public void setCircleLayout() {
		mxCircleLayout layout = new mxCircleLayout(getAdapter());
		layout.setX0(150);
		layout.setY0(30);
		layout.execute(getAdapter().getDefaultParent());
		setParallelEdgeLayout();
	}
	
	/**
	 * Ordnet den Graphen in einem hierarchischem Layout an.
	 */
	public void setHierarchyLayout() {
		mxHierarchicalLayout layout = new mxHierarchicalLayout(getAdapter());
		layout.setInterHierarchySpacing(100);
        layout.setInterRankCellSpacing(100);
        layout.setIntraCellSpacing(100);
		layout.execute(getAdapter().getDefaultParent());
		setParallelEdgeLayout();
	}
	
	/**
	 * Einrichtung des Parallel-Edge Layouts (wie parallele Kanten angezeigt
	 * werden sollen).
	 */
	public void setParallelEdgeLayout() {
		new mxParallelEdgeLayout(getAdapter(), 50).execute(getAdapter().getDefaultParent());
	}

	/**
	 * @return Gibt die Graphenkomponente des Graphenadapters zurueck, die
	 *         wiederum in der GUI zur Anzeige eingebunden werden kann.
	 */
	public mxGraphComponent getGraphComponent() {
		return graphComponent;
	}

	/**
	 * @return Gibt den in diesem Wrapper enthalten Graphen zurueck.
	 */
	public ListenableGraph<GKAVertex, GKAEdge> getGraph() {
		return jGraph;
	}
	
	/**
	 * @return Gibt den Graphenadapter zurueck.
	 */
	public mxGraph getAdapter() {
		return jgxAdapter;
	}

	
	
	
	/**
	 * BASEFUNC addEdge (eigene Typisierung)
	 * 
	 * Fuegt dem Graphen eine Kante zwischen source und target hinzu.
	 * Fehlende Knoten werden vorher hinzugefuegt.
	 * 
	 * @param source Source-Knoten Objekt.
	 * @param target Target-Knoten Objekt.
	 * @param newEdge Neues Kantenobjekt
	 * @param verbose Gibt an, ob Warnhinweise ausgegebene werden sollen.
	 * @return true, wenn Kante hinzugefuegt.
	 */
	private boolean addEdge(GKAVertex source, GKAVertex target, GKAEdge newEdge, boolean verbose) {
		if (containsEdge(newEdge))
		{
			sendMessage("FEHLER: Kantenobjekt bereits im Graphen vorhanden.");
			return false;
		}
		
		if (newEdge.isWeighted() != this.isWeighted()) // pruefen, ob Kante und Graph kompatibel sind
		{
			sendMessage("FEHLER: Inkompatible Gewichtungstypen beim Hinzufügen der Kante.");
		}
		else
		{
			addVertex(source, verbose);
			addVertex(target, verbose);
			
			if (getGraph().addEdge(source, target, newEdge)) // Kante im Graphen hinzufuegen
			{
				sendMessage("ERFOLG: Kante erstellt zwischen " + source + " : " + target);
				return true;
			}
			else
			{
				sendMessage("FEHLER: Kante konnte konnte nicht erstellt werden (" + source + " : " + target + ")");
			}
		}
		return false;
	}
	
	/**
	 * addEdge (Mischtypisierung)
	 * 	Standarttypisiert: newEdgeName, newEdgeWeight 
	 * 
	 * @param source
	 * @param target
	 * @param newEdgeName
	 * @param newEdgeWeight
	 * @param verbose
	 * @return
	 */
	public boolean addEdge(GKAVertex source, GKAVertex target, String newEdgeName, Integer newEdgeWeight, boolean verbose) {
		return addEdge(source, target, GKAEdge.valueOf(newEdgeName, newEdgeWeight, edgeIdCounter++), verbose); 
	}
	
	public boolean addEdge(GKAVertex source, GKAVertex target, String newEdgeName, Integer newEdgeWeight) {
		return addEdge(source, target, GKAEdge.valueOf(newEdgeName, newEdgeWeight, edgeIdCounter++), true);
	}
	
	public boolean addEdge(GKAVertex source, GKAVertex target, String newEdgeName, boolean verbose) {
		return addEdge(source, target, GKAEdge.valueOf(newEdgeName, null, edgeIdCounter++), verbose); 
	}
	
	public boolean addEdge(GKAVertex source, GKAVertex target, String newEdgeName) {
		return addEdge(source, target, GKAEdge.valueOf(newEdgeName, null, edgeIdCounter++), true);
	}
	
	/**
	 * Delegiert an die BASEFUNC (eigene Typisierung) mit festgelegter Ausgabe von Warnungen.
	 */
	private boolean addEdge(GKAVertex source, GKAVertex target, GKAEdge newEdge) {
		return addEdge(source, target, newEdge, true);
	}
	
	/**
	 * Delegiert an BASEFUNC (Mischtypisierung) mit festgelegter Ausgabe von Warnungen.
	 */
	private boolean addEdge(String sourceName, String targetName, GKAEdge newEdge) {
		return addEdge(sourceName, targetName, newEdge, true);
	}
	
	
	/**
     * BASEFUNC addEdge (Standarttypisierung)
	 * 
	 * @param sourceName
	 * @param targetName
	 * @param newEdgeName
	 * @param newEdgeWeight
	 * @param verbose
	 * @return
	 */
	public boolean addEdge(String sourceName, String targetName, String newEdgeName, Integer newEdgeWeight, boolean verbose) {
		// Hinzufuegen einer gewichteten Kante nur in einem gewichteten Graphen zulaessig
		if (newEdgeWeight != null)
			checkState(this.isWeighted());
		
		return addEdge(sourceName, targetName, GKAEdge.valueOf(newEdgeName, newEdgeWeight, edgeIdCounter++), verbose);
	}
	
	// newEdgeWeight fest
	public boolean addEdge(String sourceName, String targetName, String newEdgeName, boolean verbose) {
		return addEdge(sourceName, targetName, newEdgeName, null, verbose);
	}
	
	// verbose fest
	public boolean addEdge(String sourceName, String targetName, String newEdgeName, Integer newEdgeWeight) {
		return addEdge(sourceName, targetName, newEdgeName, newEdgeWeight, true);
	}
	
	// newEdgeWeight und verbose fest
	public boolean addEdge(String sourceName, String targetName, String newEdgeName) {
		return addEdge(sourceName, targetName, newEdgeName, null, true);
	}
	
	
	/**
	 * BASEFUNC addEdge (Mischtypisierung)
	 * 	Standarttypisiert: sourceName, targetName 
	 * 
	 * Nachdem die zu den Namen passenden Objekte ermittelt wurden,
	 * wird an die Basisfunktion delegiert.
	 * 
	 * @param sourceName Name des gesuchten Source-Knotens.
	 * @param targetName Name des gesuchten Target-Knotens.
	 * @param newEdge
	 * @param verbose Gibt an, ob Warnhinweise ausgegebene werden sollen.
	 * @return true, wenn Kante hinzugefuegt.
	 */
	private boolean addEdge(String sourceName, String targetName, GKAEdge newEdge, boolean verbose) {
		GKAVertex source = getVertex(sourceName);
		GKAVertex target = getVertex(targetName);

		if (source == null) {
			source = GKAVertex.valueOf(sourceName);
		}
		
		if (target == null) {
			target = GKAVertex.valueOf(targetName);
		}

		return addEdge(source, target, newEdge, verbose);
	}
	
	
	
	
	
	
	/**
	 * BASEFUNC
	 * Entfernt die uebergebene Kante aus dem Graphen.
	 * (Delegiert an removeEdge von JGraph)
	 * 
	 * @param edge Zu entfernende Kante.
	 * @return true, wenn erfolgreich.
	 */
	public boolean removeEdge(GKAEdge edge) {
		if (edge == null)
			return false;
		
		if (getGraph().removeEdge(edge)) {
			sendMessage("ERFOLG: Kante " + edge + " entfernt (" + edge.getSource() + " : " + edge.getTarget() + ").");
			return true;
		}
		else
		{
			sendMessage("FEHLER: Kante " + edge + " konnte nicht entfernt werden (" + edge.getSource() + " : " + edge.getTarget() + ").");
			return false;
		}
	}
	
	/**
	 * BASEFUNC
	 * Entfernt die zwischen source und target zuerst gefundene Kante.
	 * 
	 * @param source
	 * @param target
	 * @return true, wenn erfolgreich.
	 */
	public boolean removeEdge(GKAVertex source, GKAVertex target) {
		if (source == null || target == null)
			return false;
		
		// moeglicherweise wird hier nicht die gewuenschte Kante entfernt,
		// da nur durch source und target Knoten eine dazwischen vorhandene
		// Kante ermittelt wird.
		if (getGraph().removeEdge(getGraph().getEdge(source, target)))
		{
			sendMessage("ERFOLG: Kante entfernt (" + source + " : " + target + ").");
			return true;
		}
		else
		{
			sendMessage("FEHLER: Kante konnte nicht entfernt werden (" + source + " : " + target + ").");
			return false;
		}
	}
	
	/**
	 * Entfernt die Kante (Referenz wird anhand des Namens beschafft).
	 * 
	 * @param edgeName Name der zu entfernenden Kante.
	 * @return true, wenn erfolgreich.
	 */
	public boolean removeEdge(String edgeName) {
		if (edgeName == null) return false;
		return removeEdge( getEdge(edgeName) );
	}
	
	
	
	/**
	 * BASEFUNC
	 * Ermittelt das Kantenobjekt zwischen Source- und Targetknoten.
	 * 
	 * @param edgeName Kante 
	 * @return Kantenobjekt zwischen Source- und Targetknoten.
	 */
	public GKAEdge getEdge(GKAVertex source, GKAVertex target) {
		return getGraph().getEdge(source, target);
	}

	/**
	 * Delegiert nach Ermittlung der Objekte, an die Basisfunktion.
	 * 
	 * @param sourceName Source Knoten
	 * @param targetName Target Knoten
	 * @return Kantenobjekt 
	 */
	public GKAEdge getEdge(String sourceName, String targetName) {
		GKAVertex source, target;
		source = getVertex(sourceName);
		target = getVertex(targetName);

		return getEdge(source, target);

		// Alternative, falls das Uebergeben von null-Referenzen an
		// getGraph().getEdge() fehlschlaegt.
//		if (source != null && target != null) {
//			return getEdge(source, target);
//		}
//
//		return null;
	}
	
	/**
	 * BASEFUNC
	 * Ermittelt anhand des Namens das Kantenobjekt und gibt es zurueck.
	 * 
	 * @param edgeName Name des gesuchten Kantenobjekts.
	 * @return Kantenobjekt
	 */
	public GKAEdge getEdge(String edgeName) {
		for (GKAEdge edge : getGraph().edgeSet())
		{
			if (edge.getName().equals(edgeName))
			{
				return edge;
			}
		}
		return null;
	}
	
	//TODO doc
	public Set<GKAEdge> getEdges(String... edgeNames) {
		Set<GKAEdge> edges = new HashSet<>();
		
		for (String name : edgeNames) {
			GKAEdge edge = getEdge(name);
			if (edge != null)
				edges.add(edge);
		}
		return edges;
	}
	
	//TODO doc
	public GKAEdge getEdgeBy(long uniqueId) {
		for (GKAEdge edge : getGraph().edgeSet())
		{
			if (edge.getUniqueId() == uniqueId)
			{
				return edge;
			}
		}
		return null;
	}
	
	//TODO doc
	public Set<GKAEdge> getEdgesBy(long... edgeIds) {
		Set<GKAEdge> edges = new HashSet<>();
		
		for (long id : edgeIds) {
			GKAEdge edge = getEdgeBy(id);
			if (edge != null)
				edges.add(edge);
		}
		return edges;
	}
	
	/**
	 * @return Eine Map, ueber die man auf alle Edges anhand ihrer UniqueIds zugreifen kann 
	 */
	public Map<Long, GKAEdge> getEdgeMap() {
		Map<Long, GKAEdge> resultMap = new HashMap<>();
		
		for (GKAEdge edge : getGraph().edgeSet())
		{
			resultMap.put(edge.getUniqueId(), edge);
		}
		return resultMap;
	}
	
	
	
	//TODO
	public Collection<GKAEdge> outgoingEdgesOf(GKAVertex source) {
		Set<GKAEdge> edges = new HashSet<>();
		
		for (GKAEdge edgeOfSource : this.getGraph().edgesOf(source)) {
			
			if (source.equals( (GKAVertex) edgeOfSource.getSource() ) && // Wenn source als Source in Kante eingetragen ist 
			   !source.equals( (GKAVertex) edgeOfSource.getTarget() )) { // und nicht gleichezeitig als Target (Selbstbezug-Ausschluss)
				
				edges.add(edgeOfSource);								 // dann ist edgeOfSource eine ausgehende Kante von source
			}
			
		}
		
		return edges;
	}
	
	
	
	
	
	/**
	 * Prueft, ob die Kante edge im Graphen enthalten ist.
	 * 
	 * @param edge
	 * @return true, wenn Kante enthalten.
	 */
	public boolean containsEdge(GKAEdge edge) {
		return getGraph().containsEdge(edge);
	}
	
	/**
	 * Prueft anhand ihres Namens, ob eine Kante existiert und gibt sie ggf. zurueck.
	 * 
	 * @param edgeName Name der gesuchten Kante.
	 * @return true, wenn Kante enthalten.
	 */
	public boolean containsEdge(String edgeName) {
		return getEdge(edgeName) != null;
	}
	
	
	
	/**
	 * BASEFUNC
	 * Faerbt die gewaehlte Kante mit der gewaehlten Farbe ein.
	 * 
	 * @param edge Einzufaerbende Kante
	 * @param colorCode Farbcode
	 */
	public void colorVertex(GKAVertex vertex, String colorCode) {
		if (vertex != null) {
			getAdapter().getModel().setStyle(
					jgxAdapter.getVertexToCellMap().get(vertex),
					"fillColor="+colorCode);
		}
	}
	
	public void colorVertex(String vertexName, String colorCode) {
		if (vertexName != null) {
			colorVertex(getVertex(vertexName), colorCode);
		}
	}
	
	public void colorVertexStart(GKAVertex vertex) {
		colorVertex(vertex, COLOR_YELLOW);
	}
	
	public void colorVertexStart(String vertexName) {
		if (vertexName != null) {
			colorVertexStart(getVertex(vertexName));
		}
	}
	
	public void colorVertexEnd(GKAVertex vertex) {
		colorVertex(vertex, COLOR_GREEN);
	}
	
	public void colorVertexEnd(String vertexName) {
		if (vertexName != null) {
			colorVertexStart(getVertex(vertexName));
		}
	}
	
	/**
	 * BASEFUNC
	 * Faerbt die gewaehlte Kante mit der gewaehlten Farbe ein.
	 * 
	 * @param edge Einzufaerbende Kante
	 * @param colorCode Farbcode
	 */
	public void colorEdge(GKAEdge edge, String colorCode) {
		if (edge != null) {
			getAdapter().getModel().setStyle(
					jgxAdapter.getEdgeToCellMap().get(edge),
					"strokeColor="+colorCode);
		}
	}
	
	public void colorEdge(String edgeName, String colorCode) {
		GKAEdge edge = getEdge(edgeName);
		
		if (edge != null) {
			colorEdge(edge, colorCode);
		} else {
			System.err.println("Kante einfaerben fehlgeschlagen.");
			System.err.println("edge == " + edge);
		}
	}
	
	/**
	 * Faerbt die uebergebene Kante rot ein.
	 * 
	 * @param edge Einzufaerbende Kante.
	 */
	public void colorEdgeRed(GKAEdge edge) {
		colorEdge(edge, COLOR_RED);
	}
	
	/**
	 * Setzt die Faerbung aller Kanten zurueck.
	 */
	public void resetEdgeColors() {
		for (GKAEdge edge : getGraph().edgeSet()) {
			colorEdge(edge, COLOR_NEUTRAL_EDGE);
		}
	}
	
	/**
	 * Setzt die Faerbung aller Knoten zurueck.
	 */
	public void resetVertexColors() {
		for (GKAVertex vertex : getGraph().vertexSet()) {
			colorVertex(vertex, COLOR_NEUTRAL_VERTEX);
		}
	}
	
	/**
	 * Setzt alle Einfaerbungen zurueck.
	 */
	public void resetColors() {
		resetEdgeColors();
		resetVertexColors();
	}
	
	
	

	/**
	 * BASEFUNC
	 * Fuegt dem Graphen den uebergebenen Knoten hinzu.
	 * 
	 * @param vertex
	 * @param verbose Gibt an, ob Warnungen gemeldet werden sollen.
	 * @return true, wenn Knoten hinzugefuegt.
	 */
	public boolean addVertex(GKAVertex vertex, boolean verbose) {
		checkNotNull(vertex);
//		if (vertex == null)
//		{
//			sendMessage("FEHLER: null-Knotenobjekte koennen nicht hinzugefuegt werden!");
//			return false;
//		}
		if (getGraph().containsVertex(vertex))
		{
			if (verbose)
			{
				sendMessage("WARNUNG: Knoten " + vertex.getName() + " existiert bereits.");
			}
		}
		else
		{
			if (getGraph().addVertex(vertex))
			{
				sendMessage("ERFOLG: Knoten " + vertex + " hinzugefuegt.");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Delegiert, mit standartmaessigem Melden von Warnungen, an die Basisfunktion.
	 * 
	 * @param vertex
	 * @return
	 */
	public boolean addVertex(GKAVertex vertex) {
		return addVertex(vertex, true);
	}
	
	/**
	 * Delegiert, nach Kreation des Knotens anhand eines Namens, an die Basisfunktion.
	 * 
	 * @param vertexName
	 * @param verbose Gibt an, ob Warnung gemeldet werden sollen.
	 * @return
	 */
	public boolean addVertex(String vertexName, boolean verbose) {
		return addVertex(GKAVertex.valueOf(vertexName), verbose);
	}
	
	/**
	 * Delegiert, mit standartmaessigem Melden von Warnungen, an die Basisfunktion.
	 * 
	 * @param vertexName
	 * @return
	 */
	public boolean addVertex(String vertexName) {
		return addVertex(vertexName, true);
	}


	

	/**
	 * BASEFUNC
	 * Entfernt das Knotenobjekt aus dem Graphen.
	 * 
	 * @param vertex Knotenobjekt.
	 * @return true, wenn Knoten entfernt.
	 */
	public boolean removeVertex(GKAVertex vertex) {
		if (vertex == null) {
			return false;
		}
		if (!containsVertex(vertex)) {
			sendMessage("HINWEIS: Der Knoten" + vertex + " existiert nicht.");
			return false;
		}
		if (getGraph().removeVertex(vertex)) {
			sendMessage("ERFOLG: Knoten " + vertex + " entfernt.");
			
//			reportVsAndEs(); //TODO DEBUG UTIL
			
			return true;
		} else {
			sendMessage("FEHLER: Knoten " + vertex + " konnte nicht entfernt werden.");
			return false;
		}
	}
	
	/**
	 * Ermittelt das Knotenobjekt anhand seines Namends und
	 * delegiert anschliessend an die Basisfunktion.
	 * 
	 * @param vertexName Knotenname.
	 * @return
	 */
	public boolean removeVertex(String vertexName) {
		return removeVertex(getVertex(vertexName));
	}
	
	/**
	 * BASEFUNC
	 * Prueft, ob der Knoten vertex im Graphen enthalten ist.
	 * 
	 * @param vertex
	 * @return true, wenn Knoten im Graphen enthalten.
	 */
	public boolean containsVertex(GKAVertex vertex) {
		return getGraph().vertexSet().contains(vertex);
	}

	
	
	/**
	 * Ermittelt das Knotenobjekt anhand seines Namends und
	 * delegiert anschliessend an die Basisfunktion.
	 * 
	 * @param vertexName Knotenname.
	 * @return
	 */
	public boolean containsVertex(String vertexName) {
		return getVertex(vertexName) != null;
	}
	
	/**
	 * @param vertexName
	 * @return Ermittelt das Knotenobjekt anhand seines Namens.
	 */
	public GKAVertex getVertex(String vertexName) {
		for (GKAVertex vertex : getGraph().vertexSet()) {
			if (vertex.getName().equals(vertexName)) {
				return vertex;
			}
		}
		return null;
	}
	
	
	/**
	 * Notiz vorab: Gibt es parallele Kanten, wuerden sich duplizierte
	 * Adjazenten in der Ergebnisliste wiederfinden => daher,
	 * statt Liste ein Set verwendet.
	 * 
	 * Ermittelt alle Adjazenten eines Knoten. Beim ungerichteten Graphen sind
	 * alle Knoten Adjazenten des Ausgangsknoten, sobald zwischen ihnen eine
	 * Kante existiert. Beim gerichteten Graphen sind Knoten des Ausgangsknoten
	 * nur dann Adjazenten, wenn an ihnen eine eingehende Kante vom
	 * Ausgangsknoten aus anliegt. Eingehende Kanten am Ausgangsknoten zaehlen
	 * nicht.
	 * Intern wird anhand des Graphentypen (gerichtet / ungerichtet) ueber die
	 * Ermittlungsmethode entschieden, wenn die ueberladene Methode ohne directed
	 * aufgerufen wird.
	 * 
	 * @param sourceVertex Ausgangsknoten, dessen Adjazenten ermitteln werden sollen.
	 * @param directed Gibt an, ob nach Richtlinie von gerichteten oder ungerichteten
	 *        Graphen ermittelt werden soll.
	 * @return Collection aller Adjazenten des Ausgangsknotens.
	 */
	public Collection<GKAVertex> getAllAdjacentsOf(GKAVertex sourceVertex, boolean directed) {
		checkNotNull(sourceVertex);
		
		Set<GKAVertex> resultList = new HashSet<>();

		Set<GKAEdge> incidentEdges = getGraph().edgesOf(sourceVertex);	// Alle am Knoten sourceVertex anliegenden Kanten ermitteln
		
		for (GKAEdge edge : incidentEdges)								// Iteration ueber alle Kanten, die mit sourceVertex verbunden sind
		{
			if (edge.getSource() != edge.getTarget()) {          		// Schlaufen ausschliessen Source != Target
				
				if (directed)                                       	// Sonderregelung fuer gerichtete Graphen:                      
				{														// Nur adjazente Knoten in die Ergebnisliste stecken,           
					if (edge.getSource() == sourceVertex) {     		// zu denen man vom Knoten sourceVertex aus kommen kann.        
						resultList.add((GKAVertex)edge.getTarget());  	// D.h. Adjazenten hinter eingehenden Kanten werden ausgenommen.
					}			
				}
				else
				{
					if (edge.getSource() == sourceVertex) {				// Ist sourceVertex Source der Kante,
						resultList.add((GKAVertex)edge.getTarget());	// dann ist Target der Adjazent.
					} else if (edge.getTarget() == sourceVertex) {		// Ist sourceVertex Target der Kante,
						resultList.add((GKAVertex)edge.getSource());	// dann ist Source der Adjazent.
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * Verkuerzte Version, bei der von einem gerichteten Graphen ausgegangen wird.
	 */
	public Collection<GKAVertex> getAllAdjacentsOf(GKAVertex sourceVertex) {
		return getAllAdjacentsOf(sourceVertex, isDirected());
	}
	
	
	/**
	 * @param algorithm
	 * @param startVertex
	 * @param goalVertex
	 * @return
	 */
	public List<GKAVertex> findShortestWay(PathFinder algorithm, String startVertex, String goalVertex) {
		GKAVertex start = getVertex(startVertex);
		GKAVertex goal = getVertex(goalVertex);
		
		if (start != null && goal != null) {
			return findShortestWay(algorithm, start, goal);
		} else {
			sendMessage("FEHLER: Start- oder Zielknoten ungültig oder nicht existent.");
			return null;
		}
	}
	
	/**
	 * @param algorithm
	 * @param sourceVertex Quelle
	 * @param sinkVertex Senke
	 * @return 
	 */
	public Integer calculateMaxFlow(FlowCalculator algorithm, String sourceVertex, String sinkVertex) {
		checkNotNull(sourceVertex);
		checkNotNull(sinkVertex);
		
		return new Integer(0); // STUB
	}
	
	/**
	 * Laesst im Graphen die kuerzesten Weg zwischen Start- und Zielknoten suchen.
	 * 
	 * @param start Startknoten.
	 * @param goal Zielknoten.
	 */
	public List<GKAVertex> findShortestWay(PathFinder algorithm, GKAVertex start, GKAVertex goal) {
		checkNotNull(start);
		checkNotNull(goal);
		
		List<GKAVertex> way = null;
		resetColors();

		try {
			
			way = algorithm.findShortestWay(this, start, goal);
			
			if (way.isEmpty())
				return way;
		
			sendMessage("\nKürzester Weg:");
			String wayString = "";
			GKAVertex tempNodeA = null, tempNodeB = null;
			for (GKAVertex v : way) {
				wayString += v.toString();
				wayString += " -> ";
				
				//// Code Fragment zum 
				// Knoten durchschieben
				tempNodeA = tempNodeB;
				tempNodeB = v;
				
				// wenn zwei Knoten angewahlt wurden
				if (tempNodeA != null && tempNodeB != null) {
					// inzidente Kante holen
					GKAEdge tempEdge = getGraph().getEdge(tempNodeA, tempNodeB);
					if (tempEdge != null) {
						// und einfaerben
						colorEdgeRed(tempEdge);
					}
				}
				// wenn tempNodeA noch null ist, wurde gerade der erste
				// Knoten geholt => Startknoten
//				if (tempNodeA == null) {
					colorVertexStart(tempNodeB);
//					// diesen einfaerben
//					// ... TODO
//				}
			}
			// zuletzt angewaehlten Knoten einfaerben (Zielknoten)
			// ... TODO
			colorVertexEnd(tempNodeB);
			
			sendMessage(wayString.substring(0, wayString.length() - 4));
			sendMessage("\n");
			
			return way;
		} catch (IllegalStateException | NoWayException e) {
			e.printStackTrace();
			sendMessage("FEHLER: " + e.getMessage());
			return null;
		}
	}
	
		
	/**
	 * Gibt alle Kanten des Graphen aus.
	 */
	public void printAllEdges() {
		sendMessage("--Kanten--");
		for (GKAEdge edge : getGraph().edgeSet()) {
			sendMessage(edge.toString());
		}
	}

	/**
	 * Gibt alle Knoten des Graphen aus.
	 */
	public void printAllVertices() {
		sendMessage("--Knoten--");
		for (GKAVertex vertex : getGraph().vertexSet()) {
			sendMessage(vertex.toString());
		}
	}
	
	/**
	 * Gibt alle Elemente des Graphen aus.
	 */
	public void printAllGraphElems() {
		printAllVertices();
		printAllVertices();
	}
	
	/**
	 * @return Graphenkontrolleinheit (genutzt fuer addMouseAdapter)
	 */
	public mxGraphControl getGraphControl() {
		return getGraphComponent().getGraphControl();
	}

	/**
	 * Meldet eine Objekt zum Empfang von Nachrichten an.
	 * 
	 * @param ml Referenz des Objekts, das Nachrichten erhalten soll
	 */
	@Override
	public void addMessageListener(MessageListener ml) {
		msgListeners.add(ml);
	}

	/**
	 * Setzt eine Nachricht an alle angemeldeten MessageListener ab
	 * 
	 * @param message Abzusetzende Nachricht
	 */
	public void sendMessage(String message) {
		for (MessageListener ml : msgListeners) {
			ml.receiveMessage(message);
		}
	}
	
	

	/**
	 * Meldet an der Graphenkontrolleinheit einen MouseListener an, um
	 * Maus-Interaktionen mit der visuellen Graphenkomponente abzufangen.
	 */
	public void addMouseAdapter() {
		final GKAGraph wrapper = this;
		getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Object cell = getGraphComponent().getCellAt(e.getX(), e.getY());
				
				if (e.getButton() == 3) {
					new GraphPopUp(wrapper, getGraphComponent(), e, (mxCell)cell);
					if (cell != null && cell instanceof mxCell) {
						sendCell((mxCell) cell, e);		// Weiterleiten der Zelle an alle angemeldeten CellListeners
					}
				}
			}
		});
	}
	
	/**
	 * @return Praedikat gibt an, ob der Graph gerichtet ist.
	 */
	public boolean isDirected() {
		return graphType.isDirected();
	}
	
	/**
	 * @return Praedikat gibt an, ob der Graph gewichtet ist.
	 */
	public boolean isWeighted() {
		return graphType.isWeighted();
	}
	
    /**
	 * Laedt einen Graphen aus einer GKA-Datei.
	 */
	public void openGraph(String filePath) {
		try {
			File inFile = null;
			if (filePath == null) {
				inFile = FileHandler.getInFile();				// Dateiobjekt holen
			} else {
				inFile = new File(filePath);
			}
			List<String> input = FileHandler.readFile(inFile);	// Datei auslesen
			if (parseFile(input)) {								// Ergebnisliste parsen
				currentFilePath = inFile.getAbsolutePath();		// bei Erfolg: Dateipfad merken
				setLayout();
			};
			sendMessage("ERFOLG: Graphendatei geladen.");
			sendMessage("ERFOLG: " + "\"" + currentFilePath + "\"");
			
		} catch (IOException e) {
			e.printStackTrace();
			sendMessage("FEHLER: " + e.getMessage());
			sendMessage("FEHLER: Graph öffnen fehlgeschlagen.");
		}
		sendMessage("/pbe");
		sendMessage("/gps");
	}

    /**
	 * Konfiguration: FileChooser oeffnen.
	 */
	public void openGraph() {
		openGraph(null);
	}
	
	/**
	 * BASEFUNC
	 * Speichert den aktuellen Graphen in einer Datei.
	 * 
	 * @param filePath Zielpfad der Datei.
	 */
	public void saveGraph(String filePath) {
		File outFile = null;
		try {
			outFile = FileHandler.getOutFile(filePath);	// Holt Fileobjekt zum angeg. Dateipfad.
		} catch (IOException e) {
			e.printStackTrace();
			sendMessage("FEHLER: " + e.getMessage());
			sendMessage("FEHLER: Graph konnte nicht gespeichert werden.");
		}
		List<String> output = parseGraph();	// Speichert den geparsten Graph in einer Liste.
		try {
			if (FileHandler.writeToFile(output, outFile)) {	// schreibt die Liste in eine Datei.
				currentFilePath = outFile.getAbsolutePath();
				sendMessage("ERFOLG: Graph gespeichert.");
				sendMessage("ERFOLG: " + "\"" + outFile.getAbsolutePath() + "\"");
			}
		} catch (IOException e) {
			e.printStackTrace();
			sendMessage("FEHLER: " + e.getMessage());
			sendMessage("FEHLER: Graph konnte nicht gespeichert werden.");
		};
		sendMessage("/pbe");
	}
	
	/**
	 * Abkuerzung, bei der ein aktueller Graph direkt gespeichert werden kann,
	 * ohne den Dateipfad waehlen zu muessen, sofern der aktuelle Graph bereits
	 * zuvor gespeichert wurde und ein Dateipfad (currentFilePath) hinterlegt ist.
	 */
	public void saveGraph() {
		saveGraph(currentFilePath);
	}
	
	
	/**
	 * Parst alle Zeilen der Input-Liste und generiert daraus einen Graphen.
	 * 
	 * @param input Liste aller aus einer Datei gescannten Zeilen.
	 * @return true, wenn Methode erfolgreich.
	 */
	private boolean parseFile(List<String> input) {
		String regex = "^([a-zA-Z0-9üÜäÄöÖ]+) ?((-[>|-]) ?([a-zA-Z0-9üÜäÄöÖ]+) ?([a-zA-Z0-9üÜäÄöÖ]+)?)? ?( ?: ?([0-9]+))?;$";
		Pattern p = Pattern.compile(regex);
		Matcher m;
		GraphType graphType = null;
		Integer tempEdgeNo = 0;
		
		long startTime = System.nanoTime();
		int lineCount = 0;
		
		sendMessage("/gph");
		sendMessage("/pbi " + input.size());
		
		for (String line : input) {
			m = p.matcher(line);
			
			if (m.find()) {
				/**
				 * group id schluessel:
				 * 1: Vertex Source
				 * 3: EdgeSymbol
				 * 4: Vertex Target
				 * 5: EdgeName
				 * 7: EdgeWeight
				 */
				
				String  source 		= m.group(1); // Source
				String  edgeSym 	= m.group(3); // EdgeSym
				String  target 		= m.group(4); // Target
				String  edgeName 	= m.group(5); // EdgeName
				String  edgeWeight 	= m.group(7); // EdgeWeight
				Integer parsedEdgeWeight = null;
				
				// Falls Graphtyp noch nicht festgelegt:
				// Ermittlung anhand der ersten gueltigen Zeile.
				if (graphType == null) {
					if (edgeSym.equals(DIRECTED_SYMBOL)) {
						if (edgeWeight == null) {
							graphType = GraphType.DIRECTED_UNWEIGHTED;
						} else {
							graphType = GraphType.DIRECTED_WEIGHTED;
						}
					} else if (edgeSym.equals(UNDIRECTED_SYMBOL)) {
						if (edgeWeight == null) {
							graphType = GraphType.UNDIRECTED_UNWEIGHTED;
						} else {
							graphType = GraphType.UNDIRECTED_WEIGHTED;
						}
					}
					newGraph(graphType);
				}
				
				if (edgeWeight != null) parsedEdgeWeight = Integer.parseInt(edgeWeight);
				
				if (edgeName == null) {
					edgeName = "e" + tempEdgeNo.toString();
					tempEdgeNo++;
				}
				
				if (edgeSym == null) {
					addVertex(source, false);
				} else {
					addEdge(source, target, edgeName, parsedEdgeWeight, false);
				}
			}
			sendMessage("/pbu " + ++lineCount);
		}
		sendMessage("ERFOLG: Benoetigte Zeit: " + String.valueOf((System.nanoTime() - startTime) / 1E9D) + " Sekunden");
		return true;
	}
	
	/**
	 * @return Typen des Graphen.
	 */
	public GraphType getGraphType() {
		return graphType;
	}
	
	/**
	 * Rechnet den Graphen in eine String-basierte Liste um.
	 */
	public List<String> parseGraph() {
		List<String> resultList = new ArrayList<>();
		String edgeSym = (isDirected())?(DIRECTED_SYMBOL):(UNDIRECTED_SYMBOL);
		String outLine = null;
		Set<GKAVertex> singleVertices = new HashSet<>(getGraph().vertexSet());
		
		long startTime = System.nanoTime();
		int elemsProcessed = 0;
		sendMessage("/pbi " + getGraph().edgeSet().size());
		
		// zuerst Kanten
		for (GKAEdge e : getGraph().edgeSet()) {
			outLine = e.getSource() + " " + edgeSym + " " + e.getTarget() + ((e.getName()==null)?(" "):(" " + e.getName())) + ((isWeighted())?(" : " + e.getWeight()):("")) + ";";
			resultList.add(outLine);
			singleVertices.remove(e.getSource());
			singleVertices.remove(e.getTarget());
			sendMessage("/pbu " + ++elemsProcessed);
		}
		
//		// danach alleinstehende Knoten
//		for (GKAVertex v : getGraph().vertexSet()) {
//			if (getAllAdjacentsOf(v, false).isEmpty()) {
//				resultList.add(v.getName() + ";");
//				sendMessage("/pbu " + ++elemsProcessed);
//			}
//		}
		elemsProcessed = 0;
		sendMessage("/pbi " + singleVertices.size());
		
		// danach alleinstehende Knoten
		for (GKAVertex v : singleVertices) {
			resultList.add(v.getName() + ";");
				sendMessage("/pbu " + ++elemsProcessed);
		}
		
		sendMessage("ERFOLG: Benoetigte Zeit: " + String.valueOf((System.nanoTime() - startTime) / 1E9D) + " Sekunden");
		return resultList;
	}
	
	/* (non-Javadoc)
	 * @see controller.CellSender#addCellListener(controller.CellListener)
	 */
	@Override
	public void addCellListener(CellListener<mxCell> cellListener) {
		this.cellListeners.add(cellListener);
	}
	
	/**
	 * 
	 * Methode zum Versenden der ermittelten Zelle an angemeldeten alle Interessenten.
	 * 
	 * @param cell Ermittelte Zelle.
	 * @param e Ausloesendes Mausevent.
	 */
	private void sendCell(mxCell cell, MouseEvent e) {
		for (CellListener<mxCell> cl : cellListeners) {
			cl.receiveCell(cell, e);
		}
	}

	@Override
	public void addAdapterUpdateListener( AdapterUpdateListener adapterUpdateListener) {
		if (adapterUpdateListener != null)
			adapterUpdateListeners.add(adapterUpdateListener);
	}
	
	private void sendAdapterUpdate(mxGraphComponent graphComponent) {
		for (AdapterUpdateListener aul : adapterUpdateListeners) {
			aul.receiveAdapterUpdate(graphComponent);
		}
	}
	
	@Override
	public void addStatsListener( StatsListener statsListener) {
		if (statsListener != null)
			statsListeners.add(statsListener);
	}
	
	public void sendStats(String... stats) {
		for (StatsListener sl : statsListeners) {
			sl.receiveStats(stats);
		}
	}
//	public void sendStats(Map<String, String> stats) {
//		for (StatsListener sl : statsListeners) {
//			sl.receiveStats(stats);
//		}
//	}
	
	/**
	 * @param vertexName
	 */
	public void setStartNode(String vertexName) {
		if (containsVertex(vertexName)) {
			for (NodeListener nl : nodeListeners) {
				nl.receiveStartNode(vertexName);
			}
		}
	}
	
	/**
	 * @param vertexName
	 */
	public void setEndNode(String vertexName) {
		if (containsVertex(vertexName)) {
			for (NodeListener nl : nodeListeners) {
				nl.receiveEndNode(vertexName);
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || !(other instanceof GKAGraph))
			return false;
		
		GKAGraph graph = (GKAGraph) other;
		
		if (this.getGraphType() != graph.getGraphType())
			return false;
		
		if (this.getGraph().edgeSet().size() != graph.getGraph().edgeSet().size())
			return false;
		
		if (this.getGraph().vertexSet().size() != graph.getGraph().vertexSet().size())
			return false;
		
		if (!this.getGraph().edgeSet().containsAll(graph.getGraph().edgeSet()))
			return false;
		
		if (!this.getGraph().vertexSet().containsAll(graph.getGraph().vertexSet()))
			return false;
		
		return true;
	}
	
	
	
	/**
	 *	TODO
	 *	DEBUG UTILS 
	 */
	
	private void reportVsAndEs() {
		sendSetLine(null);
		sendSetLine("Edges--------------------");
		
		for (GKAEdge e : getGraph().edgeSet())
			sendSetLine(e.getName());
		
		sendSetLine("Vertices-----------------");
		for (GKAVertex v : getGraph().vertexSet())
			sendSetLine(v.getName());
		
		sendSetLine("-------------------------");
	}
	
	@Override
	public void addSetListener( SetListener setListener) {
		if (setListener != null)
			setListeners.add(setListener);
	}
	
	public void sendSetLine(String message) {
		for (SetListener setl : setListeners) {
			setl.receiveSetLine(message);
		}
	}

	@Override
	public void addNodeListener(NodeListener nodeListener) {
		if (nodeListener != null)
			nodeListeners.add(nodeListener);
	}

}