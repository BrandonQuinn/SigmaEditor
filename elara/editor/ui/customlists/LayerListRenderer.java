/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : LayerListRenderer.java
 */
package elara.editor.ui.customlists;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import elara.assets.Layer;

/**
 * LayerListRenderer
 *
 * Description:
 */
public class LayerListRenderer extends JLabel 
	implements ListCellRenderer<Layer>
{
	private static final long serialVersionUID = 1L;
	
	private Layer layer;
	
	public LayerListRenderer()
	{
		setOpaque(true);
	}
	
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
		this.layer = layer;
		
		setLayout(new FlowLayout());
		setText((index + 1) + ": " + layer.name());

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(Color.WHITE);
		} else {
			setBackground(list.getBackground());
			setForeground(Color.BLACK);
		}
		
		setPreferredSize(new Dimension(120, 20));
		
		if (!this.layer.isRendered()) {
			setBackground(Color.GRAY);
			setForeground(Color.LIGHT_GRAY);
		}
		
		return this;
	}
}
