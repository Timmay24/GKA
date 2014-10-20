package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.graphs.Vertex;
import main.graphs.BFS;
import controller.GKAEdge;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Before;
import org.junit.Test;



public class main {

	@Before
	public void setUp() throws Exception {
	}


	
	@Test
	public void testAllAdjNode() {
		
		
		
		Vertex v1 = Vertex.valueOf("v1");
		Vertex v2 = Vertex.valueOf("v2");
		Vertex v3 = Vertex.valueOf("v3");
		Vertex v4 = Vertex.valueOf("v4");
		
		 UndirectedGraph<Vertex, GKAEdge> g = new SimpleGraph<>(GKAEdge.class);
		 g.addVertex( v1);
	     g.addVertex( v2);
	     g.addVertex( v3);
	     g.addVertex( v4);

	     g.addEdge( v1, v2, new GKAEdge("test1"));
	     g.addEdge( v1, v3, new GKAEdge("test2")); 
	     g.addEdge( v3, v4, new GKAEdge("test3"));
	 
	 
	     List<Vertex> l = new ArrayList<>();
	     l.add(v2);
	     l.add(v3);
	      
	     assertEquals(l, BFS.allAdjNode(g,v1));
	}
	
	@Test
	public void testFindShortestWay() {
		
		 // creat graph
		 //add vertices
		 UndirectedGraph<Vertex, GKAEdge> graph1 = new SimpleGraph<>(GKAEdge.class);
		 graph1.addVertex( Vertex.valueOf("A"));
		 graph1.addVertex( Vertex.valueOf("B"));
		 graph1.addVertex( Vertex.valueOf("E"));
		 graph1.addVertex( Vertex.valueOf("F"));
		 graph1.addVertex( Vertex.valueOf("G"));
		 graph1.addVertex( Vertex.valueOf("H"));
		 graph1.addVertex( Vertex.valueOf("K"));
		 graph1.addVertex( Vertex.valueOf("M"));
		 
		 //add edges
		 graph1.addEdge( Vertex.valueOf("A"), Vertex.valueOf("F"));
		 graph1.addEdge( Vertex.valueOf("A"), Vertex.valueOf("B")); 
		 graph1.addEdge( Vertex.valueOf("A"), Vertex.valueOf("H"));
		 
		 graph1.addEdge( Vertex.valueOf("F"), Vertex.valueOf("E"));
		 graph1.addEdge( Vertex.valueOf("F"), Vertex.valueOf("A"));
		 
		 graph1.addEdge( Vertex.valueOf("B"), Vertex.valueOf("A"));
		 graph1.addEdge( Vertex.valueOf("B"), Vertex.valueOf("E"));
		 graph1.addEdge( Vertex.valueOf("B"), Vertex.valueOf("G"));
		 graph1.addEdge( Vertex.valueOf("B"), Vertex.valueOf("H"));
		 
		 graph1.addEdge( Vertex.valueOf("H"), Vertex.valueOf("B"));
		 graph1.addEdge( Vertex.valueOf("H"), Vertex.valueOf("A"));
		 graph1.addEdge( Vertex.valueOf("H"), Vertex.valueOf("G"));
		 graph1.addEdge( Vertex.valueOf("H"), Vertex.valueOf("K"));
		 
		 graph1.addEdge( Vertex.valueOf("K"), Vertex.valueOf("H"));
		 graph1.addEdge( Vertex.valueOf("K"), Vertex.valueOf("M"));
		 
		 graph1.addEdge( Vertex.valueOf("G"), Vertex.valueOf("H"));
		 graph1.addEdge( Vertex.valueOf("G"), Vertex.valueOf("B"));
		 graph1.addEdge( Vertex.valueOf("G"), Vertex.valueOf("E"));
		 graph1.addEdge( Vertex.valueOf("G"), Vertex.valueOf("M"));
		 
		 graph1.addEdge( Vertex.valueOf("M"), Vertex.valueOf("K"));
		 graph1.addEdge( Vertex.valueOf("M"), Vertex.valueOf("G"));
		 graph1.addEdge( Vertex.valueOf("M"), Vertex.valueOf("E"));
		 
		 graph1.addEdge( Vertex.valueOf("E"), Vertex.valueOf("F"));
		 graph1.addEdge( Vertex.valueOf("E"), Vertex.valueOf("B"));
		 graph1.addEdge( Vertex.valueOf("E"), Vertex.valueOf("G"));
		 graph1.addEdge( Vertex.valueOf("E"), Vertex.valueOf("M"));
		 
		 
		 //Start vertex: A
		 //End vertex: M
		 
		 List<Vertex> shortWay1 = new ArrayList<>();
	     shortWay1.add(Vertex.valueOf("A"));
	     shortWay1.add(Vertex.valueOf("F"));
	     shortWay1.add(Vertex.valueOf("E"));
	     shortWay1.add(Vertex.valueOf("M"));
	      
	     assertEquals(shortWay1, BFS.findShortestWay(graph1, Vertex.valueOf("A"), Vertex.valueOf("M")));
		
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
