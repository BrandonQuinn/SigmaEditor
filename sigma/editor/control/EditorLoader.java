package sigma.editor.control;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import sigma.editor.debug.EditorLoadingException;
import sigma.editor.debug.StackTraceUtil;
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
	 * Loads the configuration for the editor and updates loading dialog as it goes.
	 * 
	 * @param eld Takes in a dialog which can be used to display the progress of how far
	 * everything has loaded.
	 * @throws EditorLoadingException
	 */
	public static void load(EngineLoadingDialog eld) throws EditorLoadingException {
		// load general config
		GeneralConfLoader generalConfigLoader = new GeneralConfLoader();
		
		eld.setText("Loading entity configuration");
		
		try {
			generalConfigLoader.load();
		} catch (IOException | ParseException e) {
			EditorLoadingException ex = new EditorLoadingException();
			ex.setMessage("Could not load general configuration.");
			ex.setStackTraceMessage(StackTraceUtil.stackTraceToString(e));
			throw ex;
		}
		
		eld.setProgress(50);
		eld.setText("Loading entity configuration");
		
		// load entity config
		EntityConfLoader entityConfLoader = new EntityConfLoader();
		try {
			entityConfLoader.load();
		} catch (IOException | ParseException e) {
			EditorLoadingException ex = new EditorLoadingException();
			ex.setMessage("Could not load entity configuration.");
			ex.setStackTraceMessage(StackTraceUtil.stackTraceToString(e));
			throw ex;
		}

		eld.setProgress(100);
	}
}
