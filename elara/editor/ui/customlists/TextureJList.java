/**
 * Author: Brandon
 * Date Created: 4 Oct. 2017
 * File : TextureJList.java
 */
package elara.editor.ui.customlists;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.Texture;
import elara.project.ProjectContext;

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
		/*
		 * It's very likely here is where the editing state
		 * will change to texture editing where the texture
		 * will then be able to be painted on to the the workspace
		 */
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

	/**
	 * Uses the ProjectContext to get a list of loaded textures.
	 * And adds then to the list.
	 * @see ProjectContext
	 */
	public void loadFromContext()
	{
		ProjectContext context = ProjectContext.projectContext();
		for (Texture texture : context.loadedTextures()) {
			addTexture(texture);
		}
	}
}
