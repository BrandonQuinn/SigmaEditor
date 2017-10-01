
package sigma.editor.config.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sigma.editor.Constants;
import sigma.editor.config.configs.EntityConfig;
import sigma.editor.debug.LogType;
import sigma.editor.debug.StaticLogs;

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

public class EntityConfLoader extends ConfLoader
{
	private static final String ENTITY_CONF_FILE = Constants.CONFIG_DIRECTORY + "entities.conf";

	private static File configFile = new File(ENTITY_CONF_FILE);

	public EntityConfLoader()
	{
	}

	/**
	 * Load the entity configuration from a JSON file and create an object out of
	 * it.
	 * 
	 * @throws ParseException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void load() throws FileNotFoundException, IOException, ParseException
	{
		// get list of entity types from JSON file
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(configFile.getAbsolutePath()));
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray types = (JSONArray) jsonObject.get("entityTypes");

		// add types to model
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = types.iterator();

		while (iterator.hasNext()) {
			EntityConfig.addType(iterator.next());
		}

		StaticLogs.debug.log(LogType.INFO, "Entity configuration loaded.");
	}

	/**
	 * Save the configuration, since it may have been changed by the user.
	 * 
	 * @throws JsonIOException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void save() throws IOException
	{
		JSONObject obj = new JSONObject();
		JSONArray entityList = new JSONArray();

		// get types and create json from it
		ArrayList<String> entityTypes = EntityConfig.getTypeList();
		for (String type : entityTypes)
			entityList.add(type);
		obj.put("entityTypes", entityList);

		// write out conf
		FileWriter file = new FileWriter(configFile.getAbsolutePath());
		file.write(obj.toJSONString());
		file.flush();
		file.close();
	}

	/**
	 * Returns the path of the configuration file.
	 * 
	 * @return
	 */
	public String getFilePath()
	{
		return ENTITY_CONF_FILE;
	}
}
