package gui.menu;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import main.graphs.GKAGraph;
import controller.FileHandler;
import controller.GKAController;

public class JMenuSave extends JMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 514649601828588358L;

	public JMenuSave(final GKAController controller) {
		this.setText("Speichern");
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.saveGraph();
					}
				}
				).start();
			}
		});
	}

	public JMenuSave(Icon arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuSave(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuSave(Action arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuSave(String arg0, Icon arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public JMenuSave(String arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
}
