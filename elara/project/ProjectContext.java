/**
 * Author: Brandon
 * Date Created: 1 Oct. 2017
 * File : ProjectContext.java
 */

package elara.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import elara.assets.Script;
import elara.assets.Sound;
import elara.assets.Texture;
import elara.scene.SceneManager;

/**
 * ProjectContext
 *
 * Description: Static project context.
 * Represents all the assets that are available to each scene,
 * so it's all the assets the user/developer has to selected from
 * which are added to the game.
 *
 * Before anything can be added to a scene it first
 * has to exist here.
 *
 */
public class ProjectContext
{
	private static ProjectContext instance = new ProjectContext();

	private static final int MAX_SCRIPTS = 1000;

	private boolean projectLoaded = false;
	private String projectName = "";
	private String projectDirectory = "";
	private File projectDirFile;

	/**
	 * List of all scenes.
	 */
	private ArrayList<String> scenes = new ArrayList<String>(SceneManager.MAX_SCENES);

	/**
	 * List of all scripts.
	 */
	private HashMap<String, Script> scriptList = new HashMap<String, Script>(MAX_SCRIPTS);

	/**
	 * List of loaded textures which can be used.
	 */
	private ArrayList<Texture> textures = new ArrayList<Texture>();

	/**
	 * A list of loaded decals which can be placed.
	 */
	private ArrayList<Texture> decals = new ArrayList<Texture>();

	/**
	 * List of sounds, but only their meta data.
	 * Sound are loaded in as needed. They generally
	 * don't need to be played in the editor.
	 */
	private ArrayList<Sound> sounds = new ArrayList<Sound>();

	private ProjectContext()
	{
	}

	public static ProjectContext projectContext()
	{
		return instance;
	}

	/**
	 * Changes whether or not there is currently a project loaded
	 * in the editor.
	 * @param isLoaded
	 */
	public void setProjectLoaded(boolean isLoaded)
	{
		projectLoaded = isLoaded;
	}

	/**
	 * Returns whether or not a project has been loaded in the editor.
	 * @return
	 */
	public boolean isProjectLoaded()
	{
		return projectLoaded;
	}

	/**
	 * @param projectName
	 */
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public void setProjectDirectory(String projectDirectory)
	{
		this.projectDirectory = projectDirectory;
		this.projectDirFile = new File(projectDirectory);
	}

	/**
	 * Returns a list of all currently loaded textures.
	 * @return
	 */
	public ArrayList<Texture> loadedTextures()
	{
		return textures;
	}

	/**
	 * Add a texture to the project.
	 *
	 * @param texture
	 */
	public void addTexture(Texture texture)
	{
		textures.add(texture);
	}

	public File projectDirectoryFile()
	{
		return projectDirFile;
	}

	public String projectPath()
	{
		return projectDirectory;
	}

	/**
	 * @return
	 */
	public String projectName()
	{
		return projectName;
	}

	/**
	 * Returns a list of all sounds, but not the sounds
	 * themselves. Only meta data. The sounds should not
	 * be stored in memory because they may take up
	 * too much space.
	 *
	 * They will be loaded as needed.
	 *
	 * @return
	 */
	public ArrayList<Sound> sounds()
	{
		return sounds;
	}

	/**
	 * Adds a sound to the project context.
	 *
	 * @param newSound
	 */
	public void addSound(Sound newSound)
	{
		sounds.add(newSound);
	}

	/**
	 * Adds a script to the context.
	 * @param script
	 */
	public void addScript(Script script)
	{
		scriptList.put(script.name(), script);
	}

	/**
	 * Returns a list of filenames of scripts.
	 * @return
	 */
	public Collection<Script> scriptList()
	{
		return scriptList.values();
	}

	/**
	 * Creates a new instance.
	 */
	public void reset()
	{
		ProjectContext.instance = new ProjectContext();
	}

	/**
	 * Removes the script from the list of script.
	 * @param script
	 */
	public void deleteScript(String script)
	{
		scriptList.remove(script);
	}

	/**
	 * Adds a texture to the context.
	 * @param decal
	 */
	public void addDecal(Texture decal)
	{
		decals.add(decal);
	}

	/**
	 * Gets the list of loaded decals.
	 * @return
	 */
	public ArrayList<Texture> loadedDecals()
	{
		return decals;
	}

	/**
	 * Returns the names of all scenes that are in the loaded project.
	 * @return
	 */
	public ArrayList<String> scenes()
	{
		return scenes;
	}

	/**
	 * Let's the project context know that there is a new scene so it can
	 * be accessed.
	 * @param sceneName
	 */
	public void registerScene(String sceneName)
	{
		scenes.add(sceneName);
	}
}
