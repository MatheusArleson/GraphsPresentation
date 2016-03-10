package br.com.xavier.graphs.representation.model;

public class Delimiters {

	private String rowDelimiter;
	private String rowElementsDelimiter;
	private String representsEmpty;

	public Delimiters(String rowDelimiter, String rowElementsDelimiter, String representsEmpty) {
		super();
		this.rowDelimiter = rowDelimiter;
		this.rowElementsDelimiter = rowElementsDelimiter;
		this.representsEmpty = representsEmpty;
	}

	public String getRowDelimiter() {
		return rowDelimiter;
	}

	public void setRowDelimiter(String rowDelimiter) {
		this.rowDelimiter = rowDelimiter;
	}

	public String getRowElementsDelimiter() {
		return rowElementsDelimiter;
	}

	public void setRowElementsDelimiter(String rowElementsDelimiter) {
		this.rowElementsDelimiter = rowElementsDelimiter;
	}
	
	public String getRepresentsEmpty() {
		return representsEmpty;
	}
	
	public void setRepresentsEmpty(String representsEmpty) {
		this.representsEmpty = representsEmpty;
	}
}
