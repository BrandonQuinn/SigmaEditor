/**
 * Author: Brandon
 * Date Created: 4 Oct. 2017
 * File : TextureListRenderer.java
 */
package elara.editor.ui.customlists;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import elara.assets.DefaultIcons;
import elara.assets.Texture;

/**
 * TextureListRenderer
 *
 * Description: Custom renderer for a JList of Texture instances.
 */
public class TextureListRenderer extends JLabel 
	implements ListCellRenderer<Texture>
{
	private static final long serialVersionUID = 1L;
	
	private static final int TEXTURE_WIDTH = 32;
	private static final int TEXTURE_HEIGHT = 32;
	
	public TextureListRenderer()
	{
		setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Texture> list,
			Texture texture,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{
		BufferedImage scaledImage = null;
		if(texture.hasCachedScaledImage()) {
			scaledImage = texture.scaledTo(TEXTURE_WIDTH, TEXTURE_HEIGHT);
		} else {
			scaledImage = DefaultIcons.BLANK_ICON_32;
		}
		
		setIcon(new ImageIcon(scaledImage));
		setText(texture.name());
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(Color.WHITE);
		} else {
			setBackground(list.getBackground());
			setForeground(Color.BLACK);
		}
		
		setPreferredSize(new Dimension(220, TEXTURE_HEIGHT + 2));
		setBorder(new EmptyBorder(1, 1, 1, 1));
		
		return this;
	}
	
}
