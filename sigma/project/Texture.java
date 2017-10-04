package sigma.project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

/*============================================================*
	Author: brq
	Date: 4 Oct. 2017
	File: Texture.java
	
	Description: Represents a simple texture.
	
	TODO Texture modifcation methods like fall-off and scale
 *============================================================*/

public class Texture
{
	private String name;
	private File file;
	private BufferedImage image;
	
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
	 * Scales the image to the specified size
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
		
		BufferedImage scaledBufferedImage = new BufferedImage(scaleToWidth, scaleToHeight, 
				BufferedImage.TYPE_INT_ARGB);
		Image scaledImage = image.getScaledInstance(scaleToWidth, scaleToHeight, 
				BufferedImage.SCALE_DEFAULT);
		
		Graphics bg = scaledBufferedImage.getGraphics();
		bg.drawImage(scaledImage, 0, 0, null);
		bg.dispose();
		
		return scaledBufferedImage;
	}
}
