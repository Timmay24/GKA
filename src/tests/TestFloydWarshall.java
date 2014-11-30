package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;
import main.graphs.algorithms.path.FloydWarshall;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
	
public class TestFloydWarshall {
	
		GKAGraph g1;
		GKAVertex a,b,c,d,e,f;
		GKAEdge vAB, vAC, vAE, vBC, vCD, vCE, vDF, vEF;
		List<GKAVertex> shortestWays;
		
		GKAGraph g2;
		GKAGraph g3;
		GKAGraph g4;
		GKAVertex a1,b2,c3,d4, e5, f6, g7, h8;
		GKAEdge vA1B2, vA1C3, vB2C3, vB2D4, vC3D4, vE5C3, vF6E5, vD4F6, vB2G7, vG7H8,  
			vA1B2a, vA1C3a, vB2C3a, vB2D4a, vC3D4a, vA1B2b, vA1C3b, vB2D4b, vC3D4b;


		List<GKAVertex> shortestWays1, shortestWays2, shortestWays3, shortestWays4;
		

		@Before
		public void setUp() throws Exception {
			
			/**
			 * Test 1 
			 * Ungerichteter Graph
			 */
			g1 = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
			shortestWays = new ArrayList<>();
			
			g1.addEdge("a", "b", "vAB", 4);
			g1.addEdge("a", "c", "vAC", 2);
			g1.addEdge("a", "e", "vAE", 4);
			g1.addEdge("b", "c", "vBC", 1);
			g1.addEdge("c", "d", "vCD", 3);
			g1.addEdge("c", "e", "vCE", 5);
			g1.addEdge("d", "f", "vDF", 2);
			g1.addEdge("e", "f", "vEF", 1);
			
			a = g1.getVertex("a");
			b = g1.getVertex("b");
			c = g1.getVertex("c");
			d = g1.getVertex("d");
			e = g1.getVertex("e");
			f = g1.getVertex("f");
			
			vAB = g1.getEdge("vAB");
			vAC = g1.getEdge("vAC");
			vAE = g1.getEdge("vAE");
			vBC = g1.getEdge("vBC");
			vCD = g1.getEdge("vCD");
			vCE = g1.getEdge("vCE");
			vDF = g1.getEdge("vDF");
			vEF = g1.getEdge("vEF");
			

		
			/**
			 * Test 2
			 * Ungerichteter Graph
			 */
			g2 = GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
			shortestWays1 = new ArrayList<>();
			
			g2.addEdge("a1", "b2", "vA1B2", 2);
			g2.addEdge("a1", "c3", "vA1C3", 4);
			g2.addEdge("b2", "c3", "vB2C3", 1);
			g2.addEdge("b2", "d4", "vB2D4", 5);
			g2.addEdge("c3", "d4", "vC3D4", 3);
			
			a1 = g2.getVertex("a1");
			b2 = g2.getVertex("b2");
			c3 = g2.getVertex("c3");
			d4 = g2.getVertex("d4");
			
			vA1B2 = g2.getEdge("vA1B2");
			vA1C3 = g2.getEdge("vA1C3");
			vB2C3 = g2.getEdge("vB2C3");
			vB2D4 = g2.getEdge("vB2D4");
			vC3D4 = g2.getEdge("vC3D4");
                          
			
			
			/**
			 * Test3
			 * Gerichteter Graph
			 */
			g3 = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
			
			g3.addEdge("a1", "b2", "vA1B2a", -2);
			g3.addEdge("a1", "c3", "vA1C3a", -4);
			g3.addEdge("b2", "c3", "vB2C3a", 1);
			g3.addEdge("b2", "d4", "vB2D4a", -5);
			g3.addEdge("c3", "d4", "vC3D4a", 3);
			g3.addEdge("e5", "c3", "vE5C3", 7);
			g3.addEdge("f6", "e5", "vF6E5", -3);
			g3.addEdge("d4", "f6", "vD4F6", 9);
			g3.addEdge("b2", "g7", "vB2G7", 8);
			g3.addEdge("g7", "h8", "vG7H8", 4);
			
			e5 = g3.getVertex("e5");
			f6 = g3.getVertex("f6");
			g7 = g3.getVertex("g7");
			h8 = g3.getVertex("h8");

			vA1B2a = g3.getEdge("vA1B2a");
			vA1C3a = g3.getEdge("vA1C3a");
			vB2C3a = g3.getEdge("vB2C3a");
			vB2D4a = g3.getEdge("vB2D4a");
			vC3D4a = g3.getEdge("vC3D4a");
			vE5C3  = g3.getEdge("vE5C3");
			vF6E5  = g3.getEdge("vF6E5");
			vD4F6  = g3.getEdge("vD4F6");
			vB2G7  = g3.getEdge("vB2G7");
			vG7H8  = g3.getEdge("vG7H8");
			
			/**
			 * Test4
			 * Gerichteter Graph
			 * gleiche Kantengewichtung
			 */
			g4 = GKAGraph.valueOf(GraphType.DIRECTED_WEIGHTED);
			shortestWays3 = new ArrayList<>();
			shortestWays4 = new ArrayList<>();
			
			g4.addEdge("a1", "b2", "vA1B2b", 1);
			g4.addEdge("a1", "c3", "vA1C3b", 1);
			g4.addEdge("b2", "d4", "vB2D4b", 1);
			g4.addEdge("c3", "d4", "vC3D4b", 1);
			
			a1 = g4.getVertex("a1");
			b2 = g4.getVertex("b2");
			d4 = g4.getVertex("d4");
			
			vA1B2b = g4.getEdge("vA1B2b");
			vA1C3b = g4.getEdge("vA1C3b");
			vB2D4b = g4.getEdge("vB2D4b");
			vC3D4b = g4.getEdge("vC3D4b");
			
			shortestWays3.add(a1);
			shortestWays3.add(b2);
			shortestWays3.add(d4);
			
		}


		@Test
		public void testFindShortestWay() {
			
			//TODO: Konstruktion der Testcases aktualisieren, da Erzeugung der Edges geändert wurde
			
			assertEquals(3, (g1.findShortestWay(new FloydWarshall(), a, f)).size());
//			
//			assertEquals(3, (g2.findShortestWay(new FloydWarshall(), a1, c3)).size());
//			
//			assertEquals(2, (g3.findShortestWay(new FloydWarshall(), a1, c3)).size());
//			assertEquals(3, (g3.findShortestWay(new FloydWarshall(), a1, d4)).size());
//			assertEquals(4, (g3.findShortestWay(new FloydWarshall(), d4, c3)).size());
//			assertEquals(4, (g3.findShortestWay(new FloydWarshall(), a1, h8)).size());
//			
//			assertEquals(3, (g4.findShortestWay(new FloydWarshall(), a1, d4)).size());
//			assertEquals(shortestWays3, (g4.findShortestWay(new FloydWarshall(), a1, d4)));
		}
		
		
		

	

}
