
package elara.editor.ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import elara.assets.AssetSelector;
import elara.assets.DefaultIcons;
import elara.assets.Entity;
import elara.assets.Sound;
import elara.assets.Texture;
import elara.editor.Constants;
import elara.editor.debug.Debug;
import elara.editor.debug.ElaraException;
import elara.editor.debug.LogType;
import elara.editor.rendering.RenderUpdateThread;
import elara.editor.ui.codeeditor.CodeEditor;
import elara.editor.ui.customlists.AssetsJList;
import elara.editor.ui.customlists.DecalJList;
import elara.editor.ui.customlists.LayerJList;
import elara.editor.ui.customlists.SoundJList;
import elara.editor.ui.customlists.TextureJList;
import elara.editor.ui.dialogs.AboutDialog;
import elara.editor.ui.dialogs.NewProjectDialog;
import elara.editor.ui.dialogs.SceneDialog;
import elara.editor.ui.dialogs.TextureLayerDialog;
import elara.editor.ui.dialogs.WaitingDialog;
import elara.editor.ui.propertiespanels.DecalPropsPanel;
import elara.editor.ui.propertiespanels.SoundPropsPanel;
import elara.editor.ui.propertiespanels.TexturePropsPanel;
import elara.project.AssetLoader;
import elara.project.EditingContext;
import elara.project.ProjectContext;
import elara.project.ProjectManager;
import elara.scene.SceneLayer;

/**
 * The main window which holds everything.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class MainWindow extends JFrame implements
		ActionListener
{
	private static final long serialVersionUID = 1L;

	private ProjectContext projCon = ProjectContext.projectContext();
	private EditingContext editCon = EditingContext.editingContext();
	private ProjectManager projMan = ProjectManager.manager();

	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private RenderPanel renderPanel;
	private JLabel statusLabel;
	private JScrollPane leftScrollPane;
	private JPanel propertiesPanel;
	
	/**
	 * Misc panels or components needed
	 */
	private TexturePropsPanel texturePropertiesPanel = new TexturePropsPanel();
	private SoundPropsPanel soundPropertiesPanel = new SoundPropsPanel();
	private DecalPropsPanel decalPropertiesPanel = new DecalPropsPanel();
	private AssetsJList assetsList = new AssetsJList();
	
	/**
	 * Buttons
	 */
	private JButton selectionToolBtn;
	private JButton newLayerBtn;
	private JButton deleteLayerBtn;
	private JButton textureLayersBtn;
	
	/**
	 * Asset JLists
	 */
	private TextureJList textureList;
	private SoundJList soundList;
	private LayerJList layerList;
	private DecalJList decalList;

	/**
	 * Menu Items
	 */
	private JMenuItem newProjectItem;
	private JMenuItem openProjectItem;
	private JMenuItem saveProjectItem;
	private JMenuItem loadTextureItem;
	private JMenuItem loadDecalItem;
	private JMenuItem loadSoundItem;
	private JMenuItem openCodeEditorItem;
	private JMenuItem sceneMenuItem;
	private JMenuItem aboutItem;
	
	/**
	 * Get the default toolkit to resize the application.
	 */
	private Toolkit toolkit = Toolkit.getDefaultToolkit();

	/**
	 * Create the frame.
	 */
	public MainWindow()
	{
		setTitle(Constants.EDITOR_TITLE + " "
				+ Constants.EDITOR_VERSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int) (toolkit.getScreenSize().width / 1.3f),
				(int) (toolkit.getScreenSize().height / 1.3f));
		setLocationRelativeTo(null);
		setIconImage(DefaultIcons.windowIcon.getImage());

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		newProjectItem = new JMenuItem("New Project...");
		newProjectItem.addActionListener(this);
		mnFile.add(newProjectItem);

		openProjectItem = new JMenuItem("Open Project...");
		openProjectItem.addActionListener(this);
		mnFile.add(openProjectItem);
		
		saveProjectItem = new JMenuItem("Save");
		saveProjectItem.addActionListener(this);
		mnFile.add(saveProjectItem);

		JMenu mnAssets = new JMenu("Assets");
		menuBar.add(mnAssets);

		loadTextureItem = new JMenuItem("Import Texture");
		loadTextureItem.addActionListener(this);
		mnAssets.add(loadTextureItem);
		
		loadDecalItem = new JMenuItem("Import Decal");
		loadDecalItem.addActionListener(this);
		mnAssets.add(loadDecalItem);
		
		loadSoundItem = new JMenuItem("Import Sound");
		loadSoundItem.addActionListener(this);
		mnAssets.add(loadSoundItem);

		JMenu codeMenu = new JMenu("Scripting");
		openCodeEditorItem = new JMenuItem("Script Editor"); 
		openCodeEditorItem.addActionListener(this);
		codeMenu.add(openCodeEditorItem);
		menuBar.add(codeMenu);
		
		JMenu sceneMenu = new JMenu("Scenes");
		sceneMenu.addActionListener(this);
		
		sceneMenuItem = new JMenuItem("Edit Scenes");
		sceneMenuItem.addActionListener(this);
		sceneMenu.add(sceneMenuItem);
		menuBar.add(sceneMenu);
		
		JMenu mnHelp = new JMenu("Help");
		
		aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(this);
		mnHelp.add(aboutItem);
		menuBar.add(mnHelp);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		selectionToolBtn = new JButton();
		selectionToolBtn.addActionListener(this);
		selectionToolBtn.setIcon(DefaultIcons.selectToolIcon);
		toolBar.add(selectionToolBtn);

		JButton moveToolBtn = new JButton();
		moveToolBtn.setIcon(DefaultIcons.moveToolIcon);
		toolBar.add(moveToolBtn);

		JButton rotateToolBtn = new JButton();
		rotateToolBtn.setIcon(DefaultIcons.rotateToolIcon);
		toolBar.add(rotateToolBtn);

		JButton playBtn = new JButton();
		playBtn.setIcon(DefaultIcons.playIcon);
		toolBar.add(playBtn);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JSplitPane leftSplitPane = new JSplitPane();
		splitPane.setLeftComponent(leftSplitPane);
		leftSplitPane.setContinuousLayout(true);
		leftSplitPane.setResizeWeight(0.5);
		leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel leftSideNorthPanel = new JPanel();
		leftSplitPane.setLeftComponent(leftSideNorthPanel);
		leftSideNorthPanel.setLayout(new BorderLayout(0, 0));

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(this);

		// init combo box
		comboBox.addItem("Texture Painting");
		comboBox.addItem("Spawn Points");
		comboBox.addItem("Sounds");
		comboBox.addItem("Decals");
		
		leftSideNorthPanel.add(comboBox,BorderLayout.NORTH);

		leftScrollPane = new JScrollPane();
		leftSideNorthPanel.add(leftScrollPane, BorderLayout.CENTER);

		textureList = new TextureJList(this);
		textureList.setVisibleRowCount(5);
		leftScrollPane.setViewportView(textureList);
		
		soundList = new SoundJList(this);
		layerList = new LayerJList(this);
		decalList = new DecalJList(this);
		
		JPanel leftSideSouthPanel = new JPanel();
		leftSideSouthPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		leftSplitPane.setRightComponent(leftSideSouthPanel);
		leftSideSouthPanel.setLayout(new BorderLayout(0, 0));

		propertiesPanel = new JPanel();
		leftSideSouthPanel.add(propertiesPanel, BorderLayout.CENTER);
		propertiesPanel.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setContinuousLayout(true);
		splitPane_1.setResizeWeight(0.9);
		panel.add(splitPane_1, BorderLayout.CENTER);

		JSplitPane rightSplitPane = new JSplitPane();
		rightSplitPane.setContinuousLayout(true);

		rightSplitPane.setResizeWeight(0.5);
		rightSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		renderPanel = new RenderPanel(this);

		/*
		 * Create the thread which will be used to continuously re-render the editor
		 * frame.
		 */
		RenderUpdateThread renUpThread = new RenderUpdateThread(renderPanel);
		Thread thread = new Thread(renUpThread);
		thread.setName("Render Update Thread");

		splitPane_1.setLeftComponent(renderPanel);
		splitPane_1.setRightComponent(rightSplitPane);

		JPanel layerPanel = new JPanel();
		rightSplitPane.setLeftComponent(layerPanel);
		layerPanel.setLayout(new BorderLayout(0, 0));
		
		rightSplitPane.setRightComponent(new JScrollPane(assetsList));

		JToolBar layerToolBar = new JToolBar();
		layerToolBar.setFloatable(false);
		
		// layer toolbar buttons
		newLayerBtn = new JButton(DefaultIcons.newLayerIcon);
		newLayerBtn.setToolTipText("Create object layer");
		newLayerBtn.addActionListener(this);
		deleteLayerBtn = new JButton(DefaultIcons.deleteLayerIcon);
		deleteLayerBtn.setToolTipText("Delete object layer");
		deleteLayerBtn.addActionListener(this);
		textureLayersBtn = new JButton(DefaultIcons.textureLayersIcon);
		textureLayersBtn.addActionListener(this);
		textureLayersBtn.setToolTipText("Create or delete texture layers");
		
		layerToolBar.add(newLayerBtn);
		layerToolBar.add(deleteLayerBtn);
		layerToolBar.add(textureLayersBtn);
		layerPanel.add(layerToolBar, BorderLayout.SOUTH);
		layerPanel.add(layerList, BorderLayout.CENTER);

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusLabel = new JLabel("No project loaded.");
		statusPanel.add(statusLabel);
		contentPane.add(statusPanel, BorderLayout.SOUTH);

		thread.start();

		Debug.debug.log(LogType.INFO, "Update thread started");
		Debug.debug.log(LogType.INFO, "Main Window created");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		/*==============================================*
		 * CREATE NEW PROJECT
		 *==============================================*/
		
		if (source == newProjectItem) {
			NewProjectDialog npd = new NewProjectDialog();
			npd.setVisible(true);

			// fixes error when clicking x causing waiting dialog to still be
			// created and directoriy and files are also created even without
			// complete information
			if (!npd.isConfirmed()) {
				return;
			}

			if (!npd.isComplete()) {
				JOptionPane.showMessageDialog(null,
						"One or more field were incorrect or blank.",
						"Bad Values",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			WaitingDialog waitingDialog = new WaitingDialog("Creating new project...");
			waitingDialog.setVisible(true);

			// create a new project context which is how the editor knows where everything is
			// and keeps tracks of assets etc.
			ProjectManager manager = ProjectManager.manager();
			try {
				manager.createNewProject(npd.projectName(),
						npd.projectLocation());

				waitingDialog.changeMessageTo("Opening project...");
				manager.open(npd.projectLocation() + "/" + npd.projectName());
				textureList.loadFromContext();
				waitingDialog.setVisible(false);
			
				statusLabel.setText(npd.projectName());
			} catch (ElaraException e1) {
				waitingDialog.setVisible(false);
				
				JOptionPane.showMessageDialog(null,
						"Could not create project. " + e1.getMessage()
								+ "\nCheck the logs.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} 
		
		/*==============================================*
		 * OPEN EXISTING PROJECT
		 *==============================================*/
		
		else if (source == openProjectItem) {
			WaitingDialog waitingDialog = new WaitingDialog("Opening project...");
			
			// show a file dialog and get the chosen project directory
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open Project");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int fcResponse = fc.showOpenDialog(null);
			if (fcResponse == JFileChooser.APPROVE_OPTION) {
				File projectDirectory = fc.getSelectedFile();

				try {
					waitingDialog.setVisible(true);
					projMan.open(projectDirectory.getAbsolutePath());
					textureList.loadFromContext();
					soundList.loadFromContext();
				} catch (ElaraException  e1) {
					waitingDialog.setVisible(false);
					
					Debug.debug.log(LogType.CRITICAL,
							"Failed to open project: " + e1.getMessage());
					JOptionPane.showMessageDialog(null,
							"Failed to load project: " + e1.getMessage()
									+ "\nCheck the logs.",
							"Failure to Load",
							JOptionPane.ERROR_MESSAGE);
				}
				
				statusLabel.setText(ProjectContext.projectContext().projectName());
			}
			
			waitingDialog.setVisible(false);
		} 
		
		/*==============================================*
		 * SAVE PROJECT
		 *==============================================*/
		
		else if (source == saveProjectItem) {
			WaitingDialog waitingDialog = new WaitingDialog("Saving project...");
			waitingDialog.setVisible(true);
			
			try {
				projMan.save();
			} catch (ElaraException | NullPointerException ex) {
				waitingDialog.setVisible(false);
				Debug.debug.log(LogType.CRITICAL,
						"Failed to save project: " + ex.getMessage());
				JOptionPane.showMessageDialog(null,
						"Failed to save project: " + ex.getMessage()
								+ "\nCheck the logs.",
						"Save failed",
						JOptionPane.ERROR_MESSAGE);
			}
			
			waitingDialog.setVisible(false);
		}
		
		/*==============================================*
		 * LOAD TEXTURE
		 *==============================================*/
		
		else if (source == loadTextureItem) {
			if (projCon.isProjectLoaded()) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Import Texture");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);

				FileNameExtensionFilter textureFilter = new FileNameExtensionFilter("Textures", "jpg", "jpeg", "png");
				fc.addChoosableFileFilter(textureFilter);

				int fcResponse = fc.showOpenDialog(null);
				if (fcResponse == JFileChooser.APPROVE_OPTION) {
					File selectedImage = fc.getSelectedFile();
					String texName = JOptionPane.showInputDialog(null,
							"Enter the name of the texture",
							"Texture name",
							JOptionPane.PLAIN_MESSAGE);

					try {
						// add the texture to the project and to the texture list in the GUI
						Texture loadedTexture = AssetLoader.loadTexture(texName, selectedImage);
						textureList.addTexture(loadedTexture);
						projMan.importTexture(texName, selectedImage);
					} catch (ElaraException e2) {
						JOptionPane.showMessageDialog(null,
								"Failed to load texture",
								"IOException | SigmaException",
								JOptionPane.ERROR_MESSAGE);
						Debug.debug.log(LogType.ERROR,
								"Failed to load texture, " + e2.getMessage());
						return;
					}
				}
				
				setTitle(Constants.EDITOR_TITLE + " | " + projCon.projectName());
			} else { 
				// no project is loaded, show a warning
				JOptionPane.showMessageDialog(null,
						"There is currently no project loaded.",
						"No Project",
						JOptionPane.ERROR_MESSAGE);
			}
		} 
		
		/*==============================================*
		 * LOAD DECAL
		 *==============================================*/
		
		else if (source == loadDecalItem) {
			if (projCon.isProjectLoaded()) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Import dECAL");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);

				FileNameExtensionFilter decalFilter = new FileNameExtensionFilter("Decals", "jpg", "jpeg", "png");
				fc.addChoosableFileFilter(decalFilter);

				int fcResponse = fc.showOpenDialog(null);
				if (fcResponse == JFileChooser.APPROVE_OPTION) {
					File selectedImage = fc.getSelectedFile();
					String decalName = JOptionPane.showInputDialog(null,
							"Enter the name of the decal",
							"Decal name",
							JOptionPane.PLAIN_MESSAGE);

					// add the texture to the project and to the texture list in the GUI
					Texture decal = projMan.importDecal(decalName, selectedImage);
					decalList.addDecal(decal);
				}
				
				setTitle(Constants.EDITOR_TITLE + " | " + projCon.projectName());
			} else { 
				// no project is loaded, show a warning
				JOptionPane.showMessageDialog(null,
						"There is currently no project loaded.",
						"No Project",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		/*==============================================*
		 * LOAD SOUND
		 *==============================================*/
		
		else if (source == loadSoundItem) {
			if (projCon.isProjectLoaded()) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Import Sound");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				
				FileNameExtensionFilter textureFilter = new FileNameExtensionFilter("Sounds", "ogg", "wav");
				fc.addChoosableFileFilter(textureFilter);
				
				int choice = fc.showOpenDialog(this);
				if (choice == JFileChooser.APPROVE_OPTION) {
					File sourceSoundFile = fc.getSelectedFile();
					
					String soundName = JOptionPane.showInputDialog(null,
							"Enter the name of the sound",
							"Sound name",
							JOptionPane.PLAIN_MESSAGE);
					
					Sound sound = new Sound(soundName, sourceSoundFile.getName());
					soundList.addSound(sound);
					
					try {
						projMan.importSound(soundName, sourceSoundFile);
					} catch (ElaraException e1) {
						JOptionPane.showMessageDialog(null,
								e1,
								"Exception",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				// no project is loaded, show a warning
				JOptionPane.showMessageDialog(null,
						"There is currently no project loaded.",
						"No Project",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		/*==============================================*
		 * SWAP LISTS
		 *==============================================*/
		
		else if (source == comboBox) {
			String selectedItem = (String) comboBox.getSelectedItem();
			
			if (leftScrollPane != null) {
				if (selectedItem.equals("Texture Painting")) {
					leftScrollPane.setViewportView(textureList);
				} else if (selectedItem.equals("Sounds")) {
					leftScrollPane.setViewportView(soundList);
				} else if (selectedItem.equals("Decals")) {
					leftScrollPane.setViewportView(decalList);
				}
			}
		} 
		
		/*==============================================*
		 * LAYER MANAGEMENT
		 *==============================================*/
		
		else if (source == textureLayersBtn) {
			TextureLayerDialog tld = new TextureLayerDialog();
			tld.setVisible(true);
		} 
		
		else if (source == newLayerBtn) {
			if (projCon.isProjectLoaded()) {
				String name = JOptionPane.showInputDialog(this, "Enter layer name.");
				SceneLayer newLayer = new SceneLayer(editCon.scene(), name);
				editCon.scene().addLayer(newLayer);
				layerList.addLayer(newLayer);
				layerList.setSelectedIndex(layerList.getModel().getSize() - 1);
				editCon.setSelectedLayer(newLayer.id());
			} else {
				JOptionPane.showMessageDialog(this, "No project loaded", "No project",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		else if (source == deleteLayerBtn) {
			if (editCon.scene().numLayers() > 0 && layerList.getSelectedIndex() != -1) {
				editCon.scene().deleteLayer(layerList.getSelectedValue());
				layerList.removeLayer(layerList.getSelectedIndex());
				layerList.setSelectedIndex(layerList.getModel().getSize() - 1);
			}
		} 
		
		/*==============================================*
		 * SCENE MENU
		 *==============================================*/
		
		else if (source == sceneMenuItem) {
			if (projCon.isProjectLoaded()) {
				SceneDialog sd = new SceneDialog();
				sd.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "No project loaded.", "No Project", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		} 
		
		/*==============================================*
		 * TOOL BAR
		 *==============================================*/
		
		else if (source == selectionToolBtn) {
			editCon.assignState(EditingContext.EditingState.SELECT);
			textureList.setSelectedIndices(new int[] {});
		} 
		
		/*==============================================*
		 * CODE/SCRIPTS MENU
		 *==============================================*/
		
		else if (source == openCodeEditorItem) {
			CodeEditor codeEditor = new CodeEditor();
			codeEditor.setVisible(true);
		}
		
		/*==============================================*
		 * HELP MENU
		 *==============================================*/
		
		else if (source == aboutItem) {
			AboutDialog ab = new AboutDialog();
			ab.setVisible(true);
		}
	}
	
	/**
	 * Changes the interface to reflect the current state it's in.
	 * If you plan on calling this method, do not do it too frequently,
	 * like for example, in the render loop or in a certain state of the
	 * render loop, only do it under specifically, uncommon circumstances like
	 * a key was pressed. It will block all interaction with the interface if
	 * called too frequently.
	 */
	public void evaluateState()
	{
		switch(editCon.state()) {
			case SELECT:
				
				/*
				 * This nice little feature makes sure that if we are in select mode
				 * and selection objects that we can modify the properties of
				 * any single selected entity.
				 * 
				 * It will replace the properties panel to be for the currently selected
				 * object.
				 * 
				 * Perhaps I'll soo make it so that as long as the entities
				 * selected are all the same then you can en-masse change properties
				 * of all selected entities.
				 */
				Iterator<Entity> iter = AssetSelector.selectedEntities().iterator();
				if (AssetSelector.selectedEntities().size() == 1) {
					Entity entity = iter.next();
					
					if (entity instanceof Sound) {
						propertiesPanel.removeAll();
						propertiesPanel.add(soundPropertiesPanel, BorderLayout.CENTER);
						propertiesPanel.updateUI();
						editCon.setSelectedSound((Sound)entity);
					} else {
						propertiesPanel.removeAll();
						propertiesPanel.add(new JPanel(), BorderLayout.CENTER);
						propertiesPanel.updateUI();
					}
				} else if (propertiesPanel != null) {
					propertiesPanel.removeAll();
					propertiesPanel.add(new JPanel(), BorderLayout.CENTER);
					propertiesPanel.updateUI();
				}
				
			break;
			
			case TEXTURE_PAINT:
				propertiesPanel.removeAll();
				propertiesPanel.add(texturePropertiesPanel, BorderLayout.CENTER);
				propertiesPanel.updateUI();
			break;
			
			case MOVE_WORLD:
			break;
			
			case ADD_SOUND:
				propertiesPanel.removeAll();
				propertiesPanel.add(soundPropertiesPanel, BorderLayout.CENTER);
				propertiesPanel.updateUI();
			break;

			case DECAL_PLACEMENT:
				propertiesPanel.removeAll();
				propertiesPanel.add(decalPropertiesPanel, BorderLayout.CENTER);
				propertiesPanel.updateUI();
			break;
			
			default:
			break;
		}

		assetsList.loadFromContext();
		texturePropertiesPanel.assignOpacity((int)(editCon.textureBrushOpacity() * 100.0f));
		decalPropertiesPanel.assignRotation(editCon.decalRotation());
	}
}
