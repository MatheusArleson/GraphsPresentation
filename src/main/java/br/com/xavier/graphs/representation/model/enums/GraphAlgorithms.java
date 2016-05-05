package br.com.xavier.graphs.representation.model.enums;

public enum GraphAlgorithms {
	
	BFS("BFS", true, false, false),
	DFS("DFS", true, false, false),
	DIJKSTRA("DIJ", true, true, true),
	KRUSKAL("KRU", false, false, true);
	
	private final String label;
	private final boolean needSourceNode;
	private final boolean needTargetNode;
	private final boolean onlyWeighted;
	
	private GraphAlgorithms(String label, boolean needSourceNode, boolean needTargetNode, boolean onlyWeighted) {
		this.label = label;
		this.needSourceNode = needSourceNode;
		this.needTargetNode = needTargetNode;
		this.onlyWeighted = onlyWeighted;
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean isNeedSourceNode() {
		return needSourceNode;
	}
	
	public boolean isNeedTargetNode() {
		return needTargetNode;
	}
	
	public boolean isOnlyWeighted() {
		return onlyWeighted;
	}
	
}
