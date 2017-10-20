/*****************************************************************
 * Author: Brandon
 * Date Created: 19 Oct. 2017
 * File : InputManager.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *****************************************************************/

package elara.editor.input;

import elara.editor.debug.LogType;
import elara.editor.debug.QLogger;
import elara.editor.debug.StaticLogs;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/*****************************************************************
 * InputManager
 *
 * Description: Used to manage the input system using jinput.
 *****************************************************************/

public class InputManager
{
	private static InputManager inMan = new InputManager();

	/**
	 * All controllers detected.
	 */
	private Controller[] controllers;
	
	/**
	 * The first mouse controller found out of all the controllers.
	 */
	private Controller mouse;
	
	/**
	 * The first keyboard controller found out of all the controllers.
	 */
	private Controller keyboard;
	
	/**
	 * Mouse input event queue.
	 */
	private EventQueue mouseEventQueue;
	
	/**
	 * Keyboard input event queue;
	 */
	private EventQueue keyboardEventQueue;
	
	public InputManager()
	{
		controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		// detect first mouse controller
		for (Controller controller : controllers) {
			if (controller.getType() == Controller.Type.MOUSE) {
				mouse = controller;
				StaticLogs.debug.log(LogType.INFO, "Mouse detected for use: " + mouse.getName());
				break;
			}
		}
		
		// log an error if no mouse if found
		if (mouse == null) {
			StaticLogs.debug.log(LogType.ERROR, "No mouse detected");
		}
		
		mouseEventQueue = mouse.getEventQueue();
		
		// get the first keyboard device
		for (Controller controller : controllers) {
			if (controller.getType() == Controller.Type.KEYBOARD) {
				keyboard = controller;
				StaticLogs.debug.log(LogType.INFO, "Keyboard detected for use: " + keyboard.getName());
				break;
			}
		}
		// log an error if no keyboard if found
		if (keyboard == null) {
			StaticLogs.debug.log(LogType.ERROR, "No keyboard detected");
		}
		
		keyboardEventQueue = keyboard.getEventQueue();
	}
	
	/**
	 * Polls the input devices. Put this in your game/render loop.
	 */
	public void pollInput()
	{
		if (mouse != null) {
			mouse.poll();
		}
		
		if (keyboard != null) {
			keyboard.poll();
		}
		
		Event mouseEvent = new Event();
		Event keyboardEvent = new Event();
		
		while (mouseEventQueue.getNextEvent(mouseEvent)) {
			interpretMouseEvent(mouseEvent);
		}
		
		while (keyboardEventQueue.getNextEvent(keyboardEvent)) {
			interpretKeyboardEvent(keyboardEvent);
		}
	}

	/**
	 * @param keyboardEvent
	 */
	private void interpretKeyboardEvent(Event keyboardEvent)
	{
		Component comp = keyboardEvent.getComponent();
		
		// brackets
		
		if (comp.getName().equals("[")) {
			if (keyboardEvent.getValue() == 1.0) {
				Keyboard.BRACKET_LEFT = KeyState.PRESSED;
			} else {
				Keyboard.BRACKET_LEFT = KeyState.RELEASED;
			}
		} else if (comp.getName().equals("]")) {
			if (keyboardEvent.getValue() == 1.0) {
				Keyboard.BRACKET_RIGHT = KeyState.PRESSED;
			} else {
				Keyboard.BRACKET_RIGHT = KeyState.RELEASED;
			}
		} 
		
		// grammar
		
		else if (comp.getName().equals(".")) {
			if (keyboardEvent.getValue() == 1.0) {
				Keyboard.PERIOD = KeyState.PRESSED;
			} else {
				Keyboard.PERIOD = KeyState.RELEASED;
			}
		} else if (comp.getName().equals(",")) {
			if (keyboardEvent.getValue() == 1.0) {
				Keyboard.COMMA = KeyState.PRESSED;
			} else {
				Keyboard.COMMA = KeyState.RELEASED;
			}
		}
	}

	
	/**
	 * Takes each mouse event and turns it in to something more
	 * useable.
	 * 
	 * @param mouseEvent
	 */
	private void interpretMouseEvent(Event mouseEvent)
	{
		Component comp = mouseEvent.getComponent();
		
		if (comp.getName().equals("x")) {
			Mouse.incrementX((int) mouseEvent.getValue());
		}
		
		if (comp.getName().equals("y")) {
			Mouse.incrementY((int) mouseEvent.getValue());
		}
	}

	/**
	 * Simply logs all the devices and information about those devices to the
	 * logger given as the parameter.
	 * 
	 * @param log
	 */
	public void logControllerInfo(QLogger log) 
	{
		// print a list of input devices
		for (Controller controller : controllers) {
			
			log.log(LogType.INFO, "Input Device Detected: " + controller.getName());
			log.log(LogType.INFO, "Device Type: " + controller.getType().toString());
			
			Component[] components = controller.getComponents();
			log.log(LogType.INFO, "Number of Inputs on Device: " + components.length);
		}
	}
	
	/**
	 * Returns the static instance of the input manager.
	 * 
	 * @return
	 */
	public static InputManager inputManager()
	{
		return inMan;
	}
}

