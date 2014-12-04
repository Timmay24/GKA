package main.graphs.algorithms.interfaces;

public interface Batch<E> {
	
	/**
	 * Fuegt ein Element, an der von der implementierenden Klasse vorgesehenden Stelle, ein
	 * 
	 * @param arg0 Einzufuegendes Element
	 * @return true, wenn Element hinzugefuegt wurde
	 */
	public boolean add(E arg0);
	
	/**
	 * Entfernt ein Element an der von an der von der implementierenden Klasse vorgesehenden Stelle und gibt es zurueck
	 * 
	 * @return Das entfernte Element
	 */
	public E remove();
	
	/**
	 * Prueft, ob der Objektinhalt leer ist
	 * 
	 * @return true, wenn leer
	 */
	public boolean isEmpty();
	
	/**
	 * Leert den Objektinhalt
	 */
	public void clear();
	
}
