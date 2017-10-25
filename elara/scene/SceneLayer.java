/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : Layer.java
 */
package elara.scene;

import java.awt.Graphics2D;
import java.util.ArrayList;
import elara.assets.Drawable;
import elara.assets.Entity;
import elara.assets.Sound;
import elara.physics.Collider;

/**
 * Layer
 *
 * Description: Layer of assets
 */
public class SceneLayer
	implements Drawable
{
	/**
	 * An ID for the layer
	 */
	private int id = -1;
	
	/**
	 * The scene this layer is contained in
	 */
	private Scene parentScene;
	
	/**
	 * Name of layer.
	 */
	private String name = "Untitled";
	
	/**
	 * A list of every entity that appears in this layer
	 */
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private ArrayList<Collider> colliders = new ArrayList<Collider>();
	
	/**
	 * List of sounds on this layer
	 */
	private ArrayList<Sound> sounds = new ArrayList<Sound>();
	
	/**
	 * Should we render this layer?
	 */
	boolean isRendered = true;
	
	public SceneLayer(Scene parentScene, String name)
	{
		this.name = name;
		this.parentScene = parentScene;
	}
	
	@Override
	public void draw(int xOffset, int yOffset, Graphics2D g2d)
	{
		
	}
	
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

	/**
	 * Add a sound to this layer.
	 * @param selectedSound
	 */
	public void addSound(Sound s)
	{
		if (s != null) {
			sounds.add(s);
			entities.add(s);
		}
	}
	
	/**
	 * Get all the sounds on this layer.
	 * @return
	 */
	public ArrayList<Sound> sounds()
	{
		return sounds;
	}

	/**
	 * Returns a list of ALL entities!
	 * 
	 * @return
	 */
	public ArrayList<Entity> entities()
	{
		return entities;
	}

	/**
	 * Returns whether or not to return this layer.
	 * @return
	 */
	public boolean isRendered()
	{
		return isRendered;
	}

	/**
	 * Tells this layer whether it should render or not.
	 * @param b
	 */
	public void setRendered(boolean rendered)
	{
		isRendered = rendered;
	}

	/**
	 * Get the layer id.
	 * @return
	 */
	public int id()
	{
		return id;
	}

	/**
	 * Set the ID.
	 * @param size
	 */
	public void setID(int id)
	{
		this.id = id;
	}
}
