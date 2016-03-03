package br.com.xavier.graphs.view;

public enum GraphRepresentation {
	
	//XXX ENUM MEMBERS
	EDGES_LIST("Edges List"),
	ADJACENCY_MATRIX_LIST("Adjacency Matrix List");
	
	private final String label;
	
	private GraphRepresentation(String label) {
		this.label = label;
	}

	//XXX GETTERS
	public String getLabel() {
		return label;
	}
}
