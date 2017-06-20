package sigma.editor.ui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sigma.editor.control.EditorLoader;
import sigma.editor.debug.EditorLoadingException;
import sigma.editor.debug.StackTraceUtil;

/**
 * A level editor for the "Project Sigma" game.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 8 Jun 2017
 */

public class Main {
	
	public static void main(String args[]) {
		
		/*
		 * Load system look and feel, not the java cross platform one.
		 */
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			ErrorDialog eDialog = new ErrorDialog("Could not load the system look and feel.", 
					StackTraceUtil.stackTraceToString(e));	
			eDialog.setVisible(true);
		}
		
		/*
		 * Load the configurations and anything else that needs loading before the editor
		 * recieves input.
		 */
		
		EngineLoadingDialog eld = new EngineLoadingDialog();
		eld.setVisible(true);
		
		try {
			EditorLoader.load(eld);
		} catch (EditorLoadingException e) {
			ErrorDialog eDialog = new ErrorDialog(e.getMessage(), e.getStackTraceMessage());
			eDialog.setVisible(true);
		}
		
		eld.setVisible(false);
		
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
}
