/**
 * Author: Brandon
 * Date Created: 11 Oct. 2017
 * File : AboutDialog.java
 */
package elara.editor.ui;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import elara.assets.DefaultIcons;

/**
 * AboutDialog
 *
 * Description:
 */
public class AboutDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();


	/**
	 * Create the dialog.
	 */
	public AboutDialog()
	{
		setTitle("About Elara");
		setBounds(100, 100, 448, 405);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(DefaultIcons.windowIcon);
		iconLabel.setBounds(10, 11, 52, 49);
		contentPanel.add(iconLabel);
		
		JLabel lblElara = new JLabel("Elara");
		lblElara.setBounds(72, 28, 46, 14);
		contentPanel.add(lblElara);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 71, 412, 284);
		contentPanel.add(scrollPane);
		
		JTextPane txtpnElaraEditor = new JTextPane();
		txtpnElaraEditor.setEditable(false);
		txtpnElaraEditor.setText("Elara Editor 0.0.2\r\n\r\nCurrently in development.\r\n\r\nBuild by Brandon Quinn as a side project with the intention of using it to make a small top-down shooter game.\r\n\r\nLibraries and Frameworks\r\n\r\n- JOML\r\n- lwjgl");
		scrollPane.setViewportView(txtpnElaraEditor);
	}
}
