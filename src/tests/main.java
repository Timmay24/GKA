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
import org.junit.Test;



public class main {

	@Before
	public void setUp() throws Exception {
	}


	
//	@Test
//	public void testAllAdjNode() {
//		
//		
//		
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
	
//	Vertex a = Vertex.valueOf("A");
//	Vertex b = Vertex.valueOf("B");
//	Vertex c = Vertex.valueOf("C");
//	Vertex d = Vertex.valueOf("D");
//	Vertex e = Vertex.valueOf("E");
//	Vertex f = Vertex.valueOf("F");
//	Vertex g = Vertex.valueOf("G");
//	Vertex h = Vertex.valueOf("H");
//	Vertex k = Vertex.valueOf("K");
//	Vertex m = Vertex.valueOf("M");
//	
//	
//	 //creat graph
//	 //add vertices
//	UndirectedGraph<Vertex, GKAEdge> graph1 = new SimpleGraph<>(GKAEdge.class);
//	 graph1.addVertex( a);
//	 graph1.addVertex( b);
//	 graph1.addVertex( c);
//	 graph1.addVertex( d);
//	 graph1.addVertex( e);
//	 graph1.addVertex( f);
//	 graph1.addVertex( g);
//	 graph1.addVertex( h);
//	 graph1.addVertex( k);
//	 graph1.addVertex( m);
//	 
//	 //add edges
//	 graph1.addEdge( a, f, new GKAEdge("e1"));
//	 graph1.addEdge( a, b, new GKAEdge("e2")); 
//	 graph1.addEdge( a, h, new GKAEdge("e3"));
//	 
//	 graph1.addEdge( f, e, new GKAEdge("e4"));
//	 graph1.addEdge( f, a, new GKAEdge("e5"));
//	
//	 graph1.addEdge( b, a, new GKAEdge("e6"));
//	 graph1.addEdge( b, e, new GKAEdge("e7"));
//	 graph1.addEdge( b, g, new GKAEdge("e8"));
//	 graph1.addEdge( b, h, new GKAEdge("e9"));
//	 
//	 graph1.addEdge( h, b, new GKAEdge("e10"));
//	 graph1.addEdge( h, a, new GKAEdge("e11"));
//	 graph1.addEdge( h, g, new GKAEdge("e12"));
//	 graph1.addEdge( h, k, new GKAEdge("e13"));
//	 
//	 graph1.addEdge( k, h, new GKAEdge("e14"));
//	 graph1.addEdge( k, m, new GKAEdge("e15"));
//	 
//	 graph1.addEdge( g, h, new GKAEdge("e16"));
//	 graph1.addEdge( g, b, new GKAEdge("e17"));
//	 graph1.addEdge( g, e, new GKAEdge("e18"));
//	 graph1.addEdge( g, m, new GKAEdge("e19"));
//	 
//	 graph1.addEdge( m, k, new GKAEdge("e20"));
//	 graph1.addEdge( m, g, new GKAEdge("e21"));
//	 graph1.addEdge( m, e, new GKAEdge("e22"));
//	 
//	 graph1.addEdge( e, f, new GKAEdge("e23"));
//	 graph1.addEdge( e, b, new GKAEdge("e24"));
//	 graph1.addEdge( e, g, new GKAEdge("e25"));
//	 graph1.addEdge( e, m, new GKAEdge("e26"));
//	 
//	 
//	 List<Vertex> l = new ArrayList<>();
//     l.add(f);
//     l.add(b);
//     l.add(h);
//      
//     assertEquals(l, BFS.allAdjNode(graph1, a));
//	 
//	}
	
	
	
	
	
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
		 graph1.addEdge( a, f, new GKAEdge("e1"));
		 graph1.addEdge( a, b, new GKAEdge("e2")); 
		 graph1.addEdge( a, h, new GKAEdge("e3"));
		 
		 graph1.addEdge( f, e, new GKAEdge("e4"));
		 graph1.addEdge( f, a, new GKAEdge("e5"));
		
		 graph1.addEdge( b, a, new GKAEdge("e6"));
		 graph1.addEdge( b, e, new GKAEdge("e7"));
		 graph1.addEdge( b, g, new GKAEdge("e8"));
		 graph1.addEdge( b, h, new GKAEdge("e9"));
		 
		 graph1.addEdge( h, b, new GKAEdge("e10"));
		 graph1.addEdge( h, a, new GKAEdge("e11"));
		 graph1.addEdge( h, g, new GKAEdge("e12"));
		 graph1.addEdge( h, k, new GKAEdge("e13"));
		 
		 graph1.addEdge( k, h, new GKAEdge("e14"));
		 graph1.addEdge( k, m, new GKAEdge("e15"));
		 
		 graph1.addEdge( g, h, new GKAEdge("e16"));
		 graph1.addEdge( g, b, new GKAEdge("e17"));
		 graph1.addEdge( g, e, new GKAEdge("e18"));
		 graph1.addEdge( g, m, new GKAEdge("e19"));
		 
		 graph1.addEdge( m, k, new GKAEdge("e20"));
		 graph1.addEdge( m, g, new GKAEdge("e21"));
		 graph1.addEdge( m, e, new GKAEdge("e22"));
		 
		 graph1.addEdge( e, f, new GKAEdge("e23"));
		 graph1.addEdge( e, b, new GKAEdge("e24"));
		 graph1.addEdge( e, g, new GKAEdge("e25"));
		 graph1.addEdge( e, m, new GKAEdge("e26"));
		 
		 
		 //Start vertex: A
		 //End vertex: M
		 
		 List<Vertex> shortWay1 = new ArrayList<>();
	     shortWay1.add(a);
	     shortWay1.add(f);
	     shortWay1.add(e);
	     shortWay1.add(m);
	      
	     assertEquals(shortWay1, BFS.findShortestWay(graph1, a, m));
		
	}
	
	
	
	
	@Test
	public void testReverse(){
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
	}
	
	
	
	
}
