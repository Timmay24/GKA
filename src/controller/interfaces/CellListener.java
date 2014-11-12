package controller.interfaces;

import java.awt.event.MouseEvent;

public interface CellListener<E> {

	/**
	 * Anlaufstelle zum Empfang von JGraph Zellen zur Weiterverarbeitung.
	 * 
	 * @param cell Zelle, die durch die Maus anvisiert wurde.
	 * @param e Das ausloesende Mausereignis.
	 */
	public void receiveCell(E cell, MouseEvent e);
	
}
