package main.graphs.algorithms.flow;


public class FordFulkerson extends FlowCalculatorBase {

	/**
	 * Initalisierung des Algorithmus mit einem Stack-Wrapper
	 */
	public FordFulkerson() {
		super(new MyStack<>());
	}
}
