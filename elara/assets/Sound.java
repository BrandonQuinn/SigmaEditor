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
	private org.newdawn.slick.Sound sound;

	/**
	 * @param soundName
	 * @param name2
	 */
	public Sound(String name, String filename)
	{
		super(name);
		this.name = name;
		this.filename = filename;
	}

	/**
	 * Copy constructor.
	 * @param selectedSound
	 */
	public Sound(Sound copySrc)
	{
		super(copySrc.name());
		this.name = copySrc.name;
		this.filename = copySrc.filename;
		this.sound = copySrc.sound;
	}

	private Vector2f tmp = new Vector2f(0, 0);
	private int xpos = 0;
	private int ypos = 0;

	/**
	 * Draw the sound.
	 */
	@Override
	public void draw(int xOffset, int yOffset, Graphics2D g2d)
	{
		if (EditingContext.editingContext().gizmoRenderingEnabled()) {
			xpos = xOffset + (int) position.x - (DefaultIcons.soundIcon.getIconWidth() >> 1);
			ypos = yOffset + (int) position.y - (DefaultIcons.soundIcon.getIconHeight() >> 1);

			g2d.drawImage(DefaultIcons.soundIcon.getImage(), xpos, ypos, null);

			// setup and draw selection box
			selectionBox.assignPosition(tmp.set(xpos, ypos));
			selectionBox.assignSize(DefaultIcons.soundIcon.getIconWidth(),
					DefaultIcons.soundIcon.getIconHeight());

			if (isSelected) {
				selectionBox.draw(xOffset, yOffset, g2d);
			}
		}
	}

	/**
	 * Returns the filename of this sound.
	 * @return
	 */
	public String filename()
	{
		return filename;
	}

	/**
	 * Sets the Slick2D version of the sound with the
	 * actual playable content.
	 */
	public void setSound(org.newdawn.slick.Sound sound)
	{
		this.sound = sound;
	}

	/**
	 * Returns the sound content as an instance of a sound
	 * from the Slick2D library. This intance contains the
	 * sound itself and can be played.
	 */
	public org.newdawn.slick.Sound sound()
	{
		return sound;
	}
}
