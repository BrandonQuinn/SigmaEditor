/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 26 Nov. 2017
 * File : AssetImporter.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.editor.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import elara.assets.Script;
import elara.assets.ScriptLang;
import elara.assets.Sound;
import elara.assets.Texture;
import elara.project.ProjectConfiguration;
import elara.project.ProjectContext;
import elara.project.ProjectManager;
import elara.project.ProjectStruct;

/*****************************************************************
 *
 * ImportAsset
 *
 * Description: Used to import raw assets, like images and sounds
 * in to a project.
 *
 *****************************************************************/

public class ImportAsset
{
	private static ProjectManager projMan = ProjectManager.manager();
	private static ProjectContext projCon = ProjectContext.projectContext();
	private static ProjectConfiguration configuration = ProjectConfiguration.instance();
	
	public static Texture texture(File textureFile, String name) 
		throws IOException
	{
		File destination = new File(projCon.projectPath() + File.pathSeparator + ProjectStruct.TEXTURE_DIR
				+ File.pathSeparator + textureFile.getName());
		Files.copy(textureFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
		Texture texture = new Texture(name, destination);
		configuration.writeTexture(texture);
		return texture;
	}
	
	public static Texture decal(File decalLocation, String name)
	{
		return null;
		
	}
	
	public static Sound sound(File soundLocation, String name)
	{
		return null;
		
	}
	
	/**
	 * Script importing behave a little different to most other
	 * types of assets in that it does not copy the script from
	 * and existing location, but rather it creates a new empty
	 * script in the scripts asset directory.
	 * 
	 * @param name
	 */
	public static Script script(String name, ScriptLang lang)
	{
		return null;
		
	}
}
