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
	private static final int UPDATE_FREQ_TICKS = 100;
	private static int currentTick = 0;
	private static final int FRAME_TARGET_60FPS = 60;
	private static final int FRAME_TARGET_30FPS = 30;
	private static final int FRAME_TARGET_NONE = Integer.MAX_VALUE;
	private static volatile int currentFrameRateTarget = FRAME_TARGET_60FPS;
	private static final double MILLIS_IN_60FPS = 16.66;
	private static final double MILLIS_IN_30FPS = 33.33;
	private static final double MILLIS_IN_NONE = Double.MAX_VALUE;
	private static volatile double currentTimeLimitMillis = MILLIS_IN_60FPS;
	private static volatile double frameTimeMillis = 0.0;
	private static volatile double distanceFromTarget = 0.0;
	private static volatile double FPS = 0.0;
	private static long startTime = 0L;

	public static void startClock()
	{
		currentTick++;
		startTime = System.currentTimeMillis();
	}

	// NOTE(brandon) FIX FPS counter
	public static void finaliseClock()
	{
		frameTimeMillis = System.currentTimeMillis() - startTime;
		distanceFromTarget = currentTimeLimitMillis - frameTimeMillis;

		if (currentTick >= UPDATE_FREQ_TICKS) {
			if (distanceFromTarget < MILLIS_IN_60FPS && distanceFromTarget > 0) { 
				currentTimeLimitMillis = MILLIS_IN_60FPS;
			} else if (distanceFromTarget < MILLIS_IN_30FPS && distanceFromTarget > MILLIS_IN_60FPS) {
				currentTimeLimitMillis = MILLIS_IN_30FPS;
			} else {
				currentTimeLimitMillis = MILLIS_IN_30FPS;
			}
			
			FPS = ((1.0 / (Math.max(frameTimeMillis, 1.0) / 1000.0) / 10.0)); currentTick = 0;
		} 
	}

	public static int frameRateTarget()
	{
		return currentFrameRateTarget;
	}

	/**
	 * Wait until we fill the different between how long the frame
	 * took and what our target is. So basically, fix the frame rate at
	 * either 30 or 60 FPS, which ever is closer.
	 */
	public static void lockFrameRate()
	{
		try {
			Thread.sleep((long)distanceFromTarget);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Get the frame rate in seconds.
	 */
	public static double FPS() 
	{
		return FPS;
	}
}
