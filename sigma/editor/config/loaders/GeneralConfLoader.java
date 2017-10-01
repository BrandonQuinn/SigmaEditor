
package sigma.editor.config.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sigma.editor.Constants;
import sigma.editor.config.configs.GeneralConfig;
import sigma.editor.debug.LogType;
import sigma.editor.debug.StaticLogs;
import sigma.editor.util.OSDetector;

/**
 * Loads the general configuration file for the editor.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 14 Jun 2017
 */

public class GeneralConfLoader extends ConfLoader
{
	private static final String GENERAL_CONF_FILE = Constants.CONFIG_DIRECTORY + "general.conf";

	private static File configFile = new File(GENERAL_CONF_FILE);

	@Override
	public void load() throws FileNotFoundException, IOException, ParseException
	{
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new FileReader(configFile.getAbsolutePath()));
		JSONObject jsonObject = (JSONObject) obj;

		/**********************************************
		 * Asset and save directories.
		 **********************************************/

		/*
		 * Load data which tells the application where it's safe to store new levels.
		 * There will be if there is not already an option for the user to save the
		 * level where ever they want,
		 * however, the default location is loaded from this configuration.
		 */

		JSONObject assetsDirectories = (JSONObject) jsonObject.get("assetDirectories");
		GeneralConfig.setDefaultAssetsDirectory_WIN((String) assetsDirectories.get(
				"gameDefaultAssetsDirectory_WIN"));
		GeneralConfig.setDefaultAssetsDirectory_MAC((String) assetsDirectories.get(
				"gameDefaultAssetsDirectory_MAC"));
		GeneralConfig.setDefaultAssetsDirectory_LINUX((String) assetsDirectories.get(
				"gameDefaultAssetsDirectory_LINUX"));

		String assetDir = (String) assetsDirectories.get("gameAssetsDirectory");
		GeneralConfig.setAssetsDirectory(assetDir);

		if (assetDir == null) {
			throw new IOException("Missing configuration: asset directory.");
		}

		// if it's empty load it up with the default for the current OS
		if (assetDir.equals("")) {
			OSDetector.OS opsys = OSDetector.getOS();

			switch (opsys) {

			case WINDOWS:
				GeneralConfig.setAssetsDirectory(GeneralConfig.getDefaultAssetsDir_WIN()
						.getAbsolutePath());
			break;

			case MAC:
				GeneralConfig.setAssetsDirectory(GeneralConfig.getDefaultAssetsDir_MAC()
						.getAbsolutePath());
			break;

			// assume unix/linux for all else

			case UNIX:
				GeneralConfig.setAssetsDirectory(GeneralConfig.getDefaultAssetsDir_LINUX()
						.getAbsolutePath());
			break;

			case UNKNOWN:
				GeneralConfig.setAssetsDirectory(GeneralConfig.getDefaultAssetsDir_LINUX()
						.getAbsolutePath());
			break;

			default:
				GeneralConfig.setAssetsDirectory(GeneralConfig.getDefaultAssetsDir_LINUX()
						.getAbsolutePath());
			}
		}

		StaticLogs.debug.log(LogType.INFO, "General configuration loaded.");
	}

	@Override
	public void save() throws IOException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getFilePath()
	{
		return null;
	}

}
