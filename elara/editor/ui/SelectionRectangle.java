/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : SelectionRectangle.java
 */
package elara.editor.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import elara.editor.input.Mouse;

/**
 * SelectionRectangle
 *
 * Description: Used to represent a selection rectangle
 * and it's location etc.
 */
public class SelectionRectangle
{
	private boolean isDrawn = false;
	
	private int startX = 0, startY = 0;
	private boolean startSet = false;
	
	private int topLeftX = 0;
	private int topLeftY = 0;
	private int width = 0;
	private int height = 0;
	
	/**
	 * Draw the selection rectangle.
	 * @param g2d
	 */
	public void draw(Graphics2D g2d)
	{
		topLeftX = 0;
		topLeftY = 0;
		width = 0;
		height = 0;
		
		// get the starting position
		if (Mouse.isLeftButtonDown() && !startSet) {
			startX = Mouse.x;
			startY = Mouse.y;
			startSet = true;
		}

		// ready to draw rectangle
		if (Mouse.isLeftButtonDown() && startSet) {
			// draw border

			// figure out which direction we are going in to draw the rect
			if (Mouse.x < startX) {
				topLeftX = Mouse.x;
				width = startX - Mouse.x;
			}
			
			if (Mouse.x > startX) {
				topLeftX = startX;
				width = Mouse.x - startX;
			}
			
			if (Mouse.y < startY) {
				topLeftY = Mouse.y;
				height = startY - Mouse.y;
			}
			
			if (Mouse.y > startY) {
				topLeftY = startY;
				height = Mouse.y - startY;
			}
			
			// when the size is 0, then there can't be anything
			// that intersects with it
			if (width == 0 || height == 0)
				isDrawn = false;
			else
				isDrawn = true;
			
			// draw border
			g2d.setColor(new Color(93, 138, 153, 180));
			g2d.drawRect(topLeftX, topLeftY, width, height);
			
			// draw center
			g2d.setColor(new Color(137, 227, 255, 100));
			g2d.fillRect(topLeftX + 1, topLeftY + 1, width - 1, height - 1);

			g2d.setColor(Color.WHITE);
		}

		if (!Mouse.isLeftButtonDown()) {
			isDrawn = false;
			startSet = false;
			startX = 0;
			startY = 0;
		}
	}

	/**
	 * Gets a rectangle that is the selection rectangle.
	 * @return
	 */
	public Rectangle rectangle()
	{
		return new Rectangle(topLeftX, topLeftY, width, height);
	}
	
	/**
	 * Returns whether or not the selection recetangle
	 * is currently being drawn.
	 * @return
	 */
	public boolean isDrawn() 
	{
		return isDrawn;
	}
}
