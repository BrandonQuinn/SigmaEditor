/**
 * Author: Brandon
 * Date Created: 10 Oct. 2017
 * File : ImageWriteThread.java
 */
package elara.threading;

import java.util.concurrent.LinkedBlockingQueue;
import elara.editor.debug.LogType;
import elara.editor.debug.StaticLogs;

/**
 * ImageWriteThread
 *
 * Description: Allows us to send write operations to this
 * thread so that they'll essentially the source image will be painted on
 * to the destination image  in the background as fast as possible as to not 
 * halt the execution of the original thread.
 */
public class ImageWriteThread extends Thread
{
	private LinkedBlockingQueue<ImageWriteEvent> writeQueue 
		= new LinkedBlockingQueue<ImageWriteEvent>();
	
	public ImageWriteThread(String name)
	{
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		while(true) {
			try {
				ImageWriteEvent iwe = writeQueue.take();
				iwe.doWrite();
			} catch (InterruptedException e) {
				StaticLogs.debug.log(LogType.CRITICAL, "ImageWriteThread: " + getName() + " was interrupted.");
				break;
			}
		}
	}
	
	/**
	 * Add a write event, returns false if there's not enough space.
	 * @param iwe
	 * @return
	 */
	public synchronized void addWriteEvent(ImageWriteEvent iwe)
	{
		try {
			writeQueue.put(iwe);
		} catch (InterruptedException e) {
			// TODO writeQueue.put runs out of memory
			e.printStackTrace();
		}
	}
}
