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
public abstract class Entity
	implements Drawable
{
	static int idCounter = 0;
	int id;
	String name;
	
	Vector2f position = new Vector2f(0.0f, 0.0f);
	AssetSelectionBox selectionBox = new AssetSelectionBox();
	boolean isSelected = false;
	
	public Entity(String name) {
		this.name = name;
		id = idCounter++;
	}
	
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
	
	/**
	 * Get the name of the entity.
	 * @return
	 */
	public String name()
	{
		return name;
	}
	
	/**
	 * Assign the entities name.
	 * @param name
	 */
	public void assignName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Returns whether or not this object has been selected.
	 * @return
	 */
	public boolean isSelected()
	{
		return isSelected;
	}

	/**
	 * Sets this entity as selected.
	 * @param b
	 */
	public void setSelected(boolean isSelected)
	{
		isSelected = true;
	}
}
