/**
 * Author: Brandon
 * Date Created: 12 Oct. 2017
 * File : ConfigReader.java
 */
package elara.editor.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * ConfigReader
 *
 * Description: Provides methods for reading and writing JSON 
 * configuration files.
 */
public class JSON
{
	public static JSONObject read(File file) throws ParseException, FileNotFoundException, IOException 
	{
		if (file.exists()) {
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(new FileReader(file));
		}
		
		return null;
	}
	
	public static JSONObject read(String file) throws ParseException, FileNotFoundException, IOException
	{
		if (new File(file).exists()) {
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(new FileReader(file));
		}
		
		return null;
	}

	/**
	 * Write out the json, prettified.
	 * @param string
	 * @throws IOException 
	 */
	public static void write(JSONObject object, String path) throws IOException
	{
		Files.write(new File(path).toPath(), JSONFormatter.makePretty(object.toJSONString()).getBytes());
	}
}
