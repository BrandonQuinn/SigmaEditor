/**
 * Author: Brandon
 * Date Created: 1 Oct. 2017
 * File : ProjectContext.java
 */

package sigma.project;

import java.io.File;

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

	private static String projectName = "";
	private static String projectDirectory = "";
	private static File projectDirFile;

	private ProjectContext()
	{
	}

	public static ProjectContext projectContext()
	{
		return instance;
	}

	public static ProjectContext newContext(String projectName, String projectDir)
	{
		ProjectContext.projectName = projectName;
		ProjectContext.projectDirectory = projectDir;
		return instance;
	}
}
