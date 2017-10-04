package sigma.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import sigma.editor.debug.SigmaException;

/*============================================================*
	Author: brq
	Date: 4 Oct. 2017
	File: AssetLoader.java
	
	Description: Loads assets in to the project directory
	and in to the editor.
 *============================================================*/

public class AssetLoader
{
	
	/**
	 * Copy a texture file in to the project.
	 * 
	 * @param selectedImage
	 * @return
	 * @throws IOException
	 * @throws SigmaException 
	 */
	public static Texture loadTexture(String name, File selectedImage) throws IOException, SigmaException
	{
		ProjectContext projectContext = ProjectContext.projectContext();
		ProjectManager manager = ProjectManager.manager();
		Texture texture = null;
		
		// only load a texture if an actual project is loaded,
		// otherwise we don't know where to put the texture
		if (projectContext.isProjectLoaded()) {
			// copy the texture in to the project
			File newFile = new File(projectContext.projectPath() + "/assets/images/textures/" + selectedImage.getName());
			Files.copy(selectedImage.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			// load the texture
			BufferedImage bufferedImage = ImageIO.read(newFile);
			texture = new Texture(name, newFile, bufferedImage);
			projectContext.addTexture(texture);
			
			manager.addTextureToConfig(name, newFile);
		}
		
		return texture;
	}
}
