package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.Utils;
import main.graphs.algorithms.interfaces.PathFinder;
import main.graphs.algorithms.path.BFS;
import main.graphs.exceptions.NoWayException;

import org.junit.Before;
import org.junit.Test;

public class TestBFS {

	@Before
	public void setUp() throws Exception {}

	
//	@Ignore
	@Test
	public void testFindShortestWay() throws NoWayException {
		/*Test 1
		 * Undirected Graph
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
		/*
		 * create graph
		 * add vertices
		 */
		GKAGraph graph1 = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		 graph1.addVertex(a);
		 graph1.addVertex(b);
		 graph1.addVertex(c);
		 graph1.addVertex(d);
		 graph1.addVertex(e);
		 graph1.addVertex(f);
		 graph1.addVertex(g);
		 graph1.addVertex(h);
		 graph1.addVertex(k);
		 graph1.addVertex(m);
		 /*
		  * add edges
		  * */
		 graph1.addEdge( a, f, "e1");
		 graph1.addEdge( a, b, "e2"); 
		 graph1.addEdge( a, h, "e3");		                     
		 graph1.addEdge( f, e, "e4");		                       
		 graph1.addEdge( b, e, "e5");
		 graph1.addEdge( b, g, "e6");
		 graph1.addEdge( b, h, "e7");		                      
		 graph1.addEdge( h, g, "e8");
		 graph1.addEdge( h, k, "e9");		                      
		 graph1.addEdge( k, m, "e10");		                      
		 graph1.addEdge( g, e, "e11");
		 graph1.addEdge( g, m, "e12");		                       
		 graph1.addEdge( m, e, "e13");
		                      
		 /*Start vertex: A
		  * End vertex: M
		  */
		 List<GKAVertex> shortWay1 = new ArrayList<>();
	     shortWay1.add(a);
	     shortWay1.add(b);
	     shortWay1.add(e);
	     shortWay1.add(m);
	      
	     assertEquals(shortWay1, graph1.findShortestWay(new BFS() , a, m));
	     
	     
	     /*Test 2
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
	     graph2.addEdge(a1, a2, "e1");
	     graph2.addEdge(a1, a3, "e2");
	     graph2.addEdge(a1, a8, "e3");
	     graph2.addEdge(a2, a4, "e4");
	     graph2.addEdge(a3, a5, "e5");
	     graph2.addEdge(a3, a8, "e6");
	     graph2.addEdge(a4, a5, "e7");
	     graph2.addEdge(a5, a2, "e8");
	     graph2.addEdge(a3, a4, "e9");
	     graph2.addEdge(a4, a1, "e10");
	     graph2.addEdge(a8, a5, "e11");
	     graph2.addEdge(a6, a7, "e12");
	     graph2.addEdge(a7, a9, "e13");
	     graph2.addEdge(a9, a6, "e14");
	     graph2.addEdge(a11, a12, "e15");
	     
	     /*Start vertex: 1 (a1)
		  * End vertex: 5 (a5)
		  */
	     List<GKAVertex> shortWay2 = new ArrayList<>();
	     shortWay2.add(a1);
	     shortWay2.add(a3);
	     shortWay2.add(a5);
	     
	     
	     assertEquals(shortWay2, graph2.findShortestWay(new BFS(), a1, a5));
	     
	     
	     /*Test 2
	      * Directed Graph
	      * */
	     GKAVertex b1 = GKAVertex.valueOf("A1");
		 GKAVertex b2 = GKAVertex.valueOf("B1");
		 GKAVertex b3 = GKAVertex.valueOf("C1");
		 GKAVertex b4 = GKAVertex.valueOf("D1");
		 GKAVertex b5 = GKAVertex.valueOf("E1");
		 GKAVertex b6 = GKAVertex.valueOf("F1");
		 GKAVertex b7 = GKAVertex.valueOf("G1");
		 GKAVertex b8 = GKAVertex.valueOf("H1");
		 GKAVertex b9 = GKAVertex.valueOf("I1");
		 GKAVertex b10 = GKAVertex.valueOf("K1");
		 GKAVertex b11 = GKAVertex.valueOf("L1");
		 GKAVertex b12 = GKAVertex.valueOf("M1");
		 GKAVertex b13 = GKAVertex.valueOf("N1");
		 GKAVertex b14 = GKAVertex.valueOf("O1");
	     /*
		  * create graph
		  * add vertices
		  */
		 GKAGraph graph3 = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		 graph3.addEdge(b1, b2, "edge1");
		 graph3.addEdge(b1, b3, "edge2");
		 graph3.addEdge(b1, b4, "edge3");
		 graph3.addEdge(b1, b5, "edge4");
		 graph3.addEdge(b2, b6, "edge5");
		 graph3.addEdge(b2, b7, "edge6");
		 graph3.addEdge(b3, b8, "edge7");
		 graph3.addEdge(b4, b9, "edge8");
		 graph3.addEdge(b5, b10, "edge9");
		 graph3.addEdge(b8, b14, "edge10");
		 graph3.addEdge(b10, b11,"edge11");
		 graph3.addEdge(b14, b13,"edge12");
		 graph3.addEdge(b14, b12,"edge13");
		 
		 /*Start vertex: A1 (b1)
		  * End vertex: M1 (b12)
		  */
	     List<GKAVertex> shortWay3 = new ArrayList<>();
	     shortWay3.add(b1);
	     shortWay3.add(b3);
	     shortWay3.add(b8);
	     shortWay3.add(b14);
	     shortWay3.add(b12);
		 
		 assertEquals(shortWay3, graph3.findShortestWay(new BFS(), b1, b12));
	}
	
	
//	@Ignore
	@Test
	public void testReverse(){
		
		/*
		 * Test 3: einfache Liste aus Vertices
		 * */
		List<GKAVertex> reverseList = new ArrayList<>();
		reverseList.add(GKAVertex.valueOf("a"));
		reverseList.add(GKAVertex.valueOf("b"));
		reverseList.add(GKAVertex.valueOf("c"));
		reverseList.add(GKAVertex.valueOf("d"));
		reverseList.add(GKAVertex.valueOf("e"));
		
		List<GKAVertex> list = new ArrayList<>();
		list.add(GKAVertex.valueOf("e"));
		list.add(GKAVertex.valueOf("d"));
		list.add(GKAVertex.valueOf("c"));
		list.add(GKAVertex.valueOf("b"));
		list.add(GKAVertex.valueOf("a"));
		
		assertEquals(reverseList, Utils.reverse(list));
		
		
		/*
		 * Test 4: Liste mit Vertices mit String Zahlen
		 * */
		List<GKAVertex> reverseList1 = new ArrayList<>();
		reverseList1.add(GKAVertex.valueOf("1"));
		reverseList1.add(GKAVertex.valueOf("1"));
		reverseList1.add(GKAVertex.valueOf("2"));
		reverseList1.add(GKAVertex.valueOf("0"));
		reverseList1.add(GKAVertex.valueOf("03"));
		
		List<GKAVertex> list1 = new ArrayList<>();
		list1.add(GKAVertex.valueOf("03"));
		list1.add(GKAVertex.valueOf("0"));
		list1.add(GKAVertex.valueOf("2"));
		list1.add(GKAVertex.valueOf("1"));
		list1.add(GKAVertex.valueOf("1"));
		
		assertEquals(reverseList1, Utils.reverse(list1));
		
		
		/*
		 * Test 5: ein Elementige Liste aus Vertices
		 * */
		List<GKAVertex> reverseList2 = new ArrayList<>();
		reverseList1.add(GKAVertex.valueOf("0"));
		
		List<GKAVertex> list2 = new ArrayList<>();
		list1.add(GKAVertex.valueOf("0"));
		
		assertEquals(reverseList2, Utils.reverse(list2));
		
		
		/*
		 * Test 6: Liste mit Vertices mit String Zahlen
		 * */
		List<GKAVertex> reverseList3 = new ArrayList<>();
		
		List<GKAVertex> list3 = new ArrayList<>();
		
		assertEquals(reverseList3, Utils.reverse(list3));
	}
	
	/*
	* Test 7: Null-Objekt Exeption
	* */
//	@Ignore
	@Test(expected = NullPointerException.class)
	public void testReverseFail(){
		Utils.reverse(null);
	}
	
	
	/*Test 8
	 * Directed Graph
	 * */
	@Test
	public void testFSW2() {
	
		
	    GKAVertex b1 = GKAVertex.valueOf("1");
	    GKAVertex b2 = GKAVertex.valueOf("2");
	    GKAVertex b3 = GKAVertex.valueOf("3");
	    GKAVertex b4 = GKAVertex.valueOf("4");
	    GKAVertex b5 = GKAVertex.valueOf("5");
	    GKAVertex b6 = GKAVertex.valueOf("6");
	    GKAVertex b7 = GKAVertex.valueOf("7");
	    GKAVertex b8 = GKAVertex.valueOf("8");
	    GKAVertex b9 = GKAVertex.valueOf("9");
	    GKAVertex b10 = GKAVertex.valueOf("10");
	    GKAVertex b11 = GKAVertex.valueOf("11");
	    GKAVertex b12 = GKAVertex.valueOf("12");
	    /*
		 * create graph
		 * add vertices
		 */
	    GKAGraph graph3 = GKAGraph.valueOf(GraphType.DIRECTED_UNWEIGHTED);
//	    graph3.addVertex(b1);
//	    graph3.addVertex(b2);
//	    graph3.addVertex(b3);
//	    graph3.addVertex(b4);
//	    graph3.addVertex(b5);
//	    graph3.addVertex(b6);
//	    graph3.addVertex(b7);
//	    graph3.addVertex(b8);
//	    graph3.addVertex(b9);
//	    graph3.addVertex(b10);
//	    graph3.addVertex(b11);
//	    graph3.addVertex(b12);
	    /*
	     * add edges
		  * */
	    graph3.addEdge(b1, b2, "e1");
	    graph3.addEdge(b1, b3, "e2");
	    graph3.addEdge(b1, b8, "e3");
	    graph3.addEdge(b2, b4, "e4");
	    graph3.addEdge(b3, b5, "e5");
	    graph3.addEdge(b3, b8, "e6");
	    graph3.addEdge(b4, b5, "e7");
	    graph3.addEdge(b5, b2, "e8");
	    graph3.addEdge(b3, b4, "e9");
	    graph3.addEdge(b4, b1, "e10");
	    graph3.addEdge(b8, b5, "e11");
	    graph3.addEdge(b6, b7, "e12");
	    graph3.addEdge(b7, b9, "e13");
	    graph3.addEdge(b9, b6, "e14");
	    graph3.addEdge(b11, b12, "e15");
	    
	    /*Start vertex: 1 (b1)
		 * End vertex: 5 (b5)
		 */
	    assertNull(graph3.findShortestWay(new BFS(), b3, b10));
	}
}
