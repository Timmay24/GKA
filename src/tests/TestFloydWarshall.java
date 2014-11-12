package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.graphs.FloydWarshall;
import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;

import org.junit.Before;
import org.junit.Test;
	
public class TestFloydWarshall {
	
		GKAGraph graph;
		GKAVertex a,b,c,d,e,f;
		GKAEdge vAB, vAC, vAE, vBC, vCD, vCE, vDF, vEF;
		List<GKAVertex> shortestWays;
		
		GKAGraph graph1;
		GKAGraph graph2;
		GKAVertex a1,b2,c3,d4, e5, f6, g7, h8;
		GKAEdge vA1B2, vA1C3, vB2C3, vB2D4, vC3D4, vE5C3, vF6E5, vD4F6, vB2G7, vG7H8,  vA1B2a, vA1C3a, vB2C3a, vB2D4a, vC3D4a;

		List<GKAVertex> shortestWays1, shortestWays2;
		
		@Before
		public void setUp() throws Exception {
			
			graph = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
			shortestWays = new ArrayList<>();
			a = GKAVertex.valueOf("a");
			b = GKAVertex.valueOf("b");
			c = GKAVertex.valueOf("c");
			d = GKAVertex.valueOf("d");
			e = GKAVertex.valueOf("e");
			f = GKAVertex.valueOf("f");
			
			vAB = GKAEdge.valueOf("vAB", 4);
			vAC = GKAEdge.valueOf("vAC", 2);
			vAE = GKAEdge.valueOf("vAE", 4);
			vBC = GKAEdge.valueOf("vBC", 1);
			vCD = GKAEdge.valueOf("vCD", 3);
			vCE = GKAEdge.valueOf("vCE", 5);
			vDF = GKAEdge.valueOf("vDF", 2);
			vEF = GKAEdge.valueOf("vEF", 1);
			
			graph.addEdge(a, b, vAB);
			graph.addEdge(a, c, vAC);
			graph.addEdge(a, e, vAE);
			graph.addEdge(b, c, vBC);
			graph.addEdge(c, d, vCD);
			graph.addEdge(c, e, vCE);
			graph.addEdge(d, f, vDF);
			graph.addEdge(e, f, vEF);
			

		
			
			graph1 = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
			shortestWays1 = new ArrayList<>();
			a1 = GKAVertex.valueOf("1");
			b2 = GKAVertex.valueOf("2");
			c3 = GKAVertex.valueOf("3");
			d4 = GKAVertex.valueOf("4");
			
			vA1B2 = GKAEdge.valueOf("12",2);
			vA1C3 = GKAEdge.valueOf("13",4);
			vB2C3 = GKAEdge.valueOf("23",1);
			vB2D4 = GKAEdge.valueOf("24",5);
			vC3D4 = GKAEdge.valueOf("34",3);

			graph1.addEdge(a1, b2, vA1B2);
			graph1.addEdge(a1, c3, vA1C3);
			graph1.addEdge(b2, c3, vB2C3);
			graph1.addEdge(b2, d4, vB2D4);
			graph1.addEdge(c3, d4, vC3D4);
			
			
			
			graph2 = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
			e5 = GKAVertex.valueOf("e5");
			f6 = GKAVertex.valueOf("f6");
			g7 = GKAVertex.valueOf("g7");
			h8 = GKAVertex.valueOf("h8");

			
			vA1B2a = GKAEdge.valueOf("12",-2);
			vA1C3a = GKAEdge.valueOf("13",-4);
			vB2C3a = GKAEdge.valueOf("23",1);
			vB2D4a = GKAEdge.valueOf("24",-5);
			vC3D4a = GKAEdge.valueOf("34",3);
			vE5C3 = GKAEdge.valueOf("53",7);
			vF6E5 = GKAEdge.valueOf("65",-3);
			vD4F6 = GKAEdge.valueOf("46",9);
			vB2G7 = GKAEdge.valueOf("27",8);
			vG7H8 = GKAEdge.valueOf("78",4);

			graph2.addEdge(a1, b2, vA1B2a);
			graph2.addEdge(a1, c3, vA1C3a);
			graph2.addEdge(b2, c3, vB2C3a);
			graph2.addEdge(b2, d4, vB2D4a);
			graph2.addEdge(c3, d4, vC3D4a);
			graph2.addEdge(e5, c3, vE5C3);
			graph2.addEdge(f6, e5, vF6E5);
			graph2.addEdge(d4, f6, vD4F6);
			graph2.addEdge(b2, g7, vB2G7);
			graph2.addEdge(g7, h8, vG7H8);
			
			
		}

		@Test
		public void testFindShortestWay() {
			
			assertEquals(3, (FloydWarshall.findShortestWay(graph, a, f)).size());
			assertEquals(3, (FloydWarshall.findShortestWay(graph1, a1, c3)).size());
			
			assertEquals(2, (FloydWarshall.findShortestWay(graph2, a1, c3)).size());
			assertEquals(3, (FloydWarshall.findShortestWay(graph2, a1, d4)).size());
			assertEquals(4, (FloydWarshall.findShortestWay(graph2, d4, c3)).size());
			assertEquals(4, (FloydWarshall.findShortestWay(graph2, a1, h8)).size());
		}
		
		
		

	

}
