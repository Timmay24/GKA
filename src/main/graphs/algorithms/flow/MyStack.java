package main.graphs.algorithms.flow;

import java.util.Stack;

import main.graphs.algorithms.interfaces.Batch;


public class MyStack<E> implements Batch<E> {

	Stack<E> stack;
	
	public MyStack() {
		stack = new Stack<>();
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.Batch#add(java.lang.Object)
	 */
	@Override
	public boolean add(E arg0) {
		return (stack.push(arg0) != null);
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.Batch#remove()
	 */
	@Override
	public E remove() {
		return stack.pop();
	}

	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.Batch#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.Batch#clear()
	 */
	@Override
	public void clear() {
		stack.clear();
	}
	
}
