package main.graphs.algorithms.interfaces;

public interface GKAAlgorithm {

	/**
	 * Startet die Laufzeitmessung fuer den Algorithmus
	 */
	public abstract void startTimeMeasurement();

	/**
	 * Stoppt die Laufzeitmessung fuer den Algorithmus und berechnet die Differenz zwischen Start- und Endzeit
	 */
	public abstract void stopTimeMeasurement();

	/**
	 * @return Die benoetigte Rechenzeit fuer den Algorithmus
	 */
	public abstract long getRuntime();

	/**
	 * @return Anzahl Zugriffe auf den Graphen bei der Nutzung des Algorithmus
	 */
	public abstract long getHitCounter();

	/**
	 * @return Bezeichnung der Algorithmus-Klasse
	 */
	public abstract String toString();

}