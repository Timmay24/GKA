package gui;

import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import controller.interfaces.StatsListener;

public class PathFinderStatsWindow implements StatsListener {

	private 		JFrame 					frmStatistiken;
	private 		JTextField 				txtTime;
	private 		JTextField 				txtLength;
	private 		JTextField 				txtHits;
	private 		JTable 					tableHistory;
	private 		JLabel 					chart;
	private 		JButton 				btnClearHistory;
	
	private 		Vector<Vector<String>> 	history;
	private final 	Vector<String>			headline = new Vector<>(Arrays.asList("Run","Algorithmus","Zeit (ms)", "Weglaenge", "Hits"));


	/**
	 * KONSTRUKTOR
	 */
	public PathFinderStatsWindow() {
		initialize();
		history = new Vector<>();
	}
	
	/**
	 * Zeigt dieses Fenster an.
	 */
	public void show() {
		this.frmStatistiken.setVisible(true);
	}
	
	/**
	 * Versteckt dieses Fenster an.
	 */
	public void hide() {
		this.frmStatistiken.setVisible(false);
	}

	/**
	 * Inhalt des Fenster initialisieren.
	 */
	private void initialize() {
		
		// Erst mal die System-Optik aufzwingen
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
				
		frmStatistiken = new JFrame();
		frmStatistiken.setType(Type.UTILITY);
		frmStatistiken.setTitle("Statistik - Wegfindung");
		frmStatistiken.setResizable(false);
		frmStatistiken.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		frmStatistiken.setBounds(100, 100, 380, 377);
		frmStatistiken.getContentPane().setLayout(null);
		
		JPanel panelLastRun = new JPanel();
		panelLastRun.setBorder(new TitledBorder(null, "Letzter Durchgang", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLastRun.setBounds(10, 11, 228, 140);
		frmStatistiken.getContentPane().add(panelLastRun);
		panelLastRun.setLayout(null);
		
		JLabel label = new JLabel("Ben\u00F6tigte Zeit:");
		label.setBounds(10, 21, 71, 20);
		panelLastRun.add(label);
		
		txtTime = new JTextField();
		txtTime.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTime.setEditable(false);
		txtTime.setColumns(10);
		txtTime.setBounds(91, 21, 105, 20);
		panelLastRun.add(txtTime);
		
		JLabel label_1 = new JLabel("ms");
		label_1.setBounds(206, 21, 13, 20);
		panelLastRun.add(label_1);
		
		JLabel label_2 = new JLabel("Wegl\u00E4nge:");
		label_2.setBounds(10, 47, 80, 20);
		panelLastRun.add(label_2);
		
		txtLength = new JTextField();
		txtLength.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLength.setEditable(false);
		txtLength.setColumns(10);
		txtLength.setBounds(91, 47, 105, 20);
		panelLastRun.add(txtLength);
		
		JLabel label_3 = new JLabel("Hitcounter:");
		label_3.setBounds(10, 73, 80, 20);
		panelLastRun.add(label_3);
		
		txtHits = new JTextField();
		txtHits.setHorizontalAlignment(SwingConstants.RIGHT);
		txtHits.setEditable(false);
		txtHits.setColumns(10);
		txtHits.setBounds(91, 73, 105, 20);
		panelLastRun.add(txtHits);
		
		btnClearHistory = new JButton("Verlauf l\u00F6schen");
		btnClearHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				history = new Vector<>();
				updateHistory();
				txtTime.setText("");
				txtLength.setText("");
				txtHits.setText("");
				
			}
		});
		btnClearHistory.setBounds(91, 104, 105, 23);
		panelLastRun.add(btnClearHistory);
		panelLastRun.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtTime, txtLength, txtHits, btnClearHistory, label, label_1, label_2, label_3}));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 161, 354, 181);
		frmStatistiken.getContentPane().add(scrollPane);
		
		tableHistory = new JTable();
		tableHistory.setColumnSelectionAllowed(true);
		scrollPane.setViewportView(tableHistory);
		
		try {
			chart = new JLabel(new ImageIcon(ImageIO.read(new File(".\\src\\ressources\\images\\chart.png".replace("\\", "/")))));
			chart.setBounds(245, 26, 115, 115);
			chart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					InfoPopup.showInfo(null);
				}
			});
			frmStatistiken.getContentPane().add(chart);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		updateHistory();
	}
	
	/**
	 * Aktualisiert die Anzeige der Verlaufstabelle.
	 */
	private void updateHistory() {
		tableHistory.setModel( new DefaultTableModel(history, headline) );
	}

	/* (non-Javadoc)
	 * @see controller.StatsListener#receiveStats(java.lang.String[])
	 */
	@Override
	public void onStatsReceived(Object prototype, String... stats) {
		Vector<String> row = new Vector<>();
		
		// Run-Nummer hinzugefuegen
		row.add(String.valueOf(((history.size() + 1))));
		
		// Algo, Zeit, Weglaenge und Hits hinzufuegen
		for (String s : stats) {
			row.add(s);
		}
		// Zeile zum Hauptvektor hinzufuegen
		history.add(row);
		
		// Table update
		updateHistory();
		
		displayLastRun(row.get(2), row.get(3), row.get(4));
	}
	
	/**
	 * Kombi-Setter fuer Textfelder fuer den letzten Durchgang.
	 * 
	 * @param timeElapsed
	 * @param wayLength
	 * @param hitCount
	 */
	private void displayLastRun(String timeElapsed, String wayLength, String hitCount) {
		txtTime.setText(timeElapsed);
		txtLength.setText(wayLength);
		txtHits.setText(hitCount);
	}
}
