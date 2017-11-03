/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 25 Oct. 2017
 * File : Scene.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.scene;

import java.awt.Graphics2D;
import java.util.ArrayList;
import elara.assets.Drawable;

/*****************************************************************
 *
 * Scene
 *
 * Description: Represents essentially an entire level, with
 * all the assets and which layer they are one.
 *
 *****************************************************************/

public class Scene 
	implements Drawable
{
	public static int GRID_SIZE = 50;
	public static final int MAX_WIDTH = 500;
	public static final int MAX_HEIGHT = 500;
	public static final int MIN_WIDTH = 5;
	public static final int MIN_HEIGHT = 5;
	
	private static int idCounter = 0;
	private int id;
	private String name;
	
	private ArrayList<SceneLayer> layers = new ArrayList<SceneLayer>();
	
	private int width, height;
	// private BufferedImage groundTexture;
	
	public Scene(String name, int width, int height)
	{
		this.name = name;
		this.width = width;
		this.height = height;
		id = idCounter++;
	}

	@Override
	public void draw(int xOffset, int yOffset, Graphics2D g2d)
	{
		for (SceneLayer layer : layers) {
			layer.draw(xOffset, yOffset, g2d);
		}
	}
	
	/**
	 * Returns the name.
	 * @return
	 */
	public String name()
	{
		return this.name;
	}
	
	/**
	 * Returns the id.
	 * @return
	 */
	public int id()
	{
		return this.id;
	}
	
	/**
	 * Delete a layer using a reference to it.
	 * @param layer
	 */
	public void deleteLayer(SceneLayer layer)
	{
		layers.remove(layer.id());
	}

	/**
	 * Delete a layer by it's ID.
	 * @param id
	 */
	public void deleteLayerById(int id)
	{
		for (int i = 0; i < layers.size(); i++) {
			if (layers.get(i).id() == id)
				layers.remove(i);
		}
	}

	/**
	 * Add a new layer.
	 * @param newLayer
	 */
	public void addLayer(SceneLayer newLayer)
	{
		// reassign the id, making the id the index in
		newLayer.setID(layers.size());
		layers.add(newLayer);
	}
	
	/**
	 * Return the width of the scene ground texture.
	 * @return
	 */
	public int width() 
	{
		return width;
	}
	
	/**
	 * Return the height of the scene ground texture.
	 * @return
	 */
	public int height()
	{
		return height;
	}

	/**
	 * Set the world width.
	 * @param worldWidth
	 */
	public void setWorldWidth(int worldWidth)
	{
		this.width = worldWidth;
	}

	/**
	 * Set the world height.
	 * @param worldHeight
	 */
	public void setWorldHeight(int worldHeight)
	{
		this.height = worldHeight;
	}

	/**
	 * Return a layer using its ID.
	 * @param selectedLayerID
	 */
	public SceneLayer layerById(int selectedLayerID)
	{
		return layers.get(selectedLayerID);
	}

	/**
	 * Number of layers.
	 * @return
	 */
	public int numLayers()
	{
		return layers.size();
	}

	/**
	 * Insert the layer at this specific location in the order.
	 * @param tmpLayer
	 * @param layerIndex
	 */
	public void addLayer(SceneLayer layer, int layerIndex)
	{
		layers.add(layerIndex, layer);
	}

	/**
	 * Return all the entity layers.
	 * @return
	 */
	public ArrayList<SceneLayer> layers()
	{
		return layers;
	}

	/**
	 * Height of the background in pixels.
	 * @return
	 */
	public int heightPixels()
	{
		return width * GRID_SIZE;
	}
	
	/**
	 * Width of the scene in pixels.
	 * @return
	 */
	public int widthPixels()
	{
		return height * Scene.GRID_SIZE;
	}
}
