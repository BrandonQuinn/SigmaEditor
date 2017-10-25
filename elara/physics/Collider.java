/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 25 Oct. 2017
 * File : Collider.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.physics;

import java.awt.Shape;
import org.joml.Vector2f;

/*****************************************************************
 *
 * Collider
 *
 * Description: A collidable object, so all of these are
 * part of a scene and are run through and collisions are checked.
 *
 *****************************************************************/

public abstract class Collider
{
	static int idCounter = 0;
	
	int id;
	Vector2f position = new Vector2f(0.0f, 0.0f);
	Shape shape;
	
	public Shape shape()
	{
		return shape;
	}
}
