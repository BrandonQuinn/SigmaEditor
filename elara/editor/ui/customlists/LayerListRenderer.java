/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : LayerListRenderer.java
 */
package elara.editor.ui.customlists;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import elara.assets.Layer;

/**
 * LayerListRenderer
 *
 * Description:
 */
public class LayerListRenderer extends JLabel implements ListCellRenderer<Layer>
{

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Layer> list,
			Layer layer,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{
		add(new JCheckBox());
		setText(layer.name());
		
		return null;
	}
	
}
