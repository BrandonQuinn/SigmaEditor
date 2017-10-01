/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : ProjectManager.java
 */
package sigma.project;

import java.util.ArrayList;
import sigma.editor.debug.SigmaException;

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
	 * Provide a list of recently opening projects.
	 */
	private ArrayList<String> recentProjects;
	
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
	public void createNewProject(String projectName,
			String projectLocation,
			int worldWidth,
			int worldHeight,
			EditingContext editingContext,
			ProjectContext projectContext) throws SigmaException
	{
		// TODO Create new project
		
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
	public void open(String projectLocation,
			EditingContext editingContext,
			ProjectContext projectContext) throws SigmaException
	{
		// TODO Open project
	}
	
	public static ProjectManager manager()
	{
		return instance;
	}
	
}
