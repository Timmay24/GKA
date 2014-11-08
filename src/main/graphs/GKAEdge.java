package main.graphs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.jgrapht.graph.DefaultEdge;

public class GKAEdge extends DefaultEdge implements Comparable<GKAEdge> {

	private static final long serialVersionUID = -6523434696944744833L;

	private String  name = null;
	private Integer weight = null;

	/**
	 * KONSTRUKTOR
	 * 
	 * @param name Kantenname
	 * @param weight Kantengewichtung
	 */
	private GKAEdge(String name, Integer weight) {
		super();
		checkNotNull(name);
		checkArgument(!name.isEmpty());
		this.name = name;
		this.weight = weight;
	}
	
	/**
	 * Factory-Methode
	 * 
	 * @param name
	 * @param weight
	 * @return Kantenobjekt
	 */
	public static GKAEdge valueOf(String name, Integer weight) {
		return new GKAEdge(name, weight);
	}
	
	/**
	 * Konfiguration: ohne Kantengewichtung
	 */
	public static GKAEdge valueOf(String name) {
		return valueOf(name, null);
	}

	/* (non-Javadoc)
	 * @see org.jgrapht.graph.DefaultEdge#getSource()
	 */
	public Object getSource() {
		return super.getSource();
	}

	/* (non-Javadoc)
	 * @see org.jgrapht.graph.DefaultEdge#getTarget()
	 */
	public Object getTarget() {
		return super.getTarget();
	}

	/**
	 * @return Kantengewicht.
	 */
	public Integer getWeight() {
		return this.weight;
	}

	/**
	 * @return Kantenname
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return true, wenn Kante gewichtet, false, wenn nicht.
	 */
	public boolean isWeighted() {
		return weight != null;
	}
	
	/* (non-Javadoc)
	 * @see org.jgrapht.graph.DefaultEdge#toString()
	 */
	@Override
	public String toString() {
		String retVal;
		if (getName() == null) {
			retVal = "(" + getSource() + " : " + getTarget() + ")";
		} else {
			retVal = "(" + getName() + ")";
		}
		if (getWeight() != null) {
			retVal += " : " + getWeight();
		}
		return retVal;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		} else if (object == this) {
			return true;
		} else if (!(object instanceof GKAEdge)) {
			return false;
		} else {
			GKAEdge edge = (GKAEdge) object;
			boolean retval = true;
			if (getSource() == null) {
				retval = retval && edge.getSource() == null;
			} else {
				retval = retval && getSource().equals(edge.getSource());
			}

			if (getTarget() == null) {
				retval = retval && edge.getTarget() == null;
			} else {
				retval = retval && getTarget().equals(edge.getTarget());
			}

			if (getName() == null) {
				retval = retval && edge.getName() == null;
			} else {
				retval = retval && getName().equals(edge.getName());
			}

			if (getWeight() == null) {
				retval = retval && edge.getWeight() == null;
			} else {
				retval = retval && getWeight().equals(edge.getWeight());
			}
			return retval;
		}
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int retVal = 31;
		if (getSource() != null) {
			retVal += getSource().hashCode();
		}
		if (getTarget() != null) {
			retVal += getTarget().hashCode();
		}
		if (getName() != null) {
			retVal += getName().hashCode();
		}
		if (getWeight() != null) {
			retVal += getWeight().hashCode();
		}
		return retVal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(GKAEdge other) {
		return this.getWeight().compareTo(other.getWeight());
	}

}
