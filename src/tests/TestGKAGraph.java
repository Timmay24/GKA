package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GraphType;
import main.graphs.Vertex;

import org.junit.Ignore;
import org.junit.Test;

public class TestGKAGraph {

	@Ignore
	@Test
	public void testGetAllAdjacentsOf() {
		
	
		 /*Test 1
	      * Undirected Graph
	      * */
		Vertex v1 = Vertex.valueOf("v1");
		Vertex v2 = Vertex.valueOf("v2");
		Vertex v3 = Vertex.valueOf("v3");
		Vertex v4 = Vertex.valueOf("v4");
		
		GKAGraph g1 = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		g1.addVertex( v1);
	    g1.addVertex( v2);
	    g1.addVertex( v3);
	    g1.addVertex( v4);

	    g1.addEdge( v1, v2, GKAEdge.valueOf("test1"));
	    g1.addEdge( v1, v3, GKAEdge.valueOf("test2")); 
	    g1.addEdge( v3, v4, GKAEdge.valueOf("test3"));
	 
	 
	    List<Vertex> l = new ArrayList<>();
	    l.add(v2);
	    l.add(v3);
	     
	    assertEquals(l, g1.getAllAdjacentsOf(v1));
	
		
	    /*Test 2
	     * Undirekted Graph
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
		
		
		 //creat graph
		 //add vertices
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
		 
		 
		List<Vertex> l1 = new ArrayList<>();
	    l1.add(f);
	    l1.add(b);
	    l1.add(h);
	     
	    assertEquals(l1, graph1.getAllAdjacentsOf(a));
	 
	     
	     
	     
	     

	    /*Test 3
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
	     
	     
	    List<Vertex> l2 = new ArrayList<>();
	    l2.add(a4);
	     
	    List<Vertex> l3 = new ArrayList<>();
	     
	    //all adjacent vertices of a vertix with one outgoing edge --> return a list with one vertex(-> l2)
	    assertEquals(l2, graph1.getAllAdjacentsOf(a2));
	     
	    //all adjacent vertices of a vertix without any edges --> return empty list (-> l3)
	    assertEquals(l3, graph1.getAllAdjacentsOf(a10));
	     
	     
	}
	

}
