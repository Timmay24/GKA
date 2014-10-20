package main.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;




public class Vertex{

	private String name;
	private int nodeWeight;
	private String color;
	
	
	
	//Getter, Setter
	public String name(){
		return this.name;
	}
	
	public void name(String name){
		this.name = name;
	}
	
	public int nodeWeight(){
		return this.nodeWeight;
	}
	
	public void nodeWeight(int nodeWeight){
		this.nodeWeight = nodeWeight;
	}
	
	public String color(){
		return this.color;
	}
	
	public void color(String color){
		this.color = color;
	}
	
	
	
	private Vertex(String name){
		this.name = name;
		this.color = "white";
	}
	
	public static Vertex valueOf(String name){
		return new Vertex(name);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nodeWeight;
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
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nodeWeight != other.nodeWeight)
			return false;
		return true;
	}

	
	
	
	
	
}
