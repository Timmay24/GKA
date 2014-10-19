//package tests;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import main.graphs.Vertex;
//import main.graphs.Edge;
//import main.graphs.BFS;
//
//import org.jgraph.graph.DefaultEdge;
//import org.jgrapht.Graph;
//import org.jgrapht.UndirectedGraph;
//import org.jgrapht.graph.SimpleGraph;
//import org.junit.Before;
//import org.junit.Test;
//
//public class main {
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//
//	
//	@Test
//	public void testAllAdjNode() {
//		
//		//Graph<String, String> g = new Graph<String, String>;
//		UndirectedGraph<Vertex, Edge> g = new SimpleGraph<>(Edge.class);
//		  g.addVertex( Vertex.valueOf("v1"));
//	      g.addVertex( Vertex.valueOf("v2"));
//	      g.addVertex( Vertex.valueOf("v3"));
//	      g.addVertex( Vertex.valueOf("v4"));
//
//	      g.addEdge( Vertex.valueOf("v1"), Vertex.valueOf("v2"));
//	      g.addEdge( Vertex.valueOf("v1"), Vertex.valueOf("v3")); 
//	      g.addEdge( Vertex.valueOf("v3"), Vertex.valueOf("v4"));
//	      
////	      Edge.valueOf(g.addEdge( Vertex.valueOf("v1"), Vertex.valueOf("v2") ).toString());
////	      Edge.valueOf(g.addEdge( Vertex.valueOf("v1"), Vertex.valueOf("v3") ).toString());
////	      Edge.valueOf(g.addEdge( Vertex.valueOf("v2"), Vertex.valueOf("v3") ).toString());
////	      Edge.valueOf(g.addEdge( Vertex.valueOf("v3"), Vertex.valueOf("v1") ).toString());
////	      Edge.valueOf(g.addEdge( Vertex.valueOf("v4"), Vertex.valueOf("v3") ).toString());
//	      
//	     // BFS.allAdjNode(g, Vertex.valueOf("v1"));
//	      List<Vertex> l = new ArrayList<>();
//	      l.add(Vertex.valueOf("v23"));
//	      l.add(Vertex.valueOf("v3"));
//	      
//	      assertEquals(l, BFS.allAdjNode(g,Vertex.valueOf("v1")));
//	}
//	
//	
//	
//	@Test
//	public void testReverse(){
//		List<Vertex> reverseList = new ArrayList<>();
//		reverseList.add(Vertex.valueOf("a"));
//		reverseList.add(Vertex.valueOf("b"));
//		reverseList.add(Vertex.valueOf("c"));
//		reverseList.add(Vertex.valueOf("d"));
//		reverseList.add(Vertex.valueOf("e"));
//		
//		List<Vertex> list = new ArrayList<>();
//		list.add(Vertex.valueOf("e"));
//		list.add(Vertex.valueOf("d"));
//		list.add(Vertex.valueOf("c"));
//		list.add(Vertex.valueOf("b"));
//		list.add(Vertex.valueOf("a"));
//		
//		assertEquals(reverseList, BFS.reverse(list));
//	}
//	
//	
//	
//	
//}
