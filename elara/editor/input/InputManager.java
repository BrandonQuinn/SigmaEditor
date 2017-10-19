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
	implements Runnable
{
	private static InputManager inMan = new InputManager();
	
	/**
	 * Polling input?
	 */
	private boolean polling = true;
	
	/**
	 * Input event handling frequency.
	 */
	private static final int EVENT_HANDLE_FREQ = 16;
	
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
	}
	

	/**
	 * Loop over the event queues
	 */
	@Override
	public void run()
	{
		boolean tmp = false;
		Event mouseEvent = new Event();
		Event keyboardEvent = new Event();
		while (polling) {
			
			// get the next mouseEvent
			tmp = mouseEventQueue.getNextEvent(mouseEvent);
			if (tmp == true) {
				interpretMouseEvent(mouseEvent);
				tmp = false;
			}
			
			// next keyboard event
			tmp = keyboardEventQueue.getNextEvent(keyboardEvent);
			if (tmp == true) {
				interpretKeyboardEvent(keyboardEvent);
				tmp = false;
			}
			
			try {
				Thread.sleep(EVENT_HANDLE_FREQ);
			} catch (InterruptedException e) {
				StaticLogs.debug.log(LogType.CRITICAL, "Event handling thread was interrupted on sleep");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param keyboardEvent
	 */
	private void interpretKeyboardEvent(Event keyboardEvent)
	{
		Component c = keyboardEvent.getComponent();
		System.out.println(c.getPollData());
		System.out.println(c.getName());
		
		// brackets
		
		if (c.getName().equals("[")) {
			if (keyboardEvent.getValue() == 1.0) {
				Keyboard.BRACKET_LEFT = KeyState.PRESSED;
			} else {
				Keyboard.BRACKET_LEFT = KeyState.RELEASED;
			}
		} else if (c.getName().equals("]")) {
			if (keyboardEvent.getValue() == 1.0) {
				Keyboard.BRACKET_RIGHT = KeyState.PRESSED;
			} else {
				Keyboard.BRACKET_RIGHT = KeyState.RELEASED;
			}
		}
	}

	/**
	 * @param mouseEvent
	 */
	private void interpretMouseEvent(Event mouseEvent)
	{
		Component c = mouseEvent.getComponent();
		System.out.println(c.getName());
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

