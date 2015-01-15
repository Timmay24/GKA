package tests;

import static org.junit.Assert.*;
import main.graphs.CompleteGraphGenerator;
import main.graphs.GKAGraph;
import main.graphs.GraphType;

import org.junit.Test;

public class TestCompleteGraphGenerator {

	@Test
	public void testIfGraphIsComplete() {
		int 					desiredVertexCount = 25;
		GKAGraph 				g	= GKAGraph.valueOf(GraphType.UNDIRECTED_WEIGHTED);
		CompleteGraphGenerator 	gen = new CompleteGraphGenerator(g, g.getGraphType(), desiredVertexCount);
		gen.run(); // Generator aktivieren
		
		int actual = g.getGraph().edgeSet().size();
		int desired = (desiredVertexCount * (desiredVertexCount-1)) / 2;
		assertEquals(desired, actual);
	}

}
