/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : ProjectManager.java
 */

package elara.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import elara.assets.Sound;
import elara.assets.Texture;
import elara.editor.debug.LogType;
import elara.editor.debug.SigmaException;
import elara.editor.debug.StaticLogs;
import elara.editor.util.JSONFormatter;

/**
 * ProjectManager
 *
 * Description: Deals with the project and it's context
 * in terms of disk and loading, saving moving, that kind of stuff.
 */
public class ProjectManager
{
	private static ProjectManager instance = new ProjectManager();

	/**
	 * Just a simple class to store information to identify
	 * a past project.
	 * RecentProject
	 *
	 * Description: Just stores the most basic information to
	 * identify a project.
	 */
	public class RecentProject {
		public String projectName;
		public String projectPath;
	}
	
	/**
	 * Provide a list of recently opening projects.
	 * TODO Manage recent projects
	 */
	private ArrayList<RecentProject> recentProjects = new ArrayList<RecentProject>();

	private ProjectManager()
	{
	}

	/**
	 * Creates a new project on disk and then opens it.
	 * 
	 * @param projectName
	 * @param projectLocation
	 * @param worldWidth
	 * @param worldHeight
	 */
	@SuppressWarnings("unchecked")
	public void createNewProject(String projectName,
			String projectLocation,
			int worldWidth,
			int worldHeight) throws SigmaException
	{
		ProjectContext projContext = ProjectContext.projectContext();
		projContext.assignProjectName(projectName);
		projContext.assignProjectDirectory(projectLocation);
		
		// create the directory structure
		File tempDir;
		for (String directory : ProjectStructure.directoryList) {
			tempDir = new File(projectLocation + "/" + projectName + "/" + directory);
			tempDir.mkdirs();
		}

		// create files
		File tempFile;
		for (String fileStr : ProjectStructure.fileList) {
			tempFile = new File(projectLocation + "/" + projectName + "/" + fileStr);
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				throw new SigmaException("Could not create file in project directory: "
						+ fileStr);
			}
		}

		// write JSON template configuration
		File configFile = new File(projectLocation + "/" + projectName + "/"
				+ ProjectStructure.CONFIG_FILE);
		StaticLogs.debug.log(LogType.INFO,
				"Creating project configuration: " + configFile.getAbsolutePath());

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(configFile);
			writer.println(ProjectStructure.CONFIG_JSON_TEMPLATE);
			writer.close();
		} catch (IOException e) {
			StaticLogs.debug.log(LogType.CRITICAL,
					"Failed to write project configuration template: " + configFile
							.getAbsolutePath());
			throw new SigmaException("IOExcetion while writer template JSON");
		}
		
		// parse the JSON file to make sure when we start making changes
		// nothing is deleted
		JSONParser parser = new JSONParser();
		JSONObject newObject = null;
		try {
			newObject = (JSONObject) parser.parse(new FileReader(configFile));
		} catch (IOException | ParseException e1) {
			StaticLogs.debug.log(LogType.CRITICAL, "Failed to read JSON config template");
			throw new SigmaException("Failed to read JSON config template");
		}
		
		LocalDateTime today = LocalDateTime.now();
		
		// modify the template with the new project's details
		newObject.put("projectName", projectName);
		newObject.put("worldWidth", worldWidth);
		newObject.put("worldHeight", worldHeight);
		newObject.put("creationDate", today.getDayOfMonth() + "/" 
				+ today.getMonthValue() + "/" + today.getYear());
		
		// reopen the writer to write out changes to the JSON config file
		try {
			writer = new PrintWriter(configFile);
		} catch (FileNotFoundException e) {
			StaticLogs.debug.log(LogType.CRITICAL,
					"Failed to open configuration: " + configFile
							.getAbsolutePath());
			throw new SigmaException("IOExcetion while writer opening config file");
		}
		
		writer.write(JSONFormatter.makePretty(newObject.toJSONString()));
		writer.close();
		
		StaticLogs.debug.log(LogType.INFO,
				"New project created '" + projectName + "' in "
						+ projectLocation);
	}

	/**
	 * Opens a project which will allow the editor to become completely
	 * functional for editing.
	 * 
	 * @param projectLocation
	 * @param editingContext
	 * @param projectContext
	 * @throws SigmaException
	 */
	public void open(String projectLocation) throws SigmaException
	{
		EditingContext editingContext = EditingContext.editingContext();
		ProjectContext projectContext = ProjectContext.projectContext();
		GameModel model = GameModel.gameModel();
		
		StaticLogs.debug.log(LogType.INFO, "Opening project at: " + projectLocation);

		try {
			loadConfiguration(projectLocation, editingContext, projectContext, model);
		} catch (IOException | ParseException e) {
			StaticLogs.debug.log(LogType.CRITICAL, "Failed to load project configuration");
			throw new SigmaException("Failed to load project configuration. " + e.getMessage());
		}

		projectContext.setProjectLoaded(true);
		
		// TODO Use config to setup contexts

		StaticLogs.debug.log(LogType.INFO, "Project opened '" + projectLocation + "'");
	}

	/**
	 * Loads the project configuration from the project configuration file.
	 * 
	 * @param projectLocation
	 * @param editingContext
	 * @param projectContext
	 * @throws SigmaException
	 * @throws ParseException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void loadConfiguration(String projectLocation,
			EditingContext editingContext,
			ProjectContext projectContext,
			GameModel model)
			throws SigmaException,
			FileNotFoundException,
			IOException,
			ParseException
	{
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(projectLocation + "/"
				+ ProjectStructure.CONFIG_FILE));
		JSONObject jsonObject = (JSONObject) obj;

		String projectName = (String) jsonObject.get("projectName");
		int worldWidth = ((Long) jsonObject.get("worldWidth")).intValue();
		int worldHeight = ((Long) jsonObject.get("worldHeight")).intValue();

		projectContext.assignProjectName(projectName);
		projectContext.assignProjectDirectory(projectLocation);
		
		model.assignWorldWidth(worldWidth);
		model.assignWorldHeight(worldHeight);
		
		// load textures
		JSONArray textureArray = (JSONArray) jsonObject.get("textureList");
		
		for (int i = 0; i < textureArray.size(); i++) {
			JSONObject textureInfo = (JSONObject) textureArray.get(i);
			String textureName = (String) textureInfo.get("name");
			String textureFilename = (String) textureInfo.get("filename");
			
			File textureFile = new File(projectLocation + "/assets/images/textures/" + textureFilename);

			// check if the file exists and then add it
			if (textureFile.exists() && !textureFilename.equals("")) {
				Texture texture = AssetLoader.loadTexture(textureName, textureFile);
				projectContext.addTexture(texture);
			} else {
				textureArray.remove(i);
				StaticLogs.debug.log(LogType.WARNING, "Texture could not be found, it has been removed: " 
						+ textureName + ", " + textureFilename);
			}
		}
		
		// load sounds
		JSONArray soundArray = (JSONArray) jsonObject.get("soundList");
		
		for (int i = 0; i < soundArray.size(); i++) {
			JSONObject soundInfo = (JSONObject) soundArray.get(i);
			String soundName = (String) soundInfo.get("name");
			String soundFilename = (String) soundInfo.get("filename");
			
			File soundFile = new File(projectLocation + "/assets/sounds/" + soundFilename);

			// check if the file exists and then add it
			if (soundFile.exists() && !soundFilename.equals("")) {
				Sound sound = new Sound(soundName, soundFilename);
				projectContext.addSound(sound);
			} else {
				soundArray.remove(i);
				StaticLogs.debug.log(LogType.WARNING, "Sound could not be found, it has been removed: " 
						+ soundName + ", " + soundFilename);
			}
		}
	}
	
	/**
	 * Adds a texture to the project configuration,
	 * this is just for integrity, and easier finding of textures
	 * that have been intentionally imported.
	 * 
	 * @param name
	 * @param textureFile
	 * @throws SigmaException
	 */
	@SuppressWarnings("unchecked")
	public void addTexture(String name, File textureFile) throws SigmaException
	{
		ProjectContext projContext = ProjectContext.projectContext();
		
		// check if the texture already exists
		for (Texture texture : projContext.loadedTextures()) {
			if (texture.name() == name) {
				StaticLogs.debug.log(LogType.WARNING, "Texture with name: " + name + " already exists");
				throw new SigmaException("Texture with name: " + name + " already exists");
			}
		}
		
		String projectLoc = projContext.projectPath();
		File configFile = new File(projectLoc + "/"+ ProjectStructure.CONFIG_FILE);

		// copy the texture to the assets directory
		File newFile = new File(projContext.projectPath() + "/assets/images/textures/" 
				+ textureFile.getName());
		
		try {
			Files.copy(textureFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e1) {
			StaticLogs.debug.log(LogType.ERROR, "Failed to load texture in to project, texture from " 
					+ textureFile.getAbsolutePath());
			throw new SigmaException("Failed to load texture in to project, texture from " 
					+ textureFile.getAbsolutePath());
		}
		
		// add the texture to the project configuration
		JSONParser parser = new JSONParser();
		try {
			JSONObject configObj = (JSONObject) parser.parse(new FileReader(configFile));
			JSONArray textureList = (JSONArray) configObj.get("textureList");
			JSONObject newTexObj = new JSONObject();
			newTexObj.put("name", name);
			newTexObj.put("filename", textureFile.getName());
			textureList.add(newTexObj);
			
			// write out
			Files.write(configFile.toPath(), JSONFormatter.makePretty(configObj.toJSONString()).getBytes(), StandardOpenOption.WRITE);
		} catch (IOException | ParseException e) {
			StaticLogs.debug.log(LogType.ERROR, "Failed to add texture '" + textureFile.getAbsolutePath() 
					+ "' to project configuration");
			throw new SigmaException("Failed to add texture '" + textureFile.getAbsolutePath()  
					+ "' to project configuration");
		}
		
		// add our new texture to the project context
		Texture newTex;
		try {
			newTex = AssetLoader.loadTexture(name, newFile);
		} catch (IOException e) {
			StaticLogs.debug.log(LogType.ERROR, "Failed to add texture '" + newFile.getAbsolutePath() 
			+ "' to project context");
			throw new SigmaException("Failed to add texture '" + newFile.getAbsolutePath()  
			+ "' to project context");
		}
		
		projContext.addTexture(newTex);
	}
	
	/**
	 * Returns a list of recent projects.
	 * @return
	 */
	public ArrayList<RecentProject> recentProjects()
	{
		return recentProjects;
	}

	public static ProjectManager manager()
	{
		return instance;
	}

	/**
	 * @param soundName
	 * @param sourceSoundFile
	 * @throws SigmaException 
	 */
	@SuppressWarnings("unchecked")
	public void addSound(String name, File sourceSoundFile) throws SigmaException
	{
		ProjectContext projContext = ProjectContext.projectContext();
		
		// check if the sound already exists
		for (Sound sound : projContext.sounds()) {
			if (sound.name() == name) {
				StaticLogs.debug.log(LogType.WARNING, "Sound with name: " + name + " already exists");
				throw new SigmaException("Sound with name: " + name + " already exists");
			}
		}
		
		String projectLoc = projContext.projectPath();
		File configFile = new File(projectLoc + "/"+ ProjectStructure.CONFIG_FILE);

		// copy the sound to the assets directory
		File newFile = new File(projContext.projectPath() + "/assets/sounds/" 
				+ sourceSoundFile.getName());
		
		try {
			Files.copy(sourceSoundFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e1) {
			StaticLogs.debug.log(LogType.ERROR, "Failed to load sound in to project, texture from " 
					+ sourceSoundFile.getAbsolutePath());
			throw new SigmaException("Failed to load sound in to project, texture from " 
					+ sourceSoundFile.getAbsolutePath());
		}
		
		// add the sound to the project configuration
		JSONParser parser = new JSONParser();
		try {
			JSONObject configObj = (JSONObject) parser.parse(new FileReader(configFile));
			JSONArray soundList = (JSONArray) configObj.get("soundList");
			JSONObject newTexObj = new JSONObject();
			newTexObj.put("name", name);
			newTexObj.put("filename", sourceSoundFile.getName());
			soundList.add(newTexObj);
			
			// write out
			Files.write(configFile.toPath(), JSONFormatter.makePretty(configObj.toJSONString()).getBytes(), StandardOpenOption.WRITE);
		} catch (IOException | ParseException e) {
			StaticLogs.debug.log(LogType.ERROR, "Failed to add sound '" + sourceSoundFile.getAbsolutePath() 
					+ "' to project configuration");
			throw new SigmaException("Failed to add sound '" + sourceSoundFile.getAbsolutePath()  
					+ "' to project configuration");
		}
		
		// add our new sound to the project context
		Sound newSound = new Sound(name, sourceSoundFile.getName());
		projContext.addSound(newSound);
	}
}
