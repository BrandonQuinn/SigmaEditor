
package elara.editor.util;

/**
 * Tells us what operations system we are on.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 15 Jun 2017
 */

public class OSDetector
{

	public enum OS
	{
		WINDOWS,
		MAC,
		UNIX,
		UNKNOWN
	}

	/**
	 * Returns the operating system currently being run.
	 * 
	 * @return OS instance
	 */
	public static OS getOS()
	{
		String osString = System.getProperty("os.name").toLowerCase();

		if (osString.contains("win")) {
			return OS.WINDOWS;
		} else if (osString.contains("mac")) {
			return OS.MAC;
		} else if (osString.contains("nix") || osString.contains("nux") || osString.contains(
				"aix")) {
			return OS.UNIX;
		}

		return OS.UNKNOWN;
	}
}
