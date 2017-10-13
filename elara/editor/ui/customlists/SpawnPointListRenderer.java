/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : SpawnPointListRenderer.java
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
import elara.assets.SpawnPoint;

/**
 * SpawnPointListRenderer
 *
 * Description:
 */
public class SpawnPointListRenderer extends JLabel
	implements ListCellRenderer<SpawnPoint>
{
	private static final long serialVersionUID = 1L;
	
	public SpawnPointListRenderer()
	{
		setOpaque(true);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends SpawnPoint> list,
			SpawnPoint sp,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{

		switch (sp.team()) {
			case 0:
				setIcon(DefaultIcons.spawnIcon);
			break;
			
			case 1:
				setIcon(DefaultIcons.spawnIcon1);
			break;
			
			case 2:
				setIcon(DefaultIcons.spawnIcon2);
			break;
			
			case 3:
				setIcon(DefaultIcons.spawnIcon3);
			break;
		}

		setText("Team: " + sp.team());
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(Color.WHITE);
		} else {
			setBackground(list.getBackground());
			setForeground(Color.BLACK);
		}
		
		setPreferredSize(new Dimension(220, DefaultIcons.spawnIcon.getIconHeight() + 2));
		setBorder(new EmptyBorder(1, 1, 1, 1));
		
		return this;
	}

}
