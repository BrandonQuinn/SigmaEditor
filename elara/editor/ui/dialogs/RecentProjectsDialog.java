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
import org.json.simple.parser.ParseException;
import elara.assets.DefaultIcons;
import elara.editor.debug.SigmaException;
import elara.project.ProjectManager;
import elara.project.ProjectManager.RecentProject;

/**
 * RecentProjectsDialog
 *
 * Description:
 */
public class RecentProjectsDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	
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
		ProjectManager pc = ProjectManager.manager();
		ArrayList<RecentProject> recentProjects = pc.recentProjects();
		
		for (RecentProject rp : recentProjects) {
			model.addElement(rp.projectName);
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
						setVisible(false);
						try {
							pc.open(recentProjects.get(list.getSelectedIndex()).projectPath);
						} catch (SigmaException | IOException | ParseException e1) {
							JOptionPane.showMessageDialog(null, "Failed to open project", "Failure", 
									JOptionPane.ERROR_MESSAGE);
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
