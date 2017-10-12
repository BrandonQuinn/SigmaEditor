/**
 * Author: Brandon
 * Date Created: 11 Oct. 2017
 * File : ImageFilterEvent.java
 */
package elara.editor.imageprocessing;

import java.awt.image.BufferedImage;

/**
 * ImageFilterEvent
 *
 * Description: Applies a filter to the given image.
 */
public class ImageFilterEvent implements ImageProcessingEvent
{
	private BufferedImage dest;
	// private int toX;
	// private int toY;
	
	// private int width;
	// private int height;

	private BrushFilter filter;
	
	public ImageFilterEvent(BufferedImage destination, 
			int x, int y, 
			int width, int height, 
			BrushFilter filter) 
	{
		this.dest = destination;
		// this.toX = x;
		// this.toY = y;
		// this.width = width;
		// this.height = height;
		
		this.filter = filter;
	}

	/* (non-Javadoc)
	 * @see elara.editor.imageprocessing.ImageProcessingEvent#processImages()
	 */
	@Override
	public void processImages()
	{
		switch (filter) {
			case NONE:
			break;
			
			case RADIAL_FALLOFF:
				ImageProcessor.radialAlphaFalloff(dest);
			break;
			
			default:
			break;
		}
	}
}
