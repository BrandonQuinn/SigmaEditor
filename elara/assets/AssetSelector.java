/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : AssetSelector.java
 */
package elara.assets;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
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
	
	private static ArrayList<Entity> selectedEntities = new ArrayList<Entity>();
	
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
					entity.isSelected = true;
					selectedEntities.add(entity);
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
		
		if (selectedLayer != null) {
			for (Entity entity : selectedLayer.entities()) {
				Rectangle rect = entity.selectionBox.rectangle();
				if (rect.contains(point)) {
					entity.isSelected = true;
					selectedEntities.add(entity);
					break; 	// this is important, makes sure we don't continue to select ALL 
							// entites under the mouse, just the top one. This makes sure
							// that if there are many entities stacked on top of each other
							// we are able to drag them off one by one
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
				selectedEntities.clear();
			}
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
}
