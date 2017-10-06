
package elara.editor.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 * A logging backend for thread safety.
 * 
 * A new logger is created on a per-file basis.
 * 
 * Contains caching of logs that were blocked by a
 * thread already entering a log. i.e. the file is currently
 * being written to and can not be access by another thread.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 20 Jun 2017
 */

public class QLogger
{
	private Semaphore semaphore;

	private String logDirectory;
	private String logFilename;
	private File logFile;

	private PrintWriter writer;

	/**
	 * This is where logs that could not be made due to the file already being
	 * written to
	 * will be places, once the writeLog function is done writing it will go back
	 * through this list and
	 * write them all in.
	 */
	private volatile ConcurrentLinkedQueue<Log> logQueue;

	public QLogger(String logDirectory, String logFilename) throws IOException
	{
		this.logDirectory = logDirectory;
		this.logFilename = logFilename;

		logFile = new File(this.logDirectory + "/" + this.logFilename);
		semaphore = new Semaphore(1);
		logQueue = new ConcurrentLinkedQueue<Log>();

		// check files, create if they don't exist

		File dir = new File(this.logDirectory);

		if (!dir.exists())
			dir.mkdirs();

		if (!logFile.exists()) {
			logFile.createNewFile();
		}
	}

	/**
	 * Public interface for creating a new log. Simple call and it will log,
	 * but not necessarily instantly.
	 * 
	 * @param type
	 * @param message
	 * @throws FileNotFoundException
	 */
	public synchronized void log(LogType type, String message)
	{
		// retrive the current time

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		String dateStr = dateFormat.format(date);

		// check if we are free to write, otherwise send the log in to the queue

		if (semaphore.tryAcquire()) {
			try {
				writeLog(type, message, dateStr);
			} catch (FileNotFoundException e) {
				System.err.println("FAILED LOGGING, FileNotFoundException");
			}
			semaphore.release();
		} else {
			Log cacheLog = new Log(type, message, dateStr);
			logQueue.add(cacheLog);
		}

	}

	/**
	 * Write the log to the file, and while it's there it will write the logs from
	 * the queue.
	 * 
	 * @param type
	 * @param message
	 * @param dateStr
	 * @throws FileNotFoundException
	 */
	private synchronized void writeLog(LogType type, String message, String dateStr)
			throws FileNotFoundException
	{
		writer = new PrintWriter(new FileOutputStream(logFile, true));

		writer.println("[" + dateStr + "] [" + type.toString() + "] " + message);

		// clear the queue
		Log tmpLog;
		while (!logQueue.isEmpty()) {
			tmpLog = logQueue.poll();
			writer.println("[" + tmpLog.getDatetime() + "] [" + tmpLog.getType().toString() + "] "
					+ tmpLog.getMessage());
		}

		writer.close();
	}
}
