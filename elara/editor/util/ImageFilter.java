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
				RGBA[3] = MathUtil.clamp(newAlpha, 0, 255);
				
				sourceRaster.setPixel(x, y, RGBA);
			}
		}
		
		return image;
	}

	/**
	 * Multiplies each pixel channel together.
	 * 
	 * @param newImage
	 * @param src
	 * @return
	 */
	public static BufferedImage multiply(BufferedImage dest, BufferedImage src)
	{
		WritableRaster destRaster = dest.getRaster();
		WritableRaster srcRaster = src.getRaster();
		
		int[] srcTmp = new int[4];
		int[] destTmp = new int[4];
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				srcRaster.getPixel(x, y, srcTmp);
				destRaster.getPixel(x, y, destTmp);
				destRaster.setPixel(x, y, new int[] {
						MathUtil.clamp((int)(((srcTmp[0] / 255.0f) * (destTmp[0] / 255.0f)) * 255), 0, 255),
						MathUtil.clamp((int)(((srcTmp[1] / 255.0f) * (destTmp[1] / 255.0f)) * 255), 0, 255),
						MathUtil.clamp((int)(((srcTmp[2] / 255.0f) * (destTmp[2] / 255.0f)) * 255), 0, 255),
						destTmp[3]
				});
			}
		}
		
		return dest;
	}
	
	/**
	 * Overlay filter
	 * 
	 * @param newImage
	 * @param srcScreen
	 * @return
	 */
	public static BufferedImage overlay(BufferedImage dest, BufferedImage src)
	{
		WritableRaster destRaster = dest.getRaster();
		WritableRaster srcRaster = src.getRaster();
		
		int[] RGBA = new int[4];
		int[] srcTmp = new int[4];
		int[] destTmp = new int[4];
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				srcRaster.getPixel(x, y, srcTmp);
				destRaster.getPixel(x, y, destTmp);
				
				if (destTmp[0] < (255.0f / 2.0f)) {
					RGBA[0] = (int)(2 * (destTmp[0] / 255.0f) * (srcTmp[0] / 255.0f) * 255);
				} else {
					RGBA[0] = (int)((1 - 2 * (1 - (destTmp[0] / 255.0f)) * (1 - (srcTmp[0] / 255.0f))) * 255);
				}
				
				if (destTmp[1] < (255.0f / 2.0f)) {
					RGBA[1] = (int)(2 * (destTmp[1] / 255.0f) * (srcTmp[1] / 255.0f) * 255);
				} else {
					RGBA[1] = (int)((1 - 2 * (1 - (destTmp[1] / 255.0f)) * (1 - (srcTmp[1] / 255.0f))) * 255);
				}
				
				if (destTmp[2] < (255.0f / 2.0f)) {
					RGBA[2] = (int)(2 * (destTmp[2] / 255.0f) * (srcTmp[2] / 255.0f) * 255);
				} else {
					RGBA[2] = (int)((1 - 2 * (1 - (destTmp[2] / 255.0f)) * (1 - (srcTmp[2] / 255.0f))) * 255);
				}
				
				RGBA[3] = destTmp[3];
				
				destRaster.setPixel(x, y, RGBA);
			}
		}
		
		return dest;
	}

	/**
	 * Change the opacity of every pixel on the given image.
	 * 
	 * FIXME Opacity has weird effect at low factors like 0.02
	 * 
	 * @param newImage
	 * @param textureBrushOpacity
	 * @return
	 */
	public static BufferedImage setOpacity(BufferedImage src, float opacity)
	{
		WritableRaster raster = src.getRaster();
		
		int[] RGBA = new int[4];
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				RGBA = raster.getPixel(x, y, RGBA);
				raster.setPixel(x, y, new int[] {
						RGBA[0],
						RGBA[1],
						RGBA[2],
						(int) (((RGBA[3] / 255.0) * opacity) * 255.0)
				});
			}
		}
		
		return src;
	}
}
