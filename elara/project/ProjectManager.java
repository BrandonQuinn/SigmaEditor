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
import elara.assets.Sound;
import elara.assets.Texture;
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
 * NOTE(brandon) Consider breaking down ProjectManager, it's going to get big and clunky
 */
public class ProjectManager
{
	private static ProjectManager instance = new ProjectManager();
	private ProjectContext projCon = ProjectContext.projectContext();
	private SceneManager sceneMan = SceneManager.manager();

	/**
	 * Store the project configuration in memory and write change to this
	 * which will eliminate the need to keep reading it every time
	 * a change is made. 
	 */
	private static JSONObject projectConfig;
	private static File configFile;

	/**
	 * Just a simple class to store information to identify a past project.
	 * RecentProject
	 *
	 * Description: Just stores the most basic information to identify a project.
	 */
	public class RecentProject {
		public String projectName;
		public String projectPath;
	}
	
	/**
	 * Provide a list of recently opening projects.
	 */
	private ArrayList<RecentProject> recentProjects = new ArrayList<RecentProject>();

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
			Debug.debug.log(LogType.ERROR, "Could not create project, "
					+ "directory already exists");
			throw new ElaraException("Could not create project, "
					+ "directory already exists");
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
			Debug.debug.log(LogType.ERROR, "Could not create project file: "
					+ newProjFile.getAbsolutePath());
			throw new ElaraException("Could not create project file: "
					+ newProjFile.getAbsolutePath());
		}
		
		// write out the project configuration using the initial structure.
		try {
			configFile = new File(projectLocation + "/" + projectName + "/" + ProjectStruct.CONFIG);
			projectConfig = ProjectStruct.initialJSONObj(projectName);
			JSON.write(projectConfig, configFile.getPath());
		} catch (IOException e) {
			Debug.debug.log(LogType.ERROR, "Could not write out new project configuration: "
					+ projectLocation + "/" + projectName + "/" + ProjectStruct.CONFIG);
			throw new ElaraException("Could not write out new project configuration: "
					+ projectLocation + "/" + projectName + "/" + ProjectStruct.CONFIG);
		}
		
		addRecentProject(projectName, projDir.getAbsolutePath());
		projCon.setProjectLoaded(true);
		projCon.setProjectDirectory(projDir.getAbsolutePath());
		projCon.setProjectName(projectName);
		
		// create an initial scene
		createScene("Default", 50, 50);
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
		
		Texture[] textures = Assets.readTextures();
		for (Texture texture : textures)
			projCon.addTexture(texture);

		Decal[] decals = Assets.readDecals();
		for (Decal decal : decals) 
			projCon.addDecal(decal);

		Sound[] sounds = Assets.readSounds();
		for (Sound sound : sounds)
			projCon.addSound(sound);

		Script[] scripts = Assets.readScripts();
		for (Script script : scripts)
			projCon.addScript(script);
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
	
	public Texture importDecal(String name, File file) {
		// TODO import decal
		return null;
	}

	public void addScript(String filename) 
			throws ElaraException
	{
		// TODO add script
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
	
	public ArrayList<RecentProject> recentProjects()
	{
		return recentProjects;
	}

	public void addRecentProject(String name, String path)
	{
		RecentProject rp = new RecentProject();
		rp.projectName = name;
		rp.projectPath = path;
		recentProjects.add(rp);
		
		// TODO add recent project to project config
	}

	public static ProjectManager manager()
	{
		return instance;
	}
}
