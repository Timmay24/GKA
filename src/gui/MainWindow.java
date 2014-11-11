package gui;

import gui.menus.JMenuOpen;
import gui.menus.JMenuSave;
import gui.menus.JMenuSaveAs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import main.graphs.Algorithms;
import main.graphs.GKAEdge;
import main.graphs.GraphType;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import controller.GraphController;
import controller.interfaces.AdapterUpdateListener;
import controller.interfaces.CellListener;
import controller.interfaces.MessageListener;
import controller.interfaces.NodeListener;
import controller.interfaces.SetListener;
import controller.interfaces.StatsListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class MainWindow implements MessageListener, CellListener<mxCell>, AdapterUpdateListener, StatsListener, SetListener, NodeListener {
	
	private 	int[]				verNo = {0,8,88};
	private 	GraphController		graphController;
	private 	JFrame 				mainFrame;
	private		StatsWindow			statsWindow;
	private 	JMenuItem 			mntmInfo;
	private 	JMenu 				mnQuestionmark;
	private 	JPanel 				graphPanel;
	private 	JScrollPane 		reportPane;
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
	private     JTextField 			txtSearchStart;
	private     JTextField 			txtSearchGoal;
	private     JPanel 				edgePanel;
	private     JSpinner 			spinAEWeight;
	private     JLabel 				lblGewicht;
	private     JButton 			btnAddEdge;
	private     JPanel 				pathPanel;
	private     JLabel 				lblStart;
	private     JLabel 				lblZiel;
	private     JButton 			btnSearchStart;
	private 	JMenuItem 			mntmApplyHierarchyLayout;
	private 	JComboBox<String> 	cmbSearchAlgo;
	private		JLabel 				lblAlgorithmus;
	private 	JSeparator 			separator_3;
	private 	JMenuItem 			mntmFarbenReset;
	private 	JMenu 				mnStatistik;
	private 	JMenuItem 			mntmAnzeigen;
	private 	JProgressBar 		progressBar;
	private 	JLabel 				lblProcessing;
	private 	JMenuItem 			mntmZufallsgraph;
	
	//TODO DEBUG UTIL
	private JTextArea taDebug;
	private JScrollPane scrDebugPane;

	

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
		statsWindow = new StatsWindow();
		initialize();
		
		graphController.addMessageListener(this); // Fuer den Empfang von Nachrichten
		graphController.addCellListener(this);    // und Zellen anmelden
		graphController.addAdapterUpdateListener(this);
		graphController.addStatsListener(this);
		graphController.addNodeListener(this);
		
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
		mainFrame.setBounds(0, 0, 800, 595);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(
				(dim.width / 2 - mainFrame.getSize().width / 2),
				(dim.height / 2 - mainFrame.getSize().height / 2));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null); // == absolute layout
		

		progressBar = new JProgressBar();
		progressBar.setBounds(10, 150, 774, 25);
		progressBar.setVisible(false);
		
		lblProcessing = new JLabel("Verarbeitung...");
		lblProcessing.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/bling.png")));
		lblProcessing.setBounds(10, 130, 102, 20);
		lblProcessing.setVisible(false);
		mainFrame.getContentPane().add(lblProcessing);
		mainFrame.getContentPane().add(progressBar);
		
		/**
		 * Panel fuer den JGraph-Adapter
		 */
		graphPanel = new JPanel();
		graphPanel.setBackground(Color.LIGHT_GRAY);
		graphPanel.setBounds(10, 11, 774, 326);
		mainFrame.getContentPane().add(graphPanel);
		graphPanel.setLayout(new BorderLayout(0, 0));
		
		reportPane = new JScrollPane();
		reportPane.setBounds(10, 410, 476, 125);
		mainFrame.getContentPane().add(reportPane);
		
		reportTextArea = new JTextArea();
		reportTextArea.setEditable(false);
		// Autoscrolling aktivieren
		DefaultCaret caret = (DefaultCaret) reportTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//
		reportTextArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == 3) {			// Rechts Maustaste
					pmRClickReport.setVisible(true);
				}
			}
		});
		reportTextArea.setFont(new Font("Consolas", Font.PLAIN, 11));
		reportPane.setViewportView(reportTextArea);
		
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
		vertexPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Knoten hinzuf\u00FCgen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		vertexPanel.setBounds(10, 343, 156, 56);
		mainFrame.getContentPane().add(vertexPanel);
		vertexPanel.setLayout(null);
		
		lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(10, 23, 37, 14);
		vertexPanel.add(lblNewLabel);
		
		txtAddVertex = new JTextField();
		txtAddVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAddVertex.doClick();
				
			}
		});
		txtAddVertex.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtAddVertex.setText("");
			}
		});
		txtAddVertex.setBounds(46, 20, 51, 20);
		vertexPanel.add(txtAddVertex);
		txtAddVertex.setColumns(10);
		
		btnAddVertex = new JButton("");
		btnAddVertex.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/add.png")));
		btnAddVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtAddVertex.getText().isEmpty()) {
					graphController.addVertex(txtAddVertex.getText());
					txtAddVertex.setText("");
				}
			}
		});
		btnAddVertex.setBounds(107, 19, 37, 23);
		vertexPanel.add(btnAddVertex);
		vertexPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, txtAddVertex, btnAddVertex}));
		
		edgePanel = new JPanel();
		edgePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Kanten hinzuf\u00FCgen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		edgePanel.setBounds(176, 343, 498, 56);
		mainFrame.getContentPane().add(edgePanel);
		edgePanel.setLayout(null);
		
		label = new JLabel("Source:");
		label.setBounds(10, 23, 37, 14);
		edgePanel.add(label);
		
		txtAESource = new JTextField();
		txtAESource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtAESource.getText().isEmpty())
					txtAETarget.requestFocus();
			}
		});
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
		txtAETarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtAETarget.getText().isEmpty())
					txtAEName.requestFocus();
			}
		});
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
		txtAEName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtAEName.getText().isEmpty()) {
					if (graphController.isWeighted()) {
						spinAEWeight.requestFocus();
					} else {
						btnAddEdge.doClick();
						txtAESource.requestFocus();
					}
				}
			}
		});
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
		spinAEWeight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAddEdge.doClick();
					txtAESource.requestFocus();
				}
			}
		});
		spinAEWeight.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		spinAEWeight.setBounds(390, 20, 51, 20);
		edgePanel.add(spinAEWeight);
		
		lblGewicht = new JLabel("Gewicht:");
		lblGewicht.setBounds(334, 23, 46, 14);
		edgePanel.add(lblGewicht);
		
		btnAddEdge = new JButton("");
		btnAddEdge.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/add.png")));
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
		btnAddEdge.setBounds(451, 19, 37, 23);
		edgePanel.add(btnAddEdge);
		
		pathPanel = new JPanel();
		pathPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Wegfindung (k\u00FCrzester Weg)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		pathPanel.setBounds(496, 410, 288, 125);
		mainFrame.getContentPane().add(pathPanel);
		pathPanel.setLayout(null);
		
		txtSearchStart = new JTextField();
		txtSearchStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtSearchStart.getText().isEmpty()) {
					txtSearchGoal.requestFocus();
				}
			}
		});
		txtSearchStart.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtSearchStart.setText("");
			}
		});
		txtSearchStart.setBounds(88, 53, 68, 20);
		pathPanel.add(txtSearchStart);
		txtSearchStart.setColumns(10);
		
		txtSearchGoal = new JTextField();
		txtSearchGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSearchStart.doClick();
			}
		});
		txtSearchGoal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtSearchGoal.setText("");
			}
		});
		txtSearchGoal.setBounds(209, 53, 68, 20);
		pathPanel.add(txtSearchGoal);
		txtSearchGoal.setColumns(10);
		
		lblStart = new JLabel("Start:");
		lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStart.setBounds(10, 56, 68, 14);
		pathPanel.add(lblStart);
		
		lblZiel = new JLabel("Ziel:");
		lblZiel.setBounds(179, 56, 20, 14);
		pathPanel.add(lblZiel);
		
		btnSearchStart = new JButton("suchen");
		btnSearchStart.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/binocular.png")));
		btnSearchStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String start = txtSearchStart.getText();
				String goal = txtSearchGoal.getText();
				
				if (!(start.isEmpty() || goal.isEmpty())) {
					graphController.findShortestWay(
							Algorithms.getAlgorithm(cmbSearchAlgo.getSelectedIndex()),
							start,
							goal);
				}
			}
		});
		btnSearchStart.setBounds(88, 84, 189, 23);
		pathPanel.add(btnSearchStart);
		
		cmbSearchAlgo = new JComboBox<>();
		cmbSearchAlgo.setModel(new DefaultComboBoxModel<String>(new String[] {"BFS", "Dijkstra", "Floyd-Warshall"}));
		cmbSearchAlgo.setBounds(88, 22, 189, 20);
		pathPanel.add(cmbSearchAlgo);
		
		lblAlgorithmus = new JLabel("Algorithmus:");
		lblAlgorithmus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlgorithmus.setBounds(10, 24, 68, 14);
		pathPanel.add(lblAlgorithmus);
		
		scrDebugPane = new JScrollPane();
		scrDebugPane.setBounds(10, 596, 178, 90);
		mainFrame.getContentPane().add(scrDebugPane);
		
		taDebug = new JTextArea();
		taDebug.setFont(new Font("Consolas", Font.PLAIN, 11));
		scrDebugPane.setViewportView(taDebug);
		
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
		
		mntmZufallsgraph = new JMenuItem("Zufallsgraph");
		mntmZufallsgraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graphController.getGraphWrapper().createRandomGraph();
			}
		});
		mntmZufallsgraph.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/dice.png")));
		mnNeuerGraph.add(mntmZufallsgraph);
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
		mntmApplyCircleLayout.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/circle-empty.png")));
		mntmApplyCircleLayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						graphController.getGraphWrapper().setCircleLayout();
					}
				}
				).start();
			}
		});
		mnLayout.add(mntmApplyCircleLayout);
		
		mntmApplyHierarchyLayout = new JMenuItem("Hierarchisch");
		mntmApplyHierarchyLayout.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/crown2.png")));
		mntmApplyHierarchyLayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						graphController.getGraphWrapper().setHierarchyLayout();
					}
				}
				).start();
			}
		});
		mnLayout.add(mntmApplyHierarchyLayout);
		
		separator_3 = new JSeparator();
		separator_3.setForeground(Color.LIGHT_GRAY);
		mnLayout.add(separator_3);
		
		mntmFarbenReset = new JMenuItem("F\u00E4rbungen zur\u00FCcksetzen");
		mntmFarbenReset.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/icon_reset.png")));
		mnLayout.add(mntmFarbenReset);
		mntmFarbenReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						graphController.getGraphWrapper().resetColors();
					}
				}
				).start();
			}
		});
		
		mnStatistik = new JMenu("Statistik");
		menuBar.add(mnStatistik);
		
		mntmAnzeigen = new JMenuItem("Anzeigen");
		mntmAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statsWindow.show();
			}
		});
		mntmAnzeigen.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/stats.png")));
		mnStatistik.add(mntmAnzeigen);
		
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
		if (message.startsWith("/pbi ")) {
			lblProcessing.setVisible(true);
			progressBar.setVisible(true);
			progressBar.setValue(0);
			progressBar.setMaximum(Integer.valueOf( message.substring(5) ));
			report(progressBar.getMaximum() + " Elemente.\nVerarbeitung beginnt...");
			
		} else if (message.startsWith("/pbu ")) {
			progressBar.setValue(Integer.valueOf(message.substring(5)));
			
		} else if (message.startsWith("/pbe")) {
			lblProcessing.setVisible(false);
			progressBar.setVisible(false);
			
		} else if (message.startsWith("/gps")) {
			graphPanel.setVisible(true);
			
		} else if (message.startsWith("/gph")) {
			graphPanel.setVisible(false);
			
		} else {
			report(message);
		}
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

	/* (non-Javadoc)
	 * @see controller.StatsListener#receiveStats(java.lang.String[])
	 */
	@Override
	public void receiveStats(String... stats) {
		if (stats != null) {
			statsWindow.receiveStats(stats);
			statsWindow.show();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see controller.NodeListener#receiveStartNode(java.lang.String)
	 */
	@Override
	public void receiveStartNode(String nodeName) {
		if (nodeName != null) {
			txtSearchStart.setText(nodeName);
		}
	}

	/* (non-Javadoc)
	 * @see controller.NodeListener#receiveEndNode(java.lang.String)
	 */
	@Override
	public void receiveEndNode(String nodeName) {
		if (nodeName != null) {
			txtSearchGoal.setText(nodeName);
		}
	}

	
	//TODO DEBUG LISTENER METHOD
	@Override
	public void receiveSetLine(String message) {
		if (message == null)
			taDebug.setText("");
		else
			taDebug.append(message + "\n");
	}
	
}
