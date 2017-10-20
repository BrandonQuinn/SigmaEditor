/*****************************************************************
 * 
 * Author: Brandon
 * Date Created: 20 Oct. 2017
 * File : DecalProsPanel.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 * 
 *****************************************************************/

package elara.editor.ui.propertiespanels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import elara.project.EditingContext;

/*****************************************************************
 * 
 * DecalProsPanel
 *
 * Description: Properties panel for decals.
 * 
 *****************************************************************/

public class DecalPropsPanel extends JPanel
	implements ChangeListener
{
	private static final long serialVersionUID = 1L;
	
	private EditingContext editCon = EditingContext.editingContext();

	private JSpinner rotationSpinner;
	private JSpinner rotationSpeedSpinner;
	
	/**
	 * Create the panel.
	 */
	public DecalPropsPanel()
	{
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane contentScrollPane = new JScrollPane();
		add(contentScrollPane);
		
		JPanel contextPanel = new JPanel();
		contextPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentScrollPane.setViewportView(contextPanel);
		GridBagLayout gbl_contextPanel = new GridBagLayout();
		gbl_contextPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contextPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contextPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contextPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contextPanel.setLayout(gbl_contextPanel);
		
		JLabel lblRotationdegrees = new JLabel("Rotation (degrees):");
		
		GridBagConstraints gbc_lblRotationdegrees = new GridBagConstraints();
		gbc_lblRotationdegrees.anchor = GridBagConstraints.EAST;
		gbc_lblRotationdegrees.insets = new Insets(0, 0, 5, 5);
		gbc_lblRotationdegrees.gridx = 0;
		gbc_lblRotationdegrees.gridy = 0;
		contextPanel.add(lblRotationdegrees, gbc_lblRotationdegrees);
		
		rotationSpinner = new JSpinner();
		rotationSpinner.addChangeListener(this);
		rotationSpinner.setModel(new SpinnerNumberModel(new Double(0), new Double(0), new Double(360), new Double(1)));
		GridBagConstraints gbc_rotationSpinner = new GridBagConstraints();
		gbc_rotationSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_rotationSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_rotationSpinner.gridx = 1;
		gbc_rotationSpinner.gridy = 0;
		contextPanel.add(rotationSpinner, gbc_rotationSpinner);
		
		JLabel lblRotationSpeed = new JLabel("Rotation Speed:");
		GridBagConstraints gbc_lblRotationSpeed = new GridBagConstraints();
		gbc_lblRotationSpeed.insets = new Insets(0, 0, 0, 5);
		gbc_lblRotationSpeed.anchor = GridBagConstraints.WEST;
		gbc_lblRotationSpeed.gridx = 0;
		gbc_lblRotationSpeed.gridy = 1;
		contextPanel.add(lblRotationSpeed, gbc_lblRotationSpeed);
		
		rotationSpeedSpinner = new JSpinner();
		rotationSpeedSpinner.addChangeListener(this);
		rotationSpeedSpinner.setModel(new SpinnerNumberModel(1.0, 0.0, 100.0, 0.1));
		GridBagConstraints gbc_rotationSpeedSpinner = new GridBagConstraints();
		gbc_rotationSpeedSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_rotationSpeedSpinner.gridx = 1;
		gbc_rotationSpeedSpinner.gridy = 1;
		contextPanel.add(rotationSpeedSpinner, gbc_rotationSpeedSpinner);
	}

	@Override
	public void stateChanged(ChangeEvent ce)
	{
		Object source = ce.getSource();
		
		if (source == rotationSpinner) {
			editCon.assignDecalRotation((Double) rotationSpinner.getValue()); 
		} else if (source == rotationSpeedSpinner) {
			editCon.assignRotationSpeed((Double) rotationSpeedSpinner.getValue());
		}
	}

	/**
	 * Sets the GUI value for the decal rotation.
	 * 
	 * @param decalRotation
	 */
	public void assignRotation(double decalRotation)
	{
		rotationSpinner.setValue(decalRotation);
	}
}
