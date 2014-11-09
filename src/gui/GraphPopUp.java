package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import main.graphs.GKAGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

public class GraphPopUp extends JPopupMenu {

	private static final long serialVersionUID = -5118595692411946953L;
	
	private GKAGraph 	wrapper;
	private mxCell 		cell;
	private Component 	component;
//	private MouseEvent  mEvent;
	
	public GraphPopUp(GKAGraph wrapper, mxGraphComponent graphComponent, MouseEvent mEvent, mxCell cell) {
		component = graphComponent;
//		this.mEvent = mEvent;
		this.wrapper = wrapper;
		cellSetup(cell);
		this.show(component, mEvent.getX(), mEvent.getY());
	}
	
	private void cellSetup(mxCell cell) {
		this.cell = cell;
		
		if (cell != null && cell instanceof mxCell) {
			if (cell.isEdge()) {
				edgeSetup();
			} else if (cell.isVertex()) {
				vertexSetup();
			}
		} else {
			bareSetup();
		}
		
	}
	
	private void bareSetup() {
		final JMenuItem item = new JMenuItem("Knoten hinzuf\u00FCgen");
		item.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/vertex_add.png")));
		item.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			String newVertexName = JOptionPane.showInputDialog(null, "Namen des Knoten angeben:");
			if (newVertexName != null && !newVertexName.isEmpty()) {
				wrapper.addVertex(newVertexName);
			}
			dispose();
		}
		});
		this.add(item);
	}

	private void edgeSetup() {
		// TODO DEBUG TOOL
		final JMenuItem item2 = new JMenuItem("Kante einf\u00E4rben");
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String newColor = JOptionPane.showInputDialog(null, "Farbcode eingeben:");
				if (newColor != null && !newColor.isEmpty()) {
					String name = cell.getValue().toString().replace("(", "").replace(")","");
					if (wrapper.isWeighted()) {
						name = name.substring(0, name.indexOf(":")).trim();
					}
					wrapper.colorEdge(name, newColor);
				}
			}
		});
		this.add(item2);
		
		final JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		this.add(separator);
		//TODO DEBUG END
		
		final JMenuItem item = new JMenuItem("Kante l\u00F6schen");
		item.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/edge_remove.png")));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = cell.getValue().toString().replace("(", "").replace(")","");
				if (wrapper.isWeighted()) {
					name = name.substring(0, name.indexOf(":")).trim();
				}
				wrapper.removeEdge(name);
			}
		});
		this.add(item);
		
	}
	
	private void vertexSetup() {
		
		// TODO DEBUG TOOL
		final JMenuItem itemS = new JMenuItem("Als Startknoten markieren");
		itemS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wrapper.setStartNode(cell.getValue().toString());
			}
		});
		this.add(itemS);
		
		final JMenuItem itemT = new JMenuItem("Als Zielknoten markieren");
		itemT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wrapper.setEndNode(cell.getValue().toString());
			}
		});
		this.add(itemT);
				
		final JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		this.add(separator);
		//TODO DEBUG END
		
		final JMenuItem item = new JMenuItem("Knoten l\u00F6schen");
		item.setIcon(new ImageIcon(MainWindow.class.getResource("/ressources/images/vertex_remove.png")));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = cell.getValue().toString();
				wrapper.removeVertex(name);
			}
		});
		this.add(item);
	}
	
	private void dispose() {
	}
}
