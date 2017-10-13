/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : LayerJList.java
 */
package elara.editor.ui.customlists;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.Layer;
import elara.project.EditingContext;
import elara.project.GameModel;

/**
 * LayerJList
 *
 * Description: Represents a list of layers which can
 * hold anything object or anything else that will
 * appear in the game.
 * 
 */
public class LayerJList extends JList<Layer> 
	implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private EditingContext editingContext = EditingContext.editingContext();
	private GameModel gameModel = GameModel.gameModel();
	
	private DefaultListModel<Layer> layerModel = new DefaultListModel<Layer>();
	
	public LayerJList()
	{
		setCellRenderer(new LayerListRenderer());
		addListSelectionListener(this);
		setModel(layerModel);
		
		ArrayList<Layer> layers = gameModel.assetLayers();
		
		for (Layer layer : layers) {
			layerModel.addElement(layer);
		}
		
		if (!layerModel.isEmpty()) {
			setSelectedIndex(0);
			editingContext.setSelectedAssetLayer(layers.get(0));
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		int selectedIndex = getSelectedIndex();
		
		if (selectedIndex > -1) {
			editingContext.setSelectedAssetLayer(gameModel.assetLayers().get(selectedIndex));
		}
	}
	/**
	 * Adds a layer to the list.
	 * @param layer
	 */
	public void addLayer(Layer layer)
	{
		layerModel.addElement(layer);
	}
	
	/**
	 * Removes a layer.
	 * @param index
	 */
	public void removeLayer(int index) 
	{
		layerModel.remove(index);
	}
}
