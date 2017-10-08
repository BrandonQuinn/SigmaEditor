package elara.assets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

/*============================================================*
	Author: brq
	Date: 4 Oct. 2017
	File: Texture.java
	
	Description: Represents a simple texture.
 *============================================================*/

public class Texture
{
	private String name;
	private File file;
	private BufferedImage image;
	
	/**
	 * Store the past scaled size, if it's the same then we don't need
	 * to scale it again.
	 */
	private int lastScaleWidth = 0;
	private int lastScaleHeight = 0;
	private BufferedImage cachedScaledImage;
	
	public Texture(String name, File file)
	{
		this.name = name;
		this.file = file;
	}
	
	public Texture(String name, File file, BufferedImage image)
	{
		this.name = name;
		this.file = file;
		this.image = image;
	}
	
	public void assignImage(BufferedImage image)
	{
		this.image = image;
	}
	
	public BufferedImage image()
	{
		return image;
	}
	
	public String name()
	{
		return name;
	}
	
	public File file()
	{
		return file;
	}

	/**
	 * Scales the image to the specified size. Stores a cached version
	 * so that repeated operation don't run the algorithm too much.
	 * 
	 * @param textureWidth
	 * @param textureHeight
	 * @return
	 */
	public BufferedImage scaledTo(int scaleToWidth, int scaleToHeight)
	{
		if (image == null) {
			return null;
		}
		
		// if all the dimensions are the same, return the cached version
		if (lastScaleWidth == scaleToWidth && lastScaleHeight == scaleToHeight) {
			return cachedScaledImage;
		}
		
		lastScaleWidth = scaleToWidth;
		lastScaleHeight = scaleToHeight;
		
		BufferedImage scaledBufferedImage = new BufferedImage(scaleToWidth, scaleToHeight, 
				BufferedImage.TYPE_INT_ARGB);
		Image scaledImage = image.getScaledInstance(scaleToWidth, scaleToHeight, 
				BufferedImage.SCALE_DEFAULT);
		
		Graphics bg = scaledBufferedImage.getGraphics();
		bg.drawImage(scaledImage, 0, 0, null);
		bg.dispose();
		
		cachedScaledImage = scaledBufferedImage;
		
		return scaledBufferedImage;
	}
}
