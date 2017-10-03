package sigma.project;

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
}
