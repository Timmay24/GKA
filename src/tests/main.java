package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.graphs.GKAEdge;
import main.graphs.Vertex;
import main.graphs.BFS;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



public class main {

	@Before
	public void setUp() throws Exception {
	}


	@Ignore
	@Test
	public void testAllAdjNode() {
		
		
//		Testcase1
//		Vertex v1 = Vertex.valueOf("v1");
//		Vertex v2 = Vertex.valueOf("v2");
//		Vertex v3 = Vertex.valueOf("v3");
//		Vertex v4 = Vertex.valueOf("v4");
//		
//		 UndirectedGraph<Vertex, GKAEdge> g = new SimpleGraph<>(GKAEdge.class);
//		 g.addVertex( v1);
//	     g.addVertex( v2);
//	     g.addVertex( v3);
//	     g.addVertex( v4);
//
//	     g.addEdge( v1, v2, new GKAEdge("test1"));
//	     g.addEdge( v1, v3, new GKAEdge("test2")); 
//	     g.addEdge( v3, v4, new GKAEdge("test3"));
//	 
//	 
//	     List<Vertex> l = new ArrayList<>();
//	     l.add(v2);
//	     l.add(v3);
//	      
//	     assertEquals(l, BFS.allAdjNode(g,v1));
	
		
//		Testcase2
		Vertex a = Vertex.valueOf("A");
		Vertex b = Vertex.valueOf("B");
		Vertex c = Vertex.valueOf("C");
		Vertex d = Vertex.valueOf("D");
		Vertex e = Vertex.valueOf("E");
		Vertex f = Vertex.valueOf("F");
		Vertex g = Vertex.valueOf("G");
		Vertex h = Vertex.valueOf("H");
		Vertex k = Vertex.valueOf("K");
		Vertex m = Vertex.valueOf("M");
		
		
		 //creat graph
		 //add vertices
		UndirectedGraph<Vertex, GKAEdge> graph1 = new SimpleGraph<>(GKAEdge.class);
		 graph1.addVertex( a);
		 graph1.addVertex( b);
		 graph1.addVertex( c);
		 graph1.addVertex( d);
		 graph1.addVertex( e);
		 graph1.addVertex( f);
		 graph1.addVertex( g);
		 graph1.addVertex( h);
		 graph1.addVertex( k);
		 graph1.addVertex( m);
		 
		 //add edges
		 graph1.addEdge( a, f, GKAEdge.valueOf("e1"));
		 graph1.addEdge( a, b, GKAEdge.valueOf("e2")); 
		 graph1.addEdge( a, h, GKAEdge.valueOf("e3"));
		                     
		 graph1.addEdge( f, e, GKAEdge.valueOf("e4"));
		 graph1.addEdge( f, a, GKAEdge.valueOf("e5"));
		                       
		 graph1.addEdge( b, a, GKAEdge.valueOf("e6"));
		 graph1.addEdge( b, e, GKAEdge.valueOf("e7"));
		 graph1.addEdge( b, g, GKAEdge.valueOf("e8"));
		 graph1.addEdge( b, h, GKAEdge.valueOf("e9"));
		                      
		 graph1.addEdge( h, b, GKAEdge.valueOf("e10"));
		 graph1.addEdge( h, a, GKAEdge.valueOf("e11"));
		 graph1.addEdge( h, g, GKAEdge.valueOf("e12"));
		 graph1.addEdge( h, k, GKAEdge.valueOf("e13"));
		                      
		 graph1.addEdge( k, h, GKAEdge.valueOf("e14"));
		 graph1.addEdge( k, m, GKAEdge.valueOf("e15"));
		                      
		 graph1.addEdge( g, h, GKAEdge.valueOf("e16"));
		 graph1.addEdge( g, b, GKAEdge.valueOf("e17"));
		 graph1.addEdge( g, e, GKAEdge.valueOf("e18"));
		 graph1.addEdge( g, m, GKAEdge.valueOf("e19"));
		                       
		 graph1.addEdge( m, k, GKAEdge.valueOf("e20"));
		 graph1.addEdge( m, g, GKAEdge.valueOf("e21"));
		 graph1.addEdge( m, e, GKAEdge.valueOf("e22"));
		                      
		 graph1.addEdge( e, f, GKAEdge.valueOf("e23"));
		 graph1.addEdge( e, b, GKAEdge.valueOf("e24"));
		 graph1.addEdge( e, g, GKAEdge.valueOf("e25"));
		 graph1.addEdge( e, m, GKAEdge.valueOf("e26"));
		 
		 
		 List<Vertex> l = new ArrayList<>();
	     l.add(f);
	     l.add(b);
	     l.add(h);
	      
	     assertEquals(l, BFS.allAdjNode(graph1, a));
	 
	}
	
	
	
//	@Ignore
	@Test
	public void testFindShortestWay() {
		
		Vertex a = Vertex.valueOf("A");
		Vertex b = Vertex.valueOf("B");
		Vertex c = Vertex.valueOf("C");
		Vertex d = Vertex.valueOf("D");
		Vertex e = Vertex.valueOf("E");
		Vertex f = Vertex.valueOf("F");
		Vertex g = Vertex.valueOf("G");
		Vertex h = Vertex.valueOf("H");
		Vertex k = Vertex.valueOf("K");
		Vertex m = Vertex.valueOf("M");
		
		
		 //creat graph
		 //add vertices
		UndirectedGraph<Vertex, GKAEdge> graph1 = new SimpleGraph<>(GKAEdge.class);
		 graph1.addVertex( a);
		 graph1.addVertex( b);
		 graph1.addVertex( c);
		 graph1.addVertex( d);
		 graph1.addVertex( e);
		 graph1.addVertex( f);
		 graph1.addVertex( g);
		 graph1.addVertex( h);
		 graph1.addVertex( k);
		 graph1.addVertex( m);
		 
		 //add edges
		 graph1.addEdge( a, f, GKAEdge.valueOf("e1"));
		 graph1.addEdge( a, b, GKAEdge.valueOf("e2")); 
		 graph1.addEdge( a, h, GKAEdge.valueOf("e3"));
		                     
		 graph1.addEdge( f, e, GKAEdge.valueOf("e4"));
		                       
		 graph1.addEdge( b, e, GKAEdge.valueOf("e5"));
		 graph1.addEdge( b, g, GKAEdge.valueOf("e6"));
		 graph1.addEdge( b, h, GKAEdge.valueOf("e7"));
		                      
		 graph1.addEdge( h, g, GKAEdge.valueOf("e8"));
		 graph1.addEdge( h, k, GKAEdge.valueOf("e9"));
		                      
		 graph1.addEdge( k, m, GKAEdge.valueOf("e10"));
		                      
		 graph1.addEdge( g, e, GKAEdge.valueOf("e11"));
		 graph1.addEdge( g, m, GKAEdge.valueOf("e12"));
		                       
		 graph1.addEdge( m, e, GKAEdge.valueOf("e13"));
		                      
		 
		 //Start vertex: A
		 //End vertex: M
		 
		 List<Vertex> shortWay1 = new ArrayList<>();
	     shortWay1.add(a);
	     shortWay1.add(h);
	     shortWay1.add(k);
	     shortWay1.add(m);
	      
	     assertEquals(shortWay1, BFS.findShortestWay(graph1, a, m));
		
	}
	
	
	
//	@Ignore
	@Test
	public void testReverse(){
		
		/*
		 * Test 1: einfache Liste aus Vertices
		 * */
		List<Vertex> reverseList = new ArrayList<>();
		reverseList.add(Vertex.valueOf("a"));
		reverseList.add(Vertex.valueOf("b"));
		reverseList.add(Vertex.valueOf("c"));
		reverseList.add(Vertex.valueOf("d"));
		reverseList.add(Vertex.valueOf("e"));
		
		List<Vertex> list = new ArrayList<>();
		list.add(Vertex.valueOf("e"));
		list.add(Vertex.valueOf("d"));
		list.add(Vertex.valueOf("c"));
		list.add(Vertex.valueOf("b"));
		list.add(Vertex.valueOf("a"));
		
		assertEquals(reverseList, BFS.reverse(list));
		
		
		/*
		 * Test 2: Liste mit Vertices mit String Zahlen
		 * */
		List<Vertex> reverseList1 = new ArrayList<>();
		reverseList1.add(Vertex.valueOf("1"));
		reverseList1.add(Vertex.valueOf("1"));
		reverseList1.add(Vertex.valueOf("2"));
		reverseList1.add(Vertex.valueOf("0"));
		reverseList1.add(Vertex.valueOf("03"));
		
		List<Vertex> list1 = new ArrayList<>();
		list1.add(Vertex.valueOf("03"));
		list1.add(Vertex.valueOf("0"));
		list1.add(Vertex.valueOf("2"));
		list1.add(Vertex.valueOf("1"));
		list1.add(Vertex.valueOf("1"));
		
		assertEquals(reverseList1, BFS.reverse(list1));
		
		
		/*
		 * Test 3: ein Elementige Liste aus Vertices
		 * */
		List<Vertex> reverseList2 = new ArrayList<>();
		reverseList1.add(Vertex.valueOf("0"));
		
		List<Vertex> list2 = new ArrayList<>();
		list1.add(Vertex.valueOf("0"));
		
		assertEquals(reverseList2, BFS.reverse(list2));
		
		
		/*
		 * Test 4: Liste mit Vertices mit String Zahlen
		 * */
		List<Vertex> reverseList3 = new ArrayList<>();
		
		List<Vertex> list3 = new ArrayList<>();
		
		assertEquals(reverseList3, BFS.reverse(list3));
	}
	
	/*
	 * Test 5: Null-Objekt Exeption
	 * */
	 @Ignore
	 @Test(expected = NullPointerException.class)
	    public void testReverseFail(){
		 
		BFS.reverse(null);
	 }
	
}
