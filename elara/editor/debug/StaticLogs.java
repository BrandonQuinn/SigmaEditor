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
public class StaticLogs
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
}
