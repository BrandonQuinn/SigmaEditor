/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : ProjectManager.java
 */

package elara.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
import elara.assets.Script;
import elara.assets.Sound;
import elara.assets.Texture;
import elara.editor.EditorConfiguration;
import elara.editor.debug.Debug;
import elara.editor.debug.ElaraException;
import elara.editor.debug.LogType;

/**
 * ProjectManager
 *
 * Description: Deals with the project and it's context
 * in terms of disk and loading, saving moving, that kind of stuff.
 *
 * NOTE(brandon) Continue breaking down project manager
 */
public class ProjectManager
{
	private static ProjectManager instance = new ProjectManager();
	private ProjectContext projCon = ProjectContext.projectContext();
	private ProjectConfiguration configuration = ProjectConfiguration.instance();

	private ProjectManager() {}

	/*======================================================================== *
	 *  PROJECT OPEN, CREATE AND SAVE
	 * ========================================================================*/

	public void createNewProject(String projectName, String projectLocation)
			throws ElaraException
	{
		// create project directory
		File projDir = new File(projectLocation + "/" + projectName);
		if (projDir.exists()) {
			Debug.debug.log(LogType.ERROR, "Could not create project, directory already exists");
			throw new ElaraException("Could not create project, directory already exists");
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
			Debug.error("Could not create project file: " + newProjFile.getAbsolutePath());
			throw new ElaraException("Could not create project file: " + newProjFile.getAbsolutePath());
		}

		// initialise configuration
		try {
			configuration.init(new File(projectLocation + "/" + ProjectStruct.CONFIG), projectName);
		} catch (IOException e) {
			Debug.error("Failed to initialise new project configuration");
			throw new ElaraException("Failed to initialise new project configuration");
		}

		// create an initial scene
		// NOTE(brandon) Create new scene on new project

		projCon.setProjectLoaded(true);
		projCon.setProjectDirectory(projDir.getAbsolutePath());
		projCon.setProjectName(projectName);

		try {
			EditorConfiguration.addRecentProject(projectName, projDir);
		} catch (IOException e) {
			// it's not the end of the world if this fails, but we do want to know if it happens
			Debug.warning("Failed to add new project to recent projects list in editor confguration");
		}
	}

	/**
	 * Open a project by first loading the configurations and then
	 * adding all the assets and the data required to access them
	 * through the editor to the editor and the various contexts.
	 */
	public void open(String projectLocation)
			throws ElaraException
	{
		// read the configuration file
		try {
			configuration.initFromFile(new File(projectLocation + "/" + ProjectStruct.CONFIG));
		} catch (ParseException e) {
			Debug.error("Could not parse configuration file");
			throw new ElaraException("Could not parse configuration file");
		} catch (IOException e) {
			Debug.error("Could not perform IO operation on configruration file");
			throw new ElaraException("Could not perform IO operation on configuration file");
		}

		projCon.setProjectDirectory(projectLocation);
		projCon.setProjectName(configuration.name());

		ArrayList<Texture> textures = Assets.readTexturesMetaData();
		for (Texture texture : textures) projCon.addTexture(texture);

		ArrayList<Texture> decals = Assets.readDecalsMetaData();
		for (Texture decal : decals) projCon.addDecal(decal);

		ArrayList<Sound> sounds = Assets.readSoundsMetaData();
		for (Sound sound : sounds) projCon.addSound(sound);

		ArrayList<Script> scripts = Assets.readScriptsMetaData();
		for (Script script : scripts) projCon.addScript(script);

		// add the project to recent projects, this will only
		// work if the project doesn't already exist
		try {
			EditorConfiguration.addRecentProject(configuration.name(), new File(projectLocation));
		} catch (IOException e) {
			Debug.warning("Failed to add project to recent projects list on opening");
		}

		projCon.setProjectLoaded(true);
		Debug.info("Project opened: " + projectLocation);
	}

	public void save() throws ElaraException
	{
		// TODO save project
	}

	/*======================================================================== *
	 *  OTHER
	 * ========================================================================*/

	public static ProjectManager manager()
	{
		return instance;
	}
}
