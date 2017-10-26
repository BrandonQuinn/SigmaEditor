/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 26 Oct. 2017
 * File : NewSceneDialog.java
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import elara.scene.Scene;
import elara.scene.SceneManager;

/*****************************************************************
 *
 * NewSceneDialog
 *
 * Description:
 *
 *****************************************************************/

public class NewSceneDialog extends JDialog
	implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	SceneManager sceneMan = SceneManager.manager();
	
	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	private JTextField widthField;
	private JTextField heightField;
	
	private JButton doneBtn;
	private JButton cancelBtn;
	
	/**
	 * Have all the field been completed and the OK button pressed?
	 */
	private boolean isComplete = false;

	/**
	 * Create the dialog.
	 */
	public NewSceneDialog()
	{
		setTitle("New Scene");
		setBounds(100, 100, 227, 155);
		getContentPane().setLayout(new BorderLayout());
		setModal(true);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblSceneName = new JLabel("Scene Name:");
			GridBagConstraints gbc_lblSceneName = new GridBagConstraints();
			gbc_lblSceneName.insets = new Insets(0, 0, 5, 5);
			gbc_lblSceneName.anchor = GridBagConstraints.EAST;
			gbc_lblSceneName.gridx = 0;
			gbc_lblSceneName.gridy = 0;
			contentPanel.add(lblSceneName, gbc_lblSceneName);
		}
		{
			nameField = new JTextField();
			GridBagConstraints gbc_nameField = new GridBagConstraints();
			gbc_nameField.insets = new Insets(0, 0, 5, 0);
			gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
			gbc_nameField.gridx = 1;
			gbc_nameField.gridy = 0;
			contentPanel.add(nameField, gbc_nameField);
			nameField.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Width:");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 1;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			widthField = new JTextField();
			GridBagConstraints gbc_widthField = new GridBagConstraints();
			gbc_widthField.insets = new Insets(0, 0, 5, 0);
			gbc_widthField.fill = GridBagConstraints.HORIZONTAL;
			gbc_widthField.gridx = 1;
			gbc_widthField.gridy = 1;
			contentPanel.add(widthField, gbc_widthField);
			widthField.setColumns(10);
		}
		{
			JLabel lblHeight = new JLabel("Height:");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.anchor = GridBagConstraints.EAST;
			gbc_lblHeight.insets = new Insets(0, 0, 0, 5);
			gbc_lblHeight.gridx = 0;
			gbc_lblHeight.gridy = 2;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			heightField = new JTextField();
			heightField.setColumns(10);
			GridBagConstraints gbc_heightField = new GridBagConstraints();
			gbc_heightField.fill = GridBagConstraints.HORIZONTAL;
			gbc_heightField.gridx = 1;
			gbc_heightField.gridy = 2;
			contentPanel.add(heightField, gbc_heightField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				doneBtn = new JButton("OK");
				doneBtn.addActionListener(this);
				doneBtn.setActionCommand("OK");
				buttonPane.add(doneBtn);
				getRootPane().setDefaultButton(doneBtn);
			}
			{
				cancelBtn = new JButton("Cancel");
				cancelBtn.addActionListener(this);
				cancelBtn.setActionCommand("Cancel");
				buttonPane.add(cancelBtn);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if (source == doneBtn) {
			// check each field
			
			try {
				Integer.valueOf(widthField.getText());
				Integer.valueOf(heightField.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Width or height field not a number.", "Bad Input", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			if (Integer.valueOf(widthField.getText()) > Scene.MAX_WIDTH 
					|| Integer.valueOf(widthField.getText()) < Scene.MIN_WIDTH) {
				JOptionPane.showMessageDialog(this, "Width out of bounds. " + Scene.MIN_WIDTH + " to " + Scene.MAX_WIDTH, 
						"Bad Input", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			if (Integer.valueOf(heightField.getText()) > Scene.MAX_WIDTH 
					|| Integer.valueOf(heightField.getText()) < Scene.MIN_WIDTH) {
				JOptionPane.showMessageDialog(this, "Height out of bounds. " + Scene.MIN_HEIGHT + " to " + Scene.MAX_HEIGHT, 
						"Bad Input", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			isComplete = true;
			setVisible(false);
		} else if (source == cancelBtn) {
			setVisible(false);
		}
	}

	public String name()
	{
		return nameField.getText();
	}
	
	public int width()
	{
		return Integer.valueOf(widthField.getText());
	}
	
	public int height()
	{
		return Integer.valueOf(heightField.getText());
	}
	
	public boolean isComplete()
	{
		return isComplete;
	}
}
