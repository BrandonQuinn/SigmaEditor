package sigma.editor.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sigma.editor.Constants;
import sigma.editor.model.GeneralConfModel;
import sigma.editor.util.OSDetector;

/**
 * Loads the general configuration file for the editor.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 14 Jun 2017
 */

public class GeneralConfLoader extends ConfLoader {
	private static final String GENERAL_CONF_FILE 
		= Constants.CONFIG_DIRECTORY + "general.conf";

	private static File configFile = new File(GENERAL_CONF_FILE);
	
	@Override
	public void load() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(new FileReader(configFile.getAbsolutePath()));
		JSONObject jsonObject = (JSONObject) obj;
		
		/**********************************************
		 * Asset and save directories.
		 **********************************************/
		
		/*
		 * Load data which tells the application where it's safe to store new levels.
		 * 
		 * There will be if there is not already an option for the user to save the level where ever they want,
		 * however, the default location is loaded from this configuration.
		 */
		
		JSONObject assetsDirectories = (JSONObject) jsonObject.get("assetDirectories");
		GeneralConfModel.setDefaultAssetsDirectory_WIN((String) assetsDirectories.get("gameDefaultAssetsDirectory_WIN"));
		GeneralConfModel.setDefaultAssetsDirectory_MAC((String) assetsDirectories.get("gameDefaultAssetsDirectory_MAC"));
		GeneralConfModel.setDefaultAssetsDirectory_LINUX((String) assetsDirectories.get("gameDefaultAssetsDirectory_LINUX"));
		
		String assetDir = (String) assetsDirectories.get("gameAssetsDirectory");
		GeneralConfModel.setAssetsDirectory(assetDir);

		// if it's empty load it up with the default for the current OS
		
		if (assetDir.equals("")) {	
			OSDetector.OS opsys = OSDetector.getOS();
			
			switch (opsys) {
			
				case WINDOWS:
					GeneralConfModel.setAssetsDirectory(GeneralConfModel.getDefaultAssetsDir_WIN().getAbsolutePath());
				break;
				
				case MAC:
					GeneralConfModel.setAssetsDirectory(GeneralConfModel.getDefaultAssetsDir_MAC().getAbsolutePath());
				break;
				
				// assume unix/linux for all else
				
				case UNIX:
					GeneralConfModel.setAssetsDirectory(GeneralConfModel.getDefaultAssetsDir_LINUX().getAbsolutePath());
				break;
				
				case UNKNOWN:
					GeneralConfModel.setAssetsDirectory(GeneralConfModel.getDefaultAssetsDir_LINUX().getAbsolutePath());
				break;
				
				default:
					GeneralConfModel.setAssetsDirectory(GeneralConfModel.getDefaultAssetsDir_LINUX().getAbsolutePath());
			}
		}
		
		/**********************************************
		 * Load Simple Error Reporter Config
		 **********************************************/
		
		JSONObject serJson = (JSONObject) jsonObject.get("ser2000");
		String serAddress = (String) serJson.get("address");
		String serProjectName = (String) serJson.get("projectName");
		
		GeneralConfModel.setSERAddress(serAddress);
		GeneralConfModel.setSERProjectName(serProjectName);
	}

	@Override
	public void save() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFilePath() {
		return null;
	}
	
}
