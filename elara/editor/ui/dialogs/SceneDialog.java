/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 26 Oct. 2017
 * File : SceneDialog.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.editor.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import elara.project.EditingContext;
import elara.project.ProjectContext;
import elara.project.ProjectManager;

/*****************************************************************
 *
 * SceneDialog
 *
 * Description: Used to allow the user to manage scenes.
 * Load, delete and create new scenes.
 *
 *****************************************************************/

public class SceneDialog extends JDialog
	implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private ProjectManager projMan = ProjectManager.manager();
	private EditingContext editCon = EditingContext.editingContext();
	private ProjectContext projCon = ProjectContext.projectContext();
	
	
	private final JPanel contentPanel = new JPanel();
	
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	
	private JButton openBtn;
	private JButton deleteBtn;
	private JButton newBtn;
	
	/**
	 * Create the dialog.
	 */
	public SceneDialog()
	{
		setTitle("Scenes");
		setBounds(100, 100, 337, 303);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				JList<String> list = new JList<String>();
				list.setModel(listModel);
				scrollPane.setViewportView(list);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				openBtn = new JButton("Open");
				openBtn.addActionListener(this);
				buttonPane.add(openBtn);
			}
			{
				deleteBtn = new JButton("Delete");
				deleteBtn.addActionListener(this);
				buttonPane.add(deleteBtn);
			}
			{
				newBtn = new JButton("New Scene");
				newBtn.addActionListener(this);
				buttonPane.add(newBtn);
			}
		}
		
		evaluateList();
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if (source == openBtn) {
			
		} else if (source == deleteBtn) {
			
		} else if (source == newBtn) {
			NewSceneDialog nsd = new NewSceneDialog();
			nsd.setVisible(true);
			
			// create the scene
			if (nsd.isComplete()) {
				// NOTE(brandon) Create new scene in scene dialog
				editCon.setSelectedLayer(0);
				// editCon.setSelectedScene(newScene);
				evaluateList();
			}
		}
	}

	/**
	 * Revaluates the elements in the list.
	 */
	public void evaluateList()
	{
		if (projCon.isProjectLoaded()) {
			listModel.clear();
			for (String sceneName : projCon.scenes()) {
				listModel.addElement(sceneName);
			}
		}
	}
}
