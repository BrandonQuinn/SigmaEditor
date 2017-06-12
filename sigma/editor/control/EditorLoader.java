package sigma.editor.control;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import sigma.editor.control.conf.EntityConfLoader;
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
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eld.setProgress(100);
	}
}
