/**
 * Author: Brandon
 * Date Created: 4 Oct. 2017
 * File : TextureListRenderer.java
 */
package sigma.editor.ui;

import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import sigma.editor.debug.LogType;
import sigma.editor.debug.StaticLogs;
import sigma.project.Texture;

/**
 * TextureListRenderer
 *
 * Description: Custom renderer for a JList of Texture instances.
 */
public class TextureListRenderer extends JLabel implements ListCellRenderer<Texture>
{
	private static final long serialVersionUID = 1L;
	
	private static final int TEXTURE_WIDTH = 64;
	private static final int TEXTURE_HEIGHT = 64;
	
	public TextureListRenderer()
	{
		setOpaque(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Texture> list,
			Texture texture,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{
		
		// get the texture at a specific size
		// FIXME don't scale every cell render
		BufferedImage scaledImage = texture.scaledTo(TEXTURE_WIDTH, TEXTURE_HEIGHT);
		
		if (scaledImage == null) {
			StaticLogs.debug.log(LogType.ERROR, "Failed to scale texture: " + texture.name() + ", it's null");
		} else {
			setIcon(new ImageIcon(scaledImage));
		}
		
		setText(texture.name());
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
		} else {
			setBackground(list.getBackground());
		}
				
		return this;
	}
	
}
