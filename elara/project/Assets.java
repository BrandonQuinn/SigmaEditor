/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 20 Nov. 2017
 * File : Assets.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import elara.assets.Script;
import elara.assets.ScriptLang;
import elara.assets.Texture;
import elara.editor.debug.Debug;
import elara.editor.util.JSON;

/*****************************************************************
 *
 * Assets
 *
 * Description: Represents the assets directory and abstracts out 
 * all the details of reading through and loading assets from
 * the assets directory. So basically, allows the caller to
 * load and save any asset simply.
 *
 *****************************************************************/

public class Assets
{
	private static ProjectContext projCon = ProjectContext.projectContext();

	/**
	 * Read a texture, including the image.
	 */
	public static Texture readTexture(String name)
	{
		Texture texture = null;
		
		try {
			JSONObject jsonConfig = JSON.read(projCon.projectDirectoryFile().getAbsolutePath() 
					+ "/" + ProjectStruct.CONFIG);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return texture;
	}
	
	/**
	 * Get the meta data, so the name and file path, but not the image itself.
	 */
	public static Texture readTextureMetaData(String name)
	{
		Texture texture = null;
		return texture;
	}
	
	/**
	 * Read all the information regarding textures that are part of the current
	 * project. Do not load the image itself in to memory.
	 */
	public static Texture[] readTexturesMetaData() 
	{
		ArrayList<Texture> textures = new ArrayList<Texture>();

		// get list of textures and load them
		try {
			JSONObject projectConfig = JSON.read(projCon.projectDirectoryFile().getAbsolutePath() 
					+ "/" + ProjectStruct.CONFIG);
			
			JSONArray textureList = (JSONArray) projectConfig.get("textures");
			for (int i = 0; i < textureList.size(); i++) {
				JSONObject textureData = (JSONObject) textureList.get(i);
				String name = (String) textureData.get("name");
				String filename = (String) textureData.get("filename");
			
				Texture newTexture = new Texture(name, new File(projCon.projectPath() + "/" 
						+ ProjectStruct.TEXTURE_DIR + "/" + filename));
				textures.add(newTexture);
			}
		} catch (IOException e) {
			Debug.error("Failed to load texture list from configuration file");
			return null;
		} catch (ParseException e) {
			Debug.error("Failed to parse configuration file while reading textures meta data");
			return null;
		}

		return (Texture[]) textures.toArray();
	}

	/**
	 * Read all textures and the image data as well.
	 */
	public static Texture[] readTextures()
	{
		ArrayList<Texture> textures = new ArrayList<Texture>();

		// get list of textures and load them
		try {
			JSONObject projectConfig = JSON.read(projCon.projectDirectoryFile().getAbsolutePath() 
					+ "/" + ProjectStruct.CONFIG);
			
			JSONArray textureList = (JSONArray) projectConfig.get("textures");
			for (int i = 0; i < textureList.size(); i++) {
				JSONObject textureData = (JSONObject) textureList.get(i);
				String name = (String) textureData.get("name");
				String filename = (String) textureData.get("filename");
			
				Texture newTexture = new Texture(name, new File(projCon.projectPath() + "/" 
						+ ProjectStruct.TEXTURE_DIR + "/" + filename));
				BufferedImage newImage = ImageIO.read(newTexture.file());
				newTexture.assignImage(newImage);
				textures.add(newTexture);
			}
		} catch (IOException e) {
			Debug.error("Failed to load texture list from configuration file");
			return null;
		} catch (ParseException e) {
			Debug.error("Failed to parse configuration file while reading textures");
			return null;
		}

		return (Texture[]) textures.toArray();
	}
	
	/**
	 * Read the contents of a script in to a string and return it.
	 */
	public static Script readScript(String name)
	{
		Script script = new Script();
		
		JSONObject jsonConfig;
		try {
			jsonConfig = JSON.read(projCon.projectDirectoryFile().getAbsolutePath()
					+ "/" + ProjectStruct.CONFIG);
			
			JSONArray scriptsJson = (JSONArray) jsonConfig.get("scripts");
			
			for (int i = 0; i < scriptsJson.size(); i++) {
				JSONObject scriptInfo = (JSONObject) scriptsJson.get(i);
				String filename = (String) scriptInfo.get("filename");
				
				// split at decimal and check file type
				String[] splitFilename = filename.split(".");
				
				if (splitFilename[0].equals(name)) {
					String fileExt = splitFilename[1];
					
					if (fileExt.equals("java")) {
						script.setLang(ScriptLang.JAVA);
					} else if (fileExt.equals("kt")) {
						script.setLang(ScriptLang.KOTLIN);
					}
					
					// assign the values of the new script including the contents of the script
					File scriptFile = new File(projCon.projectDirectoryFile() + "/"
							+ ProjectStruct.SCRIPT_DIR + "/" + filename);
					String content = new String(Files.readAllBytes(scriptFile.toPath()));
					script.setContent(content);
					script.setFile(scriptFile);
					script.setName(name);
				}
			}
		} catch (FileNotFoundException e) {
			Debug.error("Could not find the script file named: " + name);
			return null;
		} catch (ParseException e) {
			Debug.error("Could not parse configuration file while trying to load script");
			return null;
		} catch (IOException e) {
			Debug.error("IO Error while trying to read configuration file or script file");
			return null;
		}
		
		return script;
	}
}
