package br.com.xavier.graphs.representation.util.messages.enums;

/**
 * Enum holding the keys for the default messages that must be in 
 * the messages file.
 * 
 * @author Matheus Xavier
 *
 */
public enum DefaultMessagesKey {
	
	//XXX ENUM MEMBERS
	MESSAGE_KEY_NOT_FOUND("message.notfound"),
	PARAMETER_NULL("parameter.null"),
	UNSUPPORTED_OPERATION_EXCEPTION("exception.operation.unsupported"),
	GRAPH_REPRESENTATION_UNKNOW("graph.representation.unknow"); 
	
	//XXX ENUM PROPERTIES
	private final String key;
	
	//XXX CONSTRUCTOR
	private DefaultMessagesKey(String key) {
		this.key = key;
	}
	
	//XXX GETTERS
	public String getKey() {
		return key;
	}

}
