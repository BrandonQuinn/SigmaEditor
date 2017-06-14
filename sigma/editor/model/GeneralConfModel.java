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
	private static File defaultGameAssetsDir_WIN;
	private static File defaultGameAssetsDir_MAC;
	private static File defaultGameAssetsDir_LINUX;
	
	/**
	 * The location of where to save the level being edited.
	 */
	private static File gameAssetsDir;
	
	public File getAssetsDirectory() {
		return gameAssetsDir;
	}
	
	/**
	 * Gets the default directory to save assets/levels for Windows.
	 * @return File instance
	 */
	public File getDefaultAssetsDir_WIN() {
		return defaultGameAssetsDir_WIN;
	}
	
	/**
	 * Gets the default directory to save assets/levels for Mac.
	 * @return File instance
	 */
	public File getDefaultAssetsDir_MAC() {
		return defaultGameAssetsDir_MAC;
	}

	/**
	 * Gets the default directory to save assets/levels for Linux. 
	 */
	public File getDefaultAssetsDir_LINUX() {
		return defaultGameAssetsDir_LINUX;
	}
}
