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
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.SlickException;
import elara.assets.DefaultIcons;
import elara.assets.Script;
import elara.assets.ScriptLang;
import elara.assets.Sound;
import elara.assets.Texture;
import elara.editor.debug.Debug;

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
	private static ProjectManager projMan = ProjectManager.manager();

	/* =================================== *
	 * TEXTURES
	 * =================================== */

	/**
	 * Read a texture, including the image.
	 */
	public static Texture readTexture(String name)
	{
		Texture texture = readTextureMetaData(name);
		try {
			BufferedImage image = ImageIO.read(texture.file());
			texture.assignImage(image);
		} catch(IOException e) {
			return (Texture) failedToRead("Failed to read image while reading texture: "
				+ name);
		}
		return texture;
	}

	/**
	 * Get the meta data, so the name and file path, but not the image itself.
	 */
	public static Texture readTextureMetaData(String name)
	{
		Texture texture = null;

		try {
			JSONArray textureArray = readArrayFromConfig("textures");
			for (int i = 0; i < textureArray.size(); i++) {
				JSONObject textureData = (JSONObject) textureArray.get(i);
				String texName = (String) textureData.get("name");
				String filename = (String) textureData.get("filename");

				if (name.equals(texName)) {
					texture = new Texture(name, new File(projCon.projectDirectoryFile().getAbsolutePath()
						+ "/" + ProjectStruct.TEXTURE_DIR + "/" + filename));
					return texture;
				}
			}
		} catch (IOException e) {
			return (Texture) failedToRead("Failed to read configuration while "
				+ "reading texture meta data");
		} catch (ParseException e) {
			return (Texture) failedToRead("Failed to parse configuration while "
				+ "reading texture meta data");
		}
		return texture;
	}

	/**
	 * Read all the information regarding textures that are part of the current
	 * project. Do not load the image itself in to memory.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Texture> readTexturesMetaData()
	{
		ArrayList<Texture> textures = new ArrayList<Texture>();

		// get list of textures and load them
		try {
			JSONArray textureList = readArrayFromConfig("textures");
			for (int i = 0; i < textureList.size(); i++) {
				JSONObject textureData = (JSONObject) textureList.get(i);
				String name = (String) textureData.get("name");
				String filename = (String) textureData.get("filename");

				Texture newTexture = new Texture(name, new File(projCon.projectPath() + "/"
					+ ProjectStruct.TEXTURE_DIR + "/" + filename));
				textures.add(newTexture);
			}
		} catch (IOException e) {
			return (ArrayList<Texture>) failedToRead("Failed to load texture list from "
				+ "configuration file");
		} catch (ParseException e) {
			return (ArrayList<Texture>) failedToRead("Failed to parse configuration file "
				+ "while reading textures meta data");
		}
		return textures;
	}

	/**
	 * Read all textures and the image data as well.
	 * Warning: this could be a very slow process that results in
	 * a lot of memory usage, depending on how many images there are
	 * and their size.
	 */
	public static ArrayList<Texture> readTextures()
	{
		ArrayList<Texture> textures = readTexturesMetaData();

		// add all the actual image content
		for (Texture texture : textures) {
			File textureFile = texture.file();
			try {
				texture.assignImage(ImageIO.read(textureFile));
			} catch (IOException e) {
				Debug.error("Failed to load texture: " + textureFile.getAbsolutePath());
				continue;
			}
		}
		return textures;
	}

	/* =================================== *
	 * SCRIPTS
	 * =================================== */

	/**
	 * Read the contents of a script in to a string and return it.
	 */
	public static Script readScript(String name)
	{
		Script script = readScriptMetaData(name);
		// read the content of the script file and set it as the content
		String content = "ERROR while reading content";
		try {
			content = new String(Files.readAllBytes(new File(
				projCon.projectDirectoryFile().getAbsolutePath()
				+ "/" + ProjectStruct.SCRIPT_DIR + "/" + script.filename()).toPath()));
		} catch (IOException e) {
			Debug.error("IO Error reading script file: " + script.filename());
		}
		script.setContent(content);
		return script;
	}

	/**
	 * Returns a script instance without the actual content
	 */
	public static Script readScriptMetaData(String name)
	{
		Script script = null;
		try {
			JSONArray scriptsArray = readArrayFromConfig("scripts");
			script = new Script();

			for (int i = 0; i < scriptsArray.size(); i++) {
				JSONObject scriptData = (JSONObject) scriptsArray.get(i);
				String filename = (String) scriptData.get("filename");

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
					script.setFilename(filename);
					script.setName(name);
					return script;
				}
			}
		} catch (ParseException e) {
			return (Script) failedToRead("Failed to parse configuration file "
				+ "while trying to find script");
		} catch (IOException e) {
			return (Script) failedToRead("IO Error while reading configuration "
				+ "file when finding script");
		}
		return script;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Script> readScriptsMetaData()
	{
		ArrayList<Script> scripts = new ArrayList<Script>();

		try {
			JSONArray scriptsArray = readArrayFromConfig("scripts");
			for (int i = 0; i < scriptsArray.size(); i++) {
				JSONObject scriptData = (JSONObject) scriptsArray.get(i);
				String name = (String) scriptData.get("name");
				String filename = (String) scriptData.get("filename");
				Script script = new Script(name, filename);
				scripts.add(script);
			}
		} catch (ParseException e) {
			return (ArrayList<Script>) failedToRead("Failed to parse configuration while "
				+ "reading scripts meta data");
		} catch (IOException e) {
			return (ArrayList<Script>) failedToRead("IO Error while trying to read "
				+ "scripts meta data");
		}
		return scripts;
	}

	public static ArrayList<Script> readScripts()
	{
		ArrayList<Script> scripts = readScriptsMetaData();
		for (Script script : scripts) {
			String content = "";
			try {
				content = new String(Files.readAllBytes(new File(
					projCon.projectDirectoryFile().getAbsolutePath()
					+ "/" + ProjectStruct.SCRIPT_DIR + "/" + script.filename()).toPath()));
			} catch (IOException e) {
				Debug.warning("Failed to read script content: " + script.filename());
				content = "Error: failed to read script content";
			}
			script.setContent(content);
		}
		return scripts;
	}

	/* =================================== *
	 * DECALS
	 * =================================== */

	/**
	 * Load a decals meta data, not the actual image.
	 * The name does not need to have anything to do with the name
	 * of the file.
	 */
	public static Texture readDecalMetaData(String name)
	{
		Texture decal = null;

		try {
			JSONArray decalArray = readArrayFromConfig("decals");
			for (int i = 0; i < decalArray.size(); i++) {
				JSONObject decalData = (JSONObject) decalArray.get(i);
				String decName = (String) decalData.get("name");
				String filename = (String) decalData.get("filename");

				if (decName.equals(name)) {
					decal = new Texture(name, new File(projCon.projectDirectoryFile().getAbsolutePath()
						+ "/" + ProjectStruct.DECAL_DIR + "/" + filename));
					return decal;
				}
			}
		} catch (ParseException e) {
			return (Texture) failedToRead("Could not parse configuration "
				+ "file while reading decal");
		} catch (IOException e) {
			return (Texture) failedToRead("IO Error while reading configuration "
				+ "file when loading a decal");
		}
		return decal;
	}

	/**
	 * Load the decals meta data and the actual image in to memory
	 * and return the texture.
	 */
	public static Texture readDecal(String name)
	{
		Texture decal = readDecalMetaData(name);
		try {
			BufferedImage image = ImageIO.read(decal.file());
			decal.assignImage(image);
		} catch (IOException e) {
			return (Texture) failedToRead("IO Error while reading decal image: " + name);
		}
		return decal;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Texture> readDecalsMetaData()
	{
		ArrayList<Texture> decals = new ArrayList<Texture>();

		try {
			JSONArray decalArray = readArrayFromConfig("decals");
			for (int i = 0; i < decalArray.size(); i++) {
				JSONObject decalData = (JSONObject) decalArray.get(i);
				String name = (String) decalData.get("name");
				String filename = (String) decalData.get("filename");

				File textureFile = new File(projCon.projectDirectoryFile().getAbsolutePath()
					+ "/" + ProjectStruct.TEXTURE_DIR + "/" + filename);
				Texture texture = new Texture(name, textureFile);
				decals.add(texture);
			}
		} catch (IOException e) {
			return (ArrayList<Texture>) failedToRead("IO Error while reading decals meta data");
		} catch (ParseException e) {
			return (ArrayList<Texture>) failedToRead("Failed to parse configuration "
				+ "while reading decals meta data");
		}
		return decals;
	}

	/**
	 * Return an array of all decals read from the disk
	 * from the current project.
	 */
	public static ArrayList<Texture> readDecals()
	{
		ArrayList<Texture> decals = readDecalsMetaData();
		for (Texture decal : decals) {
			File decalFile = new File(projCon.projectDirectoryFile().getAbsolutePath()
				+ "/" + ProjectStruct.DECAL_DIR + "/" + decal.file().getName());
			try {
				BufferedImage image = ImageIO.read(decalFile);
				decal.assignImage(image);
			} catch (IOException e) {
				Debug.warning("Failed to load decal: " + decal.file().getName());
				decal.assignImage(DefaultIcons.BLANK_ICON_32);
			}
		}
		return decals;
	}

	/* =================================== *
	 * SOUNDS
	 * =================================== */

	/**
	 * Load a sounds meta data.
	 */
	public static Sound readSoundMetaData(String name)
	{
		Sound sound = null;

		try {
			JSONArray soundArray = readArrayFromConfig("sounds");
			for (int i = 0; i < soundArray.size(); i++) {
				JSONObject soundData = (JSONObject) soundArray.get(i);
				String sName = (String) soundData.get("name");
				String filename = (String) soundData.get("filename");

				if (sName.equals(name)) {
					sound = new Sound(name, filename);
					return sound;
				}
			}
		} catch (ParseException e) {
			return (Sound) failedToRead("Could not parse configuration file "
				+ "while trying to load sound meta data");
		} catch (IOException e) {
			return (Sound) failedToRead("IO Error could not open configuration "
				+ "file while trying to load sound meta data");
		}
		return sound;
	}

	/**
	 * Load a sounds meta data and the content of the sound. The sound itself.
	 */
	public static Sound readSound(String name)
	{
		Sound sound = readSoundMetaData(name);
		File soundFile = null;

		try {
			soundFile = new File(projCon.projectDirectoryFile().getAbsolutePath()
				+ "/" + ProjectStruct.SOUND_DIR + "/" + sound.filename());
			@SuppressWarnings("deprecation")
			org.newdawn.slick.Sound slickSound = new org.newdawn.slick.Sound(soundFile.toURL());
			sound.setSound(slickSound);
		} catch (SlickException e) {
			return (Sound) failedToRead("Failed to load sound: " + soundFile.getAbsolutePath());
		} catch (MalformedURLException e) {
			return (Sound) failedToRead("MalformedURLException while loading sound");
		}
		return sound;
	}

	/**
	 * Loads sounds meta data.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Sound> readSoundsMetaData()
	{
		ArrayList<Sound> sounds = new ArrayList<Sound>();

		try {
			JSONArray soundsArray = readArrayFromConfig("sounds");
			for (int i = 0; i < soundsArray.size(); i++) {
				JSONObject soundData = (JSONObject) soundsArray.get(i);
				String name = (String) soundData.get("name");
				String filename = (String) soundData.get("filename");
				Sound sound = new Sound(name, filename);
				sounds.add(sound);
			}
		} catch (IOException e) {
			return (ArrayList<Sound>) failedToRead("IO Error while trying to read sounds "
				+ "from configuration");
		} catch (ParseException e) {
			return (ArrayList<Sound>) failedToRead("Failed to parse configuration file while "
				+ "reading sounds meta data");
		}
		return sounds;
	}

	/**
	 * Load all sounds in to an array and return it. This includes the sound
	 * itself.
	 * Warning: could use up a lot of memory if there's a lot of sounds
	 * and/or their file sizes are large.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Sound> readSounds()
	{
		ArrayList<Sound> sounds = readSoundsMetaData();
		try {
			for (Sound sound : sounds) {
				File soundFile = new File(projCon.projectDirectoryFile().getAbsolutePath()
					+ "/" + ProjectStruct.SOUND_DIR + "/" + sound.filename());
				@SuppressWarnings("deprecation")
				org.newdawn.slick.Sound slickSound = new org.newdawn.slick.Sound(soundFile.toURL());
				sound.setSound(slickSound);
			}
		} catch (MalformedURLException e) {
			return (ArrayList<Sound>) failedToRead("MalformedURLException while trying to "
				+ "load sounds");
		} catch (SlickException e) {
			return (ArrayList<Sound>) failedToRead("Failed to load sounds");
		}
		return sounds;
	}

	private static JSONArray readArrayFromConfig(String arrayName)
		throws IOException,
		ParseException
	{
		return (JSONArray) projMan.projectConfig().get(arrayName);
	}

	private static Object failedToRead(String message)
	{
		Debug.error(message);
		return null;
	}
}
