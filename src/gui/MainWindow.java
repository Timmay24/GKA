package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import main.graphs.GKAEdge;
import main.graphs.GraphType;
import main.graphs.Vertex;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import controller.AdapterUpdateListener;
import controller.CellListener;
import controller.GraphController;
import controller.MessageListener;
import controller.SetListener;
import controller.StatsListener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MainWindow implements MessageListener, CellListener<mxCell>, AdapterUpdateListener, StatsListener, SetListener {
	
	private 	int[]				verNo = {0,7,51};
	private 	GraphController		graphController;
	private 	JFrame 				mainFrame;
	private 	JMenuItem 			mntmInfo;
	private 	JMenu 				mnQuestionmark;
	private 	JPanel 				graphPanel;
	private 	JScrollPane 		scrollPane;
	private 	JTextArea 			reportTextArea;
	private 	JMenuBar 			menuBar;
	private 	JMenu 				mnFile;
	private 	JMenuItem 			mntmOpen;
	private 	JMenuItem 			mntmSave;
	private 	JMenuItem			mntmSaveAs;
	private 	JSeparator 			separator;
	private 	JMenuItem 			mntmQuit;
	private 	JPopupMenu 			pmRClickReport;
	private 	JMenuItem 			mntmClearReport;
	private 	JMenu 				mnReporting;
	private 	JMenuItem 			mntmAlleKantenAusgeben;
	private 	JMenu 				mnLayout;
	private		JMenuItem 			mntmApplyCircleLayout;
	private		JMenu 				mnNeuerGraph;
	private 	JMenuItem 			mntmBeispiel;
	private 	JSeparator 			separator_1;
	private 	JSeparator 			separator_2;
	private     JMenuItem 			mntmUngerichtetUngewichtet;
	private     JMenu 				mnGerichtet;
	private     JMenuItem 			mntmGerichtetUngewichtet;
	private     JMenuItem 			mntmGerichtetGewichtet;
	private     JMenu 				mnUngerichtet;
	private     JMenuItem 			mntmUngerichtetGewichtet;
	private     JTextField 			txtAddVertex;
	private     JPanel 				vertexPanel;
	private     JLabel 				lblNewLabel;
	private     JButton 			btnAddVertex;
	private     JLabel 				label;
	private     JTextField 			txtAESource;
	private     JLabel 				label_1;
	private     JTextField 			txtAETarget;
	private     JTextField 			txtAEName;
	private     JLabel 				lblName;
	private     JTextField 			txtBFSStart;
	private     JTextField 			txtBFSGoal;
	private     JTextField 			txtBFSStatsHitcount;
	private     JTextField 			txtBFSStatsTime;
	private     JPanel 				edgePanel;
	private     JSpinner 			spinAEWeight;
	private     JLabel 				lblGewicht;
	private     JButton 			btnAddEdge;
	private     JPanel 				bfsPanel;
	private     JLabel 				lblStart;
	private     JLabel 				lblZiel;
	private     JPanel 				bfsStatsPanel;
	private     JLabel	 			lblZugriffe;
	private     JLabel 				lblGesamtzeit;
	private     JButton 			btnBFSSearch;
	private 	JMenuItem 			mntmApplyHierarchyLayout;
	
	
	
	//TODO DEBUG UTIL
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		start(args);
	}
	
	public static void start(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow mainWindow = new MainWindow();
					mainWindow.mainFrame.repaint();
					mainWindow.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Applikation starten.
	 * 
	 * Zuerst muss der GraphController erstellt werden.
	 * Danach folgt die Initialisierung der Oberflaeche.
	 * Anschliessend werden alle notwendigen Listener via GraphControll.
	 * am GraphenWrapper GKAGraph angemeldet.
	 * DANN kann die Erstellung von Graphen beginnen.
	 */
	public MainWindow() {
		graphController = new GraphController();
		initialize();
		graphController.addMessageListener(this); // Fuer den Empfang von Nachrichten
		graphController.addCellListener(this);    // und Zellen anmelden
		graphController.addAdapterUpdateListener(this);
		graphController.addStatsListener(this);
		
		graphController.getGraphWrapper().addSetListener(this); //TODO DEBUG LISTENER
		
		graphController.newGraph(null); // durch uebergeben von null -> Beispielgraphen erzeugen lassen
	}
	
	/**
	 * Veranlasst den Controller einen neuen Graphen erstellen zu lassen.
	 * 
	 * @param graphType
	 */
	private void newGraph(GraphType graphType) {
		graphController.newGraph(graphType);
	}
	
	/* (non-Javadoc)
	 * @see controller.AdapterUpdateListener#receiveAdapterUpdate(com.mxgraph.swing.mxGraphComponent)
	 */
	@Override
	public void receiveAdapterUpdate(mxGraphComponent graphComponent) {
		graphPanel.removeAll();
		graphPanel.add(graphComponent);
		mainFrame.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Erst mal die System-Optik aufzwingen
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		
		// Hauptframe initialisieren
		mainFrame = new JFrame();
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/ressources/images/graph.jpg")));
		mainFrame.setResizable(false);
		mainFrame.setTitle("GKA Graph Visualizer " + verNo[0] + "." + verNo[1] + "." + verNo[2]);
		// Größe und Position des Fensters festlegen
		mainFrame.setBounds(0, 0, 800, 900);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(
				(dim.width / 2 - mainFrame.getSize().width / 2),
				(dim.height / 2 - mainFrame.getSize().height / 2));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null); // == absolute layout
		
		/**
		 * Panel fuer den JGraph-Adapter
		 */
		graphPanel = new JPanel();
		graphPanel.setBackground(Color.LIGHT_GRAY);
		graphPanel.setBounds(10, 11, 774, 326);
		mainFrame.getContentPane().add(graphPanel);
		graphPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 410, 446, 130);
		mainFrame.getContentPane().add(scrollPane);
		
		reportTextArea = new JTextArea();
		reportTextArea.setEditable(false);
		reportTextArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == 3) {			// Rechts Maustaste
					pmRClickReport.setVisible(true);
				}
			}
		});
		reportTextArea.setFont(new Font("Consolas", Font.PLAIN, 11));
		scrollPane.setViewportView(reportTextArea);
		
		pmRClickReport = new JPopupMenu();
		addPopup(reportTextArea, pmRClickReport);
		
		mntmClearReport = new JMenuItem("L\u00F6schen");
		mntmClearReport.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/delete.png")));
		mntmClearReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reportTextArea.setText("");
			}
		});
		pmRClickReport.add(mntmClearReport);
		
		vertexPanel = new JPanel();
		vertexPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Knoten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		vertexPanel.setBounds(10, 343, 214, 56);
		mainFrame.getContentPane().add(vertexPanel);
		vertexPanel.setLayout(null);
		
		lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(10, 23, 37, 14);
		vertexPanel.add(lblNewLabel);
		
		txtAddVertex = new JTextField();
		txtAddVertex.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtAddVertex.setText("");
			}
		});
		txtAddVertex.setBounds(46, 20, 51, 20);
		vertexPanel.add(txtAddVertex);
		txtAddVertex.setColumns(10);
		
		btnAddVertex = new JButton("hinzuf\u00FCgen");
		btnAddVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtAddVertex.getText().isEmpty()) {
					graphController.addVertex(txtAddVertex.getText());
				}
			}
		});
		btnAddVertex.setBounds(107, 19, 89, 23);
		vertexPanel.add(btnAddVertex);
		
		edgePanel = new JPanel();
		edgePanel.setBorder(new TitledBorder(null, "Kanten", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		edgePanel.setBounds(234, 343, 550, 56);
		mainFrame.getContentPane().add(edgePanel);
		edgePanel.setLayout(null);
		
		label = new JLabel("Source:");
		label.setBounds(10, 23, 37, 14);
		edgePanel.add(label);
		
		txtAESource = new JTextField();
		txtAESource.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtAESource.setText("");
			}
		});
		txtAESource.setColumns(10);
		txtAESource.setBounds(57, 20, 51, 20);
		edgePanel.add(txtAESource);
		
		label_1 = new JLabel("Target:");
		label_1.setBounds(118, 23, 37, 14);
		edgePanel.add(label_1);
		
		txtAETarget = new JTextField();
		txtAETarget.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtAETarget.setText("");
			}
		});
		txtAETarget.setColumns(10);
		txtAETarget.setBounds(165, 20, 51, 20);
		edgePanel.add(txtAETarget);
		
		txtAEName = new JTextField();
		txtAEName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtAEName.setText("");
			}
		});
		txtAEName.setBounds(273, 20, 51, 20);
		edgePanel.add(txtAEName);
		txtAEName.setColumns(10);
		
		lblName = new JLabel("Name:");
		lblName.setBounds(230, 23, 37, 14);
		edgePanel.add(lblName);
		
		spinAEWeight = new JSpinner();
		spinAEWeight.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		spinAEWeight.setBounds(390, 20, 51, 20);
		edgePanel.add(spinAEWeight);
		
		lblGewicht = new JLabel("Gewicht:");
		lblGewicht.setBounds(334, 23, 46, 14);
		edgePanel.add(lblGewicht);
		
		btnAddEdge = new JButton("hinzuf\u00FCgen");
		btnAddEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String source = txtAESource.getText();
				String target = txtAETarget.getText();
				String edgeName = txtAEName.getText();
				Integer edgeWeight = (Integer) spinAEWeight.getModel().getValue();
				
				if (graphController.isWeighted()) {
					graphController.addEdge(source, target, GKAEdge.valueOf(edgeName, edgeWeight), false);
				} else {
					graphController.addEdge(source, target, GKAEdge.valueOf(edgeName), false);
				}
			}
		});
		btnAddEdge.setBounds(451, 19, 89, 23);
		edgePanel.add(btnAddEdge);
		
		bfsPanel = new JPanel();
		bfsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "BFS Wegfindung (k\u00FCrzester Weg)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		bfsPanel.setBounds(466, 410, 318, 130);
		mainFrame.getContentPane().add(bfsPanel);
		bfsPanel.setLayout(null);
		
		txtBFSStart = new JTextField();
		txtBFSStart.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtBFSStart.setText("");
			}
		});
		txtBFSStart.setBounds(54, 21, 51, 20);
		bfsPanel.add(txtBFSStart);
		txtBFSStart.setColumns(10);
		
		txtBFSGoal = new JTextField();
		txtBFSGoal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtBFSGoal.setText("");
			}
		});
		txtBFSGoal.setBounds(54, 52, 51, 20);
		bfsPanel.add(txtBFSGoal);
		txtBFSGoal.setColumns(10);
		
		lblStart = new JLabel("Start:");
		lblStart.setBounds(16, 24, 28, 14);
		bfsPanel.add(lblStart);
		
		lblZiel = new JLabel("Ziel:");
		lblZiel.setBounds(16, 55, 28, 14);
		bfsPanel.add(lblZiel);
		
		bfsStatsPanel = new JPanel();
		bfsStatsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Zugriffsstatistik", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		bfsStatsPanel.setBounds(127, 21, 181, 98);
		bfsPanel.add(bfsStatsPanel);
		bfsStatsPanel.setLayout(null);
		
		lblZugriffe = new JLabel("Hitcount:");
		lblZugriffe.setBounds(10, 21, 57, 14);
		bfsStatsPanel.add(lblZugriffe);
		
		lblGesamtzeit = new JLabel("Zeit (ms):");
		lblGesamtzeit.setEnabled(false);
		lblGesamtzeit.setBounds(10, 49, 57, 14);
		bfsStatsPanel.add(lblGesamtzeit);
		
		txtBFSStatsHitcount = new JTextField();
		txtBFSStatsHitcount.setEditable(false);
		txtBFSStatsHitcount.setBounds(77, 18, 86, 20);
		bfsStatsPanel.add(txtBFSStatsHitcount);
		txtBFSStatsHitcount.setColumns(10);
		
		txtBFSStatsTime = new JTextField();
		txtBFSStatsTime.setEnabled(false);
		txtBFSStatsTime.setText("--later--");
		txtBFSStatsTime.setEditable(false);
		txtBFSStatsTime.setBounds(77, 46, 86, 20);
		bfsStatsPanel.add(txtBFSStatsTime);
		txtBFSStatsTime.setColumns(10);
		
		btnBFSSearch = new JButton("suchen");
		btnBFSSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String start = txtBFSStart.getText();
				String goal = txtBFSGoal.getText();
				
				if (!(start.isEmpty() || goal.isEmpty())) {
					graphController.findShortestWay(start, goal);
				}
			}
		});
		btnBFSSearch.setBounds(16, 83, 89, 23);
		bfsPanel.add(btnBFSSearch);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 551, 178, 289);
		mainFrame.getContentPane().add(scrollPane_1);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Consolas", Font.PLAIN, 11));
		scrollPane_1.setViewportView(textArea);
		
		menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);
		
		mnFile = new JMenu("Datei");
		menuBar.add(mnFile);
		
		mnNeuerGraph = new JMenu("Neuer Graph");
		mnNeuerGraph.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/new_graph.png")));
		mnFile.add(mnNeuerGraph);
		
		mnGerichtet = new JMenu("Gerichtet");
		mnNeuerGraph.add(mnGerichtet);
		
		mntmGerichtetUngewichtet = new JMenuItem("Ungewichtet");
		mntmGerichtetUngewichtet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGraph(GraphType.DIRECTED_UNWEIGHTED);
			}
		});
		mnGerichtet.add(mntmGerichtetUngewichtet);
		
		mntmGerichtetGewichtet = new JMenuItem("Gewichtet");
		mntmGerichtetGewichtet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGraph(GraphType.DIRECTED_WEIGHTED);
			}
		});
		mnGerichtet.add(mntmGerichtetGewichtet);
		
		mnUngerichtet = new JMenu("Ungerichtet");
		mnNeuerGraph.add(mnUngerichtet);
		
		mntmUngerichtetUngewichtet = new JMenuItem("Ungewichtet");
		mntmUngerichtetUngewichtet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGraph(GraphType.UNDIRECTED_UNWEIGHTED);
			}
		});
		mnUngerichtet.add(mntmUngerichtetUngewichtet);
		
		mntmUngerichtetGewichtet = new JMenuItem("Gewichtet");
		mntmUngerichtetGewichtet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGraph(GraphType.UNDIRECTED_WEIGHTED);
			}
		});
		mnUngerichtet.add(mntmUngerichtetGewichtet);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		mnNeuerGraph.add(separator_1);
		
		mntmBeispiel = new JMenuItem("Beispielgraphen");
		mntmBeispiel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGraph(null);
			}
		});
		mntmBeispiel.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/example.png")));
		mnNeuerGraph.add(mntmBeispiel);
		
		separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		mnFile.add(separator_2);
		
		mntmOpen = new JMenuOpen(graphController);
		mntmOpen.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/open.png")));
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuSave(graphController);
		mntmSave.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/save.png")));
		mnFile.add(mntmSave);
		
		mntmSaveAs = new JMenuSaveAs(graphController);
		mntmSaveAs.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/save_as.png")));
		mnFile.add(mntmSaveAs);
		
		separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		mnFile.add(separator);
		
		mntmQuit = new JMenuItem("Beenden");
		mntmQuit.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/quit.png")));
		mntmQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
			}
		});
		mnFile.add(mntmQuit);
		
		mnLayout = new JMenu("Layout");
		menuBar.add(mnLayout);
		
		mntmApplyCircleLayout = new JMenuItem("Kreis");
		mntmApplyCircleLayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graphController.setCircleLayout();
			}
		});
		mnLayout.add(mntmApplyCircleLayout);
		
		mntmApplyHierarchyLayout = new JMenuItem("Hierarchisch");
		mntmApplyHierarchyLayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graphController.getGraphWrapper().setHierarchyLayout();
			}
		});
		mnLayout.add(mntmApplyHierarchyLayout);
		
		mnReporting = new JMenu("Reporting");
		menuBar.add(mnReporting);
		
		mntmAlleKantenAusgeben = new JMenuItem("Alle Kanten ausgeben");
		mntmAlleKantenAusgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graphController.getGraphWrapper().printAllEdges();
			}
		});
		mnReporting.add(mntmAlleKantenAusgeben);
		
		mnQuestionmark = new JMenu("");
		mnQuestionmark.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/question_blue.png")));
		menuBar.add(mnQuestionmark);
		
		mntmInfo = new JMenuItem("Info ...");
		mntmInfo.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/info.png")));
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InfoPopup.showInfo(null);
			}
		});
		mnQuestionmark.add(mntmInfo);
	}
	
	/* (non-Javadoc)
	 * @see controller.MessageListener#receiveMessage(java.lang.String)
	 */
	@Override
	public void receiveMessage(String message) {
		report(message);
	}
	
	private void report(String message) {
		reportTextArea.append(message + "\n");
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/* (non-Javadoc)
	 * @see controller.CellListener#receiveCell(java.lang.Object, java.awt.event.MouseEvent)
	 */
	@Override
	public void receiveCell(mxCell cell, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveStats(Map<String, String> stats) {
		if (stats != null) {
			txtBFSStatsHitcount.setText(stats.get("hitcount"));
		}
	}

	
	//TODO DEBUG LISTENER METHOD
	@Override
	public void receiveSetLine(String message) {
		if (message == null)
			textArea.setText("");
		else
			textArea.append(message + "\n");
	}
}
