/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : SpawnPointJList.java
 */
package elara.editor.ui.customlists;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.SpawnPoint;
import elara.project.EditingContext;
import elara.project.EditingContext.EditingState;

/**
 * SpawnPointJList
 *
 * Description: A list of spawn points to add to the game.
 */
public class SpawnPointJList extends JList<SpawnPoint> 
	implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private EditingContext editingContext = EditingContext.editingContext();
	
	private DefaultListModel<SpawnPoint> model = new DefaultListModel<SpawnPoint>();
	
	public SpawnPointJList()
	{
		setCellRenderer(new SpawnPointListRenderer());
		addListSelectionListener(this);
		setModel(model);
		
		SpawnPoint sp;
		for (int i = 0; i < 4; i++) {
			sp = new SpawnPoint();
			sp.assignTeam(i);
			model.addElement(sp);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent lse)
	{
		if (getSelectedIndex() > -1) {
			editingContext.assignSelectedSpawnPoint(model.get(getSelectedIndex()));
			editingContext.assignState(EditingState.ADD_SPAWN_POINT);
		}
	}
}
