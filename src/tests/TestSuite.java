package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestMatrix.class,
	TestGKAVertex.class,
	TestGKAEdge.class,
	TestGKAGraph.class,
	TestUtils.class,
	TestBFS.class,
	TestDijkstra.class,
	TestFloydWarshall.class,
	TestSamePath.class})

public class TestSuite {}