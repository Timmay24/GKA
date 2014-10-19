package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import org.jgraph.JGraph;

import com.mxgraph.swing.mxGraphComponent;

import controller.GraphController;
import controller.GuiController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {
	
	private			GuiController		guiController;
	private 		GraphController 	graphController;
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
	private 		int[]				verNo = {0,1,1};

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
//		graphPanel.add((JGraph) graphController.getGraph()); //TODO: crasht eventuell wegen JGraph =/= Listenable(Un)DirectedGraph<>
//		graphPanel.add(graphController.getGraphComponent());
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

		
		/**
		 * // Standartgrößen zur Formatierung
		 * int stdWidth = 200;
		 * int stdHeight = 40;
		 * int initLeftOffset = 50;
		 * int initTopOffset = 50;
		 * int spaceOffset = 10; // Absatz zur nächsten Zeile
		 */
		

		// Hauptframe initialisieren
		mainFrame = new JFrame();
		mainFrame.setTitle("BFS Tool " + verNo[0] + "." + verNo[1] + "." + verNo[2]);
		mainFrame.setResizable(false);
		// Größe und Position des Fensters festlegen
		mainFrame.setBounds(0, 0, 800, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(
				(dim.width / 2 - mainFrame.getSize().width / 2),
				(dim.height / 2 - mainFrame.getSize().height / 2));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null); // == absolute layout
		
		graphPanel = new JPanel();
		graphPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainFrame.repaint(); //TODO 4 debug
			}
		});
		graphPanel.setBackground(Color.LIGHT_GRAY);
		graphPanel.setBounds(10, 11, 774, 326);
		graphPanel.add(graphController.getGraphComponent());
		mainFrame.getContentPane().add(graphPanel);
		graphPanel.setLayout(null);
		
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
		reportTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(reportTextArea);
		
		menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);
		
		mnFile = new JMenu("Datei");
		menuBar.add(mnFile);
		
		mntmNewGraph = new JMenuItem("Neuer Graph");
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
	
}
