package main.graphs;

import org.jgrapht.graph.DefaultEdge;

import static com.google.common.base.Preconditions.*;

public class GKAEdge extends DefaultEdge {

	private static final long serialVersionUID = -6523434696944744833L;

	private String  edgeName = null;
	private Integer edgeWeight = null;

	private GKAEdge(String edgeName, Integer edgeWeight) {
		super();
		checkNotNull(edgeName);
		checkArgument(!edgeName.isEmpty());
		this.edgeName = edgeName;
		this.edgeWeight = edgeWeight;
	}
	
	private GKAEdge(String edgeName) {
		this(edgeName, null);
	}
	
	public static GKAEdge valueOf(String edgeName, Integer edgeWeight) {
		return new GKAEdge(edgeName, edgeWeight);
	}
	
	public static GKAEdge valueOf(String edgeName) {
		return valueOf(edgeName, null);
	}

	public Object getSource() {
		return super.getSource();
	}

	public Object getTarget() {
		return super.getTarget();
	}

	public Integer getWeight() {
		return this.edgeWeight;
	}

	public String getName() {
		return this.edgeName;
	}
	
	public boolean isWeighted() {
		return edgeWeight != null;
	}
	
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

}
