package sigma.editor.ui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

/**
 * Contains all the main components that make up the main application.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 8 Jun 2017
 */

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Main components that are a part of the window.
	 */
	
	private JMenuBar menuBar;
	
	/**
	 * Menus and their items.
	 */
	
	private JMenu fileMenu;
	private JMenuItem newProjItem;
	private JMenuItem openProjItem;
	private JMenuItem saveAsItem;
	private JMenuItem saveItem;
	
	/**
	 * CanvasPanel, this is the panel that actual renders a level or at least, an as-accurate-as-possible
	 * representation of how the level will look like, since it's not being rendered the the actual games engine.
	 * There will be no lighting or behaviour, just simply placeables.
	 */
	
	private CanvasPanel canvas;
	
	/**
	 * Status bar at the bottom of the window.
	 */
	
	private JPanel statusPanel;
	private JLabel statusLabel;
	
	public MainPanel() {
		setLayout(new BorderLayout());
		
		initMenuBar();
		initCanvas();
		initStatus();
		
		add(menuBar, BorderLayout.NORTH);
		add(canvas, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Simply setup all the menus and their names etc.
	 */
	private void initMenuBar() {
		menuBar = new JMenuBar();
		
		// file menu
		
		fileMenu = new JMenu("File");
		newProjItem = new JMenuItem("New Project...");
		openProjItem = new JMenuItem("Open Project...");
		saveAsItem = new JMenuItem("Save As...");
		saveItem = new JMenuItem("Save");
				
		// add file menu components together
		
		fileMenu.add(newProjItem);
		fileMenu.add(openProjItem);
		fileMenu.add(new JSeparator());
		fileMenu.add(saveAsItem);
		fileMenu.add(saveItem);
		
		// finally add the menus to the menu bar
		
		menuBar.add(fileMenu);
	}
	
	/**
	 * Initialise the canvas which displays the items that will appear in game. The editor
	 * window.
	 */
	
	private void initCanvas() {
		canvas = new CanvasPanel();
	}
	
	/**
	 * Initialise the status bar which shows up at the bottom of the main window.
	 * It's used just for showing what the editor is currently doing.
	 */
	
	private void initStatus() {
		statusPanel = new JPanel();
		statusLabel = new JLabel("Status: ");
		
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		
		statusPanel.add(statusLabel);
	}
}
