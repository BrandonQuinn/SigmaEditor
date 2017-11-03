package elara.editor.input;

/**
 * Author: Brandon
 * Date Created: 4 Oct. 2017
 * File : Mouse.java
 */

/**
 * Mouse
 *
 * Description: Just mouse inputs...
 */
public class Mouse
{	
	private static volatile int x = 0;
	private static volatile int y = 0;
	
	private static volatile MouseState leftButton = MouseState.NOT_PRESSED;
	private static volatile MouseState middleButton = MouseState.NOT_PRESSED;
	private static volatile MouseState rightButton = MouseState.NOT_PRESSED;
	
	/**
	 * Tells us if the mouse has moved.
	 */
	private static boolean mouseMoved = false;
	
	/**
	 * Returns the x position of the mouse.
	 * @return
	 */
	public static synchronized int x() 
	{
		return x;
	}
	
	
	/**
	 * Returns the y position of the mouse.
	 * @return
	 */
	public static synchronized int y()
	{
		return y;
	}
	
	/**
	 * Increments the mouse x position.
	 * 
	 * @param inc
	 */
	public static synchronized void incrementX(int inc)
	{
		Mouse.x += inc;
	}
	
	
	/**
	 * Increments the mouse y position.
	 * @param inc
	 */
	public static synchronized void incrementY(int inc)
	{
		Mouse.y += inc;
	}
	
	/**
	 * Sets the x position.
	 * @param x
	 */
	public static synchronized void assignX(int x)
	{
		Mouse.x = x;
	}
	
	/**
	 * Sets the y position.
	 * @param y
	 */
	public static synchronized void assignY(int y)
	{
		Mouse.y = y;
	}
	
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
	
	public static synchronized void setLeftButtonState(MouseState state)
	{
		Mouse.leftButton = state;
	}
	
	public static synchronized void setMiddleButtonState(MouseState state)
	{
		Mouse.middleButton = state;
	}
	
	public static synchronized void setRightButtonState(MouseState state)
	{
		Mouse.rightButton = state;
	}

	/**
	 * Is the left mouse button clicked? Meaning it was pressed
	 * then released.
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

	/**
	 * Is the middle mouse button clicked? Meaning it was pressed
	 * then released.
	 * @return
	 */
	public static boolean isMiddleButtonClicked()
	{
		if (middleButton == MouseState.CLICKED) {
			middleButton = MouseState.NOT_PRESSED;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Let's the mouse structure know when the mouse has actually moved.
	 * @param mouseMoved
	 */
	public static synchronized void assignMouseMoved(boolean mouseMoved)
	{
		Mouse.mouseMoved = mouseMoved;
	}
	
	/**
	 * Has the mouse moved.
	 * @return
	 */
	public static boolean mouseMoved()
	{
		return Mouse.mouseMoved;
	}
}
