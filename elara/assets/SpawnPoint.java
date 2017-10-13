/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : SpawnPoint.java
 */
package elara.assets;

import java.awt.Graphics2D;
import org.joml.Vector2f;
import elara.project.EditingContext;

/**
 * SpawnPoint
 *
 * Description: A location to spawn for a particular team.
 */
public class SpawnPoint extends Entity
{
	private int team = 0;
	
	public SpawnPoint() 
	{
		
	}	
	
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
	
	/**
	 * Set the team which can spawn at this spawn point.
	 * @param team
	 */
	public void assignTeam(int team)
	{
		this.team = team;
	}
	
	/**
	 * Returns the team which can spawn at this spawn points.
	 * @return
	 */
	public int team()
	{
		return team;
	}
}
