/**
 * Author: Brandon
 * Date Created: 8 Oct. 2017
 * File : ImageFilter.java
 */
package elara.editor.imageprocessing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import elara.editor.debug.LogType;
import elara.editor.debug.StaticLogs;
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
	 * Both images must be the same size.
	 * 
	 * @param dest
	 * @param src
	 * @return
	 */
	public static BufferedImage multiply(BufferedImage dest, BufferedImage src)
	{
		WritableRaster destRaster = dest.getRaster();
		WritableRaster srcRaster = src.getRaster();
		
		if (dest.getWidth() != src.getWidth() || dest.getHeight() != src.getHeight()) {
			StaticLogs.debug.log(LogType.ERROR, "Failed image multiply operation, "
					+ "images not the same size");
			return dest;
		}
		
		int[] srcTmp = new int[4];
		int[] destTmp = new int[4]; 
		for (int x = 0; x < dest.getWidth(); x++) {
			for (int y = 0; y < dest.getHeight(); y++) {
				// don't break the bounds of the source image
				if (x < 0 || x >= src.getWidth()) {
					continue;
				}
				
				if (y < 0 || y >= src.getHeight()) {
					continue;
				}
				
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
	 * Overlay filter.
	 * 
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
	public static BufferedImage overlay(BufferedImage dest, BufferedImage src)
	{
		WritableRaster destRaster = dest.getRaster();
		WritableRaster srcRaster = src.getRaster();
		
		if (dest.getWidth() != src.getWidth() || dest.getHeight() != src.getHeight()) {
			StaticLogs.debug.log(LogType.ERROR, "Failed image multiply operation, "
					+ "images not the same size");
			return dest;
		}
		
		int[] RGBA = new int[4];
		int[] srcTmp = new int[4];
		int[] destTmp = new int[4];
		for (int x = 0; x < dest.getWidth(); x++) {
			for (int y = 0; y < dest.getHeight(); y++) {
				srcRaster.getPixel(x, y, srcTmp);
				destRaster.getPixel(x, y, destTmp);
				
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
				RGBA[3] = MathUtil.clamp(RGBA[3], 1, 255);
				raster.setPixel(x, y, new int[] {
						RGBA[0],
						RGBA[1],
						RGBA[2],
						MathUtil.clamp((int) ((RGBA[3] / 255.0f) * opacity * 255.0f), 0, 255)
				});
			}
		}
		
		return src;
	}
	
	/**
	 * This function provides a subimage, however, it also makes sure
	 * to check the bounds don't get overrun.
	 * 
	 * @param im
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage subImg(BufferedImage im, int x, int y, int width, int height) {
		BufferedImage newImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = newImg.getRaster();
		WritableRaster oldRaster = im.getRaster();

		/*
		 * Draw the image and replace an pixels that are out of bounds with
		 * blank pixels.
		 */
		int[] blankPixel = new int[] {0, 0, 0, 255};

		for (int xold = x, xnew = 0; xnew < width; xold++, xnew++) {
			for (int yold = y, ynew = 0; ynew < height; yold++, ynew++) {
				if (xold < 0 || xold >= im.getWidth()) {
					raster.setPixel(xnew, ynew, blankPixel);
				} else if (yold < 0 || yold >= im.getHeight()) {
					raster.setPixel(xnew, ynew, blankPixel);
				} else {
					raster.setPixel(xnew, ynew, oldRaster.getPixel(xold, yold, (int[]) null));
				}
			}
		}
		
		return newImg;
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
