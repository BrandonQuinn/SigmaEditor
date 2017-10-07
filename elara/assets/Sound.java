/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : Sound.java
 */
package elara.assets;

/**
 * Sound
 *
 * Description: Represents a sound, yeppie,
 * sure does.
 */
public class Sound
{
	private String name;
	private String filename;
	
	/**
	 * @param soundName
	 * @param name2
	 */
	public Sound(String name, String filename)
	{
		this.name = name;
		this.filename = filename;
	}

	public void assignName(String name)
	{
		this.name = name;
	}
	
	public String name()
	{
		return name;
	}

	/**
	 * Returns the filename of this sound
	 * @return
	 */
	public String filename()
	{
		return filename;
	}
}
