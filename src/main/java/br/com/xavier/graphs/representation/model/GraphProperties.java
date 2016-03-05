package br.com.xavier.graphs.representation.model;

import java.io.Serializable;

public class GraphProperties implements Serializable {
	
	private static final long serialVersionUID = 5676572575395775527L;
	
	//XXX CLASS PROPERTIES
	private boolean directedGraph;
	private boolean weightedGraph;
	private boolean loopsAllowed;
	private boolean multipleEdgesAllowed;
	
	//XXX CONSTRUCTOR
	public GraphProperties(boolean directedGraph, boolean weightedGraph, boolean loopsAllowed, boolean multipleEdgesAllowed) {
		super();
		this.directedGraph = directedGraph;
		this.weightedGraph = weightedGraph;
		this.loopsAllowed = loopsAllowed;
		this.multipleEdgesAllowed = multipleEdgesAllowed;
	}
	
	//XXX OVERRIDE METHODS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (directedGraph ? 1231 : 1237);
		result = prime * result + (loopsAllowed ? 1231 : 1237);
		result = prime * result + (multipleEdgesAllowed ? 1231 : 1237);
		result = prime * result + (weightedGraph ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()){ return false; }
		
		GraphProperties other = (GraphProperties) obj;
		
		if (directedGraph != other.directedGraph) { return false; }
		if (loopsAllowed != other.loopsAllowed) { return false; }
		if (multipleEdgesAllowed != other.multipleEdgesAllowed) { return false; }
		if (weightedGraph != other.weightedGraph) { return false; }
		
		return true;
	}
	
	@Override
	public String toString() {
		return "GraphProperties [" 
			+ "directedGraph=" + directedGraph 
			+ ", weightedGraph=" + weightedGraph
			+ ", loopsAllowed=" + loopsAllowed 
			+ ", multipleEdgesAllowed=" + multipleEdgesAllowed 
		+ "]";
	}

	//XXX GETTERS/SETTERS
	public boolean isDirectedGraph() {
		return directedGraph;
	}
	
	public void setDirectedGraph(boolean directedGraph) {
		this.directedGraph = directedGraph;
	}

	public boolean isWeightedGraph() {
		return weightedGraph;
	}

	public void setWeightedGraph(boolean weightedGraph) {
		this.weightedGraph = weightedGraph;
	}

	public boolean isLoopsAllowed() {
		return loopsAllowed;
	}

	public void setLoopsAllowed(boolean loopsAllowed) {
		this.loopsAllowed = loopsAllowed;
	}

	public boolean isMultipleEdgesAllowed() {
		return multipleEdgesAllowed;
	}

	public void setMultipleEdgesAllowed(boolean multipleEdgesAllowed) {
		this.multipleEdgesAllowed = multipleEdgesAllowed;
	}
	
	
	
}
