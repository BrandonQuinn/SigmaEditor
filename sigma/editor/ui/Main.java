package sigma.editor.ui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sigma.editor.control.EditorLoader;
import sigma.editor.debug.StackTraceUtil;
import sigma.editor.exception.EditorLoadingException;

/**
 * A level editor for the Sigma game.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 8 Jun 2017
 */

public class Main {
	
	public static void main(String args[]) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			ErrorDialog eDialog = new ErrorDialog("Could not load the system look and feel.", 
					StackTraceUtil.stackTraceToString(e));	
			eDialog.setVisible(true);
		}
		
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
