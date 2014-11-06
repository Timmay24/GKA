package main.graphs;

public class GKAVertex {

	private		String 		name;
	private 	String 		parent;
	private 	int 		nodeWeight;
	private 	boolean 	visited = false;

	/**
	 * @return Gibt den Knotennamen zurueck.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gibt die Knotengewichtung zurueck (benoetigt fuer BFS)
	 * 
	 * @param nodeWeight
	 */
	public int getNodeWeight() {
		return nodeWeight;
	}

	/**
	 * Setzt die Knotengewichtung (benoetigt fuer BFS)
	 * 
	 * @param nodeWeight Knotengewicht
	 */
	public void setNodeWeight(int nodeWeight) {
		this.nodeWeight = nodeWeight;
	}


	public void setParent(String parent){
		this.parent = parent;
	}
	
	public String getParent(){
		return this.parent;
	}


	/**
	 * @return Gibt an, ob Knoten bereits bei Traversierung besucht wurde.
	 */
	public boolean isVisited() {
		return this.visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * Konstruktor
	 * 
	 * @param name Name des Knotens.
	 */
	private GKAVertex(String name) {
		this.name = name;
//		this.color = "white";
	}

	/**
	 * Factory-Methode zur Erzeugung einer Instanz.
	 * 
	 * @param name Name des Knotens
	 * @return Gibt eine neue Instanz der Klasse zurueck.
	 */
	public static GKAVertex valueOf(String name) {
		return new GKAVertex(name);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GKAVertex other = (GKAVertex) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) //TODO pruefen, ob Abgleich mit dem Namen korrekt ist
			return false;
		return true;
	}

}
