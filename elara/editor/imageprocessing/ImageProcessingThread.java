/**
 * Author: Brandon
 * Date Created: 10 Oct. 2017
 * File : ImageWriteThread.java
 */
package elara.editor.imageprocessing;

import java.util.concurrent.LinkedBlockingQueue;
import elara.editor.debug.LogType;
import elara.editor.debug.StaticLogs;

/**
 * ImageProcessingThread
 *
 * Description: Allows us to run any kind of image
 * processing algorithm on either a single image or
 * from a source to a destination image in the background
 * as to not halt execution due to the expensive nature
 * of image processing algrothims.
 */
public class ImageProcessingThread extends Thread
{
	private volatile LinkedBlockingQueue<ImageProcessingEvent> processingQueue 
		= new LinkedBlockingQueue<ImageProcessingEvent>();
	
	public ImageProcessingThread(String name)
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
				ImageProcessingEvent iwe = processingQueue.take();
				iwe.processImages();
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
	public synchronized void addProcessingEvent(ImageProcessingEvent iwe)
	{
		try {
			processingQueue.put(iwe);
		} catch (InterruptedException e) {
			// TODO writeQueue.put runs out of memory
			e.printStackTrace();
		}
	}
}
