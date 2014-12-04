package main.graphs.algorithms.flow;


public class EdmondKarp extends FlowCalculatorBase {

	/**
	 * Initalisierung des Algorithmus mit einem Queue-Wrapper
	 */
	public EdmondKarp() {
		super(new MyQueue<>());
	}
}
