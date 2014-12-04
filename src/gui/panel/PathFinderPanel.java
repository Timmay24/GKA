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
import controller.GraphController;



public class PathFinderPanel extends JPanel {

	private static final long serialVersionUID = -6733832647403657236L;
	
	private JLabel 					lblAlgorithmus;
	private JComboBox<PathFinder> 	cmbSearchAlgo;
	private JLabel 					lblStart;
	private JTextField 				txtStart;
	private JLabel 					lblGoal;
	private JTextField 				txtGoal;
	private JButton 				btnStart;

	/**
	 * Create the panel.
	 */
	public PathFinderPanel(GraphController graphController, JTextArea reportTextArea) {
		checkNotNull(graphController);
		checkNotNull(reportTextArea);
		
		this.setBounds(496, 445, 288, 137);
		this.setLayout(null);

		
		txtStart = new JTextField();
		txtStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtStart.getText().isEmpty())
					txtGoal.requestFocus();
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
		
		txtGoal = new JTextField();
		txtGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtGoal.getText().isEmpty())
					btnStart.doClick();
			}
		});
		txtGoal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtGoal.selectAll();
			}
		});
		txtGoal.setBounds(80, 73, 68, 20);
		this.add(txtGoal);
		txtGoal.setColumns(10);
		
		lblStart = new JLabel("Start:");
		lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStart.setBounds(10, 45, 60, 14);
		this.add(lblStart);
		
		lblGoal = new JLabel("Ziel:");
		lblGoal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGoal.setBounds(10, 75, 60, 14);
		this.add(lblGoal);
		
		btnStart = new JButton("suchen");
		btnStart.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/path_stats.png")));
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String start = txtStart.getText();
				String goal = txtGoal.getText();
				
				if (!(start.isEmpty() || goal.isEmpty()))
				{
					if (start.equals(goal))
					{
						reportTextArea.append("HINWEIS: Start- und Zielknoten sind identisch. Wegsuche wird nicht durchgeführt.\n");
					}
					else
					{
						graphController.findShortestWay(
						cmbSearchAlgo.getItemAt(cmbSearchAlgo.getSelectedIndex()), start, goal);
					}
				}
				else
				{
					reportTextArea.append("FEHLER: Kein Start- oder Zielknoten eingegeben.\n");
				}
			}
		});
		btnStart.setBounds(80, 103, 189, 23);
		this.add(btnStart);
		
		cmbSearchAlgo = new JComboBox<>();
		cmbSearchAlgo.setModel(new DefaultComboBoxModel<PathFinder>(new PathFinder[] {new BFS(), new Dijkstra(), new FloydWarshall()}));
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
	
	public void setGoal(String goalVertexName) {
		checkNotNull(goalVertexName);
		checkState(!goalVertexName.isEmpty());
		this.txtGoal.setText(goalVertexName);
	}

}
