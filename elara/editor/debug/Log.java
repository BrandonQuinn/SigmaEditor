
package elara.editor.debug;

/**
 * The data model that represents a log.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 20 Jun 2017
 */

public class Log
{
	private LogType type;
	private String datetime;
	private String message;

	public Log(LogType type, String datetime, String message)
	{
		this.type = type;
		this.datetime = datetime;
		this.message = message;
	}

	public LogType getType()
	{
		return type;
	}

	public void setType(LogType type)
	{
		this.type = type;
	}

	public String getDatetime()
	{
		return datetime;
	}

	public void setDatetime(String datetime)
	{
		this.datetime = datetime;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
