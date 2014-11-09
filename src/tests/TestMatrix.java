package tests;

import static org.junit.Assert.*;
import main.graphs.Matrix;

import org.junit.Test;

public class TestMatrix {
	
	@Test
	public void testMatrix() {
		// setup
		Matrix<Integer, String, String> matrix = new Matrix<>();
		
		matrix.setValueAt(0, "a", "00");
		matrix.setValueAt(3, "b", "30");
		matrix.setValueAt(0, " ", "0-wtf");
		matrix.setValueAt(4, "d", "44");
		matrix.setValueAt(3, "e", "32");
		
		// assertions
		assertEquals("00", 		matrix.getValueAt(0, "a"));
		assertEquals("30", 		matrix.getValueAt(3, "b"));
		assertEquals("0-wtf", 	matrix.getValueAt(0, " "));
		assertEquals("44",		matrix.getValueAt(4, "d"));
		assertEquals("32", 		matrix.getValueAt(3, "e"));
		
		assertNull(matrix.getValueAt(9, "9"));
		assertNull(matrix.getValueAt(3, "1"));
	}
}