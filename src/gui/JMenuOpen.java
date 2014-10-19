package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import controller.FileHandler;

public class JMenuOpen extends JMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 543636227711883142L;

	public JMenuOpen() {
		this.setText("\u00D6ffnen ...");
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileHandler.openGraph();
			}
		});
	}

	public JMenuOpen(Icon arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuOpen(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuOpen(Action arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public JMenuOpen(String arg0, Icon arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public JMenuOpen(String arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
