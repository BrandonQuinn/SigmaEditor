
package elara.editor.ui.dialogs;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class EngineLoadingDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JProgressBar progressBar;
	JLabel lblStatus;

	/**
	 * Create the dialog.
	 */
	public EngineLoadingDialog()
	{
		setType(Type.POPUP);
		setBounds(100, 100, 309, 124);
		setSize(309, 104);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setToolTipText("");
		progressBar.setBounds(10, 11, 273, 22);
		contentPanel.add(progressBar);

		lblStatus = new JLabel("Status:");
		lblStatus.setBounds(10, 44, 273, 14);
		contentPanel.add(lblStatus);
	}

	/**
	 * Simply set the progress of the progress bar.
	 * 
	 * @param progress
	 *            progress as an integer percentage out of 100
	 */
	public void setProgress(int progress)
	{
		this.progressBar.setValue(progress);
	}

	/**
	 * Set the message under the loading bar.
	 * 
	 * @param text
	 *            The message
	 */
	public void setText(String text)
	{
		lblStatus.setText(text);
	}
}
