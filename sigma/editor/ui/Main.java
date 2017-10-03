
package sigma.editor.ui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sigma.editor.config.loaders.EditorLoader;
import sigma.editor.debug.EditorLoadingException;
import sigma.editor.debug.LogType;
import sigma.editor.debug.StackTraceUtil;
import sigma.editor.debug.StaticLogs;

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
		EngineLoadingDialog eld = new EngineLoadingDialog();
		eld.setVisible(true);

		// start loading editor configs and anything else needed
		try {
			StaticLogs.debug.log(LogType.INFO, "Loading editor...");
			EditorLoader.load(eld);
			StaticLogs.debug.log(LogType.INFO, "Editor done loading.");
		} catch (EditorLoadingException e) {
			ErrorDialog eDialog = new ErrorDialog(e.getMessage(), e.stackTraceMessage());
			eDialog.setVisible(true);
		}

		eld.setVisible(false);
	}
}
