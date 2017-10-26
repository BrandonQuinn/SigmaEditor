/**
 * Author: Brandon
 * Date Created: 1 Oct. 2017
 * File : NewProjectDialog.java
 */

package elara.editor.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * NewProjectDialog
 *
 * Description:
 */
public class NewProjectDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private String projectName;
	private String projectLocation;

	private final JPanel contentPanel = new JPanel();
	private JTextField projectNameField;
	private JTextField locationField;

	private boolean confirmed = false;

	/**
	 * Create the dialog.
	 */
	public NewProjectDialog()
	{
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("New Project");
		setBounds(100, 100, 236, 217);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel,
				BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblProjectName = new JLabel("Project Name");
			lblProjectName.setBounds(10, 11, 64, 14);
			contentPanel.add(lblProjectName);
		}
		{
			projectNameField = new JTextField();
			projectNameField.setBounds(10, 29, 148, 20);
			contentPanel.add(projectNameField);
			projectNameField.setColumns(10);
		}

		JLabel lblProjectLocation = new JLabel("Project Location");
		lblProjectLocation.setBounds(10, 60, 86, 14);
		contentPanel.add(lblProjectLocation);

		locationField = new JTextField();
		locationField.setEditable(false);
		locationField.setBounds(10, 79, 201, 20);
		contentPanel.add(locationField);
		locationField.setColumns(10);

		JButton changeLocBtn = new JButton("Change Location");
		changeLocBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// open a file dialog to select a directory to create the project
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Select Project Directory");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(null);

				projectLocation = fc.getSelectedFile().getAbsolutePath();
				
				if (projectLocation != null) {
					locationField.setText(projectLocation);
				}
			}
		});
		changeLocBtn.setBounds(10, 110, 148, 23);
		contentPanel.add(changeLocBtn);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(220, 220, 220));
			buttonPane.setLayout(new FlowLayout(
					FlowLayout.RIGHT));
			getContentPane().add(buttonPane,
					BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Create");
				okButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						projectName = projectNameField.getText();
						confirmed = true;
						setVisible(false);
					}
				});

				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						setVisible(false);
					}
				});

				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public boolean isConfirmed()
	{
		return confirmed;
	}
	
	public boolean isComplete()
	{
		if (projectName == null || projectLocation == null) {
			return false;
		}
		return true;
	}

	public String projectName()
	{
		return projectName;
	}

	public String projectLocation()
	{
		return projectLocation;
	}
}
