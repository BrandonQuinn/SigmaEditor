package sigma.editor.ui;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sigma.editor.Constants;

/**
 * The main window that shows for the editor.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 8 Jun 2017
 */

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Toolkit for getting window information.
	 */
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	/**
	 * The main application panel, it contains pretty much everything.
	 */
	private MainPanel mainPanel;
	
	public MainWindow() {
		setTitle(Constants.EDITOR_TITLE);
		setSize((int)(toolkit.getScreenSize().width / 1.5f), (int)(toolkit.getScreenSize().height / 1.5f));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "Could not load system look and feel.", 
					"SystemLookAndFeel not loadable", JOptionPane.OK_OPTION);
		}
		
		mainPanel = new MainPanel();
		this.add(mainPanel);
	}
}
