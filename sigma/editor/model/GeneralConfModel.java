package sigma.editor.model;

import java.io.File;

/**
 * A general configuration model for all configurations that don't really fit anywhere specific.
 * Most of the configs here are ones used by the editor for literally anything.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 14 Jun 2017
 */

public class GeneralConfModel {
	
	/**
	 * Default locations for each platform of where the assets directory is.
	 */
	private static File defaultGameAssetsDir_WIN = null;
	private static File defaultGameAssetsDir_MAC = null;
	private static File defaultGameAssetsDir_LINUX = null;
	
	/**
	 * The location of where to save the level being edited.
	 */
	private static File gameAssetsDir;
	
	/**
	 * Sets the assets directory.
	 * @param path
	 */
	public static void setAssetsDirectory(String path) {
		gameAssetsDir = new File(path);
	}
	
	/**
	 * Gets the assets directory as a File instance.
	 * @return
	 */
	public static File getAssetsDirectory() {
		return gameAssetsDir;
	}
	
	/**
	 * Sets the default assets directory for Windows.
	 * @param path
	 */
	public static void setDefaultAssetsDirectory_WIN(String path) {
		if (defaultGameAssetsDir_WIN == null)
			defaultGameAssetsDir_WIN = new File(path);
	}
	
	/**
	 * Gets the default directory to save assets/levels for Windows.
	 * @return File instance
	 */
	public static File getDefaultAssetsDir_WIN() {
		return defaultGameAssetsDir_WIN;
	}
	
	/**
	 * Sets the default assets directory for Macintosh.
	 * @param path
	 */
	public static void setDefaultAssetsDirectory_MAC(String path) {
		if (defaultGameAssetsDir_MAC == null)
			defaultGameAssetsDir_MAC = new File(path);
	}
	
	/**
	 * Gets the default directory to save assets/levels for Mac.
	 * @return File instance
	 */
	public static File getDefaultAssetsDir_MAC() {
		return defaultGameAssetsDir_MAC;
	}

	/**
	 * Sets the default assets directory for Linux.
	 * @param path
	 */
	public static void setDefaultAssetsDirectory_LINUX(String path) {
		if (defaultGameAssetsDir_LINUX == null)
			defaultGameAssetsDir_LINUX = new File(path);
	}
	
	/**
	 * Gets the default directory to save assets/levels for Linux. 
	 */
	public static File getDefaultAssetsDir_LINUX() {
		return defaultGameAssetsDir_LINUX;
	}
}
