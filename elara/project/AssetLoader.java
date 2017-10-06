package elara.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import elara.assets.Texture;
import elara.editor.debug.SigmaException;

/*============================================================*
	Author: brq
	Date: 4 Oct. 2017
	File: AssetLoader.java
	
	Description: Loads assets from within the project directory,
	that is assets that are already a part of the project.
	To import new assets in to the project, use the
	ProjectManager class.
 *============================================================*/

public class AssetLoader
{
	
	/**
	 * Loads a texture from a file.
	 * 
	 * @param selectedImage
	 * @return
	 * @throws IOException
	 * @throws SigmaException 
	 */
	public static Texture loadTexture(String name, File imageToLoad) 
			throws IOException, SigmaException
	{
		// load the texture
		BufferedImage bufferedImage = ImageIO.read(imageToLoad);
		Texture texture = new Texture(name, imageToLoad, bufferedImage);
		return texture;
	}
}
