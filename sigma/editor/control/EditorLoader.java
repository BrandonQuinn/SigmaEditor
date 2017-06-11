package sigma.editor.control;

import java.io.IOException;

import com.google.gson.JsonIOException;

import sigma.editor.control.conf.EntityConfLoader;
import sigma.editor.debug.StackTraceUtil;
import sigma.editor.exception.EditorLoadingException;
import sigma.editor.ui.EngineLoadingDialog;

/**
 * Load the editor configuration.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class EditorLoader {
	
	/**
	 * Loads the configuration for the editor.
	 * 
	 * @param eld Takes in a dialog which can be used to display the progress of how far
	 * everything has loaded.
	 * @throws EditorLoadingException
	 */
	public static void load(EngineLoadingDialog eld) throws EditorLoadingException {
		EntityConfLoader entityConfLoader = new EntityConfLoader();
		
		try {
			entityConfLoader.load();
		} catch (JsonIOException | IOException e) {
			EditorLoadingException ex = new EditorLoadingException();
			ex.setMessage("Error loading entity configuration from JSON file: " + entityConfLoader.getFilePath()
					+ ", perhaps you could try locating the file and inspecting it for anything that looks incorrect.");
			ex.setStackTraceMessage(StackTraceUtil.stackTraceToString(e));
			
			throw ex;
		}
		
		eld.setProgress(100);
	}
}
