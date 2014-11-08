package tests;

import java.util.Arrays;
import java.util.List;

import main.graphs.GKAVertex;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static main.graphs.Utils.*;

public class TestUtils {
	
	List<String> stadartList, stadartListReversed;
	GKAVertex v1,v2,v3,v4;
	List<GKAVertex> vertexList, vertexListReversed;

	@Before
	public void setUp() throws Exception {
		stadartList = Arrays.asList("a","b","c","d");
		stadartListReversed = Arrays.asList("d","c","b","a");
		
		v1 = GKAVertex.valueOf("v1");
		v2 = GKAVertex.valueOf("v2");
		v3 = GKAVertex.valueOf("v3");
		v4 = GKAVertex.valueOf("v4");
		
		vertexList = Arrays.asList(v1, v2, v3, v4);
		vertexListReversed = Arrays.asList(v4, v3, v2, v1);
		
	}

	@Test
	public void testReverse() {
//		assertEquals(stadartListReversed, reverse(stadartList));
		assertEquals(vertexListReversed, reverse(vertexList));
	}

}
