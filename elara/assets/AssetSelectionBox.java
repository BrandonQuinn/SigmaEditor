/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : AssetSelectionBox.java
 */
package elara.assets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import org.joml.Vector2f;

/**
 * AssetSelectionBox
 *
 * Description: A box which can be drawn around
 * assets in the editor which gives certain controls
 * like rotation.
 */
public class AssetSelectionBox
	implements Drawable
{
	private static final Color BOX_COLOR = new Color(8, 255, 0);
	private static final int BORDER = 2;
	
	// surrounding rectangle
	private Rectangle rect = new Rectangle(0, 0, 10, 10);

	/**
	 * Draw the selection box around selected assets.
	 */
	@Override
	public void draw(int xOffset, int yOffset, Graphics2D g2d)
	{
		Color tmp = g2d.getColor();
		g2d.setColor(BOX_COLOR);
		
		g2d.drawRect(rect.x, rect.y, 
					rect.width, rect.height);
		
		g2d.setColor(tmp);
	}
	
	/**
	 * This sets the position, however, this is
	 * @param positon
	 */
	public void assignPosition(Vector2f position)
	{
		rect.setLocation((int)position.x - BORDER, (int)position.y - BORDER);
	}
	
	public void assignSize(int width, int height)
	{
		rect.setSize(width + BORDER * 2, height + BORDER * 2);
	}
	
	/**
	 * Returns the rectangle that represents the selection box.
	 * @return
	 */
	public Rectangle rectangle()
	{
		return rect;
	}
}
