package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import main.graphs.GraphType;
import controller.GraphController;
import controller.GuiController;
import controller.MessageListener;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;

public class MainWindow implements MessageListener {
	
	private			GuiController		guiController;
	private 		GraphController		graphController;
	private 		JFrame 				mainFrame;
	private 		JMenuItem 			mntmInfo;
	private 		JMenu 				mnQuestionmark;
	private 		JPanel 				graphPanel;
	private 		JPanel 				controlPanel;
	private 		JToggleButton		tglbtnNewVertice;
	private 		JToggleButton 		tglbtnNewEdge;
	private 		JScrollPane 		scrollPane;
	private 		JTextArea 			reportTextArea;
	private 		JMenuBar 			menuBar;
	private 		JMenu 				mnFile;
	private 		JMenuItem 			mntmNewGraph;
	private 		JMenuItem 			mntmOpen;
	private 		JMenuItem 			mntmSave;
	private 		JMenuItem			mntmSaveAs;
	private 		JSeparator 			separator;
	private 		JMenuItem 			mntmQuit;
	private 		int[]				verNo = {0,2,2};
	private 		JPopupMenu 			pmRClickReport;
	private 		JMenuItem 			mntmClearReport;
	private JMenu mnReporting;
	private JMenuItem mntmAlleKantenAusgeben;

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
		guiController = new GuiController();
		graphController = GraphController.getInstance();
		initialize();
		graphController.addMessageListener(this);
		
		createSampleGraph();
	}
	
	private void createSampleGraph() {
		graphPanel.removeAll();
		graphPanel.add(graphController.createSampleSetup());
		mainFrame.repaint();
	}
	
	private void newGraph() {
		newGraph(GraphType.DIRECTED_WEIGHTED);
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
		mainFrame.setResizable(false);
		mainFrame.setTitle("BFS Tool " + verNo[0] + "." + verNo[1] + "." + verNo[2]);
		// Gr��e und Position des Fensters festlegen
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
		graphController.createSampleSetup();          // Beim Start einen Beispielgraphen laden
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
				if (arg0.getButton() == 3) {
					pmRClickReport.setVisible(true);
				}
			}
		});
		reportTextArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		scrollPane.setViewportView(reportTextArea);
		
		pmRClickReport = new JPopupMenu();
		pmRClickReport.setLabel("L\u00F6schen");
		addPopup(reportTextArea, pmRClickReport);
		
		mntmClearReport = new JMenuItem("L\u00F6schen");
		mntmClearReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reportTextArea.setText("");
			}
		});
		pmRClickReport.add(mntmClearReport);
		
		menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);
		
		mnFile = new JMenu("Datei");
		menuBar.add(mnFile);
		
		mntmNewGraph = new JMenuItem("Neuer Graph");
		mntmNewGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGraph();
			}
		});
		mnFile.add(mntmNewGraph);
		
		mntmOpen = new JMenuOpen();
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuSave();
		mnFile.add(mntmSave);
		
		mntmSaveAs = new JMenuSaveAs();
		mnFile.add(mntmSaveAs);
		
		separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		mnFile.add(separator);
		
		mntmQuit = new JMenuItem("Beenden");
		mntmQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
			}
		});
		mnFile.add(mntmQuit);
		
		JMenu mnLayout = new JMenu("Layout");
		menuBar.add(mnLayout);
		
		JMenuItem mntmApplyCircleLayout = new JMenuItem("Im Kreis anordnen");
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
				graphController.printAllEdges();
			}
		});
		mnReporting.add(mntmAlleKantenAusgeben);
		
		mnQuestionmark = new JMenu("?");
		menuBar.add(mnQuestionmark);
		
		mntmInfo = new JMenuItem("Info ...");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InfoPopup.showInfo(null);
			}
		});
		mnQuestionmark.add(mntmInfo);
	}
	
	public JTextArea getReportTextArea() {
		return reportTextArea;
	}

	@Override
	public void giveMessage(String message) {
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
