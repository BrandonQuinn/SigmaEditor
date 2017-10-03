/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : World.java
 */

package sigma.project;

import java.awt.image.BufferedImage;

/**
 * World
 *
 * Description: Maintains the state of the editor
 * in terms of tools currently selected and what
 * the user is interacting with or changing.
 * 
 * TODO Work on EditingContext
 */
public class EditingContext
{
	private static EditingContext context = new EditingContext();
	
	/**
	 * EditingState
	 *
	 * Description: The current state of the editer.
	 * This generally refers to the current tool being used
	 * in the editor.
	 */
	enum EditingState 
	{
		SELECT,
		TEXTURE_PAINT
	}
	
	/**
	 * The current state of the editor. Or rather
	 * which tool is being used.
	 */
	private EditingState state = EditingState.SELECT;
	
	/*===========================*
	 * SELECT state fields        *
	 *===========================*/
	
	/*===========================*
	 * PAINT state fields        *
	 *===========================*/
	
	/**
	 * The current index of which layer/image to paint to.
	 */
	private int selectedGroundLayer = 0;
	
	/**
	 * Current texture selected for painting.
	 */
	private Texture selectedTexture;
	
	
	// can not instantiate, do not want to
	private EditingContext()
	{
	}
	
	/*================================================*
	 * Getters and Setters                            *
	 *================================================*/
	
	/**
	 * Return the static intance to the editable context for
	 * the game.
	 * @return context
	 */
	public static EditingContext editingContext()
	{
		return context;
	}
}
