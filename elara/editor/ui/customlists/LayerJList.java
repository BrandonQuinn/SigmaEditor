/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : LayerJList.java
 */
package elara.editor.ui.customlists;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.Sound;

/**
 * LayerJList
 *
 * Description: Represents a list of layers which can
 * hold anything object or anything else that will
 * appear in the game.
 * 
 */
public class LayerJList extends JList<Sound> implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		// TODO layer list change
	}
	
}
