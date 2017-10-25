
package elara.editor.debug;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class ElaraException extends Exception
{
	private static final long serialVersionUID = 1L;

	private String message = "No message set.";
	private String stackTraceMessage = "No stack trace output set.";
	
	public ElaraException(String message)
	{
		super(message);
		this.message = message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String message()
	{
		return message;
	}

	public void setStackTraceMessage(String stackTrace)
	{
		this.stackTraceMessage = stackTrace;
	}

	public String stackTraceMessage()
	{
		return stackTraceMessage;
	}
}
