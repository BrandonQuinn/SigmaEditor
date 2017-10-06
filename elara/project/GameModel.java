/**
 * Author: Brandon
 * Date Created: 3 Oct. 2017
 * File : GameModel.java
 */

package elara.project;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import elara.editor.debug.SigmaException;

/**
 * GameModel
 *
 * Description: Describes the data in the game.
 */
public class GameModel
{
	private static GameModel model = new GameModel();
	
	/**
	 * The amount to multiply the map size by,
	 * so basically the grid size factor.
	 */
	public static final int GRID_SIZE = 10;
	
	/**
	 * Width of the world.
	 */
	private int worldWidth = 100;
	
	/**
	 * Height of the world.
	 */
	private int worldHeight = 100;
	
	/**
	 * Limit the number of texture layers which can be created.
	 */
	private static final int MAX_GROUND_TEXTURE_LAYERS = 10;
	
	/**
	 * Paint layers. This is a list of buffered images
	 * which are layed on top of each other.
	 * When the game is compiled and exported they are all combined
	 * in to one image.
	 */
	private ArrayList<BufferedImage> groundImageLayers = new ArrayList<BufferedImage>();

	private GameModel()
	{
	}
	
	/*================================================*
	 * Getters and Setters                            *
	 *================================================*/
	
	/**
	 * Returns a static instance of the game model
	 * which is a private static member of the GameModel class.
	 * @return
	 */
	public static GameModel gameModel()
	{
		return model;
	}
	
	/**
	 * Returns the width of the game world.
	 * @return
	 */
	public int worldWidth()
	{
		return worldWidth;
	}
	
	/**
	 * Returns the height of the game world.
	 * @return
	 */
	public int worldHeight()
	{
		return worldHeight;
	}

	/**
	 * @param worldWidth
	 */
	public void assignWorldWidth(int worldWidth)
	{
		this.worldWidth = worldWidth;
	}

	/**
	 * @param worldHeight
	 */
	public void assignWorldHeight(int worldHeight)
	{
		this.worldHeight = worldHeight;
	}
	
	public void addGoundTextureLayer() throws SigmaException
	{
		if (groundImageLayers.size() == MAX_GROUND_TEXTURE_LAYERS) {
			throw new SigmaException("Max layers reached, limit is " + MAX_GROUND_TEXTURE_LAYERS
					+ ".\nThis is to save memory.");
		}
		
		groundImageLayers.add(new BufferedImage(worldWidth * GRID_SIZE, worldHeight * worldHeight, 
				BufferedImage.TYPE_INT_ARGB));
	}
	
	public ArrayList<BufferedImage> groundTextureLayers()
	{
		return groundImageLayers;
	}
	
	public void deleteGroundTextureLayer(int index)
	{	
		groundImageLayers.remove(index);
	}
}
