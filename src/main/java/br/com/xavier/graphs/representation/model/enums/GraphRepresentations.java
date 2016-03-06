package br.com.xavier.graphs.representation.model.enums;

public enum GraphRepresentations {
	
	//XXX ENUM MEMBERS
	EDGES_LIST("Edges List"),
	ADJACENCY_MATRIX_LIST("Adjacency Matrix List");
	
	private final String label;
	
	private GraphRepresentations(String label) {
		this.label = label;
	}
	
	//XXX GETTERS
	public String getLabel() {
		return label;
	}
}
