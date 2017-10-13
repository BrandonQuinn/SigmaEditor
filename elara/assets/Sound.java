/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : Sound.java
 */
package elara.assets;

import java.awt.Graphics2D;
import org.joml.Vector2f;
import elara.project.EditingContext;

/**
 * Sound
 *
 * Description: Represents a sound, yeppie,
 * sure does.
 */
public class Sound extends Entity
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

	/**
	 * Copy constructor.
	 * @param selectedSound
	 */
	public Sound(Sound copySrc)
	{
		this.name = copySrc.name;
		this.filename = copySrc.filename;
	}
	
	/**
	 * Draw the sound.
	 */
	@Override
	public void draw(int xOffset, int yOffset, Graphics2D g2d)
	{
		if (EditingContext.editingContext().gizmoRenderingEnabled()) {
			int xpos = xOffset + (int)position.x - (DefaultIcons.soundIcon.getIconWidth() >> 1);
			int ypos = yOffset + (int)position.y - (DefaultIcons.soundIcon.getIconHeight() >> 1);
			
			g2d.drawImage(DefaultIcons.soundIcon.getImage(), xpos, ypos, null);
			
			// setup and draw selection box
			selectionBox.assignPosition(new Vector2f(xpos, ypos));
			selectionBox.assignSize(DefaultIcons.soundIcon.getIconWidth(), 
					DefaultIcons.soundIcon.getIconHeight());
			selectionBox.draw(xOffset, yOffset, g2d);
		}
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
