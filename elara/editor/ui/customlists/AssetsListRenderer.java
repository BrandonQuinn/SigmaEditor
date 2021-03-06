/**
 * Author: Brandon
 * Date Created: 14 Oct. 2017
 * File : AssetsListRenderer.java
 */
package elara.editor.ui.customlists;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import elara.assets.Entity;
import elara.assets.Sound;

/**
 * AssetsListRenderer
 *
 * Description:
 */
public class AssetsListRenderer extends JLabel 
implements ListCellRenderer<Entity>
{
	private static final long serialVersionUID = 1L;

	public AssetsListRenderer()
	{
		setOpaque(true);
	}
	
	/**
	 * Cell renderer for a list of assets.
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Entity> list,
			Entity entity,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{
		if (entity instanceof Sound) {
			setText("Sound: " + ((Sound)entity).toString());
		} if (entity instanceof Entity) {
			setText("Entity: " + entity.name());
		}
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(Color.WHITE);
		} else {
			setBackground(list.getBackground());
			setForeground(Color.BLACK);
		}
		
		if (entity.isSelected()) {
			setBackground(list.getSelectionBackground());
			setForeground(Color.WHITE);
		}
		
		setPreferredSize(new Dimension(120, 15));
		
		return this;
	}
}
