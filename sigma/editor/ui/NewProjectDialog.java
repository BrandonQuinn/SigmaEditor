package sigma.editor.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 9 Jun 2017
 */

public class NewProjectDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField projectTitleField;
	private JTextField projectDirectoryField;

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
		setTitle("Create New Project");
		setModal(true);
		setBounds(100, 100, 343, 298);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Project Title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 307, 53);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		projectTitleField = new JTextField();
		projectTitleField.setBounds(10, 22, 287, 20);
		panel.add(projectTitleField);
		projectTitleField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Map Size (x100)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 73, 307, 55);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(10, 24, 28, 14);
		panel_1.add(lblWidth);
		
		JComboBox worldWidthCombo = new JComboBox();
		worldWidthCombo.setBounds(48, 21, 43, 20);
		panel_1.add(worldWidthCombo);
		worldWidthCombo.setModel(new DefaultComboBoxModel(new String[] {"10", "25", "50", "75", "100"}));
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(101, 24, 31, 14);
		panel_1.add(lblHeight);
		
		JComboBox worldHeightCombo = new JComboBox();
		worldHeightCombo.setBounds(142, 21, 43, 20);
		panel_1.add(worldHeightCombo);
		worldHeightCombo.setModel(new DefaultComboBoxModel(new String[] {"10", "25", "50", "75", "100"}));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Project Directory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 139, 307, 89);
		contentPanel.add(panel_2);
		panel_2.setLayout(null);
		
		projectDirectoryField = new JTextField();
		projectDirectoryField.setBounds(10, 22, 287, 20);
		panel_2.add(projectDirectoryField);
		projectDirectoryField.setEditable(false);
		projectDirectoryField.setColumns(10);
		
		JButton selectFolderBtn = new JButton("Select Folder");
		selectFolderBtn.setBounds(10, 53, 108, 23);
		panel_2.add(selectFolderBtn);
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
