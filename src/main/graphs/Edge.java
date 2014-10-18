package main.graphs;


public class Edge {

	private String name;
	private int edgeWeight;
	
	
	
	//Getter, Setter
	public String name(){
		return this.name;
	}
	
	public void name(String name){
		this.name = name;
	}
	
	public int edgeWeight(){
		return this.edgeWeight;
	}
	
	public void edgeWeight(int edgeWeight){
		this.edgeWeight = edgeWeight;
	}
	

	
	
	private Edge(String name){
		this.name = name;
	}
	
	public static Edge valueOf(String name){
		return new Edge(name);
	}
	
}
