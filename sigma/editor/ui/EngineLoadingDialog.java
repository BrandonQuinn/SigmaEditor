package sigma.editor.ui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class EngineLoadingDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JProgressBar progressBar;
	
	/**
	 * Create the dialog.
	 */
	public EngineLoadingDialog() {
		setType(Type.POPUP);
		setBounds(100, 100, 309, 124);
		setSize(309, 124);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 52, 273, 22);
		contentPanel.add(progressBar);
		
		JLabel lblLoadingEditor = new JLabel("Loading Editor...");
		lblLoadingEditor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLoadingEditor.setBounds(86, 19, 136, 22);
		contentPanel.add(lblLoadingEditor);
	}
	
	/**
	 * Simply set the progress of the progress bar.
	 * @param progress progress as an integer percentage out of 100
	 */
	public void setProgress(int progress) {
		this.progressBar.setValue(progress);
	}
}
