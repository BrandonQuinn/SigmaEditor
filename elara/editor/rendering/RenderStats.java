/**
 * Author: Brandon
 * Date Created: 10 Oct. 2017
 * File : RenderStats.java
 */
package elara.editor.rendering;

/**
 * RenderStats
 *
 * Description: Updated by the rendering engine for debugging
 * information.
 *
 * Is to be run every frame.
 */
public class RenderStats
{
	private static volatile int FPS = 0;
	private static volatile long frameTimeNanos = 0L;

	private static long startTime = 0L;
	public static void startClock()
	{
		startTime = System.nanoTime();
	}

	private static int ticks = 0;
	private static final int TICK_LIM = 200;
	public static void finaliseClock()
	{
		frameTimeNanos = System.nanoTime() - startTime;
		
		// only calculate FPS ever TICK_LIM ticks
		if (ticks >= TICK_LIM) {
			FPS = (int) ((1.0 / (frameTimeNanos / 1000000.0)) * 1000);
			ticks = 0;
		} ticks++;
	}

	/**
	 * Get the frame rate in seconds.
	 */
	public static int FPS() 
	{
		return FPS;
	}
}
