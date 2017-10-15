/**
 * Author: Brandon
 * Date Created: 11 Oct. 2017
 * File : AboutDialog.java
 */
package elara.editor.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{52, 350, 0};
		gbl_contentPanel.rowHeights = new int[]{49, 284, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(DefaultIcons.windowIcon);
		GridBagConstraints gbc_iconLabel = new GridBagConstraints();
		gbc_iconLabel.fill = GridBagConstraints.BOTH;
		gbc_iconLabel.insets = new Insets(0, 0, 5, 5);
		gbc_iconLabel.gridx = 0;
		gbc_iconLabel.gridy = 0;
		contentPanel.add(iconLabel, gbc_iconLabel);
		
		JLabel lblElara = new JLabel("Elara");
		GridBagConstraints gbc_lblElara = new GridBagConstraints();
		gbc_lblElara.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblElara.insets = new Insets(0, 0, 5, 0);
		gbc_lblElara.gridx = 1;
		gbc_lblElara.gridy = 0;
		contentPanel.add(lblElara, gbc_lblElara);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPanel.add(scrollPane, gbc_scrollPane);
		
		JTextPane txtpnElaraEditor = new JTextPane();
		txtpnElaraEditor.setEditable(false);
		txtpnElaraEditor.setText("Elara Editor v0.0.2-dev \r\n\r\nCurrently in development.\r\n\r\nBuild by Brandon Quinn as a side project with the intention of using it to make a small top-down shooter game.\r\n\r\nLibraries and Frameworks\r\n\r\n- JSON.Simple https://github.com/fangyidong/json-simple\r\n\t- Provides simple tools for reading and writing JSON files\r\n\r\n- GSon https://github.com/google/gson\r\n\t- JSON.Simple doesn't format the JSON when writing out to look \"pretty\", GSon is used since it does have that feature\r\n\r\n- LWJGL  https://www.lwjgl.org/download\r\n\r\n- RSyntaxTextArea https://github.com/bobbylight/RSyntaxTextArea\r\n\t- This is really cool, it's a text area which handles syntax highlighting completely for you\r\n\r\nCopyright (c) 2012, Robert Futrell\r\nAll rights reserved.\r\n\r\n- RSyntaxTextArea - AutoComplete https://github.com/bobbylight/AutoComplete\r\n\t- Another really cool tool which attaches to RSyntaxTextArea which allows for auto-completion\r\n\r\nCopyright (c) 2012, Robert Futrell\r\nAll rights reserved.\r\n");
		scrollPane.setViewportView(txtpnElaraEditor);
	}
}
