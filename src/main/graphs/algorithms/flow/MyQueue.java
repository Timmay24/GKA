package main.graphs.algorithms.flow;

import java.util.LinkedList;
import java.util.Queue;

import main.graphs.algorithms.interfaces.Batch;


public class MyQueue<E> implements Batch<E> {

	Queue<E> queue;
	
	public MyQueue() {
		queue = new LinkedList<>();
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.Batch#add(java.lang.Object)
	 */
	@Override
	public boolean add(E arg0) {
		return queue.offer(arg0);
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.Batch#remove()
	 */
	@Override
	public E remove() {
		return queue.poll();
	}

	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.Batch#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.Batch#clear()
	 */
	@Override
	public void clear() {
		queue.clear();
	}
	
}
