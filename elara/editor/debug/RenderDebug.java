/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 30 Nov. 2017
 * File : RenderDebug.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.editor.debug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*****************************************************************
 *
 * RenderDebug
 *
 * Description: A debugging tool, similar to the standard Debug
 * class, however, it allows for synchronized access and does 
 * not engage in any file IO. Logs are stored in memory and'
 * are generally only used for debug message that need to be
 * immediately displayed to the user.
 * 
 * It stores debug messages in a hash table and does not store duplicates.
 * 
 * To calculate the position of the message in the hash table
 * a fast hashing algorithm will be used and the first few bits
 * will be used as the location and the hash will be used to check
 * if we have a new message or an existing one.
 * 
 * NOTE(brandon) Hash table borken, no linking on collisions
 *****************************************************************/

public class RenderDebug
{
	private static final int TABLE_LENGTH = 0b00000000000000000000011111111111; // 2047
	private volatile static Log[] hashTable = new Log[TABLE_LENGTH];
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

	public synchronized static void error(String message)
	{
		Date date = new Date();	
		String dateStr = dateFormat.format(date);
		Log log = new Log(LogType.ERROR, dateStr, message);
		hashTable[hash(message)] = log;
	}
	
	public synchronized static void warning(String message)
	{
		Date date = new Date();	
		String dateStr = dateFormat.format(date);
		Log log = new Log(LogType.WARNING, dateStr, message);
		hashTable[hash(message)] = log;
	}
	
	public synchronized static void info(String message)
	{
		Date date = new Date();	
		String dateStr = dateFormat.format(date);
		Log log = new Log(LogType.INFO, dateStr, message);
		hashTable[hash(message)] = log;
	}
	
	/**
	 * Return the list of all logs.
	 * @return
	 */
	public synchronized static ArrayList<Log> logs()
	{
		ArrayList<Log> logs = new ArrayList<Log>();
		
		// convert to normal sequential list
		for (int i = 0; i < TABLE_LENGTH; i++) {
			if (hashTable[i] != null) {
				logs.add(hashTable[i]);
			}
		}
		return logs;
	}
	
	/**
	 * Call this method at the bottom of the render loop.
	 */
	public synchronized static void clear()
	{
		for (int i = 0; i < TABLE_LENGTH; i++) {
			hashTable[i] = null;
		}
	}
	
	private static int hash(String message)
	{
		// leaves the 11 bits at the front, moving them to the right, 2^11 is 2048
		return message.hashCode() & TABLE_LENGTH;
	}
}
