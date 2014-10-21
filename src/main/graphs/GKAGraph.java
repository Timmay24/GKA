package main.graphs;

import static main.graphs.GraphType.DIRECTED_WEIGHTED;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.Pseudograph;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphComponent.mxGraphControl;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import controller.FileHandler;
import controller.MessageListener;
import controller.MessageSender;


public class GKAGraph implements MessageSender {
	
	private			ListenableGraph<Vertex, GKAEdge> 	jGraph;
	private			JGraphXAdapter<Vertex, GKAEdge> 	jgxAdapter;
	private 		mxGraphComponent 					graphComponent = null;
	private 		List<MessageListener>				msgListener = new ArrayList<>();
	private			GraphType							graphType;
	private final	String								UNDIRECTED_SYMBOL = "--";
	private final	String								  DIRECTED_SYMBOL = "->";
	
	/**
	 * Konstruktor mit Angabe des Graphentypen
	 * 
	 * @param graphType
	 */
	private GKAGraph(GraphType graphType) {
		newGraph(graphType);
	}

	/**
	 * Standart-Konstruktor fuer den Start des Programms, mit dem der
	 * Beispielgraph geladen wird.
	 */
	private GKAGraph() {
		createSampleGraph();
	}

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
	 * @param graphType
	 * @return Automatisch erzeugte, zum Graphen gehoerige Graphenkomponente
	 */
	public mxGraphComponent newGraph(GraphType graphType) {

		this.graphType = graphType;

		if (graphType.isDirected()) {
			jGraph = new ListenableDirectedGraph<>( new DirectedPseudograph<Vertex, GKAEdge>(GKAEdge.class));
		} else {
			jGraph = new ListenableUndirectedGraph<>( new Pseudograph<Vertex, GKAEdge>(GKAEdge.class));
		}

		jgxAdapter = new JGraphXAdapter<Vertex, GKAEdge>(jGraph);

		// Der Kantenstyle wird gesondert angepasst, sollte es sich um einen
		// gerichteten Graphen handeln
		if (!graphType.isDirected()) {
			jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_ENDARROW, "none");
		}

		createGraphComponent(jgxAdapter);
		setGraphConfig();

		return graphComponent;
	}
	
	/**
	 * Erzeugt eine GraphComponente die spaeter in der GUI eingebunden werden 
	 * kann
	 * 
	 * @param jgxAdapter JGraphXAdapter-Objekt
	 */
	private void createGraphComponent(JGraphXAdapter<Vertex, GKAEdge> jgxAdapter) {
		graphComponent = new mxGraphComponent(jgxAdapter);
		addMouseAdapter();
	}

	/**
	 * Erzeugt einen Beispielgraphen (gerichtet + gewichtet)
	 * 
	 * @return mxGraphComponent
	 */
	public mxGraphComponent createSampleGraph() {

		mxGraphComponent mxGC = newGraph(DIRECTED_WEIGHTED);

//		addEdge("v1", "v2", GKAEdge.valueOf("Edge 1", 1.0));
//		addEdge("v2", "v3", GKAEdge.valueOf("Edge 2", 2.0));
//		addEdge("v3", "v1", GKAEdge.valueOf("Edge 3", 3.0));
//		addEdge("v4", "v3", GKAEdge.valueOf("Edge 4", 4.0));
		addEdge("v1", "v2", GKAEdge.valueOf("e1"));
		addEdge("v1", "v3", GKAEdge.valueOf("e2"));
		addEdge("v1", "v3", GKAEdge.valueOf("e4"));
		addEdge("v4", "v1", GKAEdge.valueOf("e3"));

		sendMessage("FERTIG: Beispiel-Graph erstellt!");
		setLayout();

		return mxGC;
	}

	/**
	 * Grundeinstellungen fuer Adapter, Layout und Graphenkomponente
	 */
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

	/**
	 * ...Abkuerzung, da beide Layoutanpassungen meist zusammen und an mehreren
	 * Stellen aufgerufen werden.
	 */
	public void setLayout() {
		setCircleLayout();
		setParallelEdgeLayout();
	}
	
	/**
	 * Ordnet den Graphen in einem kreisfoermigen Layout an
	 */
	public void setCircleLayout() {
		mxCircleLayout layout1 = new mxCircleLayout(getAdapter());
		layout1.execute(getAdapter().getDefaultParent());
	}
	
	/**
	 * Einrichtung des Parallel-Edge Layouts (wie parallele Kanten angezeigt
	 * werden sollen).
	 */
	public void setParallelEdgeLayout() {
		mxParallelEdgeLayout layout = new mxParallelEdgeLayout(getAdapter(), 50);
		layout.execute(getAdapter().getDefaultParent());
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
	public ListenableGraph<Vertex, GKAEdge> getGraph() {
		return jGraph;
	}
	
	/**
	 * @return Gibt den Graphenadapter zurueck.
	 */
	public mxGraph getAdapter() {
		return jgxAdapter;
	}

	/**
	 * Fuegt dem Graphen eine Kante zwischen source und target hinzu. Fehlende
	 * Knoten werden vorher auch hinzugefuegt.
	 * 
	 * @param source
	 * @param target
	 * @param newEdge
	 * @return
	 */
	public boolean addEdge(Vertex source, Vertex target, GKAEdge newEdge) {
		addVertex(source);
		addVertex(target);
		if (getGraph().addEdge(source, target, newEdge)) {
			sendMessage("Kante erstellt: " + source + " : " + target);
			return true;
		} else {
			sendMessage("Konnte konnte nicht erstellt werden (" + source + " : " + target + ")");
			return false;
		}
	}
	
	public boolean addEdge(String sourceName, String targetName, GKAEdge newEdge) {
		Vertex source = getVertex(sourceName);
		Vertex target = getVertex(targetName);

		if (source == null) {
			source = Vertex.valueOf(sourceName);
		}
		if (target == null) {
			target = Vertex.valueOf(targetName);
		}

		return addEdge(source, target, newEdge);
	}
	
	/**
	 * Entfernt die Kante zwischen source und target.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public boolean removeEdge(Vertex source, Vertex target) {
		if (getGraph().removeEdge(getGraph().getEdge(source, target))) {
			sendMessage("Kante entfernt (" + source + " : " + target + ")");
			return true;
		} else {
			sendMessage("Kante konnte nicht entfernt werden (" + source + " : " + target + ")");
			return false;
		}
	}
	
	/**
	 * @param edgeName
	 * @return Ermittelt ein Kantenobjekt anhand der Source- und Targetknoten.
	 */
	public GKAEdge getEdge(Vertex source, Vertex target) {
		return getGraph().getEdge(source, target);
	}

	/**
	 * @param sourceName
	 * @param targetName
	 * @return
	 */
	public GKAEdge getEdge(String sourceName, String targetName) {
		Vertex source, target;
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
	
	public GKAEdge getEdge(String edgeName) {
		for (GKAEdge e : getGraph().edgeSet()) {
			if (e.getName().equals(edgeName)) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * @param edge
	 * @return Prueft, ob die Kante edge im Graphen enthalten ist.
	 */
	public boolean containsEdge(GKAEdge edge) {
		return getGraph().edgeSet().contains(edge);
	}
	
	public boolean containsEdge(String edgeName) {
		return getEdge(edgeName) != null;
	}

	/**
	 * Faerbt die uebergebene Kante ein.
	 * 
	 * @param edge
	 */
	public void colorEdge(GKAEdge edge) {
		if (edge != null) {
			getAdapter().getModel().setStyle(
					jgxAdapter.getEdgeToCellMap().get(edge),
					"strokeColor=FF0000");
		}
	}
	
	/**
	 * @param vertex
	 * @return Fuegt einen Knoten dem Graphen hinzu.
	 */
	public boolean addVertex(Vertex vertex) {
		if (getGraph().addVertex(vertex)) {
			sendMessage("Knoten " + vertex + " hinzugefuegt");
			return true;
		} else {
			sendMessage("Knoten " + vertex + " konnte nicht hinzugefuegt werden");
			return false;
		}
	}
	
	public boolean addVertex(String vertexName) {
		return addVertex(Vertex.valueOf(vertexName));
	}

	/**
	 * @param vertex
	 * @return Entfernt einen Knoten aus dem Graphen.
	 */
	public boolean removeVertex(Vertex vertex) {
		if (getGraph().removeVertex(vertex)) {
			sendMessage("Knoten " + vertex + " entfernt");
			return true;
		} else {
			sendMessage("Knoten " + vertex + " konnte nicht entfernt werden");
			return false;
		}
	}
	
	/**
	 * @param vertex
	 * @return Prueft, ob der Knoten vertex im Graphen enthalten ist.
	 */
	public boolean containsVertex(Vertex vertex) {
		return getGraph().vertexSet().contains(vertex);
		//TODO delegieren
	}

	public boolean containsVertex(String vertexName) {
		return getVertex(vertexName) != null;
	}
	
	/**
	 * @param vertexName
	 * @return Ermittelt das Knotenobjekt anhand seines Namens.
	 */
	public Vertex getVertex(String vertexName) {
		for (Vertex v : getGraph().vertexSet()) {
			if (v.getName().equals(vertexName)) {
				return v;
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
	public Collection<Vertex> getAllAdjacentsOf(Vertex sourceVertex, boolean directed) {
		Set<Vertex> resultList = new HashSet<>();

		Set<GKAEdge> incidentEdges = getGraph().edgesOf(sourceVertex);	// Alle am Knoten sourceVertex anliegenden Kanten ermitteln
		
		for (GKAEdge e : incidentEdges) {
//			sendMessage(e.getSource().toString() + " -" + e.getName() + "- " + e.getTarget().toString()); // debug
			
			if (e.getSource() != e.getTarget()) {          		// Schlaufen ausschliessen Source != Target
				
				if (directed) {								// Sonderregelung fuer gerichtete Graphen:
					if (e.getSource() == sourceVertex) {       	// Nur adjazente Knoten in die Ergebnisliste stecken,
						resultList.add((Vertex)e.getTarget());  // zu denen man vom Knoten sourceVertex aus kommen kann.
																// D.h. Adjazenten hinter eingehenden Kanten werden ausgenommen.
					}
				} else {
					if (e.getSource() == sourceVertex) {		// Ist sourceVertex Source der Kante,
						resultList.add((Vertex)e.getTarget());	// dann ist Target der Adjazent.
					} else if (e.getTarget() == sourceVertex) {	// Ist sourceVertex Target der Kante,
						resultList.add((Vertex)e.getSource());	// dann ist Source der Adjazent.
					}
				}
			}
		}
		return resultList;
	}
	
	public Collection<Vertex> getAllAdjacentsOf(Vertex sourceVertex) {
		return getAllAdjacentsOf(sourceVertex, isDirected());
	}
	
		
	/**
	 * Gibt alle Kanten des Graphen aus.
	 */
	public void printAllEdges() {
		sendMessage("Alle Kanten:");
		for (GKAEdge e : getGraph().edgeSet()) {
			sendMessage(e.toString());
		}
	}

	/**
	 * Gibt alle Knoten des Graphen aus.
	 */
	public void printAllVertices() {
		sendMessage("Alle Knoten:");
		for (Vertex v : getGraph().vertexSet()) {
			sendMessage(v.toString());
		}
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
		msgListener.add(ml);
	}

	/**
	 * Setzt eine Nachricht an alle angemeldeten MessageListener ab
	 * 
	 * @param message Abzusetzende Nachricht
	 */
	private void sendMessage(String message) {
		for (MessageListener ml : msgListener) {
			ml.receiveMessage(message);
		}
	}

	/**
	 * Meldet an der Graphenkontrolleinheit einen MouseListener an, um
	 * Maus-Interaktionen mit der visuellen Graphenkomponente abzufangen.
	 */
	public void addMouseAdapter() {
		getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Object cell = getGraphComponent().getCellAt(e.getX(), e.getY());

				if (cell != null && cell instanceof mxCell) {
					System.out.println(((mxCell) cell).getValue());
					sendMessage("Markiert: " + ((mxCell) cell).getValue());

					// System.out.println(((mxCell)cell).isEdge());
					// System.out.println(((mxCell)cell).isVertex());
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
	 * TODO Laedt einen Graphen aus einer GKA-Datei.
	 * 
	 * @param file Dateiobjekt, das den Pfad zur Datei haelt.
	 */
	public void openGraph() {
		throw new NotImplementedException();
	}
	
//	private boolean readFile(File file) {
//		CharsetDecoder decoder = Charset.forName("ISO-8859-1").newDecoder();
//		
//	    InputStreamReader reader2 = new InputStreamReader(new FileInputStream(FileHandler.checkFile(file)), decoder);
//	    BufferedReader reader = new BufferedReader(reader2);
//	    String line = null;
//	    ArrayList<String> returnValue = new ArrayList<>();
//	    while ((line = reader.readLine()) != null) 
//	    {
//	        returnValue.addAll(Arrays.asList(line.replace(" ","").replace("\t","").split(";")));
//	    }
//	    return returnValue;
//	}

	/**
	 * Rechnet den Graphen zeilenweise um und laesst die Zeilen in einer GKA-Datei ablegen.
	 */
	public void saveGraph() {
		List<String> output = new ArrayList<>();
		String edgeSym = (isDirected())?(DIRECTED_SYMBOL):(UNDIRECTED_SYMBOL);
		String outLine = null;
		
		if (isWeighted()) {
			// Kanten und zugehoerige Knoten zuerst
			for (GKAEdge e : getGraph().edgeSet()) {
				outLine = e.getSource() + " " + edgeSym + " " + e.getTarget() + " : " + e.getWeight() + ";";
				output.add(outLine);
				
			}
		} else {
			// dito
			for (GKAEdge e : getGraph().edgeSet()) {
				outLine = e.getSource() + " " + edgeSym + " " + e.getTarget() + ";";
				output.add(outLine);
			}
		}
		
		// alleinstehende Knoten danach
		for (Vertex v : getGraph().vertexSet()) {
			if (getAllAdjacentsOf(v, false).isEmpty()) {
				output.add(v.getName() + ";");
			}
		}
		
		// Verfahren fuer saveGraph und saveGraphAs ueberlegen.
		try {
			FileHandler.saveGraph();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void saveGraphAs() {
		
	}
}
































