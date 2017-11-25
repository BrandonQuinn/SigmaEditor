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
	private String filename;
	private ScriptLang lang;
	private String content = "";

	public Script() {}

	public Script(String name, String filename)
	{
		this.name = name;
		this.filename = filename;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String name()
	{
		return name;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String filename()
	{
		return filename;
	}

	public void setLang(ScriptLang scriptLang)
	{
		this.lang = scriptLang;
	}

	public ScriptLang lang()
	{
		return lang;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String content()
	{
		return content;
	}
}
