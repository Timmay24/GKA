package controller;

public interface CellSender<E> {

	/**
	 * @param cellListener Objekt, das sich zum Empfang von JGraph Zellen anmelden will.
	 */
	public void addCellListener(CellListener<E> cellListener);
	
}
