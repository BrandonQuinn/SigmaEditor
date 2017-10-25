/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 25 Oct. 2017
 * File : CircleCollider.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.physics;

import java.awt.geom.Ellipse2D;
import org.joml.Vector2f;

/*****************************************************************
 *
 * CircleCollider
 *
 * Description:
 *
 *****************************************************************/

public class CircleCollider extends Collider
{
	public CircleCollider(Vector2f position, float radius)
	{
		shape = new Ellipse2D.Float(position.x, position.y, radius, radius);
		id = idCounter++;
	}
}
