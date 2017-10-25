/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 25 Oct. 2017
 * File : PolyCollider.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.physics;

import java.awt.Polygon;

/*****************************************************************
 *
 * PolyCollider
 *
 * Description: Collider with complex polygon shape.
 *
 *****************************************************************/

public class PolyCollider extends Collider
{
	public PolyCollider(Polygon poly)
	{
		this.shape = poly;
		id = idCounter++;
	}
}
