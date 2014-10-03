package main.graphs;

public class Vertex {

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
	
	public Vertex valueOf(String name){
		return new Vertex(name);
	}
	
}
