package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import main.graphs.Matrix;

import org.junit.Test;

public class TestMatrix {
	
	@Test
	public void testSetterAndGetterOfMatrix() {
		// setup
		Matrix<Integer, String, String> matrix = new Matrix<>();
		
		matrix.setValueAt(0, "a", "00");
		matrix.setValueAt(3, "b", "30");
		matrix.setValueAt(0, " ", "0-wtf");
		matrix.setValueAt(4, "d", "44");
		matrix.setValueAt(3, "e", "32");
		matrix.setValueAt(3, "ff", "FF");
		matrix.setValueAt(3, "0", "000");
		
		// assertions
		assertEquals("00", 		matrix.getValueAt(0, "a"));
		assertEquals("30", 		matrix.getValueAt(3, "b"));
		assertEquals("0-wtf", 	matrix.getValueAt(0, " "));
		assertEquals("44",		matrix.getValueAt(4, "d"));
		assertEquals("32", 		matrix.getValueAt(3, "e"));
		assertEquals("FF", 		matrix.getValueAt(3, "ff"));
		assertEquals("000", 	matrix.getValueAt(3, "0"));
		
		assertNull(matrix.getValueAt(9, "9"));
		assertNull(matrix.getValueAt(3, "1"));
		
		System.out.println(matrix);
	}
	
	@Test
	public void testConstructionsOfMatricesInitWithNull() {
		Set<Integer> rowSet = new HashSet<>( Arrays.asList(0, 1, 2, 4) );
		Set<String> columnSet = new HashSet<>( Arrays.asList("a", "b", "c", "e") );
		
		Matrix<Integer, String, String> matrix = new Matrix<>(rowSet, columnSet);
		
		assertEquals(matrix.getColumnKeys(), columnSet);
		assertEquals(matrix.getColumnKeys(0), columnSet);
		assertEquals(matrix.getColumnKeys(1), columnSet);
		assertEquals(matrix.getColumnKeys(2), columnSet);
		assertEquals(matrix.getColumnKeys(4), columnSet);
	}
	
	@Test
	public void testConstructionsOfMatricesInitWithVal() {
		Set<Integer> rowSet = new HashSet<>( Arrays.asList(0, 1, 2, 4) );
		Set<String> columnSet = new HashSet<>( Arrays.asList("a", "b", "c", "e") );
		
		Matrix<Integer, String, String> matrix = new Matrix<>(rowSet, columnSet, "val");
		
		assertEquals(matrix.getColumnKeys(), columnSet);
		assertEquals(matrix.getColumnKeys(0), columnSet);
		assertEquals(matrix.getColumnKeys(1), columnSet);
		assertEquals(matrix.getColumnKeys(2), columnSet);
		assertEquals(matrix.getColumnKeys(4), columnSet);
		
		assertEquals(matrix.getValueAt(1, "b"), "val");
		assertEquals(matrix.getValueAt(2, "c"), "val");
		assertEquals(matrix.getValueAt(2, "d"), null);
	}
}