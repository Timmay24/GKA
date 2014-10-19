package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import controller.FileHandler;



public class JMenuSaveAs extends JMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 514649601828588358L;

	public JMenuSaveAs() {
		this.setText("Speichern unter ...");
		this.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				FileHandler.saveGraphAs();
			}
		});
	}

	public JMenuSaveAs(Icon arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuSaveAs(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuSaveAs(Action arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuSaveAs(String arg0, Icon arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public JMenuSaveAs(String arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
}