/**
 * Author: Brandon
 * Date Created: 3 Oct. 2017
 * File : ProjectModel.java
 */
package sigma.project;

/**
 * ProjectModel
 *
 * Description:
 */
public class ProjectStructure
{
	/**
	 * Configuration file, this must exist for every project.
	 */
	public static final String CONFIG_FILE = "project.config";
	
	/**
	 * Default project configuration JSON format.
	 */
	public static final String CONFIG_JSON_TEMPLATE = "{\n" + 
				"\t\"projectName\": \"Untitled\",\n" + 
				"\t\"worldWidth\": 100,\n" + 
				"\t\"worldHeight\": 100\n" + 
			"}";
	
	/**
	 * List of directories inside a project on disk.
	 */
	public static String[] directoryList = {
			"assets",
			"assets/images",
			"assets/sounds",
			"logs"
	};
	
	/**
	 * List of required files inside a project.
	 */
	public static String[] fileList = {
			CONFIG_FILE
	};
}