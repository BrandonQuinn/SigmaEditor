/**
 * Author: Brandon
 * Date Created: 8 Oct. 2017
 * File : Entity.java
 */
package elara.assets;

import org.joml.Vector2f;

/**
 * Entity
 *
 * Description: Represents an entity in the game world.
 */
public class Entity
{
	protected Vector2f position = new Vector2f(0.0f, 0.0f);
	
	public Vector2f position()
	{
		return position;
	}
	
	/**
	 * Sets the position of the sound.
	 * @param vector2f
	 */
	public void setPosition(Vector2f pos)
	{
		this.position = pos;
	}
}
