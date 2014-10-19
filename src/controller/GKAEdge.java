/**
 * TODO
 * equals() impl.
 * hashCode() impl.
 */

package controller;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;

public class GKAEdge extends DefaultEdge {
	
	private String name = null;
	private Double weight = null;

	private static final long serialVersionUID = 1L;

	public GKAEdge(String name, Double weight) {
		super();
		this.name = name;
		this.weight = weight;
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

//	public void setName(String name) {
//		this.name = name;
//	}

	public Double getWeight() {
		return weight;
	}

//	public void setWeight(Double weight) {
//		this.weight = weight;
//	}
	
	public String toString() {
		return super.toString() + name;
	}
	
}
