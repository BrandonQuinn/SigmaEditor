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
import elara.editor.ui.MainWindow;
import elara.project.EditingContext;
import elara.scene.SceneLayer;

/**
 * LayerJList
 *
 * Description: Represents a list of layers which can
 * hold anything object or anything else that will
 * appear in the game.
 * 
 */
public class LayerJList extends JList<SceneLayer> 
	implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private EditingContext editCon = EditingContext.editingContext();
	
	private DefaultListModel<SceneLayer> layerModel = new DefaultListModel<SceneLayer>();
	
	private MainWindow mainWindow;
	
	public LayerJList(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		setCellRenderer(new LayerListRenderer());
		addListSelectionListener(this);
		setModel(layerModel);
		
		ArrayList<SceneLayer> layers = editCon.scene().layers();
		
		for (SceneLayer layer : layers) {
			layerModel.addElement(layer);
		}
		
		if (!layerModel.isEmpty()) {
			setSelectedIndex(0);
			editCon.setSelectedLayer(layers.get(0).id());
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
			editCon.setSelectedLayer(editCon.scene().layers().get(selectedIndex).id());
			mainWindow.evaluateState();
		}
	}
	
	/**
	 * Adds a layer to the list.
	 * @param layer
	 */
	public void addLayer(SceneLayer layer)
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
