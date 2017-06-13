package sigma.editor.debug;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class SigmaException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message = "No message set.";
	private String stackTraceMessage = "No stack trace output set.";
	
	public SigmaException() {
		
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setStackTraceMessage(String stackTrace) {
		this.stackTraceMessage = stackTrace;
	}
	
	public String getStackTraceMessage() {
		return stackTraceMessage;
	}
}