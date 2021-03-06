
package elara.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;
import org.json.simple.parser.ParseException;
import elara.editor.debug.Debug;
import elara.editor.debug.StackTraceUtil;
import elara.editor.ui.MainWindow;
import elara.editor.ui.dialogs.ErrorDialog;
import elara.editor.ui.dialogs.RecentProjectsDialog;


/*
 * The big TODO LIST
 *
 * NOTE(brandon) Texture modifiers like blur, dodge, burn
 * NOTE(brandon) Entity parenting
 * NOTE(brandon) Entity deletion
 * NOTE(brandon) Falloff graph (linear to start, then Quad Curves)
 * NOTE(brandon) Fixed recent projects
 * NOTE(brandon) Test asset loading and importing
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
	private static final String SYNTH_STYLE_FILE = "res/synth/synthStyle.xml";

	public static void main(String args[])
	{
		initialise();

		// allow user to open a recent project
		RecentProjectsDialog rpg = new RecentProjectsDialog();
		rpg.setVisible(true);

		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}

	/**
	 * Do anything that the editor needs to before showing the interface and
	 * allowing interaction.
	 */
	private static void initialise()
	{
		try {
			EditorConfiguration.init();
		} catch (FileNotFoundException e) {
			Debug.error("Could not find editor configuration file");
		} catch (IOException e) {
			Debug.error("Could not open editor configuration file");
		} catch (ParseException e) {
			Debug.error("Failed to parse editor configuration, please check the JSON file");
		}
		
		System.setProperty("-Dsun.java2d.opengl", "true");
		setLookAndFeel(false);
	}

	/**
	 * Set the look and feel.
	 */
	@SuppressWarnings("deprecation")
	private static void setLookAndFeel(boolean useCustom)
	{
		// NOTE(brandon) Custom look and feel needs work
		if (useCustom) { // custom loaded from images loaded from synthStyle.xml
			try {
				SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
				lookAndFeel.load(new File(SYNTH_STYLE_FILE).toURL());
				UIManager.setLookAndFeel(lookAndFeel);
			} catch (UnsupportedLookAndFeelException | java.text.ParseException | IOException e1) {
				e1.printStackTrace();
			}
		} else { // system look and feel
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException |
				IllegalAccessException | UnsupportedLookAndFeelException e) {
				ErrorDialog eDialog = new ErrorDialog("Could not load the system look and feel.",
					StackTraceUtil.stackTraceToString(e));
					eDialog.setVisible(true); // error dialog probably not needed
				return;
			}
		}
	}
}
