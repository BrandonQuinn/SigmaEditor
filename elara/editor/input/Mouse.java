package elara.editor.input;

import java.awt.event.MouseEvent;

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
	private static boolean entered = false;
	private static int exitedX = 0;
	private static int exitedY = 0;
	
	private static int x = 0;
	private static int y = 0;
	
	private static MouseState leftButton = MouseState.NOT_PRESSED;
	private static MouseState middleButton = MouseState.NOT_PRESSED;
	private static MouseState rightButton = MouseState.NOT_PRESSED;
	
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
		if (!entered) {
			return exitedX;
		}
		
		return x;
	}
	
	
	/**
	 * Returns the y position of the mouse.
	 * @return
	 */
	public static synchronized int y()
	{
		if (!entered) {
			return exitedY;
		}
		
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
	 * Set's whether or not the mouse cursor is inside the render
	 * frame.
	 * 
	 * @param b
	 */
	public static synchronized void setEntered(boolean b, MouseEvent me)
	{
		entered = b;
		
		Mouse.x = me.getX();
		Mouse.y = me.getY();
		
		if (!b) {
			exitedX = me.getX();
			exitedY = me.getY();
		}
	}
	
	/**
	 * Let's the mouse structure know when the mouse has actually moved.
	 * @param mouseMoved
	 */
	public static void assignMouseMoved(boolean mouseMoved)
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
