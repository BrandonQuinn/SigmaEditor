/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : ProjectManager.java
 */

package sigma.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sigma.editor.debug.LogType;
import sigma.editor.debug.SigmaException;
import sigma.editor.debug.StaticLogs;

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
			int worldHeight) throws SigmaException
	{
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

		try {
			PrintWriter writer = new PrintWriter(configFile);
			writer.println(ProjectStructure.CONFIG_JSON_TEMPLATE);
			writer.close();
		} catch (IOException e) {
			StaticLogs.debug.log(LogType.CRITICAL,
					"Failed to write project configuration template: " + configFile
							.getAbsolutePath());
			throw new SigmaException("IOExcetion while writer template JSON");
		}

		// TODO Set values in project.config on new project creation

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
	public void open(String projectLocation,
			EditingContext editingContext,
			ProjectContext projectContext,
			GameModel model) throws SigmaException
	{
		StaticLogs.debug.log(LogType.INFO, "Opening project at: " + projectLocation);

		try {
			loadConfiguration(projectLocation, editingContext, projectContext, model);
		} catch (IOException | ParseException e) {
			StaticLogs.debug.log(LogType.CRITICAL, "Failed to load project configuration");
			throw new SigmaException("Failed to load project configuration. " + e.getMessage());
		}

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
}
