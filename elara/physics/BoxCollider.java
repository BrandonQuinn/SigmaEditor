/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 25 Oct. 2017
 * File : BoxCollider.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.physics;

import java.awt.geom.Rectangle2D;
import org.joml.Vector2f;

/*****************************************************************
 *
 * BoxCollider
 *
 * Description: Collider with a box shape.
 *
 *****************************************************************/

public class BoxCollider extends Collider
{
	public BoxCollider(Vector2f position, Vector2f size)
	{
		shape = new Rectangle2D.Float(position.x, position.y, size.x, size.y);
		id = idCounter++;
	}
}
