/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : SoundJList.java
 */
package sigma.editor.ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sigma.project.ProjectContext;
import sigma.project.Sound;

/**
 * SoundJList
 *
 * Description: JList of sounds to select from.
 */
public class SoundJList extends JList<Sound> implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<Sound> model = new DefaultListModel<Sound>();
	
	public SoundJList()
	{
		setCellRenderer(new SoundListRenderer());
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
	public void addSound(Sound sound)
	{
		model.addElement(sound);
	}

	/**
	 * Uses the ProjectContext to get a list of loaded textures.
	 * And adds then to the list.
	 * @see ProjectContext
	 */
	public void loadFromContext()
	{
		ProjectContext context = ProjectContext.projectContext();
		for (Sound sound : context.sounds()) {
			addSound(sound);
		}
	}
}