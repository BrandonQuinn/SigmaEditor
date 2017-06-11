package sigma.editor.control.conf;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import sigma.editor.Constants;
import sigma.editor.model.conf.EntityConfModel;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class EntityConfLoader {
	private static final String ENTITY_CONF_FILE 
		= Constants.CONFIG_DIRECTORY + "conf_entities.json";
	
	public EntityConfLoader() {}
	
	/**
	 * Load the entity configuration from a JSON file and create an object out of it.
	 * @throws JsonIOException
	 * @throws IOException
	 */
	public void load() throws JsonIOException, IOException {
		Gson gson = new Gson();

		// Read the JSON from the file
		EntityConfModel entityModel = gson.fromJson(new FileReader(ENTITY_CONF_FILE), EntityConfModel.class);
		EntityConfModel.setInstance(entityModel);
	}
	
	/**
	 * Save the configuration, since it may have been changed by the user.
	 * @throws JsonIOException
	 * @throws IOException
	 */
	public void save() throws JsonIOException, IOException {
		Gson gson = new Gson();
		EntityConfModel entityModel = EntityConfModel.getInstance();

		gson.toJson(entityModel, new FileWriter(ENTITY_CONF_FILE));
	}
	
	/**
	 * Returns the path of the configuration file.
	 * @return
	 */
	public String getFilePath() {
		return ENTITY_CONF_FILE;
	}
}
