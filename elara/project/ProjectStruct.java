/**
 * Author: Brandon
 * Date Created: 3 Oct. 2017
 * File : ProjectModel.java
 */
package elara.project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * ProjectModel
 *
 * Description:
 */
public class ProjectStruct
{
	/**
	 * Configuration file, this must exist for every project.
	 */
	public static final String CONFIG = "project.config";

	/*
	 * All directories needed for the project.
	 */
	public static final String ASSET_DIR = "assets";
	public static final String DECAL_DIR = "assets/decals";
	public static final String TEXTURE_DIR = "assets/textures";
	public static final String SOUND_DIR = "assets/sounds";
	public static final String SCRIPT_DIR = "assets/scripts";
	public static final String SCENE_DIR = "scenes";
	public static final String LOG_DIR = "logs";

	/**
	 * List of directories inside a project on disk.
	 */
	public static String[] directoryList = {
			"assets",
			"assets/decals",
			"assets/textures",
			"assets/sounds",
			"assets/scripts",
			"scenes",
			"logs"
	};

	/**
	 * List of required files inside a project.
	 */
	public static String[] fileList = {
			CONFIG
	};
}
