package sigma.editor.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 16 Jun 2017
 */

public class NewProjectDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewProjectDialog dialog = new NewProjectDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewProjectDialog() {
		setTitle("Create New Level/Map");
		setAlwaysOnTop(true);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 305, 333);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblCreateNewProject = new JLabel("Create New Project");
		lblCreateNewProject.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCreateNewProject.setBounds(10, 11, 279, 20);
		contentPanel.add(lblCreateNewProject);
		
		JLabel lblProjectName = new JLabel("Project Name (Level/Map Name)");
		lblProjectName.setBounds(10, 42, 279, 14);
		contentPanel.add(lblProjectName);
		
		textField = new JTextField();
		textField.setBounds(10, 67, 279, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Size");
		lblNewLabel.setBounds(10, 98, 269, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblW = new JLabel("W:");
		lblW.setBounds(10, 123, 23, 14);
		contentPanel.add(lblW);
		
		textField_1 = new JTextField();
		textField_1.setBounds(30, 120, 103, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(186, 120, 103, 20);
		contentPanel.add(textField_2);
		
		JLabel lblH = new JLabel("H:");
		lblH.setBounds(162, 123, 23, 14);
		contentPanel.add(lblH);
		
		JLabel lblSaveLocation = new JLabel("Save Location (Not Recommened to Change)");
		lblSaveLocation.setBounds(10, 159, 279, 14);
		contentPanel.add(lblSaveLocation);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(10, 184, 279, 20);
		contentPanel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnChange = new JButton("Change");
		btnChange.setBounds(10, 215, 89, 23);
		contentPanel.add(btnChange);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Create");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
