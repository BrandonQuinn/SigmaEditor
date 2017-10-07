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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
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
	
	/**
	 * Create the panel.
	 */
	public TexturePropsPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane_1);
		
		JPanel borderPanel = new JPanel();
		borderPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane_1.setViewportView(borderPanel);
		borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		borderPanel.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("Blending Mode:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"OVERLAP", "BLEND", "MULTIPLY", "DIFFERENCE"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Brush Type:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"RADIAL FALLOFF", "NONE"}));
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 1;
		panel.add(comboBox_1, gbc_comboBox_1);
		
		JLabel lblTiledPlacement = new JLabel("Tiled Placement:");
		GridBagConstraints gbc_lblTiledPlacement = new GridBagConstraints();
		gbc_lblTiledPlacement.insets = new Insets(0, 0, 0, 5);
		gbc_lblTiledPlacement.gridx = 0;
		gbc_lblTiledPlacement.gridy = 2;
		panel.add(lblTiledPlacement, gbc_lblTiledPlacement);
		
		tiledPlacementChkBox = new JCheckBox("");
		tiledPlacementChkBox.setSelected(editingContext.tiledPaintingEnabled());
		tiledPlacementChkBox.addActionListener(this);
		GridBagConstraints gbc_tiledPlacementChkBox = new GridBagConstraints();
		gbc_tiledPlacementChkBox.anchor = GridBagConstraints.WEST;
		gbc_tiledPlacementChkBox.gridx = 1;
		gbc_tiledPlacementChkBox.gridy = 2;
		panel.add(tiledPlacementChkBox, gbc_tiledPlacementChkBox);
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
		}
	}

}
