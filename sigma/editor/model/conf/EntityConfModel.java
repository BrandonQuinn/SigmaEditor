package sigma.editor.model.conf;

import java.util.ArrayList;

/**
 * Entity configuration. Can not be instantiated.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class EntityConfModel {
	private static String[] entityTypes = new String[1];
	
	private static EntityConfModel instance = null;
	
	/**
	 * Single static private instance.
	 */
	private EntityConfModel() {}
	
	/**
	 * Get the single static instance, if it has not been created, create it.
	 * @return The EntityConfModel reference
	 */
	public static EntityConfModel getInstance() {
		
		if (instance == null) {
			instance = new EntityConfModel();
		}
		
		return instance;
	}
	
	/**
	 * Set the only static instance to a new instance, likely used when
	 * the instance is loaded from a file, JSON most likely.
	 * @param entityModel the new model
	 */
	public static void setInstance(EntityConfModel entityModel) {
		instance = entityModel;
	}
	
	/**
	 * Add a type to the entity list.
	 * @param type
	 */
	public static void addType(String type) {
		ArrayList<String> types = new ArrayList<String>();
		
		for(int i = 0; i < entityTypes.length; i++) {
			types.add(type);
		}
		
		entityTypes = types.toArray(entityTypes);
	}
	
	/**
	 * Get the list of entity types 
	 * @return a String[] of all entity types
	 */
	public static String[] getTypeList() {
		return entityTypes;
	}
}
