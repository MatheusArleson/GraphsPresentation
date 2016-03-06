package br.com.xavier.graphs.representation.util.checkers;

import br.com.xavier.graphs.representation.util.messages.MessageManager;
import br.com.xavier.graphs.representation.util.messages.enums.DefaultMessagesKey;

public class GraphRepresentationChecker {
	
	//XXX CONSTRUCTOR
	//defeat instantiation
	public GraphRepresentationChecker() {}
	
	public static void handleUnkwonGraphRepresentation() {
		throw new NullPointerException(MessageManager.getDefaultMessage(DefaultMessagesKey.GRAPH_REPRESENTATION_UNKNOW));
	}
	
}
