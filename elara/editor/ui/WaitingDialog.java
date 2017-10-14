/**
 * Author: Brandon
 * Date Created: 3 Oct. 2017
 * File : WaitingDialog.java
 */
package elara.editor.ui;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * WaitingDialog
 *
 * Description:
 */
public class WaitingDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private final JLabel messageLbl;
	/**
	 * Create the dialog.
	 */
	public WaitingDialog(String message)
	{
		setTitle("Waiting...");
		setAlwaysOnTop(true);
		setBounds(100, 100, 305, 102);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		messageLbl = new JLabel(message);
		messageLbl.setBounds(10, 24, 225, 14);
		contentPanel.add(messageLbl);
	}
	
	/**
	 * Changes the text on the dialog shown above the
	 * indeterminate loading bar.
	 * @param message
	 */
	public void changeMessageTo(String message)
	{
		messageLbl.setText(message);
	}
}
