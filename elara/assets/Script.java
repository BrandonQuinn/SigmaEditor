/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 21 Nov. 2017
 * File : Script.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.assets;

import java.io.File;

/*****************************************************************
 *
 * Script
 *
 * Description: Represents a script that will be loaded in game
 * and basically executed. Scripts are what make the game go.
 *
 *****************************************************************/

public class Script {
	private String name;
	private File file;
	private ScriptLang lang;
	private String content = "";

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setFile(File path)
	{
		this.file = path;
	}

	public File getFile()
	{
		return file;
	}

	public void setLang(ScriptLang scriptLang)
	{
		this.lang = scriptLang;
	}

	public ScriptLang getLang()
	{
		return lang;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getContent() 
	{
		return content;
	}
}
