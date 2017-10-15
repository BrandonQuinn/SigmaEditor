package elara.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
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
	private static ProjectContext projCon = ProjectContext.projectContext();
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
	
	/**
	 * Reads the contents of a scripts.
	 * 
	 * @param scriptFile
	 * @return
	 * @throws IOException
	 */
	public static String loadScript(String scriptFile) throws IOException {
		File file = new File(projCon.projectPath() + "/assets/scripts/" + scriptFile);
		List<String> lines = Files.readAllLines(file.toPath());
		
		String output = "";
		for (String str : lines) {
			output += str + "\n";
		}
		
		return output;
	}

	/**
	 * Saves a script.
	 * 
	 * @param elementAt
	 * @throws IOException 
	 */
	public static void saveScript(String script, String code) throws IOException
	{
		File file = new File(projCon.projectPath() + "/assets/scripts/" + script);
		Files.write(file.toPath(), code.getBytes());
	}
	
	public static void deleteScript(String script) throws IOException {
		File file = new File(projCon.projectPath() + "/assets/scripts/" + script);
		Files.delete(file.toPath());
	}
}
