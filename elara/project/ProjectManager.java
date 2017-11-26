/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : ProjectManager.java
 */

package elara.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import elara.assets.Script;
import elara.assets.Sound;
import elara.assets.Texture;
import elara.editor.EditorConfiguration;
import elara.editor.debug.Debug;
import elara.editor.debug.ElaraException;
import elara.editor.debug.LogType;
import elara.editor.util.JSON;
import elara.scene.Scene;
import elara.scene.SceneManager;

/**
 * ProjectManager
 *
 * Description: Deals with the project and it's context
 * in terms of disk and loading, saving moving, that kind of stuff.
 *
 * NOTE(brandon) Continue breaking down project manager
 */
public class ProjectManager
{
	private static ProjectManager instance = new ProjectManager();
	private ProjectContext projCon = ProjectContext.projectContext();
	private SceneManager sceneMan = SceneManager.manager();
	private ProjectConfiguration configuration = ProjectConfiguration.instance();

	/**
	 * Store the project configuration in memory and write change to this
	 * which will eliminate the need to keep reading it every time
	 * a change is made.
	 */
	private static JSONObject projectConfig;
	private static File configFile;

	private ProjectManager() {}

	/*======================================================================== *
	 *  PROJECT OPEN, CREATE AND SAVE
	 * ========================================================================*/

	public void createNewProject(String projectName, String projectLocation)
			throws ElaraException
	{
		// create project directory
		File projDir = new File(projectLocation + "/" + projectName);
		if (projDir.exists()) {
			Debug.debug.log(LogType.ERROR, "Could not create project, directory already exists");
			throw new ElaraException("Could not create project, directory already exists");
		}

		// create project directory structure
		File newProjDir = null;
		for (String dirStr : ProjectStruct.directoryList) {
			newProjDir = new File(projDir.getAbsolutePath() + "/" + dirStr);
			newProjDir.mkdirs();
		}

		// create project file structure
		File newProjFile = null;
		try {
			for (String fileStr : ProjectStruct.fileList) {
				newProjFile = new File(projDir.getAbsolutePath() + "/" + fileStr);
				newProjFile.createNewFile();
			}
		} catch (IOException e) {
			Debug.error("Could not create project file: " + newProjFile.getAbsolutePath());
			throw new ElaraException("Could not create project file: " + newProjFile.getAbsolutePath());
		}

		// initialise configuration
		try {
			configuration.init(
				new File(projectLocation + File.pathSeparator + ProjectStruct.CONFIG),
				projectName
			);
		} catch (IOException e) {
			Debug.error("Failed to initialise new project configuration");
			throw new ElaraException("Failed to initialise new project configuration");
		}

		// create an initial scene
		createScene("Default", 50, 50);

		projCon.setProjectLoaded(true);
		projCon.setProjectDirectory(projDir.getAbsolutePath());
		projCon.setProjectName(projectName);

		try {
			EditorConfiguration.addRecentProject(projectName, projDir);
		} catch (IOException e) {
			// it's not the end of the world if this fails, but we do want to know if it happens
			Debug.warning("Failed to add new project to recent projects list in editor confguration");
		}
	}

	/**
	 * Open a project by first loading the configurations and then
	 * adding all the assets and the data required to access them
	 * through the editor to the editor and the various contexts.
	 */
	public void open(String projectLocation)
			throws ElaraException
	{
		// read the configuration file
		try {
			projectConfig = JSON.read(projectLocation + "/" + ProjectStruct.CONFIG);
			configuration.initFromJSON(new File(projectLocation + "/" + ProjectStruct.CONFIG),
					projectConfig);
		} catch (ParseException e) {
			Debug.error("Could not parse configuration file");
			throw new ElaraException("Could not parse configuration file");
		} catch (FileNotFoundException e) {
			Debug.error("Could not find configuration file");
			throw new ElaraException("Could not find configuration file");
		} catch (IOException e) {
			Debug.error("Could not perform IO operation on configruration file");
			throw new ElaraException("Could not perform IO operation on configuration file");
		}

		projCon.setProjectDirectory(projectLocation);
		projCon.setProjectName(configuration.name());

		ArrayList<Texture> textures = Assets.readTexturesMetaData();
		for (Texture texture : textures)
			projCon.addTexture(texture);

		ArrayList<Texture> decals = Assets.readDecalsMetaData();
		for (Texture decal : decals)
			projCon.addDecal(decal);

		ArrayList<Sound> sounds = Assets.readSoundsMetaData();
		for (Sound sound : sounds)
			projCon.addSound(sound);

		ArrayList<Script> scripts = Assets.readScriptsMetaData();
		for (Script script : scripts)
			projCon.addScript(script);

		projCon.setProjectLoaded(true);
	}

	public void save()
			throws ElaraException
	{
		// TODO save project
	}

	/*======================================================================== *
	 *  ASSET IMPORTS/CREATION
	 * ========================================================================*/

	/**
	 * Add a texture to the project. This copies the texture image to
	 * the texture assets directory and adds it to the
	 * list of available textures in the project configuration.
	 *
	 * Importing a texture, as in adding it to the config and copying it
	 * to the assets directory are the same process, the two functions
	 * should not be separated.
	 */
	@SuppressWarnings("unchecked")
	public void importTexture(String name, File textureFile)
			throws ElaraException
	{
		if (!projCon.isProjectLoaded()) {
			Debug.info("No project loaded.");
			throw new ElaraException("No project loaded.");
		}

		if (!textureFile.exists()) {
			Debug.error("Texture does not exist: " + textureFile.getAbsolutePath());
			throw new ElaraException("File does not exist: " + textureFile.getAbsolutePath());
		}

		// copy the file to the assets directory
		File newtex = new File(projCon.projectPath() + "/" + ProjectStruct.TEXTURE_DIR
				+ "/" + textureFile.getName());
		try {
			Files.createFile(newtex.toPath());
		} catch (IOException e) {
			Debug.error("Could not create new file: " + newtex.getAbsolutePath());
			throw new ElaraException("Could not create new file: " + newtex.getAbsolutePath());
		}

		// copy the file over
		try {
			Files.copy(textureFile.toPath(), newtex.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			Debug.error("Could not copy new texture file to project.");
			throw new ElaraException("Could not copy new texture file to project: " + name);
		}

		// add the texture to the the configuration
		JSONArray textureList = (JSONArray) projectConfig.get("textures");
		JSONObject newTexJo = new JSONObject();
		newTexJo.put("name", name);
		newTexJo.put("filename", newtex.getName());
		textureList.add(newTexJo);
		projectConfig.replace("textures", textureList);
		try {
			JSON.write(projectConfig, configFile.getAbsolutePath());
		} catch(IOException e) {
			Debug.error("Failed to write out project configuration while importing texture: " + name);
			throw new ElaraException("Failed to write out project configuration while"
					+ " importing texture: " + name);
		}

		// load the actual image and add it to the context
		// NOTE(brandon) Consider only storing texture meta data, not whole image, bad on memory
		try {
			BufferedImage loadedIm = ImageIO.read(newtex);
			Texture texture = new Texture(name, newtex, loadedIm);
			projCon.addTexture(texture);
		} catch (IOException e) {
			Debug.error("Could not load image as Texture instance: " + newtex.getPath());
			throw new ElaraException("Could not load image as Texture intance: " + newtex.getPath());
		}
	}

	/**
	 * Imports a sound by copying it to the assets directory and adding it to the
	 * project configuration.
	 */
	@SuppressWarnings("unchecked")
	public void importSound(String name, File soundFile)
			throws ElaraException
	{
		if (!projCon.isProjectLoaded()) {
			Debug.info("No project loaded.");
			throw new ElaraException("No project loaded.");
		}

		if (!soundFile.exists()) {
			Debug.error("Sound does not exist: "
					+ soundFile.getAbsolutePath());
			throw new ElaraException("File does not exist exists: "
					+ soundFile.getAbsolutePath());
		}

		// copy the file to the assets directory
		File newsound = new File(projCon.projectPath() + "/" + ProjectStruct.SOUND_DIR
				+ "/" + soundFile.getName());
		try {
			Files.createFile(newsound.toPath());
		} catch (IOException e) {
			Debug.error("Could not create new file: "
					+ newsound.getAbsolutePath());
			throw new ElaraException("Could not create new file: "
					+ newsound.getAbsolutePath());
		}

		// copy the file over
		try {
			Files.copy(soundFile.toPath(), newsound.toPath(),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			Debug.error("Could not copy new sound file to project.");
			throw new ElaraException("Could not copy new sound file to project: "
					+ newsound.getAbsolutePath());
		}

		// add the sound to the the configuration
		JSONArray soundList = (JSONArray) projectConfig.get("sounds");
		JSONObject newSoundJo = new JSONObject();
		newSoundJo.put("name", name);
		newSoundJo.put("filename", newsound.getName());
		soundList.add(newSoundJo);
		projectConfig.replace("sounds", soundList);
		try {
			JSON.write(projectConfig, configFile.getAbsolutePath());
		} catch(IOException e) {
			Debug.error("Failed to write out project configuration while importing sound: " + name);
			throw new ElaraException("Failed to write out project configuration while"
					+ " importing sound: " + name);
		}

		Sound sound = new Sound(name, newsound.getName());
		projCon.addSound(sound);
	}

	/**
	 * Adds the scene to the project configuration and to the project context.
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	public Scene createScene(String name, int width, int height)
			throws ElaraException
	{
		Scene newScene = null;
		if (projCon.isProjectLoaded()) {
			newScene = sceneMan.createScene(name, width, height);

			try {
				JSONArray sceneList = (JSONArray) projectConfig.get("scenes");

				// add the scene to the project configuration
				if (!sceneList.isEmpty() && sceneList != null) {
					JSONObject newObj = new JSONObject();
					newObj.put("name", name);
					sceneList.add(newObj);
					projectConfig.replace("scenes", sceneList);
					JSON.write(projectConfig, configFile.getAbsolutePath());
				}
			} catch (IOException e) {
				Debug.error("Could not write scene to project configuration: " + name);
				throw new ElaraException("Could not write scene to project configuration: " + name);
			}
		}

		projCon.registerScene(name);
		return newScene;
	}

	/*======================================================================== *
	 *  ASSET DELETION
	 * ========================================================================*/

	public void deleteScript(String script)
			throws ElaraException
	{
		// TODO delete script
	}

	/*======================================================================== *
	 *  OTHER
	 * ========================================================================*/

	public static ProjectManager manager()
	{
		return instance;
	}

	public JSONObject projectConfig()
	{
		return projectConfig;
	}
}
