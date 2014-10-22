package controller;

import java.util.Map;

public interface StatsListener {

	public void receiveStats(Map<String, Object> stats);
	
}
