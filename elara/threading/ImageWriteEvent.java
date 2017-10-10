/**
 * Author: Brandon
 * Date Created: 10 Oct. 2017
 * File : ImageWriteEvent.java
 */
package elara.threading;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * ImageWriteEvent
 *
 * Description: A write event where an image
 * is drawn on to another image.
 */
public class ImageWriteEvent
{
	private BufferedImage destination;
	private int xDestination, yDestination;
	private BufferedImage source;
	
	public ImageWriteEvent(BufferedImage dest, int xDest, int yDest, 
			BufferedImage src)
	{
		this.destination = dest;
		this.xDestination = xDest;
		this.yDestination = yDest;
		this.source = src;
	}
	
	/**
	 * Write the image.
	 */
	public void doWrite()
	{
		Graphics2D g2d = destination.createGraphics();
		g2d.drawImage(source, xDestination, yDestination, null);
	}
}
