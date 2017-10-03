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
	 * List of directories inside a project on disk.
	 */
	private static String[] directoryList = {
			"assets",
			"assets/images",
			"assets/sounds",
			"logs"
	};
	
	/**
	 * List of required files inside a project.
	 */
	private static String[] fileList = {
			CONFIG_FILE
	};
	
	/*================================================*
	 * Getters and Setters                            *
	 *================================================*/
	
	public static String[] getDirectoryList()
	{
		return directoryList;
	}
	
	public static String[] getFileList()
	{
		return fileList;
	}
}
