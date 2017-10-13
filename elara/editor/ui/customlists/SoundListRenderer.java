/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : SoundListRenderer.java
 */
package elara.editor.ui.customlists;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import elara.assets.DefaultIcons;
import elara.assets.Sound;

/**
 * SoundListRenderer
 *
 * Description:
 */
public class SoundListRenderer extends JLabel 
	implements ListCellRenderer<Sound>
{
	private static final long serialVersionUID = 1L;

	public SoundListRenderer()
	{
		setOpaque(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Sound> list,
			Sound sound,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{
		setText(sound.name());
		setIcon(DefaultIcons.soundIcon);
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(Color.WHITE);
		} else {
			setBackground(list.getBackground());
			setForeground(Color.BLACK);
		}
		
		setPreferredSize(new Dimension(220, DefaultIcons.soundIcon.getIconHeight() + 2));
		setBorder(new EmptyBorder(1, 1, 1, 1));
		
		return this;
	}
}
