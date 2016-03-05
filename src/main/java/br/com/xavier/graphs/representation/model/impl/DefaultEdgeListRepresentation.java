package br.com.xavier.graphs.representation.model.impl;

import br.com.xavier.graphs.representation.model.abstractions.AbstractEdgesListRepresentation;

public class DefaultEdgeListRepresentation extends AbstractEdgesListRepresentation{

	private static final long serialVersionUID = 912672935404949862L;

	public DefaultEdgeListRepresentation() {
		super(
			"", 
			"", 
			"",
			""
		);
	}

	@Override
	public Object doParse(String str) {
		return null;
	}

}
