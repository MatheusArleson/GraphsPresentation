package br.com.xavier.graphs.representation.model.abstractions;

import br.com.xavier.matrix.abstraction.AbstractSquareMatrix;
import br.com.xavier.matrix.abstraction.parser.AbstractSquareMatrixParser;

public abstract class AbstractAdjacencyMatrixRepresentation extends AbstractRepresentation {
	
	private static final long serialVersionUID = -8101639426300761457L;
	
	//XXX PARSER PROPERTY
	private AbstractSquareMatrixParser<Integer> matrixParser;
	
	//XXX CONSTRUCTOR
	public AbstractAdjacencyMatrixRepresentation(
		String representationStartDelimiter, 
		String representationEndDelimiter,
		String representationRowSeparator, 
		String representationRowElementsSeparator, 
		AbstractSquareMatrixParser<Integer> matrixParser
	) {
		super(
			representationStartDelimiter, 
			representationEndDelimiter, 
			representationRowSeparator,
			representationRowElementsSeparator
		);
		
		this.matrixParser = matrixParser;
	}
	
	//XXX OVERRIDE METHODS
	
	@Override
	public AbstractSquareMatrix<Integer> parse(String str){
		updateMatrixParserDelimiters();
		return doParse(str);
	}
	
	private void updateMatrixParserDelimiters() {
		matrixParser.setMatrixRepresentatitionMatrixStartDelimiter(getRepresentationStartDelimiter());
		matrixParser.setMatrixRepresentatitionMatrixEndDelimiter(getRepresentationEndDelimiter());
		matrixParser.setMatrixRepresentationMatrixRowSeparator(getRepresentationRowSeparator());
		matrixParser.setMatrixRepresentationMatrixRowElementsSeparator(getRepresentationRowElementsSeparator());
	}
	
	//XXX ABSTRACT METHODS
	public abstract AbstractSquareMatrix<Integer> doParse(String str);

	//XXX GETTERS/SETTERS
	public AbstractSquareMatrixParser<Integer> getMatrixParser() {
		return matrixParser;
	}
	
	public void setMatrixParser(AbstractSquareMatrixParser<Integer> matrixParser) {
		this.matrixParser = matrixParser;
	}
	
}
