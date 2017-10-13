/**
 * Author: Brandon
 * Date Created: 7 Oct. 2017
 * File : TexturePropsPanel.java
 */
package elara.editor.ui.propertiespanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import elara.editor.imageprocessing.BlendMode;
import elara.editor.imageprocessing.BrushFilter;
import elara.project.EditingContext;

/**
 * TexturePropsPanel
 *
 * Description:
 */
public class TexturePropsPanel extends JPanel 
	implements ActionListener, ChangeListener
{
	private static final long serialVersionUID = 1L;
	
	private EditingContext editingContext = EditingContext.editingContext();

	/**
	 * Properties components
	 */
	private JCheckBox tiledPlacementChkBox;
	private JComboBox<String> blendModeBox;
	private JComboBox<String> brushTypeBox;
	private JSpinner opacitySpinner;
	
	/**
	 * Create the panel.
	 */
	public TexturePropsPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 130, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("Blending Mode:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		blendModeBox = new JComboBox<String>();
		blendModeBox.addActionListener(this);
		blendModeBox.setModel(new DefaultComboBoxModel<String>(new String[] {"OVERLAP", "MULTIPLY", "SCREEN", "OVERLAY"}));
		blendModeBox.setSelectedItem(editingContext.selectedBlendMode().toString());
		
		GridBagConstraints gbc_blendModeBox = new GridBagConstraints();
		gbc_blendModeBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_blendModeBox.anchor = GridBagConstraints.SOUTH;
		gbc_blendModeBox.insets = new Insets(0, 0, 5, 0);
		gbc_blendModeBox.gridx = 1;
		gbc_blendModeBox.gridy = 0;
		panel.add(blendModeBox, gbc_blendModeBox);
		
		JLabel lblNewLabel_1 = new JLabel("Brush Type:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		brushTypeBox = new JComboBox<String>();
		brushTypeBox.addActionListener(this);
		brushTypeBox.setModel(new DefaultComboBoxModel<String>(new String[] {"RADIAL_FALLOFF", "NONE"}));
		brushTypeBox.setSelectedItem(editingContext.selectedBrushFilter().toString());
		
		GridBagConstraints gbc_brushTypeBox = new GridBagConstraints();
		gbc_brushTypeBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_brushTypeBox.anchor = GridBagConstraints.SOUTH;
		gbc_brushTypeBox.insets = new Insets(0, 0, 5, 0);
		gbc_brushTypeBox.gridx = 1;
		gbc_brushTypeBox.gridy = 1;
		panel.add(brushTypeBox, gbc_brushTypeBox);
		
		JLabel lblTiledPlacement = new JLabel("Tiled Placement:");
		GridBagConstraints gbc_lblTiledPlacement = new GridBagConstraints();
		gbc_lblTiledPlacement.insets = new Insets(0, 0, 5, 5);
		gbc_lblTiledPlacement.gridx = 0;
		gbc_lblTiledPlacement.gridy = 2;
		panel.add(lblTiledPlacement, gbc_lblTiledPlacement);
		
		tiledPlacementChkBox = new JCheckBox("");
		tiledPlacementChkBox.setSelected(editingContext.tiledPaintingEnabled());
		tiledPlacementChkBox.addActionListener(this);
		GridBagConstraints gbc_tiledPlacementChkBox = new GridBagConstraints();
		gbc_tiledPlacementChkBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_tiledPlacementChkBox.insets = new Insets(0, 0, 5, 0);
		gbc_tiledPlacementChkBox.gridx = 1;
		gbc_tiledPlacementChkBox.gridy = 2;
		panel.add(tiledPlacementChkBox, gbc_tiledPlacementChkBox);
		
		JLabel lblOpacity = new JLabel("Opacity:");
		GridBagConstraints gbc_lblOpacity = new GridBagConstraints();
		gbc_lblOpacity.anchor = GridBagConstraints.WEST;
		gbc_lblOpacity.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpacity.gridx = 0;
		gbc_lblOpacity.gridy = 3;
		panel.add(lblOpacity, gbc_lblOpacity);
		
		opacitySpinner = new JSpinner();
		opacitySpinner.addChangeListener(this);
		opacitySpinner.setModel(new SpinnerNumberModel(100, 0, 100, 1));
		
		int value = (Integer) opacitySpinner.getValue();
		
		GridBagConstraints gbc_opacitySpinner = new GridBagConstraints();
		gbc_opacitySpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_opacitySpinner.insets = new Insets(0, 0, 5, 0);
		gbc_opacitySpinner.gridx = 1;
		gbc_opacitySpinner.gridy = 3;
		panel.add(opacitySpinner, gbc_opacitySpinner);
		
		JLabel lblBrushSize = new JLabel("Brush Size:");
		GridBagConstraints gbc_lblBrushSize = new GridBagConstraints();
		gbc_lblBrushSize.anchor = GridBagConstraints.WEST;
		gbc_lblBrushSize.insets = new Insets(0, 0, 0, 5);
		gbc_lblBrushSize.gridx = 0;
		gbc_lblBrushSize.gridy = 4;
		panel.add(lblBrushSize, gbc_lblBrushSize);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(100, 1, 1000, 1));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.anchor = GridBagConstraints.SOUTH;
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 4;
		panel.add(spinner, gbc_spinner);
		editingContext.assignTextureBrushOpacity(value / 100.0f);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if (source == tiledPlacementChkBox) {
			if (tiledPlacementChkBox.isSelected()) {
				editingContext.enableTiledTexturePlacement();
			} else {
				editingContext.disableTiledTexturePlacement();
			}
		} else if (source == blendModeBox) {
			String value = (String) blendModeBox.getSelectedItem();
			
			if (value.equals("OVERLAP")) {
				editingContext.setSelectedBlendMode(BlendMode.OVERLAP);
			} else if (value.equals("MULTIPLY")) {
				editingContext.setSelectedBlendMode(BlendMode.MULTIPLY);
			} else if (value.equals("SCREEN")) {
				editingContext.setSelectedBlendMode(BlendMode.SCREEN);
			} else if (value.equals("OVERLAY")) {
				editingContext.setSelectedBlendMode(BlendMode.OVERLAY);
			}	
		} else if (source == brushTypeBox) {
			String value = (String) brushTypeBox.getSelectedItem();
			
			if (value.equals("RADIAL_FALLOFF")) {
				editingContext.setSelectedBrushFilter(BrushFilter.RADIAL_FALLOFF);
			} else if (value.equals("NONE")) {
				editingContext.setSelectedBrushFilter(BrushFilter.NONE);
			}
		} 
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent ce)
	{
		Object source = ce.getSource();
		
		if (source == opacitySpinner) {
			int value = (Integer) opacitySpinner.getValue();
			editingContext.assignTextureBrushOpacity(value / 100.0f);
		}
	}

	/**
	 * Set the opacity of the spinner.
	 * @param i
	 */
	public void assignOpacity(int opacity)
	{
		opacitySpinner.setValue(opacity);
	}
}
