/**
 * Author: Brandon
 * Date Created: 8 Oct. 2017
 * File : SoundPropsPanel.java
 */
package elara.editor.ui.propertiespanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

/**
 * SoundPropsPanel
 *
 * Description:
 */
public class SoundPropsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public SoundPropsPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 131, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton playBtn = new JButton("Play");
		GridBagConstraints gbc_playBtn = new GridBagConstraints();
		gbc_playBtn.gridwidth = 2;
		gbc_playBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_playBtn.insets = new Insets(0, 0, 5, 0);
		gbc_playBtn.gridx = 0;
		gbc_playBtn.gridy = 0;
		panel.add(playBtn, gbc_playBtn);
		
		JLabel lblVolume = new JLabel("Volume:");
		GridBagConstraints gbc_lblVolume = new GridBagConstraints();
		gbc_lblVolume.anchor = GridBagConstraints.WEST;
		gbc_lblVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblVolume.gridx = 0;
		gbc_lblVolume.gridy = 2;
		panel.add(lblVolume, gbc_lblVolume);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(50, 0, 100, 1));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 2;
		panel.add(spinner, gbc_spinner);
		
		JLabel lblLoop = new JLabel("Loop:");
		GridBagConstraints gbc_lblLoop = new GridBagConstraints();
		gbc_lblLoop.anchor = GridBagConstraints.WEST;
		gbc_lblLoop.insets = new Insets(0, 0, 0, 5);
		gbc_lblLoop.gridx = 0;
		gbc_lblLoop.gridy = 3;
		panel.add(lblLoop, gbc_lblLoop);
		
		JCheckBox checkBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.anchor = GridBagConstraints.WEST;
		gbc_checkBox.gridx = 1;
		gbc_checkBox.gridy = 3;
		panel.add(checkBox, gbc_checkBox);

	}
}
