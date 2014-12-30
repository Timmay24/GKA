package main.graphs.algorithms;

import main.graphs.algorithms.interfaces.GKAAlgorithm;

public class GKAAlgorithmBase implements GKAAlgorithm {

	protected long startTime;
	protected long timeElapsed;
	protected long hc;

	protected GKAAlgorithmBase() {}

	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#startTimeMeasurement()
	 */
	@Override
	public void startTimeMeasurement() {
		startTime = System.nanoTime();
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#stopTimeMeasurement()
	 */
	@Override
	public void stopTimeMeasurement() {
		timeElapsed = System.nanoTime() - startTime;
	}
	
	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#getRuntime()
	 */
	@Override
	public long getRuntime() {
		return timeElapsed;
	}

	/* (non-Javadoc)
	 * @see main.graphs.algorithms.interfaces.FlowCalculator#hitCounter()
	 */
	@Override
	public long getHitCounter() {
		return hc;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
