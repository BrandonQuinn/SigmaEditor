/**
 * Author: Brandon
 * Date Created: 2 Oct. 2017
 * File : World.java
 */

package elara.project;

import elara.assets.Texture;

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
	 * Offset by which to render the game model.
	 */
	private int yOffset = 0;
	private int xOffset = 0;
	
	/**
	 * EditingState
	 *
	 * Description: The current state of the editer.
	 * This generally refers to the current tool being used
	 * in the editor.
	 */
	public enum EditingState 
	{
		/**
		 * Allows for selection of any elements in the game 
		 * either multiple of them or single.
		 * This will then open up more object options like rotations,
		 * and movment.
		 */
		SELECT,
		
		/**
		 * Uses the currently selected ground texture layer
		 * and the current selected texture to paint the currently
		 * selected texture on to the layer using any chosen blend mode.
		 * TODO Add texture blend modes
		 */
		TEXTURE_PAINT,
		
		/**
		 * Allows the user to change the offset of the world,
		 * so basically it allows the user to move around the world.
		 */
		MOVE_WORLD
	}
	
	private EditingState previousState = null;
	
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
	private int selectedGroundLayer = -1;
	
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

	/**
	 * @param selectedIndex
	 */
	public void setSelectedGroundTextureLayer(int selectedIndex)
	{
		selectedGroundLayer = selectedIndex;
	}

	/**
	 * This is the amount the game model should be offset when rendering.
	 * y axis
	 * @return
	 */
	public int yOffset()
	{
		return yOffset;
	}
	
	/**
	 * This is the amount the game model should be offset when rendering.
	 * x axis
	 * @return
	 */
	public int xOffset()
	{
		return xOffset;
	}
	
	/**
	 * Adds to the y offset
	 * @param delta
	 */
	public void addToYOffset(int delta)
	{
		yOffset += delta;
	}
	
	/**
	 * adds to the x offset
	 * @param delta
	 */
	public void addToXOffset(int delta)
	{
		xOffset += delta;
	}

	/**
	 * Returns the current state of the editor.
	 * @return
	 */
	public EditingState state()
	{
		return state;
	}

	/**
	 * @param texturePaint
	 */
	public void assignState(EditingState state)
	{
		previousState = this.state;
		this.state = state;
	}
	
	/**
	 * Returns the previous state
	 * @return
	 */
	public EditingState previousState()
	{
		return previousState;
	}
	
	/**
	 * Selected the currently selected texture
	 * 
	 * @param texture
	 */
	public void setSelectedTexture(Texture texture)
	{
		selectedTexture = texture;
	}
	
	/**
	 * Returns the current selected texture.
	 * @return
	 */
	public Texture selectedTexture()
	{
		return selectedTexture;
	}

	/**
	 * Returns the index of the selected ground layer texture
	 * 
	 * @return
	 */
	public int getSelectedGroundLayerIndex()
	{
		return selectedGroundLayer;
	}
}
