
package elara.editor.ui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import elara.editor.debug.StackTraceUtil;

/*
 * The big TODO LIST
 * 
 * TODO Threaded texture painting, blending, OpenCL?
 * TODO Texture modifiers like blur, dodge, burn
 * TODO Keyboard shortcuts
 * 
 */

/**
 * A level editor for the "Project Sigma" game.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 8 Jun 2017
 */

public class Main
{

	public static void main(String args[])
	{
		// load the system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			ErrorDialog eDialog = new ErrorDialog("Could not load the system look and feel.",
					StackTraceUtil.stackTraceToString(e));
			eDialog.setVisible(true); // error dialog probably not needed
		}

		initialise();

		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true); // GO!
	}

	/**
	 * Do anything that the editor needs to before showing the interface and
	 * allowing
	 * interaction.
	 */
	private static void initialise()
	{
		
	}
}
