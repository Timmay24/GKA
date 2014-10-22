package main.graphs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import controller.AdapterUpdateListener;
import controller.AdapterUpdateSender;
import controller.CellListener;
import controller.CellSender;
import controller.FileHandler;
import controller.MessageListener;
import controller.MessageSender;
import controller.StatsListener;
import controller.StatsSender;


public class GKAGraph implements MessageSender, CellSender<mxCell>, AdapterUpdateSender, StatsSender {
	
	private			ListenableGraph<Vertex, GKAEdge> 	jGraph;
	private			JGraphXAdapter<Vertex, GKAEdge> 	jgxAdapter;
	private 		mxGraphComponent 					graphComponent = null;
	private 		List<MessageListener>				msgListeners = new ArrayList<>();
	private			List<CellListener<mxCell>>			cellListeners = new ArrayList<>();
	private			List<AdapterUpdateListener>			adapterUpdateListeners = new ArrayList<>();
	private			GraphType							graphType;
	private final	String								UNDIRECTED_SYMBOL = "--";
	private final	String								  DIRECTED_SYMBOL = "->";
	private 		String								currentFilePath = null;
	private 		List<StatsListener> 				statsListeners;
	
	/**
	 * Konstruktor mit Angabe des Graphentypen
	 * 
	 * @param graphType
	 */
	private GKAGraph(GraphType graphType) {
		newGraph(graphType);
	}

	/**
	 * Standart-Konstruktor fuer den Start des Programms.
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
		
//		sendAdapterUpdate(getGraphComponent()); // Gibt Listenern Bescheid, dass der Adapter erneuert wurde.
		}
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
	
			addEdge("v1", "v2", GKAEdge.valueOf("e1"));
			addEdge("v1", "v3", GKAEdge.valueOf("e2"));
			addEdge("v1", "v3", GKAEdge.valueOf("e4"));
			addEdge("v4", "v1", GKAEdge.valueOf("e3"));
		} else {
			newGraph(GraphType.UNDIRECTED_WEIGHTED);
			addEdge("v1", "v2", GKAEdge.valueOf("e1"));
			addEdge("v1", "v3", GKAEdge.valueOf("e2"));
			addEdge("v1", "v3", GKAEdge.valueOf("e4"));
			addEdge("v4", "v1", GKAEdge.valueOf("e3", 4));
			addEdge("v3", "v1", GKAEdge.valueOf("e5", 14));
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
		if (newEdge.isWeighted() != this.isWeighted()) {
			sendMessage("FEHLER: Inkompatible Gewichtungstypen beim Hinzufügen der Kante.");
		} else {
		
			addVertex(source);
			addVertex(target);
			if (getGraph().addEdge(source, target, newEdge)) {
				sendMessage("Kante erstellt: " + source + " : " + target);
				return true;
			} else {
				sendMessage("Konnte konnte nicht erstellt werden (" + source + " : " + target + ")");
			}
		}
		return false; // falls aus geg. Gruenden das Hinzufuegen nicht geklappt hat...
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
		//TODO: Hinweis senden, falls Kante nicht existiert. Das ist eig. kein echter Fehler.
		if (getGraph().removeEdge(getGraph().getEdge(source, target))) {
			sendMessage("ERFOLG: Kante entfernt (" + source + " : " + target + ").");
			return true;
		} else {
			sendMessage("FEHLER: Kante konnte nicht entfernt werden (" + source + " : " + target + ").");
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
		//TODO delegieren statt selbst machen: return getGraph().containsEdge(edge);
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
//		if (containsVertex(vertex)) {
//			
//		}
		if (getGraph().addVertex(vertex)) {
			sendMessage("Knoten " + vertex + " hinzugefuegt");
			return true;
		}
		return false;
	}
	
	public boolean addVertex(String vertexName) {
		return addVertex(Vertex.valueOf(vertexName));
	}

	/**
	 * @param vertex
	 * @return Entfernt einen Knoten aus dem Graphen.
	 */
	public boolean removeVertex(Vertex vertex) {
		if (!containsVertex(vertex)) {
			sendMessage("HINWEIS: Der Knoten" + vertex + " existiert nicht.");
			return false;
		}
		if (getGraph().removeVertex(vertex)) {
			sendMessage("ERFOLG: Knoten " + vertex + " entfernt.");
			return true;
		} else {
			sendMessage("FEHLER: Knoten " + vertex + " konnte nicht entfernt werden.");
			return false;
		}
	}
	
	/**
	 * @param vertex
	 * @return Prueft, ob der Knoten vertex im Graphen enthalten ist.
	 */
	public boolean containsVertex(Vertex vertex) {
		return getGraph().vertexSet().contains(vertex);
		//TODO delegieren statt selbst machen: return getGraph().containsVertex(vertex);
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
				
				if (directed) {									// Sonderregelung fuer gerichtete Graphen:
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
	
	
	public void findShortestWay(String startVertex, String goalVertex) {
		Vertex start = getVertex(startVertex);
		Vertex goal = getVertex(goalVertex);
		
		if (start != null && goal != null) {
			findShortestWay(start, goal);
		} else {
			sendMessage("FEHLER: Start- oder Zielknoten ungültig oder nicht existent.");
		}
	}
	
	public void findShortestWay(Vertex start, Vertex goal) {
		List<Vertex> way = BFS.findShortestWay(this, start, goal);
		
		sendMessage("Kürzester Weg:");
		for (Vertex v : way) {
			sendMessage(v.getName());
		}
		sendMessage("--------------------------------");
	}
	
		
	/**
	 * Gibt alle Kanten des Graphen aus.
	 */
	public void printAllEdges() {
		sendMessage("--Kanten--");
		for (GKAEdge e : getGraph().edgeSet()) {
			sendMessage(e.toString());
		}
	}

	/**
	 * Gibt alle Knoten des Graphen aus.
	 */
	public void printAllVertices() {
		sendMessage("--Knoten--");
		for (Vertex v : getGraph().vertexSet()) {
			sendMessage(v.toString());
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
	private void sendMessage(String message) {
		for (MessageListener ml : msgListeners) {
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
					sendMessage("Markiert: " + ((mxCell) cell).getValue()); // debug
					sendCell((mxCell) cell, e);		// Weiterleiten der Zelle an alle angemeldeten CellListeners
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
	public void openGraph() {
		try {
			File inFile = FileHandler.getInFile();				// Dateiobjekt holen
			List<String> input = FileHandler.readFile(inFile);	// Datei auslesen
			if (parseFile(input)) {								// Ergebnisliste parsen
				currentFilePath = inFile.getAbsolutePath();		// bei Erfolg: Dateipfad merken
				setCircleLayout();
			};
			sendMessage("ERFOLG: Graphendatei geladen.");
			sendMessage("ERFOLG: " + "\"" + currentFilePath + "\"");
			
		} catch (IOException e) {
			e.printStackTrace();
			sendMessage("FEHLER: " + e.getMessage());
			sendMessage("FEHLER: Graph öffnen fehlgeschlagen.");
		}
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
	}
	
	private boolean parseFile(List<String> input) {
		String regex = "^([a-zA-Z0-9üÜäÄöÖ]+) ?((-[>|-]) ?([a-zA-Z0-9üÜäÄöÖ]+) ?([a-zA-Z0-9üÜäÄöÖ]+)?)? ?( ?: ?([0-9]+))?;$";
		Pattern p = Pattern.compile(regex);
		Matcher m;
		GraphType graphType = null;
		Integer tempEdgeNo = 0;
		
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
				Integer eW = null;
				
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
				
				if (edgeWeight != null) eW = Integer.parseInt(edgeWeight);
				
				if (edgeName == null) {
					edgeName = "e" + tempEdgeNo.toString();
					tempEdgeNo++;
				}
				
				if (edgeSym == null) {
					addVertex(source);
				} else {
					addEdge(source, target, GKAEdge.valueOf( edgeName, eW ));
				}
			}
		}
		return true;
	}
	
	/**
	 * Rechnet den Graphen in eine String-basierte Liste um.
	 */
	public List<String> parseGraph() {
		List<String> resultList = new ArrayList<>();
		String edgeSym = (isDirected())?(DIRECTED_SYMBOL):(UNDIRECTED_SYMBOL);
		String outLine = null;
		
//		if (isWeighted()) {
//			// Kanten und zugehoerige Knoten zuerst
//			for (GKAEdge e : getGraph().edgeSet()) {
//				outLine = e.getSource() + " " + edgeSym + " " + e.getTarget() + " : " + e.getWeight() + ";";
//				resultList.add(outLine);
//				
//			}
//		} else {
//			// dito
			for (GKAEdge e : getGraph().edgeSet()) {
//				outLine = e.getSource() + " " + edgeSym + " " + e.getTarget() + ";";
				
				outLine = e.getSource() + " " + edgeSym + " " + e.getTarget() + ((e.getName()==null)?(" "):(" " + e.getName())) + ((isWeighted())?(" : " + e.getWeight()):("")) + ";";
				
				resultList.add(outLine);
			}
//		}
		
		// alleinstehende Knoten danach
		for (Vertex v : getGraph().vertexSet()) {
			if (getAllAdjacentsOf(v, false).isEmpty()) {
				resultList.add(v.getName() + ";");
			}
		}
		
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
	
	public void sendStats(Map<String, String> stats) {
		for (StatsListener sl : statsListeners) {
			sl.receiveStats(stats);
		}
	} 
}
































