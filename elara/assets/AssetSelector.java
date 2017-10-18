/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : AssetSelector.java
 */
package elara.assets;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import elara.editor.input.Mouse;
import elara.editor.ui.SelectionRectangle;
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

	private static HashMap<String, Entity> selEnt = new HashMap<String, Entity>(2000);
	
	/**
	 * Find all entities in the game world that are inside the given 
	 * rectangle.
	 * @param rectangle
	 */
	public static void checkSelections(SelectionRectangle rectangle)
	{
		selectedLayer = editingContext.selectedAssetLayer();
		
		if (selectedLayer != null) {
			for (Entity entity : selectedLayer.entities()) {
				Rectangle rect = entity.selectionBox.rectangle();
				
				if (rectangle.rectangle().intersects(rect)) {
					if (selEnt.get(entity.toString()) == null) {
						entity.isSelected = true;
						selEnt.put(entity.toString(), entity);
					}
				} else {
					if (selEnt.get(entity.toString()) != null && rectangle.isDrawn()) {
						entity.isSelected = false;
						selEnt.remove(entity.toString());
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
			for (Entity entity : selEnt.values()) {
				entity.isSelected = false;
			}
			selEnt.clear();
		}
	}
	
	/**
	 * Returns a list of all selected entities.
	 * @return
	 */
	public static Collection<Entity> selectedEntities()
	{
		return selEnt.values();
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
		
		for (Entity entity : selEnt.values()) {
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
		for (Entity entity : selEnt.values()) {
			
			if (Mouse.x - startX != 0) {
				entity.position.add(Mouse.x - startX, 0.0f);
			}
			
			if (Mouse.y - startY != 0) {
				entity.position.add(0.0f, Mouse.y - startY);
			}
		}
		
		startX = Mouse.x;
		startY = Mouse.y;
	}
	
	/**
	 * Reset anything that needs resetting.
	 */
	public static void resetMouseStart()
	{
		startX = null;
		startY = null;
	}

	/**
	 * Allows us to manually add in an entity the the selected list.
	 * @param entity
	 */
	public static void manualSelectEntity(Entity entity)
	{
		selEnt.put(entity.toString(), entity);
	}
}
