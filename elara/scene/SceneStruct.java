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
}
