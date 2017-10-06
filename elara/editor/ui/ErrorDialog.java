
package elara.editor.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class ErrorDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private JButton closeButton;
	private JButton sendButton;

	/**
	 * Create the dialog.
	 */
	public ErrorDialog(String message, String stackTrace)
	{
		setTitle("Error Dialog");
		setModal(true);
		setSize(450, 522);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblSomethingWentWrong = new JLabel("Something Went Wrong...");
		lblSomethingWentWrong.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSomethingWentWrong.setBounds(10, 11, 414, 24);
		contentPanel.add(lblSomethingWentWrong);
		JLabel lblProgrammersMessage = new JLabel("Programmers Message");
		lblProgrammersMessage.setBounds(10, 46, 414, 14);
		contentPanel.add(lblProgrammersMessage);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 71, 414, 107);
		contentPanel.add(scrollPane);

		JTextArea messageTextArea = new JTextArea();
		messageTextArea.setText(message);
		messageTextArea.setTabSize(4);
		messageTextArea.setLineWrap(true);
		messageTextArea.setEditable(false);
		scrollPane.setViewportView(messageTextArea);

		JLabel lblStackTraceOutput = new JLabel("Stack Trace Output");
		lblStackTraceOutput.setBounds(10, 189, 414, 14);
		contentPanel.add(lblStackTraceOutput);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(10, 214, 414, 225);
		contentPanel.add(scrollPane_1);

		JTextArea stackTraceTextArea = new JTextArea();
		stackTraceTextArea.setText(stackTrace);
		stackTraceTextArea.setTabSize(4);
		stackTraceTextArea.setLineWrap(true);
		stackTraceTextArea.setEditable(false);
		scrollPane_1.setViewportView(stackTraceTextArea);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		closeButton.setActionCommand("close");
		buttonPane.add(closeButton);
		getRootPane().setDefaultButton(closeButton);

		sendButton = new JButton("Send Error Info to Developers");
		sendButton.addActionListener(this);
		sendButton.setActionCommand("Cancel");
		buttonPane.add(sendButton);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == closeButton) {
			this.setVisible(false);
		} else if (source == sendButton) {

		}
	}
}
