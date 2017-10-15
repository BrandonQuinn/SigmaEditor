
package elara.editor.ui;

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
import elara.editor.ui.dialogs.ErrorDialog;
import elara.editor.ui.dialogs.RecentProjectsDialog;
import elara.editor.util.JSON;
import elara.project.ProjectManager;

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
	private static ProjectManager pc = ProjectManager.manager();
	
	public static void main(String args[])
	{
		System.setProperty("-Dsun.java2d.opengl", "true");
		
		// load the system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			ErrorDialog eDialog = new ErrorDialog("Could not load the system look and feel.",
					StackTraceUtil.stackTraceToString(e));
			eDialog.setVisible(true); // error dialog probably not needed
			return;
		}

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
		// load the configuration for the editor
		try {
			JSONObject editorConfig = JSON.read("conf/editor.config");
			
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
	}
}
