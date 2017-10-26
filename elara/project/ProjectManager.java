/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : ProjectManager.java
 */

package elara.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
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
 */
public class ProjectManager
{
	private static ProjectManager instance = new ProjectManager();
	
	private ProjectContext projCon = ProjectContext.projectContext();
	private SceneManager sceneMan = SceneManager.manager();

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
		
		// write out the project configuration
		try {
			JSONObject projConfObj = ProjectStruct.initialJSONObj(projectName);
			JSON.write(projConfObj, projectLocation + "/" + projectName + "/" + ProjectStruct.CONFIG);
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

	public void open(String projectLocation) 
			throws ElaraException
	{
		// TODO open project
	}
	
	public void save()
			throws ElaraException
	{
		// TODO save project
	}
	
	public void importTexture(String name, File textureFile) 
			throws ElaraException
	{	
		// TODO import texture
	}

	public void importSound(String name, File sourceSoundFile) 
			throws ElaraException
	{
		// TODO import sound
	}
	
	public Texture importDecal(String name, File file) {
		// TODO import decal
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
		
		// TODO add recent project to project config
	}

	public static ProjectManager manager()
	{
		return instance;
	}

	public void addScript(String filename) 
			throws ElaraException
	{
		// TODO add script
	}

	public void deleteScript(String script) 
			throws ElaraException
	{
		// TODO delete script
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
				JSONObject obj = JSON.read(projCon.projectPath() + "/" + ProjectStruct.CONFIG);
				JSONArray sceneList = (JSONArray) obj.get("scenes");
				
				// add the scene to the project configuration
				if (!sceneList.isEmpty() && sceneList != null) {
					JSONObject newObj = new JSONObject();
					newObj.put("name", name);
					sceneList.add(newObj);
					obj.replace("scenes", sceneList);
					JSON.write(obj, projCon.projectPath() + "/" + ProjectStruct.CONFIG);
				}
				
			} catch (ParseException | IOException e) {
				Debug.error("Could not write scene to project configuration: " + name);
				throw new ElaraException("Could not write scene to project configuration: " + name);
			}
		}
		
		projCon.addScene(name);
		
		return newScene;
	}
}
