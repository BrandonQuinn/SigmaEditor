package sigma.editor.control.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import sigma.editor.Constants;
import sigma.editor.model.conf.EntityConfModel;

/**
 * Loads the configuration for entities and their meta properties
 * that the editor needs to manage them.
 * 
 * Loads them from a JSON file.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class EntityConfLoader {
	private static final String ENTITY_CONF_FILE 
		= Constants.CONFIG_DIRECTORY + "conf_entities.json";
	
	private static File configFile = new File(ENTITY_CONF_FILE);
	
	public EntityConfLoader() {}
	
	/**
	 * Load the entity configuration from a JSON file and create an object out of it.
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	public void load() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(new FileReader(configFile.getAbsolutePath()));
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONArray types = (JSONArray) jsonObject.get("entityTypes");
		Iterator<String> iterator =  types.iterator();
		
		while(iterator.hasNext()) {
			EntityConfModel.addType(iterator.next());
		}
	}
	
	/**
	 * Save the configuration, since it may have been changed by the user.
	 * @throws JsonIOException
	 * @throws IOException
	 */
	public void save() {
		
	}
	
	/**
	 * Returns the path of the configuration file.
	 * @return
	 */
	public String getFilePath() {
		return ENTITY_CONF_FILE;
	}
}
