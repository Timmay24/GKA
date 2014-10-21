package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.graphs.BFS;
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
//	     assertEquals(l, BFS.getAllAdjacentsOf(g,v1));
	
		
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
		 
		 
		 List<Vertex> l = new ArrayList<>();
	     l.add(f);
	     l.add(b);
	     l.add(h);
	      
	     assertEquals(l, graph1.getAllAdjacentsOf(a));
	 
	}
	

}
