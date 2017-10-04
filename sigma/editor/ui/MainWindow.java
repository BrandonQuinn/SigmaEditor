
package sigma.editor.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import sigma.editor.Constants;
import sigma.editor.debug.LogType;
import sigma.editor.debug.SigmaException;
import sigma.editor.debug.StaticLogs;
import sigma.editor.rendering.RenderUpdateThread;
import sigma.project.AssetLoader;
import sigma.project.EditingContext;
import sigma.project.GameModel;
import sigma.project.ProjectContext;
import sigma.project.ProjectManager;
import sigma.project.Texture;

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

	private ProjectContext projectContext = ProjectContext.projectContext();
	private EditingContext editingContext = EditingContext.editingContext();
	private GameModel gameModel = GameModel.gameModel();

	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private RenderPanel renderPanel;
	private JLabel statusLabel;
	
	private TextureJList textureList;

	/**
	 * Icons
	 */
	private ImageIcon selectToolIcon;
	private ImageIcon moveToolIcon;
	private ImageIcon rotateToolIcon;
	private ImageIcon playIcon;
	private ImageIcon stopIcon;

	/**
	 * Menu Items
	 */
	private JMenuItem newProjectItem;
	private JMenuItem openProjectItem;
	private JMenuItem loadTextureItem;

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

		/*
		 * Initialise Icons
		 */
		selectToolIcon = new ImageIcon("res\\icons\\selectTool.png");
		moveToolIcon = new ImageIcon("res\\icons\\moveTool.png");
		rotateToolIcon = new ImageIcon("res\\icons\\rotateTool.png");
		playIcon = new ImageIcon("res\\icons\\playBtn.png");
		stopIcon = new ImageIcon("res\\icons\\stopBtn.png");

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

		JMenu mnAssets = new JMenu("Assets");
		menuBar.add(mnAssets);

		loadTextureItem = new JMenuItem("Import Texture");
		loadTextureItem.addActionListener(this);
		mnAssets.add(loadTextureItem);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		JButton selectToolBtn = new JButton();
		selectToolBtn.setIcon(selectToolIcon);
		toolBar.add(selectToolBtn);

		JButton moveToolBtn = new JButton();
		moveToolBtn.setIcon(moveToolIcon);
		toolBar.add(moveToolBtn);

		JButton rotateToolBtn = new JButton();
		rotateToolBtn.setIcon(rotateToolIcon);
		toolBar.add(rotateToolBtn);

		JButton playBtn = new JButton();
		playBtn.setIcon(playIcon);
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

		// init combo box
		comboBox.addItem("Texture");

		leftSideNorthPanel.add(comboBox,
				BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		leftSideNorthPanel.add(scrollPane, BorderLayout.CENTER);

		textureList = new TextureJList();
		textureList.setVisibleRowCount(5);
		scrollPane.setViewportView(textureList);

		JPanel leftSideSouthPanel = new JPanel();
		leftSideSouthPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		leftSplitPane.setRightComponent(leftSideSouthPanel);
		leftSideSouthPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblProperties = new JLabel("Properties");
		lblProperties.setHorizontalAlignment(
				SwingConstants.CENTER);
		lblProperties.setFont(new Font("Tahoma", Font.BOLD,
				11));
		leftSideSouthPanel.add(lblProperties,
				BorderLayout.NORTH);

		JPanel propertiesPanel = new JPanel();
		leftSideSouthPanel.add(propertiesPanel, BorderLayout.CENTER);
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.X_AXIS));

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

		renderPanel = new RenderPanel();

		/*
		 * Create the thread which will be used to continuously re-render the editor
		 * frame.
		 */
		RenderUpdateThread renUpThread = new RenderUpdateThread(
				renderPanel);
		Thread thread = new Thread(renUpThread);

		splitPane_1.setLeftComponent(renderPanel);
		splitPane_1.setRightComponent(rightSplitPane);

		JPanel layerPanel = new JPanel();
		rightSplitPane.setLeftComponent(layerPanel);
		layerPanel.setLayout(new BorderLayout(0, 0));

		JToolBar layerToolBar = new JToolBar();
		layerToolBar.setFloatable(false);
		layerPanel.add(layerToolBar, BorderLayout.SOUTH);

		List list = new List();
		layerPanel.add(list, BorderLayout.CENTER);

		JLabel lblLayers = new JLabel("Layers");
		lblLayers.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLayers.setHorizontalAlignment(SwingConstants.CENTER);
		layerPanel.add(lblLayers, BorderLayout.NORTH);

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusLabel = new JLabel("No project loaded.");
		statusPanel.add(statusLabel);
		contentPane.add(statusPanel, BorderLayout.SOUTH);

		thread.start();

		StaticLogs.debug.log(LogType.INFO, "Update thread started");
		StaticLogs.debug.log(LogType.INFO, "Main Window created");
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
			// created and directorie and files are also created even without
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
						npd.projectLocation(),
						npd.worldWidth(),
						npd.worldHeight());

				waitingDialog.changeMessageTo("Opening project...");

				manager.open(npd.projectLocation() + "/" + npd.projectName(),
						editingContext,
						projectContext,
						gameModel);

				waitingDialog.setVisible(false);
			
				statusLabel.setText(npd.projectName() + " | " + npd.worldWidth() + "x" + npd
						.worldHeight());
			} catch (SigmaException e1) {
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

			// show a file dialog and get the chosen project directory
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open Project");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int fcResponse = fc.showOpenDialog(null);

			WaitingDialog waitingDialog = new WaitingDialog("Opening project...");
			waitingDialog.setVisible(true);

			if (fcResponse == JFileChooser.APPROVE_OPTION) {
				File projectDirectory = fc.getSelectedFile();
				ProjectManager manager = ProjectManager.manager();

				try {
					manager.open(projectDirectory.getAbsolutePath(),
							editingContext,
							projectContext,
							gameModel);
				} catch (SigmaException e1) {
					waitingDialog.setVisible(false);
					StaticLogs.debug.log(LogType.CRITICAL,
							"Failed to open project: " + e1.message());
					JOptionPane.showMessageDialog(null,
							"Failed to load project: " + e1.message()
									+ "\nCheck the logs.",
							"Failure to Load",
							JOptionPane.ERROR_MESSAGE);
				}
				
				statusLabel.setText(ProjectContext.projectContext().projectName() + " | " + GameModel
						.gameModel().worldWidth() + "x" + GameModel.gameModel().worldHeight());
			}

			waitingDialog.setVisible(false);
		} 
		
		/*==============================================*
		 * LOAD TEXTURE
		 *==============================================*/
		
		else if (source == loadTextureItem) {
			if (projectContext.isProjectLoaded()) {
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
						Texture loadedTexture = AssetLoader.loadTexture(texName, selectedImage);
						
						// add the new texture to the JList
						textureList.addTexture(loadedTexture);
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null,
								"Failed to load texture",
								"IOException",
								JOptionPane.ERROR_MESSAGE);
						StaticLogs.debug.log(LogType.ERROR,
								"Failed to load texture, " + e1.getMessage());
						return;
					}
				}
			} else { // no project is loaded, show a warning
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
			
			if (selectedItem.equals("Texture")) {
				// swap the left panel list to display the texture list
			}
			
		}
	}
}
