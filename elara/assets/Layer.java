/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : Layer.java
 */
package elara.assets;

import java.awt.Graphics2D;
import java.util.ArrayList;
import elara.project.EditingContext;

/**
 * Layer
 *
 * Description: Layer of assets
 */
public class Layer
{
	private EditingContext editingContext = EditingContext.editingContext();
	
	/**
	 * Name of layer.
	 */
	private String name = "Untitled";
	
	/**
	 * List of sounds on this layer.
	 */
	private ArrayList<Sound> sounds = new ArrayList<Sound>();
	
	/**
	 * List of spawn points on this later.
	 */
	private ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();

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
	public void draw(int xOffset, int yOffset, Graphics2D g2d)
	{
		// render sound gizmo
		for (Sound sound : sounds) {
			if (editingContext.gizmoRenderingEnabled()) {
				g2d.drawImage(DefaultIcons.soundIcon.getImage(), 
						((int) sound.position().x - DefaultIcons.soundIcon.getIconWidth() / 2) + xOffset, 
						((int) sound.position().y -  - DefaultIcons.soundIcon.getIconHeight() / 2)+ yOffset, 
						null);
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
		if (s != null)
			sounds.add(s);
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
		if (sp != null)
			spawnPoints.add(sp);
	}
	
	/**
	 * Get all the spawn point on this layer.
	 * @return
	 */
	public ArrayList<SpawnPoint> spawnPoints()
	{
		return spawnPoints;
	}
}
