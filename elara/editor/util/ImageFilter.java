/**
 * Author: Brandon
 * Date Created: 8 Oct. 2017
 * File : ImageFilter.java
 */
package elara.editor.util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * ImageFilter
 *
 * Description: Modify images, combine images etc.
 * Just a bunch of function to do those things.
 */
public class ImageFilter
{
	
	/**
	 * Modifies the image to have a alpha gradient fall off,
	 * where the distance from the center to the edge is the
	 * change is transparency. Farther distance from the center = higher transparency
	 * @param image
	 * @return
	 */
	public static BufferedImage radialAlphaFalloff(BufferedImage image) {
		WritableRaster sourceRaster = image.getRaster();
		
		int maxDist = sourceRaster.getWidth() / 2;
		int xCenter = sourceRaster.getWidth() / 2;
		int yCenter = sourceRaster.getHeight() / 2;
		
		int[] sourceRGBA = new int[4];
		int[] RGBA = new int[4];
		int newAlpha = 0;
		for (int x = 0; x < sourceRaster.getWidth(); x++) {
			for (int y = 0; y < sourceRaster.getHeight(); y++) {
				newAlpha = (int) Math.sqrt(Math.pow(x - xCenter, 2) + Math.pow(y - yCenter, 2));
				newAlpha = 255 - ((int)((newAlpha / (float)maxDist) * 255));
				
				sourceRGBA = sourceRaster.getPixel(x, y, sourceRGBA);
				RGBA[0] = sourceRGBA[0];
				RGBA[1] = sourceRGBA[1];
				RGBA[2] = sourceRGBA[2];
				RGBA[3] = Math.max(0, Math.min(255, newAlpha));
				
				sourceRaster.setPixel(x, y, RGBA);
			}
		}
		
		return image;
	}
}
