/**
 * Author: Brandon
 * Date Created: 15 Oct. 2017
 * File : DecalJList.java
 */
package elara.editor.ui.customlists;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.Texture;
import elara.project.EditingContext;
import elara.project.EditingContext.EditingState;
import elara.project.ProjectContext;

/**
 * DecalJList
 *
 * Description: List of decal textures.
 */
public class DecalJList extends JList<Texture> 
	implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private EditingContext editCon = EditingContext.editingContext();
	private ProjectContext projCon = ProjectContext.projectContext();
	
	private DefaultListModel<Texture> model = new DefaultListModel<Texture>();
	
	public DecalJList()
	{
		setCellRenderer(new TextureListRenderer());
		addListSelectionListener(this);
		setModel(model);
		loadFromContext();
	}
	
	/**
	 * Check if a different value is selected.
	 */
	@Override
	public void valueChanged(ListSelectionEvent lse)
	{
		int index = getSelectedIndex();
		
		if (index != -1) {
			editCon.assignSelectedDecal(model.get(index));
			editCon.assignState(EditingState.DECAL_PLACEMENT);
		}
	}
	
	public void loadFromContext()
	{
		for (Texture tex : projCon.loadedDecals()) {
			model.addElement(tex);
		}
	}

	/**
	 * Adds an element in to the list.
	 * @param decal
	 */
	public void addDecal(Texture decal)
	{
		model.addElement(decal);
	}
}
