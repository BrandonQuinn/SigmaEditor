/**
 * Author: Brandon
 * Date Created: 1 Oct. 2017
 * File : Logs.java
 */

package elara.editor.debug;

import java.io.IOException;

/**
 * Logs
 *
 * Description: Direct access to the log files
 */
public class Debug
{
	public static QLogger debug;

	static {
		try {
			debug = new QLogger("logs", "debug.log");
		} catch (IOException e) {
			System.err.println("** COULD NOT CREATE DEBUG LOG **");
			e.printStackTrace();
		}
	}
	
	public static void debug(String msg) 
	{
		debug.log(LogType.DEBUG, msg);
	}
	
	public static void info(String msg) 
	{
		debug.log(LogType.INFO, msg);
	}
	
	public static void warning(String msg) 
	{
		debug.log(LogType.WARNING, msg);
	}
	
	public static void error(String msg) 
	{
		debug.log(LogType.ERROR, msg);
	}
	
	public static void critical(String msg) 
	{
		debug.log(LogType.CRITICAL, msg);
	}
}
