/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 25 Oct. 2017
 * File : SceneStruct.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.scene;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONObject;

/*****************************************************************
 *
 * SceneStruct
 *
 * Description: The structure of a scene.
 *
 *****************************************************************/

public class SceneStruct
{
	public static final String CONFIG = "scene.config";
	public static final String SCENE_DIR = "scenes";
	public static final String IMAGE_DIR = "images";
	
	/**
	 * Default scene configuration JSON format
	 */
	public static final String JSON_TEMPLATE =
			"{\n" +
				"\t\"name\": \"Untitled\",\n" +
				"\t\"creationDate\": \"\",\n" +
				"\t\"width\": 100\n" +
				"\t\"height\": 100\n" +
			"}";
	
	/**
	 * List of directories in a scene
	 */
	public static String[] directoryList = {
			IMAGE_DIR
	};
	
	/**
	 * List of files in a scene
	 */
	public static String[] fileList = {
			CONFIG
	};
	
	/**
	 * Returns a JSON object with the initial structure for a scene configuration.
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject initialJSONObj(String name, int width, int height)
	{
		JSONObject object = new JSONObject();
		object.put("name", name);
		object.put("creationDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
		object.put("width", width);
		object.put("height", height);
		return object;
	}
}
