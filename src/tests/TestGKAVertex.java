package tests;

import static org.junit.Assert.*;
import main.graphs.GKAEdge;
import main.graphs.GKAGraph;
import main.graphs.GKAVertex;
import main.graphs.GraphType;

import org.junit.Before;
import org.junit.Test;

public class TestGKAVertex {

	GKAVertex v1, v1eq, v1neq;
	GKAVertex v2, v2eq, v2eq2;
	GKAVertex v3, v4;
	
	@Before
	public void setUp() throws Exception {
		v1 = 	GKAVertex.valueOf("v1");
		v1eq = 	GKAVertex.valueOf("v1");
		v1neq = GKAVertex.valueOf("v1neq");
		
		v2 = 	GKAVertex.valueOf("v2", 10);
		v2eq = 	GKAVertex.valueOf("v2", 10);
		v2eq2 = GKAVertex.valueOf("v2", 12); // weight does not affect equality
											 // since it serves non-static
											 // algorithm related purposes
		v3 = 	GKAVertex.valueOf("v3");
		v4 = 	GKAVertex.valueOf("v4");
	}

	@Test
	public void testEquals() {
		assertEquals(v1eq,v1);
		assertNotEquals(v1neq,v1);
		
		assertEquals(v2eq,v2);
		assertEquals(v2eq2,v2);
		
		assertNotEquals(v2eq2.getWeight(), v2.getWeight());
	}
	
	@Test
	public void testParent() {
		v1eq.setParent(v1);
		assertEquals(v1, v1eq.getParent());
	}
	
	@Test
	public void testHasEdgeTo(){
		GKAGraph g = GKAGraph.valueOf(GraphType.UNDIRECTED_UNWEIGHTED);
		GKAEdge eV1V3 = GKAEdge.valueOf("V1V3");
		g.addVertex(v1);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addEdge(v1, v3, eV1V3);
		
		assertEquals(true, v1.hasEdgeTo(v3, g));
		assertEquals(false, v1.hasEdgeTo(v4, g));
		
	}

}
