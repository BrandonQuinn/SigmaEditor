/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : ProjectManager.java
 */

package elara.project;

import java.awt.image.BufferedImage;
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
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import elara.assets.Sound;
import elara.assets.Texture;
import elara.editor.debug.LogType;
import elara.editor.debug.SigmaException;
import elara.editor.debug.StaticLogs;
import elara.editor.imageprocessing.ImageProcessor;
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
	
	private ProjectContext projectContext = ProjectContext.projectContext();
	private EditingContext editingContext = EditingContext.editingContext();
	private GameModel gameModel = GameModel.gameModel();

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
		projectContext.assignProjectName(projectName);
		projectContext.assignProjectDirectory(projectLocation);
		
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
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void open(String projectLocation) throws SigmaException, FileNotFoundException, IOException, ParseException
	{
		StaticLogs.debug.log(LogType.INFO, "Opening project at: " + projectLocation);

		// create the parser and read the file
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(projectLocation + "/"
				+ ProjectStructure.CONFIG_FILE));
		JSONObject jsonObject = (JSONObject) obj;

		String projectName = (String) jsonObject.get("projectName");
		int worldWidth = ((Long) jsonObject.get("worldWidth")).intValue();
		int worldHeight = ((Long) jsonObject.get("worldHeight")).intValue();

		projectContext.assignProjectName(projectName);
		projectContext.assignProjectDirectory(projectLocation);
		gameModel.assignWorldWidth(worldWidth);
		gameModel.assignWorldHeight(worldHeight);
		
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
		
		// load background textures
		JSONArray bgTextureList = (JSONArray) jsonObject.get("textureLayers");
		
		for (int i = 0; i < bgTextureList.size(); i++) {
			JSONObject bgTex = (JSONObject) bgTextureList.get(i);
			int texIndex = ((Long) bgTex.get("index")).intValue();
			
			File bgTexFile = new File(projectLocation + "/assets/images/textures/ground_texture_layer_" 
					+ texIndex + ".png");

			// check if the file exists and then add it
			if (bgTexFile.exists()) {
				BufferedImage image = ImageIO.read(bgTexFile);

				/* 
				 	Ok so... there's a reason for literally copying the image we just read...
					for some reason the image loaded by ImageIO.read() take forever to draw on
					for example, when texture painting, it takes nearly twice as long.
					So for that reason I copy it to a new image. It probably has something
					to do with the format.
				*/
				BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				newImage.setAccelerationPriority(1.0f);
				ImageProcessor.overlap(newImage, 0, 0, image, 0, 0, image.getWidth(), image.getHeight());
				gameModel.groundTextureLayers().add(texIndex, newImage);
			}
		}
		
		projectContext.setProjectLoaded(true);
		StaticLogs.debug.log(LogType.INFO, "Project opened '" + projectLocation + "'");
	}
	
	/**
	 * Saves the project to disk.
	 * 
	 * @throws SigmaException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public void saveProject() throws SigmaException, 
		FileNotFoundException, 
		IOException, 
		ParseException,
		NullPointerException
	{
		if (projectContext.isProjectLoaded()) {
			ArrayList<BufferedImage> textureLayers = gameModel.groundTextureLayers();
			
			// read the configuration json file
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(projectContext.projectPath() 
					+ "/" + ProjectStructure.CONFIG_FILE));
			JSONObject jsonObject = (JSONObject) obj;
			
			// add ground textures to json
			JSONArray textureLayersList = (JSONArray) jsonObject.get("textureLayers");
			
			// delete and replace
			textureLayersList.clear();
			
			for (int i = 0; i < textureLayers.size(); i++) {
				JSONObject listItem = new JSONObject();
				listItem.put("index", i);
				
				// write out to disk
				File outputImage = new File(projectContext.projectPath() + "/assets/images/textures/" 
						+ "ground_texture_layer_" + i + ".png");
				outputImage.createNewFile();
				ImageIO.write(textureLayers.get(i), "png", outputImage);
				
				textureLayersList.add(listItem);
			}
			
			Files.write(new File(projectContext.projectPath() 
					+ "/" + ProjectStructure.CONFIG_FILE).toPath(), 
					JSONFormatter.makePretty(jsonObject.toJSONString()).getBytes());
		} else {
			throw new SigmaException("No project loaded.");
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
		// check if the texture already exists
		for (Texture texture : projectContext.loadedTextures()) {
			if (texture.name() == name) {
				StaticLogs.debug.log(LogType.WARNING, "Texture with name: " + name + " already exists");
				throw new SigmaException("Texture with name: " + name + " already exists");
			}
		}
		
		String projectLoc = projectContext.projectPath();
		File configFile = new File(projectLoc + "/"+ ProjectStructure.CONFIG_FILE);

		// copy the texture to the assets directory
		File newFile = new File(projectContext.projectPath() + "/assets/images/textures/" 
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
		
		projectContext.addTexture(newTex);
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
		// check if the sound already exists
		for (Sound sound : projectContext.sounds()) {
			if (sound.name() == name) {
				StaticLogs.debug.log(LogType.WARNING, "Sound with name: " + name + " already exists");
				throw new SigmaException("Sound with name: " + name + " already exists");
			}
		}
		
		String projectLoc = projectContext.projectPath();
		File configFile = new File(projectLoc + "/"+ ProjectStructure.CONFIG_FILE);

		// copy the sound to the assets directory
		File newFile = new File(projectContext.projectPath() + "/assets/sounds/" 
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
		projectContext.addSound(newSound);
	}
}
