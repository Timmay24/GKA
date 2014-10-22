package main.graphs;

public enum GraphType {
	DIRECTED_UNWEIGHTED(true, false),
	DIRECTED_WEIGHTED(true, true),
	UNDIRECTED_WEIGHTED(false, true),
	UNDIRECTED_UNWEIGHTED(false, false);
	
	
	private boolean isDirected = false;
	private boolean isWeighted = false;

    private GraphType(boolean isDirected, boolean isWeighted) {
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
    }
    
    public boolean isDirected() { return isDirected; }
    public boolean isWeighted() { return isWeighted; }
}
