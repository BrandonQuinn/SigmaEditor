/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : AssetTree.java
 */
package elara.editor.ui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 * AssetTree
 *
 * Description:
 */
public class AssetTreePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public AssetTreePanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);

	}

}
