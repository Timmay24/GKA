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

import main.graphs.algorithms.flow.EdmondKarp;
import main.graphs.algorithms.flow.FordFulkerson;
import main.graphs.algorithms.interfaces.FlowCalculator;
import controller.GraphController;



public class FlowCalculatorPanel extends JPanel {

	private static final long serialVersionUID = -6733832647403657236L;
	
	private JLabel 						lblAlgorithmus;
	private JComboBox<FlowCalculator> 	cmbFlowAlgo;
	private JLabel 						lblSource;
	private JTextField 					txtSource;
	private JLabel 						lblSink;
	private JTextField 					txtSink;
	private JButton 					btnStart;

	/**
	 * Create the panel.
	 */
	public FlowCalculatorPanel(GraphController graphController, JTextArea reportTextArea) {
		checkNotNull(graphController);
		checkNotNull(reportTextArea);
		
		this.setBounds(496, 445, 288, 137);
		this.setLayout(null);

		
		txtSource = new JTextField();
		txtSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtSource.getText().isEmpty())
					txtSink.requestFocus();
			}
		});
		txtSource.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtSource.selectAll();
			}
		});
		txtSource.setBounds(80, 42, 68, 20);
		this.add(txtSource);
		txtSource.setColumns(10);
		
		txtSink = new JTextField();
		txtSink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtSink.getText().isEmpty())
					btnStart.doClick();
			}
		});
		txtSink.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtSink.selectAll();
			}
		});
		txtSink.setBounds(80, 73, 68, 20);
		this.add(txtSink);
		txtSink.setColumns(10);
		
		lblSource = new JLabel("Quelle:");
		lblSource.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSource.setBounds(10, 45, 60, 14);
		this.add(lblSource);
		
		lblSink = new JLabel("Senke:");
		lblSink.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSink.setBounds(10, 75, 60, 14);
		this.add(lblSink);
		
		btnStart = new JButton("berechnen");
		btnStart.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/flow_stats.png")));
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String source = txtSource.getText();
				String sink = txtSink.getText();
				
				if (!(source.isEmpty() || sink.isEmpty()))
				{
					if (source.equals(sink))
					{
						reportTextArea.append("HINWEIS: Quelle und Senke sind identisch. Berechnung wird nicht durchgeführt.\n");
					}
					else
					{
						graphController.calculateMaxFlow(
						cmbFlowAlgo.getItemAt(cmbFlowAlgo.getSelectedIndex()), source, sink);
					}
				}
				else
				{
					reportTextArea.append("FEHLER: Keine Quelle oder Senke eingegeben.\n");
				}
			}
		});
		btnStart.setBounds(80, 103, 189, 23);
		this.add(btnStart);
		
		cmbFlowAlgo = new JComboBox<>();
		cmbFlowAlgo.setModel(new DefaultComboBoxModel<FlowCalculator>(new FlowCalculator[] {new FordFulkerson(), new EdmondKarp()}));
		cmbFlowAlgo.setBounds(80, 11, 189, 20);
		this.add(cmbFlowAlgo);
		
		lblAlgorithmus = new JLabel("Algorithmus:");
		lblAlgorithmus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlgorithmus.setBounds(10, 14, 60, 14);
		this.add(lblAlgorithmus);
	}
	
	public void setStart(String sourceVertexName) {
		checkNotNull(sourceVertexName);
		checkState(!sourceVertexName.isEmpty());
		this.txtSource.setText(sourceVertexName);
	}
	
	public void setGoal(String sinkVertexName) {
		checkNotNull(sinkVertexName);
		checkState(!sinkVertexName.isEmpty());
		this.txtSink.setText(sinkVertexName);
	}

}
