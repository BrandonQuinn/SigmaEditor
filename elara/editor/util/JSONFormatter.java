/**
 * Author: Brandon
 * Date Created: 4 Oct. 2017
 * File : JSONFormatter.java
 */
package elara.editor.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * JSONFormatter
 *
 * Description: Formats a JSON string to look pretty.
 */
public class JSONFormatter
{
	public static String makePretty(String json)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		return gson.toJson(element);
	}
}
