package controller;

public interface AdapterUpdateSender {
	
	/**
	 * @param cellListener Objekt, das sich zum Empfang von JGraph Zellen anmelden will.
	 */
	public void addAdapterUpdateListener(AdapterUpdateListener adapterUpdateListener);
}
