/**
 * Author: Brandon
 * Date Created: 11 Oct. 2017
 * File : ImageBlendEvent.java
 */
package elara.editor.imageprocessing;

import java.awt.image.BufferedImage;

/**
 * ImageBlendEvent
 *
 * Description: Run a blending mode algorithm on two images.
 * Wikipedia has a great article on blend modes: https://en.wikipedia.org/wiki/Blend_modes
 */
public class ImageBlendEvent implements ImageProcessingEvent
{
	private BlendMode mode;
	
	private BufferedImage dest;
	private int toX;
	private int toY;
	
	private BufferedImage src;
	private int fromX;
	private int fromY;
	
	private int width;
	private int height;
	
	/**
	 * Send and image event to blend the src and destination together.
	 * If blending is occurring on sub-image with the given images, then
	 * both of those sub images are assumed to be the same width and height.
	 * You cannot bend pixels with nothing.
	 * 
	 * @param destination
	 * @param toX The x position to start blending at
	 * @param toY The y postiion to start blending at
	 * @param source The source image, it will be unchanged
	 * @param fromX where to start getting pixels from the source's x-axis
	 * @param fromY where to start getting pixels from the source's y-axis
	 * @param width the number of pixels in width being blended horizontally
	 * @param height the number of pixels in height being blended vertically
	 * @param mode the blending mode to use
	 */
	public ImageBlendEvent(BufferedImage destination, int toX, int toY,
			BufferedImage source, int fromX, int fromY, 
			int width, int height,
			BlendMode mode)
	{
		this.dest = destination;
		this.toX = toX;
		this.toY = toY;
		this.src = source;
		this.fromX = fromX;
		this.fromY = fromY;
		this.width = width;
		this.height = height;
		this.mode = mode;
	}
	
	/**
	 * @see elara.editor.imageprocessing.ImageProcessingEvent#processImages()
	 */
	@Override
	public void processImages()
	{
		switch (mode) {
			case OVERLAP:
				ImageProcessor.overlap(dest, toX, toY,
						src, fromX, fromY,
						width, height);
			break;
		
			case MULTIPLY:
				// ImageProcessor.multiply(dest, src);
			break;

			case OVERLAY:
				ImageProcessor.overlay(dest, toX, toY,
						src, fromX, fromY,
						width, height);
			break;
			
			case SCREEN:
			break;
			
			default:
			break;
		}	
	}
}
