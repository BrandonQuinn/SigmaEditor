
package elara.editor.rendering;

import elara.editor.ui.RenderPanel;

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
	private static boolean updating = true;
	private RenderPanel renderPanel;

	public RenderUpdateThread(RenderPanel renderPanel)
	{
		this.renderPanel = renderPanel;
	}

	@Override
	public void run()
	{
		while (updating) {
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
