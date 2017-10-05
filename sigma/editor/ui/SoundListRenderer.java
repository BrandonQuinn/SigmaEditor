/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : SoundListRenderer.java
 */
package sigma.editor.ui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import sigma.project.Sound;

/**
 * SoundListRenderer
 *
 * Description:
 */
public class SoundListRenderer extends JLabel implements ListCellRenderer<Sound>
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
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
		} else {
			setBackground(list.getBackground());
		}
		
		return this;
	}

}
