/**
 * TODO
 * equals() impl.
 * hashCode() impl.
 */

package controller;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;

public class GKAEdge extends DefaultEdge {
	
	private String name;
	private Double weight;

	private static final long serialVersionUID = 1L;

	public GKAEdge(String name, Double weight) {
		super();
		this.name = name;
		this.weight = weight;
	}
	
	public GKAEdge(String name) {
		super();
		this.name = name;
		this.weight = null;
	}

	public GKAEdge(Object arg0) {
		super(arg0);
	}

	public GKAEdge(Object arg0, AttributeMap arg1) {
		super(arg0, arg1);
	}
	
	public Object getSource() {
		return super.getSource();
	}
	
	public Object getTarget() {
		return super.getTarget();
	}
	
	public String getName() {
		return name;
	}

	public Double getWeight() {
		return weight;
	}

	public String toString() {
		return super.toString() + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		GKAEdge other = (GKAEdge) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	
	
}
