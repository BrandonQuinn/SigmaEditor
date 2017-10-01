/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : World.java
 */

package sigma.project;

/**
 * World
 *
 * Description: Somewhat similar to the World
 * class in the actual engine, or at least it will
 * contain most of what is in that class
 * except with more fleshed out details.
 * 
 * This class is the main data structure which holds
 * everything being edited and rendered.
 * 
 * Compared to the ProjectContext class which represents
 * meta information about the project, this class
 * is the actual project.
 * 
 * TODO Start working on EditingContext
 */
public class EditingContext
{
	private static EditingContext context = new EditingContext();
	
	private int worldWidth = 100;
	private int worldHeight = 100;

	// can not instantiate, do not want to
	private EditingContext()
	{
	}
	
	/**
	 * Return the static intance to the editable context for
	 * the game.
	 * @return context
	 */
	public static EditingContext editingContext()
	{
		return context;
	}
	
	public int worldWidth()
	{
		return worldWidth;
	}
	
	public int worldHeight()
	{
		return worldHeight;
	}
}
