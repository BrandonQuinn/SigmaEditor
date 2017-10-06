/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : Layer.java
 */
package elara.assets;

import java.util.ArrayList;

/**
 * Layer
 *
 * Description: Layer of assets
 */
public class Layer
{
	private String name = "Untitled";
	
	private ArrayList<Sound> sounds = new ArrayList<Sound>();

	/**
	 * Sets the name of the layer.
	 * @param name
	 */
	public void assignName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Gets the name of the layer.
	 * @return
	 */
	public String name()
	{
		return name;
	}
}
