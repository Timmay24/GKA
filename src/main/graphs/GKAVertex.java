package main.graphs;

public class GKAVertex implements Comparable<GKAVertex> {

	private		String 		name;
	private 	GKAVertex	parent;
	private 	Integer 	weight;
	private 	boolean 	visited;

	/**
	 * @return Gibt den Knotennamen zurueck.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gibt die Knotengewichtung zurueck (benoetigt fuer BFS)
	 * 
	 * @param weight
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * Setzt die Knotengewichtung (benoetigt fuer BFS)
	 * 
	 * @param weight Knotengewicht
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}


	/**
	 * @param Referenz des Vorgaengers dieses Knotens
	 */
	public void setParent(GKAVertex parent) {
		this.parent = parent;
	}
	
	/**
	 * @return Referenz des Vorgaengers dieses Knotens
	 */
	public GKAVertex getParent() {
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
	 * 
	 * @param target GKAVertex
	 * @param g GKAGraph
	 * @return true wenn es eine Kante gibt, false wenn nicht
	 */
	public boolean hasEdgeTo(GKAVertex target, GKAGraph g){
		return (g.getEdge(this, target) != null);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param name Name des Knotens.
	 * @param weight Gewicht des Knotens.
	 */
	private GKAVertex(String name, Integer weight) {
		this.name = name;
		this.visited = false;
		this.weight = weight;
	}

	/**
	 * Factory-Methode zur Erzeugung einer Instanz.
	 * 
	 * @param name Name des Knotens
	 * @param weight Gewicht des Knotens
	 * @return Gibt eine neue Instanz der Klasse zurueck.
	 */
	public static GKAVertex valueOf(String name, Integer weight) {
		return new GKAVertex(name, weight);
	}
	
	/**
	 * Konfiguration: festes Gewicht
	 */
	public static GKAVertex valueOf(String name) {
		return valueOf(name, 0);
	}
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(GKAVertex other) {
		return this.getWeight().compareTo(other.getWeight());
	}

}
