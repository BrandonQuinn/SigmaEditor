
package sigma.editor.debug;

/**
 * LogType refers to the severity or information aspect on what the log means.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 20 Jun 2017
 */

public enum LogType
{
	INFO, // general information
	DEBUG, // debug information, generally temporary or conditionally logged
	WARNING, // a log for when something occurs that may have future consequences
	ERROR, // an immediate issue that disrupts program flow
	CRITICAL // confirmed that the program can not continue or has crashed completely
}
