package sigma.editor.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sigma.editor.Constants;
import sigma.editor.model.EntityConfModel;

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
		
		JSONObject assetsDirectories = (JSONObject) jsonObject.get("assetDirectories");
		
		
	}

	@Override
	public void save() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getFilePath() {
		// TODO Auto-generated method stub
		
	}
	
}
