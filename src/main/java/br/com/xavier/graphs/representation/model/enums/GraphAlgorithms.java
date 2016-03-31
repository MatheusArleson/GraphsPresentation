package br.com.xavier.graphs.representation.model.enums;

public enum GraphAlgorithms {
	
	BFS("BFS"),
	DFS("DFS"),
	DIJKSTRA("DIJ");
	
	private final String label;
	
	private GraphAlgorithms(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
