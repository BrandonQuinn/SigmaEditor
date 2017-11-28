/**
 * Author: Brandon
 * Date Created: 4 Oct. 2017
 * File : TextureJList.java
 */
package elara.editor.ui.customlists;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.DefaultIcons;
import elara.assets.Texture;
import elara.editor.ui.MainWindow;
import elara.project.Assets;
import elara.project.EditingContext;
import elara.project.ProjectContext;
import elara.project.ProjectStruct;

/**
 * TextureJList
 *
 * Description: A JList specifically for Textures
 */
public class TextureJList extends JList<Texture>
	implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private EditingContext editingContext = EditingContext.editingContext();
	private ProjectContext projectContext = ProjectContext.projectContext();

	private MainWindow mainWindow;

	private DefaultListModel<Texture> model = new DefaultListModel<Texture>();

	public TextureJList(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		setCellRenderer(new TextureListRenderer());
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
		// change the editing state and set the selected texture values
		if (getSelectedIndex() != -1) {
			editingContext.assignState(EditingContext.EditingState.TEXTURE_PAINT);
			ArrayList<Texture> loadedTextures = projectContext.loadedTextures();
			// read the texture from disk and set it as the currently selected texture
			Texture loadedTexture = Assets.readTexture(loadedTextures.get(getSelectedIndex()).name());
			editingContext.setSelectedTexture(loadedTexture);
		} else {
			editingContext.assignState(EditingContext.EditingState.SELECT);
		}

		mainWindow.evaluateState();
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
	 *
	 * @see ProjectContext
	 */
	public void loadFromContext()
	{
		model.clear();
		for (Texture texture : projectContext.loadedTextures()) {
				// if the scaled image is null, load the image and scale it
				// then set the image to null to allow the garbage collector
				// to free the memory for the main image so we don't waste space
			BufferedImage newImage = null;
			if (!texture.hasCachedScaledImage()) {
				try {
					newImage = ImageIO.read(new File(projectContext.projectDirectoryFile().getAbsolutePath() + "/"
						+ ProjectStruct.TEXTURE_DIR + "/" + texture.file().getName()));
				} catch (IOException e) {
					newImage = DefaultIcons.BLANK_ICON_16;
				}
			} else {
				newImage = DefaultIcons.BLANK_ICON_16;
			}
			
			texture.assignImage(newImage);
			texture.scaledTo(32, 32); // this will cache the scaled image internally
			texture.assignImage(null); // replace the main image with nothing so that the gc can clear it
			addTexture(texture);
		}
	}
}
