package main.graphs;

public enum GraphType {
	UNDIRECTED_UNWEIGHTED(false, false),
	DIRECTED_UNWEIGHTED(true, false),
	UNDIRECTED_WEIGHTED(false, true),
	DIRECTED_WEIGHTED(true, true);
	
	private boolean isDirected = false;
	private boolean isWeighted = false;

    private GraphType(boolean isDirected, boolean isWeighted) {
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
    }
    
    public boolean isDirected() { return isDirected; }
    public boolean isWeighted() { return isWeighted; }
}
