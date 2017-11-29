/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 25 Nov. 2017
 * File : ProjectConfiguration.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.project;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import elara.assets.Texture;
import elara.editor.util.JSON;

/*****************************************************************
 *
 * ProjectConfiguration
 *
 * Description: Represents the project configuration file. Provides
 * simpler, easier ways to add other standard abstract types like
 * Decals, Sounds and other things.
 *
 *****************************************************************/

public class ProjectConfiguration
{
	public static ProjectConfiguration configuration = new ProjectConfiguration();

	/**
	 * Default project configuration JSON format.
	 */
	public static final String CONFIG_JSON_TEMPLATE =
			"{\n" +
				"\t\"projectName\": \"Untitled\",\n" +
				"\t\"creationDate\": \"\",\n" +
				"\t\"scenes\": [],\n" +
				"\t\"textures\": [],\n" +
				"\t\"sounds\": [],\n" +
				"\t\"scripts\": [],\n" +
				"\t\"decals\": []" +
			"}";

	private File configFile;

	private JSONObject jsonObject = null;

	private ProjectConfiguration() {}

	@SuppressWarnings("unchecked")
	public void init(File configFile, String projectName)
		throws IOException
	{
		JSONObject object = new JSONObject();
		object.put("name", projectName);
		object.put("creationDate", LocalDateTime.now()
			.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
		object.put("scenes", new JSONArray());
		object.put("textures", new JSONArray());
		object.put("sounds", new JSONArray());
		object.put("scripts", new JSONArray());
		object.put("decals", new JSONArray());
		jsonObject = object;

		JSON.write(jsonObject, configFile.getAbsolutePath());
	}

	public void initFromFile(File configFile)
		throws IOException, 
		ParseException
	{
		JSONObject newJSON = JSON.read(configFile);
		jsonObject = newJSON;
	}

	/**
	 * Write the information in the texture to the project configuration.
	 * @param texture
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void writeTexture(Texture texture)
		throws IOException
	{
		JSONArray textureArray = (JSONArray) jsonObject.get("textures");
		JSONObject newTextureEntry = new JSONObject();
		newTextureEntry.put("name", texture.name());
		newTextureEntry.put("filename", texture.file().getName());
		textureArray.add(newTextureEntry);
		jsonObject.replace("textures", textureArray);
		JSON.write(jsonObject, configFile.getAbsolutePath());
	}

	/**
	 * Write the information in the texture to the project configuration.
	 * @param decal
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void writeDecal(Texture texture)
		throws IOException
	{
		JSONArray textureArray = (JSONArray) jsonObject.get("decals");
		JSONObject newTextureEntry = new JSONObject();
		newTextureEntry.put("name", texture.name());
		newTextureEntry.put("filename", texture.file().getName());
		textureArray.add(newTextureEntry);
		jsonObject.replace("decals", textureArray);
		JSON.write(jsonObject, configFile.getAbsolutePath());
	}

	public JSONObject jsonObject()
	{
		return jsonObject;
	}

	public static ProjectConfiguration instance()
	{
		return configuration;
	}

	/**
	 * Returns the name of the project as read from the configuration file.
	 *
	 * @return
	 */
	public String name()
	{
		return (String) jsonObject.get("name");
	}

	/**
	 * Returns a top level (at the top level of the hierarchy) array.
	 *
	 * @param arrayName
	 * @return
	 */
	public JSONArray getArray(String arrayName)
	{
		return (JSONArray) jsonObject.get(arrayName);
	}
}
