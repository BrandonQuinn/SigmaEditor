/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : Layer.java
 */
package elara.assets;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Layer
 *
 * Description: Layer of assets
 */
public class Layer implements Drawable
{
	/**
	 * Name of layer.
	 */
	private String name = "Untitled";
	
	/**
	 * A list of every entity that appears in this layer.
	 */
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * List of sounds on this layer.
	 */
	private ArrayList<Sound> sounds = new ArrayList<Sound>();
	
	/**
	 * List of spawn points on this later.
	 */
	private ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();

	/**
	 * Should we render this layer?
	 */
	private boolean isRendered = true;
	
	public Layer(String name)
	{
		this.name = name;
	}

	/**
	 * Draws the layer.
	 * 
	 * @param xOffset
	 * @param yOffset
	 * @param g2d
	 */
	@Override
	public void draw(int xOffset, int yOffset, Graphics2D g2d)
	{
		if (isRendered) {
			// render sound gizmo
			for (Entity entity: entities) {
				entity.draw(xOffset, yOffset, g2d);
			}
		}
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
	 * Add a spawn point to this layer.
	 * @param new spawn point 
	 */
	public void addSpawnPoint(SpawnPoint sp)
	{
		if (sp != null) {
			spawnPoints.add(sp);
			entities.add(sp);
		}
	}
	
	/**
	 * Get all the spawn point on this layer.
	 * @return
	 */
	public ArrayList<SpawnPoint> spawnPoints()
	{
		return spawnPoints;
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
}
