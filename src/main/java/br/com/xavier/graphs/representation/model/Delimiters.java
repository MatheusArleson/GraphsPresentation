package br.com.xavier.graphs.representation.model;

public class Delimiters {

	private String startDelimiter;
	private String endDelimiter;
	private String rowDelimiter;
	private String rowElementsDelimiter;

	public Delimiters(String startDelimiter, String endDelimiter, String rowDelimiter, String rowElementsDelimiter) {
		super();
		this.startDelimiter = startDelimiter;
		this.endDelimiter = endDelimiter;
		this.rowDelimiter = rowDelimiter;
		this.rowElementsDelimiter = rowElementsDelimiter;
	}

	public String getStartDelimiter() {
		return startDelimiter;
	}

	public void setStartDelimiter(String startDelimiter) {
		this.startDelimiter = startDelimiter;
	}

	public String getEndDelimiter() {
		return endDelimiter;
	}

	public void setEndDelimiter(String endDelimiter) {
		this.endDelimiter = endDelimiter;
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
}
