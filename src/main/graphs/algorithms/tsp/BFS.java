package main.graphs.algorithms.tsp;

import static main.graphs.Utils.reverse;

import java.util.ArrayList;
import java.util.List;

import main.graphs.GKAVertex;
import main.graphs.algorithms.path.PathFinderBase;
import main.graphs.exceptions.NoWayException;

/**
 * @author Louisa und Tim
 *
 */
public class BFS extends PathFinderBase {

	private long runtime;
	private long hitcount;

	/**
	 * Finds a shortest way from given startNode to given endNode within g, if
	 * it exists.
	 * 
	 * @param g
	 *            is the Graph
	 * @param startNode
	 *            is the startvertex
	 * @param endNode
	 *            is the endvertex
	 * @return the sortest way from the start vertex to the end vertex
	 * @throws NoWayException
	 *             //TODO update doc tag
	 */
	@Override
	public List<GKAVertex> findShortestWay() {

		long startTime = System.nanoTime();

		hitcount = 0;

		// reset relevant attributes of each vertex and edge of the graph
		hitcount++;
		for (GKAVertex v : g.getGraph().vertexSet()) {
			v.setParent(null);
			v.setVisited(false);
		}

		// queue for the vertices
		List<GKAVertex> queue = new ArrayList<>();

		// write start node in queue
		queue.add(startNode);

		// a list of all visited vertices
		List<GKAVertex> visitedVertices = new ArrayList<>();

		// as long as the queue is not empty an the end vertex is not in the
		// queue
		// add all possible vertices for the way in queue
		while (!queue.isEmpty() && (queue.get((queue.size()) - 1) != endNode)) {

			GKAVertex firstNode = queue.get(0);

			List<GKAVertex> adjacents = new ArrayList<>();
			// if the vertex is in the queue
			if (g.containsVertex(firstNode)) {
				hitcount++;
				// get a list of all adjacent vertices of the current looking
				// Vertex
				adjacents.addAll(g.getAllAdjacentsOf(firstNode));
				hitcount++;
			}

			// mark the first vertex as visited
			firstNode.setVisited(true);

			hitcount += 1;

			// take the next adjacent Vertex to be visited
			for (int i = 0; i < adjacents.size(); i++) {
				GKAVertex currentVertex = adjacents.get(i);
				if ((!currentVertex.isVisited())) {

					// mark it as visited
					currentVertex.setVisited(true);

					// set current node-weight of the start Vertex one higher
					currentVertex.setWeight(firstNode.getWeight() + 1);

					// set the parent
					currentVertex.setParent(firstNode);

					// add the child-vertex (current vertex) to the end of the
					// list
					queue.add(currentVertex);

					/*
					 * if the current vertex is the endNode then stop the
					 * for-loop and go on with the next commmand
					 */
					if (currentVertex == endNode) {
						i = adjacents.size();
						// queue.add(currentVertex);
					}
				}
			}

			// done with the start vertex, added all its children to the queue

			// add current vertex to the list of visited vertices
			visitedVertices.add(firstNode);
			// Done with first vertex. Remove it from queue.
			queue.remove(0);

		}

		if (queue.isEmpty()
				&& !visitedVertices.get(visitedVertices.size() - 1).equals(
						endNode)) {
			return new ArrayList<>();
		} else {
			visitedVertices.addAll(queue);
		}

		// #####################################################
		/*
		 * find the shortest way from the startNode to the endNode
		 */

		// reverse list with vertices to go the queue way back
		List<GKAVertex> reverseList = reverse(visitedVertices);

		List<GKAVertex> reverseReturnList = new ArrayList<GKAVertex>();

		// put the first element (the endNode of the way) in the return list
		reverseReturnList.add(reverseList.get(0));

		/*
		 * gives the shortest way in a list
		 * 
		 * starts at the end vertex and finds the parent vertex with a
		 * nodeweight one less than the end vertex, if foundtakes the current
		 * parent vertex as the new child vertex and find the parent vertex of
		 * this one...stops when start vertex is found
		 */
		GKAVertex tmpVertex = reverseList.get(0); // dummy: first vertex of the
													// way
		for (GKAVertex v : reverseList) {
			if ((tmpVertex.getWeight() == (v.getWeight() + 1))
					&& (tmpVertex.getParent().equals(v))) {
				reverseReturnList.add(v);
				tmpVertex = v;
			}
		}

		runtime = System.nanoTime() - startTime;
		return reverse(reverseReturnList);
	}

	public long getRuntime() {
		return runtime;
	}

	public long getHitcount() {
		return hitcount;
	}

}
