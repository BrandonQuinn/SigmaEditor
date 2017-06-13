package sigma.editor.model;

import java.util.ArrayList;

/**
 * Entity configuration. Can not be instantiated.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class EntityConfModel {
	private static ArrayList<String> entityTypes = new ArrayList<String>();
	
	/**
	 * Single static private instance.
	 */
	private EntityConfModel() {}
	
	/**
	 * Add a type to the entity list.
	 * @param type
	 */
	public static void addType(String type) {
		entityTypes.add(type);
	}
	
	/**
	 * Get the list of entity types 
	 * @return a String[] of all entity types
	 */
	public static ArrayList<String> getTypeList() {
		return entityTypes;
	}
}
