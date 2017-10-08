package elara.editor.input;
/**
 * Author: Brandon
 * Date Created: 4 Oct. 2017
 * File : Mouse.java
 */

/**
 * Mouse
 *
 * Description:
 */
public class Mouse
{
	public static int x = 0;
	public static int y = 0;
	
	private static MouseState leftButton = MouseState.NOT_PRESSED;
	private static MouseState middleButton = MouseState.NOT_PRESSED;
	private static MouseState rightButton = MouseState.NOT_PRESSED;

	public static boolean isLeftButtonDown()
	{
		if (leftButton == MouseState.PRESSED) {
			return true;
		}
		return false;
	}
	
	public static boolean isMiddleButtonDown()
	{
		if (middleButton == MouseState.PRESSED) {
			return true;
		}
		return false;
	}
	
	public static boolean isRightButtonDown()
	{
		if (rightButton == MouseState.PRESSED) {
			return true;
		}
		return false;
	}
	
	public static void setLeftButtonState(MouseState state)
	{
		Mouse.leftButton = state;
	}
	
	public static void setMiddleButtonState(MouseState state)
	{
		Mouse.middleButton = state;
	}
	
	public static void setRightButtonState(MouseState state)
	{
		Mouse.rightButton = state;
	}

	/**
	 * @return
	 */
	public static boolean isLeftButtonClicked()
	{
		if (leftButton == MouseState.CLICKED) {
			leftButton = MouseState.NOT_PRESSED;
			return true;
		}
		
		return false;
	}
}
