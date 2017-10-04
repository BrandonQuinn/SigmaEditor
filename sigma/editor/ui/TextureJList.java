/**
 * Author: Brandon
 * Date Created: 4 Oct. 2017
 * File : TextureJList.java
 */
package sigma.editor.ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sigma.project.Texture;

/**
 * TextureJList
 *
 * Description: A JList specifically for Textures 
 */
public class TextureJList extends JList<Texture> implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<Texture> model = new DefaultListModel<Texture>();
	
	public TextureJList()
	{
		setCellRenderer(new TextureListRenderer());
		setModel(model);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		// TODO Handle texture selection
	}

	/**
	 * Adds a texture to the list.
	 * 
	 * @param loadedTexture
	 */
	public void addTexture(Texture texture)
	{
		model.addElement(texture);
	}
}
