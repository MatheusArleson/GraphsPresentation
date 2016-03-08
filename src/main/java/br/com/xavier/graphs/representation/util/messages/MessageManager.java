package br.com.xavier.graphs.representation.util.messages;

import java.util.ResourceBundle;

import br.com.xavier.graphs.representation.util.messages.enums.DefaultMessagesKey;

/**
 * Util class for message externalization.
 * 
 * @author Matheus Xavier
 *
 */
public final class MessageManager {
	
	private static final String MESSAGES_FILE_NAME = "graphs_presentation_messages";
	private static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES_FILE_NAME);
	
	//XXX METHODS
	
	/**
	 * Get a message from the messages file. </br>
	 * The locale is the one returned by {@code java.util.Locale.getDefault()}
	 * 
	 * @param messageKey
	 * @return
	 */
	public static String getMessage(String messageKey){
		if(messageKey == null || messageKey.isEmpty()){
			return getDefaultMessage(DefaultMessagesKey.PARAMETER_NULL);
		}
		
		String message = MESSAGE_BUNDLE.getString(messageKey);
		if(message == null || message.isEmpty()){
			return getDefaultMessage(DefaultMessagesKey.MESSAGE_KEY_NOT_FOUND);
		} else {
			return message;
		}
	} 
	
	/**
	 * Get a default message from the messages file. </br>
	 * Default messages are general proporse messages that must be in the messages file.
	 * 
	 * @param messageKey {@link DefaultMessagesKey} - a key
	 * @return {@link String} - the message
	 */
	public static String getDefaultMessage(DefaultMessagesKey messageKey){
		if(messageKey == null){
			messageKey = DefaultMessagesKey.PARAMETER_NULL;
		}
		
		return MESSAGE_BUNDLE.getString(messageKey.getKey());
	}
}
