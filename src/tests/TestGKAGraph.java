package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;

import org.junit.Test;

public class TestGKAGraph {

	@Test
	public void testGetAllAdjacentsOf() {
	
		 /*Test 1
	      * Undirected Graph
	      * */
		GKAVertex v1 = GKAVertex.valueOf("v1");
		GKAVertex v2 = GKAVertex.valueOf("v2");
		GKAVertex v3 = GKAVertex.valueOf("v3");
		GKAVertex v4 = GKAVertex.valueOf("v4");
		
		GKAGraph g1 = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		g1.addVertex( v1);
	    g1.addVertex( v2);
	    g1.addVertex( v3);
	    g1.addVertex( v4);

	    g1.addEdge( v1, v2, "test1");
	    g1.addEdge( v1, v3, "test2"); 
	    g1.addEdge( v3, v4, "test3");
	 
	 
	    List<GKAVertex> l = new ArrayList<>();
	    l.add(v2);
	    l.add(v3);
	     
	    assertTrue(g1.getAllAdjacentsOf(v1).containsAll(l));
	
		
	    /*Test 2
	     * Undirekted Graph
	     * */
		GKAVertex a = GKAVertex.valueOf("A");
		GKAVertex b = GKAVertex.valueOf("B");
		GKAVertex c = GKAVertex.valueOf("C");
		GKAVertex d = GKAVertex.valueOf("D");
		GKAVertex e = GKAVertex.valueOf("E");
		GKAVertex f = GKAVertex.valueOf("F");
		GKAVertex g = GKAVertex.valueOf("G");
		GKAVertex h = GKAVertex.valueOf("H");
		GKAVertex k = GKAVertex.valueOf("K");
		GKAVertex m = GKAVertex.valueOf("M");
		
		
		 //creat graph
		 //add vertices
		GKAGraph g2 = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		g2.addVertex( a);
		g2.addVertex( b);
		g2.addVertex( c);
		g2.addVertex( d);
		g2.addVertex( e);
		g2.addVertex( f);
		g2.addVertex( g);
		g2.addVertex( h);
		g2.addVertex( k);
		g2.addVertex( m);
		 
		//add edges
		g2.addEdge( a, f, "e1");
		g2.addEdge( a, b, "e2"); 
		g2.addEdge( a, h, "e3");
		                     
		g2.addEdge( f, e, "e4");
		g2.addEdge( f, a, "e5");
		                      
		g2.addEdge( b, a, "e6");
		g2.addEdge( b, e, "e7");
		g2.addEdge( b, g, "e8");
		g2.addEdge( b, h, "e9");
		                      
		g2.addEdge( h, b, "e10");
		g2.addEdge( h, a, "e11");
		g2.addEdge( h, g, "e12");
		g2.addEdge( h, k, "e13");
		                      
		g2.addEdge( k, h, "e14");
		g2.addEdge( k, m, "e15");
		                      
		g2.addEdge( g, h, "e16");
		g2.addEdge( g, b, "e17");
		g2.addEdge( g, e, "e18");
		g2.addEdge( g, m, "e19");
		                      
		g2.addEdge( m, k, "e20");
		g2.addEdge( m, g, "e21");
		g2.addEdge( m, e, "e22");
		                      
		g2.addEdge( e, f, "e23");
		g2.addEdge( e, b, "e24");
		g2.addEdge( e, g, "e25");
		g2.addEdge( e, m, "e26");
		 
		 
		List<GKAVertex> l1 = new ArrayList<>();
	    l1.add(f);
	    l1.add(b);
	    l1.add(h);
	     
	    assertTrue(g2.getAllAdjacentsOf(a).containsAll(l1));
	 
	     
	     

	    /*Test 3
	     * Directed Graph
	     * */
	     
	    GKAVertex a1 = GKAVertex.valueOf("1");
	    GKAVertex a2 = GKAVertex.valueOf("2");
	    GKAVertex a3 = GKAVertex.valueOf("3");
	    GKAVertex a4 = GKAVertex.valueOf("4");
	    GKAVertex a5 = GKAVertex.valueOf("5");
	    GKAVertex a6 = GKAVertex.valueOf("6");
	    GKAVertex a7 = GKAVertex.valueOf("7");
	    GKAVertex a8 = GKAVertex.valueOf("8");
	    GKAVertex a9 = GKAVertex.valueOf("9");
	    GKAVertex a10 = GKAVertex.valueOf("10");
	    GKAVertex a11 = GKAVertex.valueOf("11");
	    GKAVertex a12 = GKAVertex.valueOf("12");
	    /*
		 * create graph
		 * add vertices
		 */
	    GKAGraph g3 = GKAGraph.valueOf(GraphType.DIRECTED_UNWEIGHTED);
	    g3.addVertex(a1);
	    g3.addVertex(a2);
	    g3.addVertex(a3);
	    g3.addVertex(a4);
	    g3.addVertex(a5);
	    g3.addVertex(a6);
	    g3.addVertex(a7);
	    g3.addVertex(a8);
	    g3.addVertex(a9);
	    g3.addVertex(a10);
	    g3.addVertex(a11);
	    g3.addVertex(a12);
	    /*
	     * add edges
		 * */
	    g3.addEdge(a1, a2, "e1");
	    g3.addEdge(a1, a3, "e2");
	    g3.addEdge(a1, a8, "e3");
	    g3.addEdge(a2, a4, "e4");
	    g3.addEdge(a3, a5, "e5");
	    g3.addEdge(a3, a8, "e6");
	    g3.addEdge(a4, a5, "e7");
	    g3.addEdge(a5, a2, "e8");
	    g3.addEdge(a3, a4, "e9");
	    g3.addEdge(a4, a1, "e10");
	    g3.addEdge(a8, a5, "e11");
	    g3.addEdge(a6, a7, "e12");
	    g3.addEdge(a7, a9, "e13");
	    g3.addEdge(a9, a6, "e14");
	    g3.addEdge(a11, a12, "e15");
	     
	     
	    List<GKAVertex> l2 = new ArrayList<>();
	    l2.add(a4);
	     
	    List<GKAVertex> l3 = new ArrayList<>();
	     
	    //all adjacent vertices of a vertex with one outgoing edge --> return a list with one vertex(-> l2)
	    assertTrue(g3.getAllAdjacentsOf(a2).containsAll(l2));
	     
	    //all adjacent vertices of a vertex without any edges --> return empty list (-> l3)
	    assertEquals(g3.getAllAdjacentsOf(a10).size(), l3.size());
	    assertTrue(g3.getAllAdjacentsOf(a10).containsAll(l3));
	     
	     
	}
	
	@Test
	public void testEquals() {
		
		GKAGraph g1 = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
		GKAGraph g2 = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
		GKAGraph g3 = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
		GKAGraph g4 = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		
		g1.addEdge("v1", "v2", "e1", 1);
		g1.addEdge("v3", "v4", "e2", 1);
		g1.addEdge("v4", "v5", "e3", 1);
		g1.addVertex("v6");
		g1.addVertex("v7");
		
		g2.addEdge("v1", "v2", "e1", 1);
		g2.addEdge("v3", "v4", "e2", 1);
		g2.addEdge("v4", "v5", "e3", 1);
		g2.addVertex("v6");
		g2.addVertex("v7");
		
		g3.addEdge("v1", "v2", "e1", 1);
		g3.addEdge("v4", "v3", "e2", 1);
		g3.addEdge("v4", "v5", "e3", 1);
		g3.addVertex("v6");
		g3.addVertex("v7");
		
		assertEquals(g1, g2);
		assertNotEquals(g1, g3);
		assertNotEquals(g2, g4);
	}
	
	@Test
	public void testIfAllAdjacentsOfAVertexCanBeObtainedInAnDirectedGraph() {
		GKAGraph g = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
		
		g.addEdge("v1", "v2", "12", 1);
		g.addEdge("v1", "v3", "13", 1);
		g.addEdge("v1", "v4", "14", 1);
		g.addEdge("v2", "v5", "25", 1);
		g.addEdge("v6", "v1", "61", 1);
		
		GKAVertex v1 = g.getVertex("v1");
		GKAVertex v2 = g.getVertex("v2");
		GKAVertex v3 = g.getVertex("v3");
		GKAVertex v4 = g.getVertex("v4");
		GKAVertex v5 = g.getVertex("v5");
		GKAVertex v6 = g.getVertex("v6");
		
		Set<GKAVertex> desiredAdjacentsOfV1 = new HashSet<>(Arrays.asList(v2,v3,v4));
		Collection<GKAVertex> actualAdjectensOfV1 = g.getAllAdjacentsOf(v1);
		
		assertEquals(actualAdjectensOfV1.size(), desiredAdjacentsOfV1.size());
		assertTrue(actualAdjectensOfV1.containsAll(desiredAdjacentsOfV1));
	}
	
	@Test
	public void testIfAllAdjacentsOfAVertexCanBeObtainedInAnUndirectedGraph() {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		
		g.addEdge("v1", "v2", "12", 1);
		g.addEdge("v1", "v3", "13", 1);
		g.addEdge("v1", "v4", "14", 1);
		g.addEdge("v2", "v5", "25", 1);
		g.addEdge("v6", "v1", "61", 1);
		
		GKAVertex v1 = g.getVertex("v1");
		GKAVertex v2 = g.getVertex("v2");
		GKAVertex v3 = g.getVertex("v3");
		GKAVertex v4 = g.getVertex("v4");
		GKAVertex v5 = g.getVertex("v5");
		GKAVertex v6 = g.getVertex("v6");
		
		Set<GKAVertex> desiredAdjacentsOfV1 = new HashSet<>(Arrays.asList(v2,v3,v4,v6));
		Collection<GKAVertex> actualAdjectensOfV1 = g.getAllAdjacentsOf(v1);
		
		assertEquals(actualAdjectensOfV1.size(), desiredAdjacentsOfV1.size());
		assertTrue(actualAdjectensOfV1.containsAll(desiredAdjacentsOfV1));
	}
	
	@Test
	public void testIfAllAddedVerticesCanBeObtainedFromGraph() {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		
		g.addVertex("v1");
		g.addVertex("v2");
		g.addVertex("v3");
		g.addEdge("v4", "v5", "45");
		
		GKAVertex v1 = g.getVertex("v1");
		GKAVertex v2 = g.getVertex("v2");
		GKAVertex v3 = g.getVertex("v3");
		GKAVertex v4 = g.getVertex("v4");
		GKAVertex v5 = g.getVertex("v5");
		
		Set<GKAVertex> desiredVertexSet = new HashSet<>(Arrays.asList(v1,v2,v3,v4,v5));
		Collection<GKAVertex> actualVertexSet = new HashSet<>(g.getGraph().vertexSet());
		
		assertEquals(actualVertexSet.size(), desiredVertexSet.size());
		assertTrue(actualVertexSet.containsAll(desiredVertexSet));
	}
	
	@Test
	public void testIfUniqueIdsAreAssignedProperlyToNewEdges() {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		
		g.addEdge("v1", "v2", "e12");
		g.addEdge("v1", "v3", "e13");
		g.addEdge("v2", "v4", "e24");
		
		GKAEdge e12 = g.getEdge("e12");
		GKAEdge e13 = g.getEdge("e13");
		GKAEdge e24 = g.getEdge("e24");
		
		assertEquals(0, e12.getUniqueId());
		assertEquals(1, e13.getUniqueId());
		assertEquals(2, e24.getUniqueId());
	}
	
	@Test
	public void testIfEdgeMapIsBuiltProperly() {
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		
		g.addEdge("v1", "v2", "e12");
		g.addEdge("v1", "v3", "e13");
		g.addEdge("v2", "v4", "e24");
		
		GKAEdge e12 = g.getEdge("e12");
		GKAEdge e13 = g.getEdge("e13");
		GKAEdge e24 = g.getEdge("e24");
		
		Map<Long, GKAEdge> 	desiredEdgeMap = new HashMap<>();
							desiredEdgeMap.put((long) 0, e12);
							desiredEdgeMap.put((long) 1, e13);
							desiredEdgeMap.put((long) 2, e24);
		
		Map<Long, GKAEdge> actualEdgeMap = g.getEdgeMap();
		
		assertEquals(actualEdgeMap, desiredEdgeMap);
	}
	

}
