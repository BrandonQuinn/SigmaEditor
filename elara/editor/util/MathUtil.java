package elara.editor.util;

/*============================================================*
	Author: brq
	Date: 10 Oct. 2017
	File: MathUtil.java
	
	Description: Math
 *============================================================*/

public class MathUtil
{
	
	/**
	 * Clamp an int
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static int clamp(int value, int min, int max)
	{
		return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * Clamp a float
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static float clamp(float value, float min, float max)
	{
		return Math.max(min, Math.min(max, value));
	}
}
