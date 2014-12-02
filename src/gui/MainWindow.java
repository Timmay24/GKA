package gui;

import gui.menu.JMenuOpen;
import gui.menu.JMenuSave;
import gui.menu.JMenuSaveAs;
import gui.panel.FlowCalculatorPanel;
import gui.panel.PathFinderPanel;

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






//import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
//import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;






//import main.graphs.Algorithms;
import main.graphs.GKAEdge;
import main.graphs.GraphType;
import main.graphs.algorithms.interfaces.FlowCalculator;
import main.graphs.algorithms.interfaces.PathFinder;

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

//import org.eclipse.wb.swing.FocusTraversalOnArray;






import javax.swing.JTabbedPane;

import static com.google.common.base.Preconditions.*;

public class MainWindow implements MessageListener, CellListener<mxCell>, AdapterUpdateListener, StatsListener, SetListener, NodeListener {
	
	private GraphController graphController;
	private int[] verNo = {0,8,125};
	private JButton btnAddEdge;
	private JFrame mainFrame;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblGewicht;
	private JLabel lblName;
	private JLabel lblProcessing;
	private JMenu mnFile;
	private JMenu mnGerichtet;
	private JMenu mnLayout;
	private JMenu mnNeuerGraph;
	private JMenu mnQuestionmark;
	private JMenu mnStatistik;
	private JMenu mnUngerichtet;
	private JMenuBar menuBar;
	private JMenuItem mntmPathFinderStats;
	private JMenuItem mntmApplyCircleLayout;
	private JMenuItem mntmApplyHierarchyLayout;
	private JMenuItem mntmBeispiel;
	private JMenuItem mntmClearReport;
	private JMenuItem mntmResetColors;
	private JMenuItem mntmGerichtetGewichtet;
	private JMenuItem mntmGerichtetUngewichtet;
	private JMenuItem mntmInfo;
	private JMenuItem mntmOpen;
	private JMenuItem mntmQuit;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmUngerichtetGewichtet;
	private JMenuItem mntmUngerichtetUngewichtet;
	private JMenuItem mntmZufallsgraph;
	private JPanel edgePanel;
	private JPanel graphPanel;
	private PathFinderPanel pathFinderPanel;
	private FlowCalculatorPanel flowCalculatorPanel;
//	private JPanel vertexPanel;
	private JPopupMenu pmRClickReport;
	private JProgressBar progressBar;
	private JScrollPane reportPane;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSpinner spinAEWeight;
	private JTextArea reportTextArea;
//	private JTextField txtAddVertex;
	private JTextField txtAEName;
	private JTextField txtAESource;
	private JTextField txtAETarget;
//	private JTextField txtSearchStart;
	private PathFinderStatsWindow pathStatsWindow;
	private FlowCalculatorStatsWindow flowStatsWindow;
	private int	addVertexCounter = 0;

	
	//TODO DEBUG UTIL
	private JTextArea taDebug;
	private JScrollPane scrDebugPane;
	private JTabbedPane tabsAnalysing;
	private JMenuItem mntmAddVertex;
	private JMenu mnKnoten;

	

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
		pathStatsWindow = new PathFinderStatsWindow();
		flowStatsWindow = new FlowCalculatorStatsWindow();
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
		checkNotNull(graphType);
		graphController.newGraph(graphType);
	}
	
	/* (non-Javadoc)
	 * @see controller.AdapterUpdateListener#receiveAdapterUpdate(com.mxgraph.swing.mxGraphComponent)
	 */
	@Override
	public void receiveAdapterUpdate(mxGraphComponent graphComponent) {
		checkNotNull(graphComponent);
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
		lblProcessing.setBounds(10, 130, 190, 20);
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
		
//		vertexPanel = new JPanel();
//		vertexPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Knoten hinzuf\u00FCgen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
//		vertexPanel.setBounds(10, 343, 156, 56);
//		mainFrame.getContentPane().add(vertexPanel);
//		vertexPanel.setLayout(null);
//		
//		lblNewLabel = new JLabel("Name:");
//		lblNewLabel.setBounds(10, 23, 37, 14);
//		vertexPanel.add(lblNewLabel);
//		
//		txtAddVertex = new JTextField();
//		txtAddVertex.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				btnAddVertex.doClick();
//				
//			}
//		});
//		txtAddVertex.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusGained(FocusEvent arg0) {
//				txtAddVertex.setText("");
//			}
//		});
//		txtAddVertex.setBounds(46, 20, 51, 20);
//		vertexPanel.add(txtAddVertex);
//		txtAddVertex.setColumns(10);
//		
//		btnAddVertex = new JButton("");
//		btnAddVertex.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/add.png")));
//		btnAddVertex.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				if (!txtAddVertex.getText().isEmpty()) {
//					graphController.addVertex(txtAddVertex.getText());
//					txtAddVertex.setText("");
//				}
//			}
//		});
//		btnAddVertex.setBounds(107, 19, 37, 23);
//		vertexPanel.add(btnAddVertex);
//		vertexPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, txtAddVertex, btnAddVertex}));
		
		edgePanel = new JPanel();
		edgePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Kanten hinzuf\u00FCgen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		edgePanel.setBounds(10, 348, 476, 56);
		mainFrame.getContentPane().add(edgePanel);
		edgePanel.setLayout(null);
		
		label = new JLabel("Source:");
		label.setBounds(10, 23, 37, 14);
		edgePanel.add(label);
		
		txtAESource = new JTextField();
		txtAESource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				if (!txtAESource.getText().isEmpty())
					txtAETarget.requestFocus();
			}
		});
		txtAESource.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0)
			{
				txtAESource.setText(txtAETarget.getText());
				txtAESource.selectAll();
			}
		});
		txtAESource.setColumns(10);
		txtAESource.setBounds(57, 20, 37, 20);
		edgePanel.add(txtAESource);
		
		label_1 = new JLabel("Target:");
		label_1.setBounds(104, 23, 37, 14);
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
				txtAETarget.selectAll();
			}
		});
		txtAETarget.setColumns(10);
		txtAETarget.setBounds(151, 20, 37, 20);
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
				txtAEName.setText(
						txtAESource.getText()
					+	txtAETarget.getText()
						);
				txtAEName.selectAll();
			}
		});
		txtAEName.setBounds(236, 20, 62, 20);
		edgePanel.add(txtAEName);
		txtAEName.setColumns(10);
		
		lblName = new JLabel("Name:");
		lblName.setBounds(198, 23, 37, 14);
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
		spinAEWeight.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		spinAEWeight.setBounds(364, 20, 46, 20);
		edgePanel.add(spinAEWeight);
		
		lblGewicht = new JLabel("Gewicht:");
		lblGewicht.setBounds(308, 23, 46, 14);
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
					graphController.getGraphWrapper().addEdge(source, target, edgeName, edgeWeight, false);
				} else {
					graphController.getGraphWrapper().addEdge(source, target, edgeName, false);
				}
			}
		});
		btnAddEdge.setBounds(420, 19, 46, 23);
		edgePanel.add(btnAddEdge);
		
		scrDebugPane = new JScrollPane();
		scrDebugPane.setBounds(10, 596, 178, 90);
		mainFrame.getContentPane().add(scrDebugPane);
		
		taDebug = new JTextArea();
		taDebug.setFont(new Font("Consolas", Font.PLAIN, 11));
		scrDebugPane.setViewportView(taDebug);
		
		// Tabs der Algorithmen
		pathFinderPanel = new PathFinderPanel(graphController, reportTextArea);
		flowCalculatorPanel = new FlowCalculatorPanel(graphController, reportTextArea);
		
		tabsAnalysing = new JTabbedPane(JTabbedPane.TOP);
		tabsAnalysing.setBounds(496, 348, 288, 187);
		tabsAnalysing.addTab("Wegfindung", pathFinderPanel);
		tabsAnalysing.addTab("Flussberechnung", flowCalculatorPanel);
		mainFrame.getContentPane().add(tabsAnalysing);
		
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
				System.exit(0);
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
		
		mntmResetColors = new JMenuItem("F\u00E4rbungen zur\u00FCcksetzen");
		mntmResetColors.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/icon_reset.png")));
		mnLayout.add(mntmResetColors);
		mntmResetColors.addActionListener(new ActionListener() {
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
		
		mnKnoten = new JMenu("Knoten");
		menuBar.add(mnKnoten);
		
		mntmAddVertex = new JMenuItem("hinzuf\u00FCgen");
		mntmAddVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String vertexName = (String) JOptionPane.showInputDialog(null, "Name des Knotens:", "Knoten hinzuf\u00FCgen", JOptionPane.PLAIN_MESSAGE, null, null, "v" + addVertexCounter);
				if (vertexName != null && !vertexName.isEmpty()) {
					graphController.addVertex(vertexName);
					addVertexCounter++;
				}
			}
		});
		mntmAddVertex.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/add.png")));
		mnKnoten.add(mntmAddVertex);
		
		mnStatistik = new JMenu("Statistik");
		menuBar.add(mnStatistik);
		
		mntmPathFinderStats = new JMenuItem("Wegfindung");
		mntmPathFinderStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pathStatsWindow.show();
			}
		});
		mntmPathFinderStats.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/stats.png")));
		mnStatistik.add(mntmPathFinderStats);
		
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
		checkNotNull(message);
		reportTextArea.append(message + "\n");
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		checkNotNull(component);
		checkNotNull(popup);
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
	public void onStatsReceived(Object prototype, String... stats) {
		checkNotNull(stats);
		checkNotNull(prototype);
		
		if (prototype instanceof PathFinder) {
			pathStatsWindow.onStatsReceived(prototype, stats);
			pathStatsWindow.show();
		} else if (prototype instanceof FlowCalculator) {
			flowStatsWindow.onStatsReceived(prototype, stats);
			flowStatsWindow.show();
		}
	}
	
	/* (non-Javadoc)
	 * @see controller.NodeListener#receiveStartNode(java.lang.String)
	 */
	@Override
	public void receiveStartNode(String nodeName) {
		if (nodeName == null) {
			pathFinderPanel.setStart("");
			flowCalculatorPanel.setStart("");
		} else {
			pathFinderPanel.setStart(nodeName);
			flowCalculatorPanel.setStart(nodeName);
		}
	}

	/* (non-Javadoc)
	 * @see controller.NodeListener#receiveEndNode(java.lang.String)
	 */
	@Override
	public void receiveEndNode(String nodeName) {
		if (nodeName == null) {
			pathFinderPanel.setGoal("");
			flowCalculatorPanel.setGoal("");
		} else {
			pathFinderPanel.setGoal(nodeName);
			flowCalculatorPanel.setGoal(nodeName);
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
