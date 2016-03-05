package br.com.xavier.graphs.representation.model.impl;

import br.com.xavier.graphs.representation.model.abstractions.AbstractAdjacencyMatrixRepresentation;
import br.com.xavier.matrix.impl.DefaultSquareMatrix;
import br.com.xavier.matrix.impl.parser.DefaultMatrixParserValues;
import br.com.xavier.matrix.impl.parser.DefaultSquareMatrixParser;

public class DefaultAdjacencyMatrixRepresentation extends AbstractAdjacencyMatrixRepresentation {
	
	private static final long serialVersionUID = 5862552046226488842L;
	
	//XXX STATIC FINAL PROPERTIES
	private static final DefaultMatrixParserValues DEFAULT_MATRIX_PARSER_VALUES = new DefaultMatrixParserValues();
	
	public DefaultAdjacencyMatrixRepresentation() {
		super(
			DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentatitionMatrixStartDelimiter(),
			DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentatitionMatrixEndDelimiter(), 
			DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentationMatrixRowSeparator(),
			DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentationMatrixRowElementsSeparator(), 
			new DefaultSquareMatrixParser<Integer>(
				DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentationStartDelimiter(), 
				DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentationEndDelimiter(), 
				DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentatitionMatrixStartDelimiter(), 
				DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentatitionMatrixEndDelimiter(), 
				DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentationMatrixRowSeparator(),
				DEFAULT_MATRIX_PARSER_VALUES.getMatrixRepresentationMatrixRowElementsSeparator()
			)
		);
	}

	@Override
	public DefaultSquareMatrix<Integer> doParse(String str) {
		return (DefaultSquareMatrix<Integer>) getMatrixParser().fromMatrixString(str);
	}
		
}
