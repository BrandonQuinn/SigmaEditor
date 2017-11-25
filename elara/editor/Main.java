
package elara.editor;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import elara.editor.debug.Debug;
import elara.editor.debug.LogType;
import elara.editor.debug.StackTraceUtil;
import elara.editor.ui.MainWindow;
import elara.editor.ui.dialogs.ErrorDialog;
import elara.editor.ui.dialogs.RecentProjectsDialog;
import elara.editor.util.JSON;
import elara.project.ProjectManager;


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
	private static ProjectManager pc = ProjectManager.manager();
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
		System.setProperty("-Dsun.java2d.opengl", "true");
		setLookAndFeel(false);
		
		// load the configuration for the editor
		try {
			JSONObject editorConfig = JSON.read(Conf.DIR + Conf.EDITOR);
			
			// load recent projects
			JSONArray recentProjects = (JSONArray) editorConfig.get("recentProjects");
			for (Object rpObj : recentProjects) {
				JSONObject recentProject = (JSONObject) rpObj;
				String name = (String) recentProject.get("name");
				String path = (String) recentProject.get("path");
				// pc.addRecentProject(name, path);
			}
		} catch (ParseException | IOException e) {
			Debug.debug.log(LogType.CRITICAL, "Failed to parse project configration JSON");
			JOptionPane.showMessageDialog(null, "Failed to load editor configuration.", 
					"ParseException", JOptionPane.ERROR_MESSAGE);
		}
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
