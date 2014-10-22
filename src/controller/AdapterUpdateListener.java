package controller;

import com.mxgraph.swing.mxGraphComponent;

public interface AdapterUpdateListener {

	/**
	 * Anlaufstelle zum Empfang von Updates der Graphenkomponente.
	 * 
	 * @param cell Zelle, die durch die Maus anvisiert wurde.
	 * @param e Das ausloesende Mausereignis.
	 */
	public void receiveAdapterUpdate(mxGraphComponent graphComponent);

}
