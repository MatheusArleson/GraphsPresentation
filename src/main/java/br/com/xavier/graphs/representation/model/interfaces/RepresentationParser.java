package br.com.xavier.graphs.representation.model.interfaces;

public abstract interface RepresentationParser {
	
	public void updateDelimiters(String representationStartDelimiter, String representationEndDelimiter, String representationRowSeparator, String representationRowElementsSeparator);
	public Object parse(String str);
	
}
