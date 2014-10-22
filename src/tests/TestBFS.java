package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GraphType;
import main.graphs.Vertex;
import main.graphs.BFS;

import org.junit.Before;
import org.junit.Test;



public class TestBFS {

	@Before
	public void setUp() throws Exception {
	}


	
//	@Ignore
	@Test
	public void testFindShortestWay() {
		/*Test 1
		 * Undirected Graph
		 * */
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
		/*
		 * create graph
		 * add vertices
		 */
		GKAGraph graph1 = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
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
		 /*
		  * add edges
		  * */
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
		                      
		 /*Start vertex: A
		  * End vertex: M
		  */
		 List<Vertex> shortWay1 = new ArrayList<>();
	     shortWay1.add(a);
	     shortWay1.add(b);
	     shortWay1.add(e);
	     shortWay1.add(m);
	      
	     assertEquals(shortWay1, BFS.findShortestWay(graph1, a, m));
	     
	     
	     /*Test 2
	      * Directed Graph
	      * */
	     
	     Vertex a1 = Vertex.valueOf("1");
	     Vertex a2 = Vertex.valueOf("2");
	     Vertex a3 = Vertex.valueOf("3");
	     Vertex a4 = Vertex.valueOf("4");
	     Vertex a5 = Vertex.valueOf("5");
	     Vertex a6 = Vertex.valueOf("6");
	     Vertex a7 = Vertex.valueOf("7");
	     Vertex a8 = Vertex.valueOf("8");
	     Vertex a9 = Vertex.valueOf("9");
	     Vertex a10 = Vertex.valueOf("10");
	     Vertex a11 = Vertex.valueOf("11");
	     Vertex a12 = Vertex.valueOf("12");
	     /*
		 * create graph
		 * add vertices
		 */
	     GKAGraph graph2 = GKAGraph.valueOf(GraphType.DIRECTED_UNWEIGHTED);
	     graph2.addVertex(a1);
	     graph2.addVertex(a2);
	     graph2.addVertex(a3);
	     graph2.addVertex(a4);
	     graph2.addVertex(a5);
	     graph2.addVertex(a6);
	     graph2.addVertex(a7);
	     graph2.addVertex(a8);
	     graph2.addVertex(a9);
	     graph2.addVertex(a10);
	     graph2.addVertex(a11);
	     graph2.addVertex(a12);
	     /*
	      * add edges
		  * */
	     graph2.addEdge(a1, a2, GKAEdge.valueOf("e1"));
	     graph2.addEdge(a1, a3, GKAEdge.valueOf("e2"));
	     graph2.addEdge(a1, a8, GKAEdge.valueOf("e3"));
	     graph2.addEdge(a2, a4, GKAEdge.valueOf("e4"));
	     graph2.addEdge(a3, a5, GKAEdge.valueOf("e5"));
	     graph2.addEdge(a3, a8, GKAEdge.valueOf("e6"));
	     graph2.addEdge(a4, a5, GKAEdge.valueOf("e7"));
	     graph2.addEdge(a5, a2, GKAEdge.valueOf("e8"));
	     graph2.addEdge(a3, a4, GKAEdge.valueOf("e9"));
	     graph2.addEdge(a4, a1, GKAEdge.valueOf("e10"));
	     graph2.addEdge(a8, a5, GKAEdge.valueOf("e11"));
	     graph2.addEdge(a6, a7, GKAEdge.valueOf("e12"));
	     graph2.addEdge(a7, a9, GKAEdge.valueOf("e13"));
	     graph2.addEdge(a9, a6, GKAEdge.valueOf("e14"));
	     graph2.addEdge(a11, a12, GKAEdge.valueOf("e15"));
	     
	     /*Start vertex: 1 (a1)
		  * End vertex: 5 (a5)
		  */
	     List<Vertex> shortWay2 = new ArrayList<>();
	     shortWay2.add(a1);
	     shortWay2.add(a3);
	     shortWay2.add(a5);
	     
	     
	     assertEquals(shortWay2, BFS.findShortestWay(graph2, a1, a5));
	     
	     
	     /*Test 2
	      * Directed Graph
	      * */
	     Vertex b1 = Vertex.valueOf("A1");
		 Vertex b2 = Vertex.valueOf("B1");
		 Vertex b3 = Vertex.valueOf("C1");
		 Vertex b4 = Vertex.valueOf("D1");
		 Vertex b5 = Vertex.valueOf("E1");
		 Vertex b6 = Vertex.valueOf("F1");
		 Vertex b7 = Vertex.valueOf("G1");
		 Vertex b8 = Vertex.valueOf("H1");
		 Vertex b9 = Vertex.valueOf("I1");
		 Vertex b10 = Vertex.valueOf("K1");
		 Vertex b11 = Vertex.valueOf("L1");
		 Vertex b12 = Vertex.valueOf("M1");
		 Vertex b13 = Vertex.valueOf("N1");
		 Vertex b14 = Vertex.valueOf("O1");
	     /*
		  * create graph
		  * add vertices
		  */
		 GKAGraph graph3 = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		 graph3.addEdge(b1, b2, GKAEdge.valueOf("edge1"));
		 graph3.addEdge(b1, b3, GKAEdge.valueOf("edge2"));
		 graph3.addEdge(b1, b4, GKAEdge.valueOf("edge3"));
		 graph3.addEdge(b1, b5, GKAEdge.valueOf("edge4"));
		 graph3.addEdge(b2, b6, GKAEdge.valueOf("edge5"));
		 graph3.addEdge(b2, b7, GKAEdge.valueOf("edge6"));
		 graph3.addEdge(b3, b8, GKAEdge.valueOf("edge7"));
		 graph3.addEdge(b4, b9, GKAEdge.valueOf("edge8"));
		 graph3.addEdge(b5, b10, GKAEdge.valueOf("edge9"));
		 graph3.addEdge(b8, b14, GKAEdge.valueOf("edge10"));
		 graph3.addEdge(b10, b11, GKAEdge.valueOf("edge11"));
		 graph3.addEdge(b14, b13, GKAEdge.valueOf("edge12"));
		 graph3.addEdge(b14, b12, GKAEdge.valueOf("edge13"));
		 
		 /*Start vertex: A1 (b1)
		  * End vertex: M1 (b12)
		  */
	     List<Vertex> shortWay3 = new ArrayList<>();
	     shortWay3.add(b1);
	     shortWay3.add(b3);
	     shortWay3.add(b8);
	     shortWay3.add(b14);
	     shortWay3.add(b12);
		 
		 assertEquals(shortWay3, BFS.findShortestWay(graph3, b1, b12));
	}
	
	
	
	
	
	
//	@Ignore
	@Test
	public void testReverse(){
		
		/*
		 * Test 3: einfache Liste aus Vertices
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
		 * Test 4: Liste mit Vertices mit String Zahlen
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
		 * Test 5: ein Elementige Liste aus Vertices
		 * */
		List<Vertex> reverseList2 = new ArrayList<>();
		reverseList1.add(Vertex.valueOf("0"));
		
		List<Vertex> list2 = new ArrayList<>();
		list1.add(Vertex.valueOf("0"));
		
		assertEquals(reverseList2, BFS.reverse(list2));
		
		
		/*
		 * Test 6: Liste mit Vertices mit String Zahlen
		 * */
		List<Vertex> reverseList3 = new ArrayList<>();
		
		List<Vertex> list3 = new ArrayList<>();
		
		assertEquals(reverseList3, BFS.reverse(list3));
	}
	
	/*
	* Test 7: Null-Objekt Exeption
	* */
//	@Ignore
	@Test(expected = NullPointerException.class)
	public void testReverseFail(){
		 
		BFS.reverse(null);
	 }
	
	
	
	/*Test 8
	 * Directed Graph
	 * */
//	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testTest(){
	
		
	    Vertex b1 = Vertex.valueOf("1");
	    Vertex b2 = Vertex.valueOf("2");
	    Vertex b3 = Vertex.valueOf("3");
	    Vertex b4 = Vertex.valueOf("4");
	    Vertex b5 = Vertex.valueOf("5");
	    Vertex b6 = Vertex.valueOf("6");
	    Vertex b7 = Vertex.valueOf("7");
	    Vertex b8 = Vertex.valueOf("8");
	    Vertex b9 = Vertex.valueOf("9");
	    Vertex b10 = Vertex.valueOf("10");
	    Vertex b11 = Vertex.valueOf("11");
	    Vertex b12 = Vertex.valueOf("12");
	    /*
		 * create graph
		 * add vertices
		 */
	    GKAGraph graph3 = GKAGraph.valueOf(GraphType.DIRECTED_UNWEIGHTED);
	    graph3.addVertex(b1);
	    graph3.addVertex(b2);
	    graph3.addVertex(b3);
	    graph3.addVertex(b4);
	    graph3.addVertex(b5);
	    graph3.addVertex(b6);
	    graph3.addVertex(b7);
	    graph3.addVertex(b8);
	    graph3.addVertex(b9);
	    graph3.addVertex(b10);
	    graph3.addVertex(b11);
	    graph3.addVertex(b12);
	    /*
	     * add edges
		  * */
	    graph3.addEdge(b1, b2, GKAEdge.valueOf("e1"));
	    graph3.addEdge(b1, b3, GKAEdge.valueOf("e2"));
	    graph3.addEdge(b1, b8, GKAEdge.valueOf("e3"));
	    graph3.addEdge(b2, b4, GKAEdge.valueOf("e4"));
	    graph3.addEdge(b3, b5, GKAEdge.valueOf("e5"));
	    graph3.addEdge(b3, b8, GKAEdge.valueOf("e6"));
	    graph3.addEdge(b4, b5, GKAEdge.valueOf("e7"));
	    graph3.addEdge(b5, b2, GKAEdge.valueOf("e8"));
	    graph3.addEdge(b3, b4, GKAEdge.valueOf("e9"));
	    graph3.addEdge(b4, b1, GKAEdge.valueOf("e10"));
	    graph3.addEdge(b8, b5, GKAEdge.valueOf("e11"));
	    graph3.addEdge(b6, b7, GKAEdge.valueOf("e12"));
	    graph3.addEdge(b7, b9, GKAEdge.valueOf("e13"));
	    graph3.addEdge(b9, b6, GKAEdge.valueOf("e14"));
	    graph3.addEdge(b11, b12, GKAEdge.valueOf("e15"));
	    
	    /*Start vertex: 1 (b1)
		 * End vertex: 5 (b5)
		 */
	    BFS.findShortestWay(graph3, b3, b10);
	}
	
}
