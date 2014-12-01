package main.graphs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

	Map< RK, Map<CK,V> > matrix;
	
	/**
	 * KONSTRUKTOR
	 */
	public Matrix() {
		matrix = new HashMap<>();
	}
	
	
	public Matrix(Collection<RK> rows, Collection<CK> columns, V initValue) {
		this();
		for (RK row : rows) {
			for (CK column : columns) {
				setValueAt(row, column, initValue);
			}
		}
	}
	
	public Matrix(Collection<RK> rows, Collection<CK> columns) {
		this(rows, columns, null);
	}
	
	/**
	 * @param row Zeilenschluessel
	 * @param column Spaltenschluessel
	 * @return Wert, der hinter der Kombination (row,column) hinterlegt ist.
	 */
	public V getValueAt(RK row, CK column) {
		if (!matrix.containsKey(row))
			return null;
		
		if (!matrix.get(row).containsKey(column))
			return null;
		
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
	
	/**
	 * @return Gibt alle Zeilenschluessel der Matrix zurueck
	 */
	public Set<RK> getRowKeys() {
		return Collections.unmodifiableSet(matrix.keySet());
	}
	
	
	/**
	 * @return Gibt alle Spaltenschluessel der Matrix zurueck
	 */
	public Set<CK> getColumnKeys() {
		Set<CK> result = new HashSet<>();
		
		for (RK row : getRowKeys())
			result.addAll( matrix.get(row).keySet() );
		
		return Collections.unmodifiableSet(result);
	}
	
	/**
	 * @param row Zeile, dessen Spaltenschluessel zurueckgegeben werden sollen
	 * @return Gibt alle Spaltenschluessel einer bestimmten Zeile zurueck
	 */
	public Set<CK> getColumnKeys(RK row) {
		if (!matrix.containsKey(row))
			return Collections.unmodifiableSet(new HashSet<>());
		
		return Collections.unmodifiableSet(matrix.get(row).keySet());
	}
	
	public String toString() {
		String result = "[R]:[";
		
		for (CK column : getColumnKeys())
			result += " " + column + ",";
		
		result += "]\n";
		
		int len = result.length() - 1;
		for (int i = 0; i < len; i++)
			result += "-";
		
		for (RK row : getRowKeys()) {
			result += "\n[" + row + "]:[";
			
			for (CK column : getColumnKeys()) {
				result += " " + getValueAt(row, column) + ",";
			}
			result += "]";
		}
		return result;
	}
}