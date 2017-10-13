/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : AssetSelector.java
 */
package elara.assets;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import elara.editor.input.Mouse;
import elara.project.EditingContext;

/**
 * AssetSelector
 *
 * Description: Used to manage selected assets
 * in the game model.
 */
public class AssetSelector
{
	private static EditingContext editingContext = EditingContext.editingContext();
	private static Layer selectedLayer;
	private static Point point;
	private static Integer startX = null;
	private static Integer startY = null;
	
	private static ArrayList<Entity> selectedEntities = new ArrayList<Entity>();
	
	// jesus christ... FIXME use hashtable instead
	private static HashMap<String, Entity> selEnt = new HashMap<String, Entity>(1000);
	
	/**
	 * Find all entities in the game world that are inside the given 
	 * rectangle.
	 * @param rectangle
	 */
	public static void checkSelections(Rectangle rectangle)
	{
		selectedLayer = editingContext.selectedAssetLayer();
		
		if (selectedLayer != null) {
			for (Entity entity : selectedLayer.entities()) {
				Rectangle rect = entity.selectionBox.rectangle();
				
				if (rectangle.intersects(rect)) {
					if (selEnt.get(entity.toString()) == null) {
						entity.isSelected = true;
						selectedEntities.add(entity);
						selEnt.put(entity.toString(), entity);
					}
				}
			}
		}
	}

	/**
	 * Find the first entity in the game world at the given location.
	 * @param x
	 * @param y
	 */
	public static void checkSelections(int x, int y)
	{
		selectedLayer = editingContext.selectedAssetLayer();
		point = new Point(x, y);
		Entity topmost = null;
		
		if (selectedLayer != null) {
			for (Entity entity : selectedLayer.entities()) {
				Rectangle rect = entity.selectionBox.rectangle();
				if (rect.contains(point)) {
					topmost = entity;
				}
			}
			
			if (topmost != null) {
				/* 
				 * By the nature of the way assets are rendered on each layer, the most
				 * recently access value, that is the higher the index or further down the list
				 * the entity, the later it's renderered and therefore on top of anything under it
				 * and we only want to select the top most entity with this type of selection.
				 * 
				 * This works as a fix for situations where there may be entities of the same size
				 * on top of eachother and to fix this you want to select the topmost one and drag
				 * it away revealing the lower one.
				 */
				
				if (selEnt.get(topmost.toString()) == null) {
					topmost.isSelected = true;
					selectedEntities.add(topmost);
					selEnt.put(topmost.toString(), topmost);
				}
			}
		}
	}

	/**
	 * Deselects everything.
	 */
	public static void deselectAll()
	{
		selectedLayer = editingContext.selectedAssetLayer();
		
		if (selectedLayer != null) {
			for (Entity entity : selectedLayer.entities()) {
				entity.isSelected = false;
			}
			selectedEntities.clear();
			selEnt.clear();
		}
	}
	
	/**
	 * Returns a list of all selected entities.
	 * @return
	 */
	public static ArrayList<Entity> selectedEntities()
	{
		return selectedEntities;
	}

	/**
	 * Check if the mouse is currently intersecting any of the currently selected
	 * entities. This allows us to make sure if we click down on one and drag that
	 * we can move the entire selection.
	 * 
	 * @return
	 */
	public static boolean isMouseOnSelection()
	{
		selectedLayer = editingContext.selectedAssetLayer();
		
		for (Entity entity : selectedEntities) {
			Rectangle rect = entity.selectionBox.rectangle();
			point = new Point(Mouse.x, Mouse.y);
			if (rect.contains(point)) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Moves all selected entities by the amount
	 * the mouse has moved
	 * 
	 * @param x
	 * @param y
	 */
	public static void moveSelection()
	{
		if (startX == null) {
			startX = Mouse.x;
		}
		
		if (startY == null) {
			startY = Mouse.y;
		}
		
		// go through all entities and move them the amount 
		// the mouse has moved
		for (Entity entity : selectedEntities) {
			if (Mouse.x - startX != 0) {
				entity.position.add(Mouse.x - startX, 0.0f);
				startX = Mouse.x;
			}
			
			if (Mouse.y - startY != 0) {
				entity.position.add(0.0f, Mouse.y - startY);
				startY = Mouse.y;
			}
		}
	}
	
	/**
	 * Reset anything that needs resetting.
	 */
	public static void resetMouseStart()
	{
		startX = null;
		startY = null;
	}
}
