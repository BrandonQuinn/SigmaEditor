
package elara.editor;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import elara.editor.debug.LogType;
import elara.editor.debug.StackTraceUtil;
import elara.editor.debug.StaticLogs;
import elara.editor.input.InputManager;
import elara.editor.ui.MainWindow;
import elara.editor.ui.dialogs.ErrorDialog;
import elara.editor.ui.dialogs.RecentProjectsDialog;
import elara.editor.util.JSON;
import elara.project.ProjectManager;

/*
 * The big TODO LIST
 * 
 * TODO Texture modifiers like blur, dodge, burn
 * TODO Entity parenting
 * TODO Entity deletion
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
	private static ProjectManager pc = ProjectManager.manager();
	
	// private static final String SYNTH_STYLE_FILE = "res/synth/synthStyle.xml";
	
	public static void main(String args[])
	{
		initialise();
		
		// allow user to open a recent project
		RecentProjectsDialog rpg = new RecentProjectsDialog();
		rpg.setVisible(true);
		
		MainWindow mainWindow = new MainWindow();		
		mainWindow.setVisible(true); // GO!
	}

	/**
	 * Do anything that the editor needs to before showing the interface and
	 * allowing interaction.
	 */
	private static void initialise()
	{
		System.setProperty("-Dsun.java2d.opengl", "true");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | 
				IllegalAccessException | UnsupportedLookAndFeelException e) {
			ErrorDialog eDialog = new ErrorDialog("Could not load the system look and feel.",
					StackTraceUtil.stackTraceToString(e));
			eDialog.setVisible(true); // error dialog probably not needed
			return;
		}
		
		/*try {
			SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
			lookAndFeel.load(new File(SYNTH_STYLE_FILE).toURL());
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (UnsupportedLookAndFeelException | java.text.ParseException | IOException e1) {
			e1.printStackTrace();
		}*/
		
		// load the configuration for the editor
		try {
			JSONObject editorConfig = JSON.read(Conf.DIR + Conf.EDITOR);
			
			// load recent projects
			JSONArray recentProjects = (JSONArray) editorConfig.get("recentProjects");
			
			for (Object rpObj : recentProjects) {
				JSONObject recentProject = (JSONObject) rpObj;
				String name = (String) recentProject.get("name");
				String path = (String) recentProject.get("path");
				pc.addRecentProject(name, path);
			}
					
		} catch (ParseException | IOException e) {
			StaticLogs.debug.log(LogType.CRITICAL, "Failed to parse project configration JSON");
			JOptionPane.showMessageDialog(null, "Failed to load editor configuration.", 
					"ParseException", JOptionPane.ERROR_MESSAGE);
		}
		
		// create and setup input
		InputManager inMan = InputManager.inputManager();
		inMan.logControllerInfo(StaticLogs.debug);
	}
}
