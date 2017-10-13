/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : SpawnPoint.java
 */
package elara.assets;

import java.awt.Graphics2D;
import javax.swing.ImageIcon;
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
	
	/**
	 * Copy constructor.
	 * 
	 * @param selectedSpawnPoint
	 */
	public SpawnPoint(SpawnPoint spawnPoint)
	{
		this.position = spawnPoint.position;
		this.team = spawnPoint.team;
	}

	@Override
	public void draw(int xOffset, int yOffset, Graphics2D g2d)
	{
		ImageIcon chosenIcon = DefaultIcons.spawnIcon;
		
		if (EditingContext.editingContext().gizmoRenderingEnabled()) {
			int xpos = xOffset + (int)position.x - (DefaultIcons.spawnIcon.getIconWidth() >> 1);
			int ypos = yOffset + (int)position.y - (DefaultIcons.spawnIcon.getIconHeight() >> 1);
			
			switch (team) {
				case 1:
					chosenIcon = DefaultIcons.spawnIcon1;
				break;
				
				case 2:
					chosenIcon = DefaultIcons.spawnIcon2;
				break;
				
				case 3:
					chosenIcon = DefaultIcons.spawnIcon3;
				break;
			}
			
			g2d.drawImage(chosenIcon.getImage(), xpos, ypos, null);
		
			// setup and draw selection box
			selectionBox.assignPosition(new Vector2f(xpos, ypos));
			selectionBox.assignSize(chosenIcon.getIconWidth(), 
					chosenIcon.getIconHeight());
			if (isSelected) {
				selectionBox.draw(xOffset, yOffset, g2d);
			}
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
