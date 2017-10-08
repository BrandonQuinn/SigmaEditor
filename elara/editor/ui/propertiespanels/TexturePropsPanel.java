/**
 * Author: Brandon
 * Date Created: 7 Oct. 2017
 * File : TexturePropsPanel.java
 */
package elara.editor.ui.propertiespanels;

import java.awt.FlowLayout;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import elara.project.BlendMode;
import elara.project.BrushFilter;
import elara.project.EditingContext;

/**
 * TexturePropsPanel
 *
 * Description:
 */
public class TexturePropsPanel extends JPanel 
	implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private EditingContext editingContext = EditingContext.editingContext();

	/**
	 * Properties components
	 */
	private JCheckBox tiledPlacementChkBox;
	private JComboBox<String> blendModeBox;
	private JComboBox<String> brushTypeBox;
	
	/**
	 * Create the panel.
	 */
	public TexturePropsPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane_1);
		
		JPanel borderPanel = new JPanel();
		borderPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane_1.setViewportView(borderPanel);
		borderPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		borderPanel.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 129, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
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
		gbc_tiledPlacementChkBox.insets = new Insets(0, 0, 5, 0);
		gbc_tiledPlacementChkBox.anchor = GridBagConstraints.WEST;
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
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(100, 0, 100, 1));
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 3;
		panel.add(spinner_1, gbc_spinner_1);
		
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

}
