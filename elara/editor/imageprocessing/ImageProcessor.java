/**
 * Author: Brandon
 * Date Created: 8 Oct. 2017
 * File : ImageFilter.java
 */
package elara.editor.imageprocessing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import elara.editor.util.MathUtil;

/**
 * ImageFilter
 *
 * Description: Modify images, combine images etc.
 * Just a bunch of function to do those things.
 */
public class ImageProcessor
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
		
		int maxDist = sourceRaster.getWidth() >> 1;
		int xCenter = sourceRaster.getWidth() >> 1;
		int yCenter = sourceRaster.getHeight() >> 1;
		
		int[] sourceRGBA = new int[4];
		int[] RGBA = new int[4];
		int newAlpha = 0;
		for (int x = 0; x < sourceRaster.getWidth(); x++) {
			for (int y = 0; y < sourceRaster.getHeight(); y++) {
				newAlpha = (int) Math.sqrt(((x - xCenter) * (x - xCenter)) + ((y - yCenter) * (y - yCenter)));
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
	 * Multiplies their pixel values.
	 * 
	 * @param dest
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
	 * @param height 
	 * @param width 
	 * @param fromY 
	 * @param fromX 
	 * @param toY 
	 * @param toX 
	 * 
	 * @param newImage
	 * @param srcScreen
	 * @return
	 */
	public static BufferedImage overlay(BufferedImage dest, int toX, int toY, 
			BufferedImage src, int fromX, int fromY, 
			int width, int height)
	{
		WritableRaster destRaster = dest.getRaster();
		WritableRaster srcRaster = src.getRaster();
		
		int[] RGBA = new int[4];
		int[] srcTmp = new int[4];
		int[] destTmp = new int[4];
		for (int destX = toX, srcX = fromX, i = 0; i < width; destX++, srcX++, i++) {
			for (int destY = toY, srcY = fromY, t = 0; t < height; destY++, srcY++, t++) {
				try {
					srcRaster.getPixel(srcX, srcY, srcTmp);
					destRaster.getPixel(destX, destY, destTmp);
					
					if (destTmp[0] < (127.5f)) {
						RGBA[0] = (int)(2 * (destTmp[0] / 255.0f) * (srcTmp[0] / 255.0f) * 255);
					} else {
						RGBA[0] = (int)((1 - 2 * (1 - (destTmp[0] / 255.0f)) * (1 - (srcTmp[0] / 255.0f))) * 255);
					}
					
					if (destTmp[1] < (127.5f)) {
						RGBA[1] = (int)(2 * (destTmp[1] / 255.0f) * (srcTmp[1] / 255.0f) * 255);
					} else {
						RGBA[1] = (int)((1 - 2 * (1 - (destTmp[1] / 255.0f)) * (1 - (srcTmp[1] / 255.0f))) * 255);
					}
					
					if (destTmp[2] < (127.5f)) {
						RGBA[2] = (int)(2 * (destTmp[2] / 255.0f) * (srcTmp[2] / 255.0f) * 255);
					} else {
						RGBA[2] = (int)((1 - 2 * (1 - (destTmp[2] / 255.0f)) * (1 - (srcTmp[2] / 255.0f))) * 255);
					}
					
					RGBA[3] = destTmp[3];
					
					destRaster.setPixel(destX, destY, RGBA);
				} catch (ArrayIndexOutOfBoundsException e) {
					continue; // just don't write on that pixel and continue
				}
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
				try {
					RGBA = raster.getPixel(x, y, RGBA);
					raster.setPixel(x, y, new int[] {
							RGBA[0],
							RGBA[1],
							RGBA[2],
							MathUtil.clamp((int) (((RGBA[3] / 255.0) * opacity) * 255.0), 0, 255)
					});
				} catch (ArrayIndexOutOfBoundsException e) {
					continue; // just don't write on that pixel and continue
				}
			}
		}
		
		return src;
	}

	/**
	 * Simply replaces the destination pixels with the source pixels.
	 * 
	 * @param dest
	 * @param toX
	 * @param toY
	 * @param src
	 * @param fromX
	 * @param fromY
	 * @param width
	 * @param height
	 */
	public static void overlap(BufferedImage dest, int toX, int toY,
			BufferedImage src, int fromX, int fromY,
			int width, int height)
	{
		Graphics2D destG = dest.createGraphics();
		destG.drawImage(src.getSubimage(fromX, fromY, width, height), toX, toY, 
				width, height, null);
	}
}
