/**
 * Author: Brandon
 * Date Created: 14 Oct. 2017
 * File : AssetsJList.java
 */
package elara.editor.ui.customlists;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.Entity;
import elara.project.EditingContext;

/**
 * AssetsJList
 *
 * Description: List of assets.
 */
public class AssetsJList extends JList<Entity> 
	implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	// private ProjectContext projectContext = ProjectContext.projectContext();
	private EditingContext editingContext = EditingContext.editingContext();
	// private GameModel gameModel = GameModel.gameModel();
	
	private DefaultListModel<Entity> model = new DefaultListModel<Entity>();
	
	public AssetsJList()
	{
		setCellRenderer(new AssetsListRenderer());
		addListSelectionListener(this);
		setModel(model);
		loadFromContext();
	}
	
	/**
	 * Handle value changed event.
	 */
	@Override
	public void valueChanged(ListSelectionEvent lse)
	{getSelectedValue().setSelected(true);
		if (getSelectedIndex() != -1) {
			getSelectedValue().setSelected(true);
		}
	}
	
	/**
	 * Uses the EditingContext to get a list of loaded entities.
	 * And adds then to the list.
	 * @see EditingContext
	 */
	public void loadFromContext()
	{
		if (editingContext.selectedAssetLayer() != null) {
			model.clear();
			
			if (editingContext.selectedAssetLayer() != null) {
				for (Entity entity : editingContext.selectedAssetLayer().entities()) {
					model.addElement(entity);
				}
			}
		}
	}
}
