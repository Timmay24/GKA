package main.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Timmay
 *
 * @param <RK> Typ des Zeilenschluessels
 * @param <CK> Typ des Spaltenschluessels
 * @param <V>  Typ des Zellenwertes 
 */
public class Matrix <RK,CK,V> {

	Map<RK, Map<CK,V> > matrix;
	
	/**
	 * KONSTRUKTOR
	 */
	public Matrix() {
		matrix = new HashMap<>();
	}
	
	/**
	 * @param row Zeilenschluessel
	 * @param column Spaltenschluessel
	 * @return Wert, der hinter der Kombination (row,column) hinterlegt ist.
	 */
	public V getValueAt(RK row, CK column) {
		if (!matrix.containsKey(row)) {
			return null;
		}
		
		if (!matrix.get(row).containsKey(column)) {
			return null;
		}
		
		return matrix.get(row).get(column);
	}
	
	/**
	 * @param row Zeilenschluessel
	 * @param column Spaltenschluessel
	 * @param value Einzutragener Wert
	 */
	public void setValueAt(RK row, CK column, V value) {
		Map<CK,V> columnMap = matrix.get(row);
		
		// Falls zu row kein Wert existiert, 
		if (columnMap == null) {
			// wird eine neue Werte-Map (columnMap) erzeugt
			columnMap = new HashMap<>();
			// und in ihr zum Schluessel column, value eingetragen.
			columnMap.put(column, value);
			// Zum Schluss wird die columnMap bei row eingetragen.
			matrix.put(row, columnMap);
		// Falls bereits eine Wertemap (columnMap) hinter row existiert,
		} else {
			// wird value in ihr zu column eingetragen.
			columnMap.put(column, value);
		}
	}
}