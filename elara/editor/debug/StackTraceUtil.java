
package elara.editor.debug;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class StackTraceUtil
{

	/**
	 * Return an exception to a stack trace string for printability in
	 * UI element.
	 * 
	 * @param e
	 *            The exception thrown
	 * @return The stack trace
	 */
	public static String stackTraceToString(Exception e)
	{
		StringWriter stackTraceWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stackTraceWriter));
		return e.toString() + "\n" + stackTraceWriter.toString();
	}
}
