package elara.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;
import elara.assets.Texture;
import elara.editor.debug.ElaraException;
import elara.editor.debug.LogType;
import elara.editor.debug.Debug;

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
	 * @throws ElaraException 
	 */
	public static Texture loadTexture(String name, File imageToLoad) 
			throws ElaraException
	{
		// load the texture
		Texture texture = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageToLoad);
			texture = new Texture(name, imageToLoad, bufferedImage);
		} catch (IOException e) {
			Debug.debug.log(LogType.ERROR, "Failed to load texture: " 
					+ imageToLoad.getAbsolutePath());
			throw new ElaraException("Failed to load texture: " 
					+ imageToLoad.getAbsolutePath());
		}
		
		return texture;
	}
	
	/**
	 * Reads the contents of a scripts.
	 * 
	 * @param scriptFile
	 * @return
	 * @throws IOException
	 */
	public static String loadScript(String scriptFile) throws ElaraException {
		File file = new File(projCon.projectPath() + "/assets/scripts/" + scriptFile);
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(file.toPath());
		} catch (IOException e) {
			Debug.debug.log(LogType.ERROR, "Failed to load script: " + scriptFile);
			throw new ElaraException("Failed to load script: " + scriptFile);
		}
		
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
	public static void saveScript(String script, String code) throws ElaraException
	{
		File file = new File(projCon.projectPath() + "/assets/scripts/" + script);
		try {
			Files.write(file.toPath(), code.getBytes());
		} catch (IOException e) {
			Debug.debug.log(LogType.ERROR, "Failed to save script: " + script);
			throw new ElaraException("Failed to save script: " + script);
		}
	}
	
	public static void deleteScript(String script) throws ElaraException {
		File file = new File(projCon.projectPath() + "/assets/scripts/" + script);
		try {
			Files.delete(file.toPath());
		} catch (IOException e) {
			Debug.debug.log(LogType.ERROR, "Failed to deleted script: " + script);
			throw new ElaraException("Failed to deleted script: " + script);
		}
	}
}
