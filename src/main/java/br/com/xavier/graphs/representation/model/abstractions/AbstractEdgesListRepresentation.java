package br.com.xavier.graphs.representation.model.abstractions;

public abstract class AbstractEdgesListRepresentation extends AbstractRepresentation {
	
	private static final long serialVersionUID = 3219910211591455473L;

	public AbstractEdgesListRepresentation(
		String representationStartDelimiter, 
		String representationEndDelimiter,
		String representationRowSeparator, 
		String representationRowElementsSeparator
	) {
		super(
			representationStartDelimiter, 
			representationEndDelimiter, 
			representationRowSeparator,
			representationRowElementsSeparator
		);
	}
	
	//private AbstractEdgeListParser edgeListParser;

	//XXX OVERRIDE METHODS
	@Override
	public Object parse(String str){
		updateMatrixParserDelimiters();
		return doParse(str);
	}

	private void updateMatrixParserDelimiters() {
//		edgeListParser.setMatrixRepresentatitionMatrixStartDelimiter(getRepresentationStartDelimiter());
//		edgeListParser.setMatrixRepresentatitionMatrixEndDelimiter(getRepresentationEndDelimiter());
//		edgeListParser.setMatrixRepresentationMatrixRowSeparator(getRepresentationRowSeparator());
//		edgeListParser.setMatrixRepresentationMatrixRowElementsSeparator(getRepresentationRowElementsSeparator());
	}
	
	//XXX ABSTRACT METHODS
	public abstract Object doParse(String str);
	
	
}
