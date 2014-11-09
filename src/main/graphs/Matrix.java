package main.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Matrix <RK,CK,V> {

	Map<RK, Map<CK,V> > matrix;
	
	public Matrix() {
		matrix = new HashMap<>();
	}
	
	public V getValueAt(RK row, CK column) {
		if (!matrix.containsKey(row)) {
			return null;
		}
		
		if (!matrix.get(row).containsKey(column)) {
			return null;
		}
		
		return matrix.get(row).get(column);
	}
	
	public void setValueAt(RK row, CK column, V value) {
		Map<CK,V> columnMap = matrix.get(row);
		
		if (columnMap == null) {
			columnMap = new HashMap<>();
			columnMap.put(column, value);
			matrix.put(row, columnMap);
		} else {
			columnMap.put(column, value);
		}
	}
}