/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : Drawable.java
 */
package elara.assets;

import java.awt.Graphics2D;

/**
 * Drawable
 *
 * Description: Anything that is drawable.
 */
public interface Drawable
{
	/**
	 * Draws the subclass in what ever representation is needed.
	 * 
	 * If the implementing class needs to be selected make sure
	 * to setup the selection box in this method.
	 * @param xOffset
	 * @param yOffset
	 * @param g2d
	 */
	public void draw(int xOffset, int yOffset, Graphics2D g2d);
}
