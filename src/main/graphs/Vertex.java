package main.graphs;

public class Vertex {

	private		String 		name;
	private 	String 		color;
	private 	int 		nodeWeight;
	private 	boolean 	visited = false;

	/**
	 * @return Gibt den Knotennamen zurueck.
	 */
	public String getName() {
		return this.name;
	}

	@Deprecated
	public String name() {
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

	@Deprecated
	public void nodeWeight(int nodeWeight) {
		this.nodeWeight = nodeWeight;
	}

	@Deprecated
	public int nodeWeight() {
		return this.nodeWeight;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Deprecated
	public String color() {
		return this.color;
	}

	@Deprecated
	public void color(String color) {
		this.color = color;
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

	@Deprecated
	public void isVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * Konstruktor
	 * 
	 * @param name Name des Knotens.
	 */
	private Vertex(String name) {
		this.name = name;
		this.color = "white";
	}

	/**
	 * Factory-Methode zur Erzeugung einer Instanz.
	 * 
	 * @param name Name des Knotens
	 * @return Gibt eine neue Instanz der Klasse zurueck.
	 */
	public static Vertex valueOf(String name) {
		return new Vertex(name);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		// result = prime * result + nodeWeight;
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
		Vertex other = (Vertex) obj;
		// if (color == null) {
		// if (other.color != null)
		// return false;
		// } else if (!color.equals(other.color))
		// return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		// if (nodeWeight != other.nodeWeight)
		// return false;
		return true;
	}

}
