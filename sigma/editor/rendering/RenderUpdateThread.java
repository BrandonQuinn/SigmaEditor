
package sigma.editor.rendering;

import sigma.editor.ui.RenderPanel;

/**
 * Calls the repaint method on the JPanel which is used to render
 * the actually level and all the game elements/entities etc.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 12 Jun 2017
 */

public class RenderUpdateThread implements Runnable
{
	private static final long WAIT_TIME = 16; // milliseconds
	private static boolean updating = true;
	private RenderPanel renderPanel;

	public RenderUpdateThread(RenderPanel renderPanel)
	{
		this.renderPanel = renderPanel;
	}

	@Override
	public void run()
	{
		// repaint thread after WAIT_TIME passed
		while (updating) {
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			renderPanel.repaint();
		}
	}

	/**
	 * Stop the thread from updating the RenderPanel
	 */
	public synchronized void stopUpdating()
	{
		updating = false;
	}
}
