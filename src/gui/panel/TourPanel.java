package gui.panel;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.graphs.algorithms.interfaces.PathFinder;
import main.graphs.algorithms.path.BFS;
import main.graphs.algorithms.path.Dijkstra;
import main.graphs.algorithms.path.FloydWarshall;
import main.graphs.algorithms.tsp.MSTHeuristicTour;
import main.graphs.algorithms.tsp.NearestNeighbourHeuristicSearcher;
import controller.GraphController;



public class TourPanel extends JPanel {

	private static final long serialVersionUID = -6733832647403657236L;
	
	private JLabel 					lblAlgorithmus;
	private JComboBox<String> 		cmbSearchAlgo;
	private JLabel 					lblStart;
	private JTextField 				txtStart;
	private JButton 				btnStart;

	/**
	 * Create the panel.
	 */
	public TourPanel(GraphController graphController, JTextArea reportTextArea) {
		checkNotNull(graphController);
		checkNotNull(reportTextArea);
		
		this.setBounds(496, 445, 288, 137);
		this.setLayout(null);

		
		txtStart = new JTextField();
		txtStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtStart.getText().isEmpty())
					btnStart.doClick();
			}
		});
		txtStart.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtStart.selectAll();
			}
		});
		txtStart.setBounds(80, 42, 68, 20);
		this.add(txtStart);
		txtStart.setColumns(10);
		
		lblStart = new JLabel("Start:");
		lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStart.setBounds(10, 45, 60, 14);
		this.add(lblStart);
		
		btnStart = new JButton("berechnen");
		btnStart.setIcon(new ImageIcon(TourPanel.class.getResource("/ressources/images/circle-empty.png")));
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String 	start = txtStart.getText();
				String	algorithm = (String) cmbSearchAlgo.getSelectedItem();
				
				if (!start.isEmpty())
				{
					if (algorithm.equalsIgnoreCase("MST-Heuristic"))
					{
						graphController.getGraphWrapper().findTour(start);
					}
					else if (algorithm.equalsIgnoreCase("Nearest-Neighbour-Heuristic"))
					{
						graphController.getGraphWrapper().findRoute(start);
					}
				}
				else
				{
					reportTextArea.append("FEHLER: Kein Startknoten eingegeben.\n");
				}
			}
		});
		btnStart.setBounds(80, 103, 189, 23);
		this.add(btnStart);
		
		cmbSearchAlgo = new JComboBox<>();
		cmbSearchAlgo.setModel(new DefaultComboBoxModel<String>(new String[] {"MST-Heuristic", "Nearest-Neighbour-Heuristic"}));
		cmbSearchAlgo.setBounds(80, 11, 189, 20);
		this.add(cmbSearchAlgo);
		
		lblAlgorithmus = new JLabel("Algorithmus:");
		lblAlgorithmus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlgorithmus.setBounds(10, 14, 60, 14);
		this.add(lblAlgorithmus);
	}
	
	public void setStart(String startVertexName) {
		checkNotNull(startVertexName);
		checkState(!startVertexName.isEmpty());
		this.txtStart.setText(startVertexName);
	}
}
