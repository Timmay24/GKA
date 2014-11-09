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
		List<List<GKAVertex>> shortestWays;
		
		GKAGraph graph1;
		GKAVertex a1,b2,c3,d4;
		GKAEdge vA1B2, vA1C3, vB2C3, vB2D4, vC3D4;
		List<List<GKAVertex>> shortestWays1;
		
		@Before
		public void setUp() throws Exception {
			
//			graph = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
//			
//			shortestWays = new ArrayList<>();
//			
//			a = GKAVertex.valueOf("a");
//			b = GKAVertex.valueOf("b");
//			c = GKAVertex.valueOf("c");
//			d = GKAVertex.valueOf("d");
//			e = GKAVertex.valueOf("e");
//			f = GKAVertex.valueOf("f");
//			
//			vAB = GKAEdge.valueOf("vAB", 4);
//			vAC = GKAEdge.valueOf("vAC", 2);
//			vAE = GKAEdge.valueOf("vAE", 4);
//			vBC = GKAEdge.valueOf("vBC", 1);
//			vCD = GKAEdge.valueOf("vCD", 3);
//			vCE = GKAEdge.valueOf("vCE", 5);
//			vDF = GKAEdge.valueOf("vDF", 2);
//			vEF = GKAEdge.valueOf("vEF", 1);
//			
//			graph.addEdge(a, b, vAB);
//			graph.addEdge(a, c, vAC);
//			graph.addEdge(a, e, vAE);
//			graph.addEdge(b, c, vBC);
//			graph.addEdge(c, d, vCD);
//			graph.addEdge(c, e, vCE);
//			graph.addEdge(d, f, vDF);
//			graph.addEdge(e, f, vEF);
//			
//			shortestWays.add(Arrays.asList(a,e,f));
			
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
			
			shortestWays1.add(Arrays.asList(a1,b2,c3));
			
		}

		@Test
		public void testFindShortestWay() {
//			GKAVertex startNode = a;
//			GKAVertex endNode = f;
//			
//			List<GKAVertex> shortestWay = Arrays.asList(a,e,f);
			
//			assertTrue(shortestWays.contains(FloydWarshall.findShortestWay(graph, startNode, endNode)));
			assertEquals(shortestWays1, FloydWarshall.findShortestWay(graph1, a1, c3));
		}
		
		
		

	

}
