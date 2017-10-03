/**
 * Author: Brandon
 * Date Created: 1 Oct. 2017
 * File : ProjectContext.java
 */

package sigma.project;

import java.io.File;
import java.util.ArrayList;

/**
 * ProjectContext
 *
 * Description: Static project context. This data structure is essentially
 * the main data structure which models the game data.
 * 
 * All the data here represents a project on disk that can then be
 * send to the game engine and executed.
 * 
 * TODO ProjectContext
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
}
