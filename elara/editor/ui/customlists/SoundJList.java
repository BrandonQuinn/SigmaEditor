/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : SoundJList.java
 */
package elara.editor.ui.customlists;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.Sound;
import elara.editor.ui.MainWindow;
import elara.project.EditingContext;
import elara.project.EditingContext.EditingState;
import elara.project.ProjectContext;

/**
 * SoundJList
 *
 * Description: JList of sounds to select from.
 */
public class SoundJList extends JList<Sound> 
	implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private ProjectContext projectContext = ProjectContext.projectContext();
	private EditingContext editingContext = EditingContext.editingContext();
	
	private DefaultListModel<Sound> model = new DefaultListModel<Sound>();
	
	private MainWindow mainWindow;
	
	public SoundJList(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		setCellRenderer(new SoundListRenderer());
		addListSelectionListener(this);
		setModel(model);
		loadFromContext();
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
		// TODO Handle sound selection
		
		Sound source = model.getElementAt(getSelectedIndex());
		editingContext.assignState(EditingState.ADD_SOUND);
		editingContext.setSelectedSound(source);
		
		mainWindow.evaluateState();
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
		for (Sound sound : projectContext.sounds()) {
			addSound(sound);
		}
	}
}