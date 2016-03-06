package br.com.xavier.graphs.representation.util.checkers;

import br.com.xavier.graphs.representation.util.messages.MessageManager;
import br.com.xavier.graphs.representation.util.messages.enums.DefaultMessagesKey;

public final class NullChecker {
	
	//XXX CONSTRUCTOR
	//defeat instantiation
	public NullChecker() {}
	
	public static void checkNullParameter(Object...objects){
		if(objects == null){
			handleNullParameter();
		}
		
		for (Object object : objects) {
			if(object == null){
				handleNullParameter();
			}
		}
	}

	private static void handleNullParameter() {
		throw new NullPointerException(MessageManager.getDefaultMessage(DefaultMessagesKey.PARAMETER_NULL));
	}
	
}
