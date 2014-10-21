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
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import main.graphs.GraphType;
import main.graphs.Vertex;
import controller.GraphController;
import controller.MessageListener;
import javax.swing.ImageIcon;

public class MainWindow implements MessageListener {
	
	private 	int[]				verNo = {0,4,32};
	private 	GraphController		graphController;
	private 	JFrame 				mainFrame;
	private 	JMenuItem 			mntmInfo;
	private 	JMenu 				mnQuestionmark;
	private 	JPanel 				graphPanel;
	private 	JPanel 				controlPanel;
	private 	JToggleButton		tglbtnNewVertice;
	private 	JToggleButton 		tglbtnNewEdge;
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
	private 	JMenuItem 			mntmGerichtet;
	private 	JMenuItem 			mntmUngerichtet;
	private 	JMenuItem 			mntmGerichtetGewichtet;
	private 	JMenuItem 			mntmGerichtetUngewichtet;
	private 	JMenuItem 			mntmBeispiel;
	private 	JSeparator 			separator_1;

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
	 * Create the application.
	 */
	public MainWindow() {
		graphController = new GraphController();
		initialize();
		graphController.addMessageListener(this);
		
		createSampleGraph();
	}
	
	private void createSampleGraph() {
		graphPanel.removeAll();
		graphPanel.add(graphController.getGraphComponent());
		mainFrame.repaint();
	}
	
	private void newGraph(GraphType graphType) {
		graphPanel.removeAll();
		graphPanel.add(graphController.newGraph(graphType));
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
		mainFrame.setTitle("BFS Tool " + verNo[0] + "." + verNo[1] + "." + verNo[2]);
		// Größe und Position des Fensters festlegen
		mainFrame.setBounds(0, 0, 800, 600);
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
		createSampleGraph(); // Beim Start einen Beispielgraphen laden
		mainFrame.getContentPane().add(graphPanel);
		graphPanel.setLayout(new BorderLayout(0, 0));
		
		controlPanel = new JPanel();
		controlPanel.setBorder(new TitledBorder(null, "Hinzuf\u00FCgen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		controlPanel.setBounds(10, 348, 194, 56);
		mainFrame.getContentPane().add(controlPanel);
		controlPanel.setLayout(null);
		
		tglbtnNewVertice = new JToggleButton("Knoten");
		tglbtnNewVertice.setBounds(11, 21, 80, 23);
		controlPanel.add(tglbtnNewVertice);
		
		tglbtnNewEdge = new JToggleButton("Kante");
		tglbtnNewEdge.setBounds(101, 21, 80, 23);
		controlPanel.add(tglbtnNewEdge);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 414, 774, 126);
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
		
		JButton btnNewButton = new JButton("Alle Adjazenten von v1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Collection<Vertex> res = graphController.getGraphWrapper().getAllAdjacentsOf( graphController.getGraphWrapper().getVertex("v1") );
				for (Vertex v : res) {
					report(v.toString());
				}
			}
		});
		btnNewButton.setBounds(217, 369, 151, 23);
		mainFrame.getContentPane().add(btnNewButton);
		
		menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);
		
		mnFile = new JMenu("Datei");
		menuBar.add(mnFile);
		
		mnNeuerGraph = new JMenu("Neuer Graph");
		mnNeuerGraph.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/new_graph.png")));
		mnFile.add(mnNeuerGraph);
		
		mntmGerichtet = new JMenuItem("Gerichtet");
		mnNeuerGraph.add(mntmGerichtet);
		
		mntmUngerichtet = new JMenuItem("Ungerichtet");
		mnNeuerGraph.add(mntmUngerichtet);
		
		mntmGerichtetGewichtet = new JMenuItem("Gerichtet + Gewichtet");
		mnNeuerGraph.add(mntmGerichtetGewichtet);
		
		mntmGerichtetUngewichtet = new JMenuItem("Gerichtet + Ungewichtet");
		mnNeuerGraph.add(mntmGerichtetUngewichtet);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		mnNeuerGraph.add(separator_1);
		
		mntmBeispiel = new JMenuItem("Beispielgraphen");
		mntmBeispiel.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/example.png")));
		mnNeuerGraph.add(mntmBeispiel);
		
		JSeparator separator_2 = new JSeparator();
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
		
		mntmApplyCircleLayout = new JMenuItem("Im Kreis anordnen");
		mntmApplyCircleLayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graphController.setCircleLayout();
			}
		});
		mnLayout.add(mntmApplyCircleLayout);
		
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
}
