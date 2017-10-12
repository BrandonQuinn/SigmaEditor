/**
 * Author: Brandon
 * Date Created: 1 Oct. 2017
 * File : ProjectContext.java
 */

package elara.project;

import java.io.File;
import java.util.ArrayList;
import elara.assets.Sound;
import elara.assets.Texture;

/**
 * ProjectContext
 *
 * Description: Static project context. This data structure is essentially
 * the main data structure which models the game data.
 * 
 * All the data here represents a project on disk that can then be
 * send to the game engine and executed.
 * 
 */
public class ProjectContext
{
	private static ProjectContext instance = new ProjectContext();

	private boolean projectLoaded = false;
	
	private String projectName = "";
	private String projectDirectory = "";
	private File projectDirFile;
	
	/**
	 * List of loaded textures which can be used.
	 */
	private ArrayList<Texture> textures = new ArrayList<Texture>();
	
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
	public void assignProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	
	public void assignProjectDirectory(String projectDirectory)
	{
		this.projectDirectory = projectDirectory;
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
	 * Creates a new instance.
	 */
	public void reset()
	{
		instance = new ProjectContext();
	}
}
