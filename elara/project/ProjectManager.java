/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : ProjectManager.java
 */

package elara.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import elara.assets.Texture;
import elara.editor.debug.ElaraException;
import elara.editor.debug.LogType;
import elara.editor.debug.StaticLogs;
import elara.scene.SceneStruct;

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
	 */
	private ArrayList<RecentProject> recentProjects = new ArrayList<RecentProject>();

	private ProjectManager() {}

	public void createNewProject(String projectName, 
			String projectLocation,
			int worldWidth,
			int worldHeight) 
					throws ElaraException
	{
		// create project directory
		File projDir = new File(projectLocation + "/" + projectName);
		if (projDir.exists()) {
			StaticLogs.debug.log(LogType.ERROR, "Could not create project, "
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
		
		// scene directory structure
		File newSceneDir = null;
		for (String dirStr : SceneStruct.directoryList) {
			newSceneDir = new File(projDir.getAbsolutePath() + "/" + dirStr);
			newSceneDir.mkdirs();
		}
		
		// create project file structure
		File newProjFile = null;
		try {
			for (String fileStr : ProjectStruct.fileList) {
				newProjFile = new File(projDir.getAbsolutePath() + "/" + fileStr);
				newProjFile.createNewFile();
			}
		} catch (IOException e) {
			StaticLogs.debug.log(LogType.ERROR, "Could not create project file: "
					+ newProjFile.getAbsolutePath());
			throw new ElaraException("Could not create project file: "
					+ newProjFile.getAbsolutePath());
		}
		
		// create scene files
		File newSceneFile = null;
		try {
			for (String fileStr : ProjectStruct.fileList) {
				newSceneFile = new File(projDir.getAbsolutePath() + "/" + fileStr);
				newSceneFile.createNewFile();
			}
		} catch (IOException e) {
			StaticLogs.debug.log(LogType.ERROR, "Could not create scene file: "
					+ newSceneFile.getAbsolutePath());
			throw new ElaraException("Could not create scene file: "
					+ newSceneFile.getAbsolutePath());
		}
		
		addRecentProject(projectName, projDir.getAbsolutePath());
	}

	public void open(String projectLocation) 
			throws ElaraException
	{
		
	}
	
	public void save() 
			throws ElaraException
	{
		
	}
	
	public void importTexture(String name, File textureFile) 
			throws ElaraException
	{	
		
	}

	public void importSound(String name, File sourceSoundFile) 
			throws ElaraException
	{
		
	}
	
	public Texture importDecal(String name, File file) {
		return null;
	}

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
		
		
	}

	public static ProjectManager manager()
	{
		return instance;
	}

	public void addScript(String filename) 
			throws ElaraException
	{
		
	}

	public void deleteScript(String script) 
			throws ElaraException
	{
		
	}
}
