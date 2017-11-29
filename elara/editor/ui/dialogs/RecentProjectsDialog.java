/**
 * Author: Brandon
 * Date Created: 12 Oct. 2017
 * File : RecentProjectsDialog.java
 */
package elara.editor.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import elara.assets.DefaultIcons;
import elara.editor.EditorConfiguration;
import elara.editor.EditorConfiguration.RecentProject;
import elara.editor.debug.Debug;
import elara.editor.debug.ElaraException;
import elara.project.ProjectManager;

/**
 * RecentProjectsDialog
 *
 * Description: A simple dialog displaying a list of all projects recently opened.
 */
public class RecentProjectsDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private ProjectManager projMan = ProjectManager.manager();
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public RecentProjectsDialog()
	{
		setTitle("Recent Projects");
		setModal(true);
		setBounds(100, 100, 323, 332);
		setLocationRelativeTo(null);
		setIconImage(DefaultIcons.windowIcon.getImage());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane);
		JList<String> list = new JList<String>();

		DefaultListModel<String> model = new DefaultListModel<String>();

		try {
			ArrayList<EditorConfiguration.RecentProject> recentProjects = EditorConfiguration.recentProjects();
			for (RecentProject rp : recentProjects) {
				model.addElement(rp.name);
			}
		} catch (IOException e1) {
			Debug.error("Failed to load recent proejects lists");
		}

		list.setModel(model);
		list.setSelectedIndex(0);
		scrollPane.setViewportView(list);

		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel("");
				lblNewLabel_1.setIcon(DefaultIcons.windowIcon);
				panel.add(lblNewLabel_1, BorderLayout.WEST);
			}
			{
				JLabel lblNewLabel = new JLabel("   Elara");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblNewLabel, BorderLayout.CENTER);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Open");

				okButton.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						if (list.getSelectedIndex() == -1) {
							JOptionPane.showMessageDialog(null, "No project selected", "No project selected",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						setVisible(false);

						// load project location from configuration and open it
						try {
							ArrayList<EditorConfiguration.RecentProject> recentProjects = EditorConfiguration.recentProjects();
							projMan.open(recentProjects.get(list.getSelectedIndex()).projectLocation.getAbsolutePath());
						} catch (ElaraException ex) {
							Debug.error("Failed to open project: " + ex.message());
							JOptionPane.showMessageDialog(null, "Failed to open project, check logs",
								"Failed to open project, check logs", JOptionPane.ERROR_MESSAGE);
						} catch (IOException ex2) {
							Debug.error("Failed to read recent projects list when opening from recents");
							JOptionPane.showMessageDialog(null, "Failed to open project, check logs",
								"Failed to open project, check logs", JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Skip");
				cancelButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setVisible(false);
					}
				});

				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
