package sigma.editor.control;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * Super class for all classes that load configurations from disk to a data model
 * or a class that is essentially a basic data structure under sigma.editor.model
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 14 Jun 2017
 */

public abstract class ConfLoader {
	public abstract void load() throws FileNotFoundException, 
										IOException, 
										ParseException;
	public abstract void save() throws IOException;
	public abstract String getFilePath();
}
