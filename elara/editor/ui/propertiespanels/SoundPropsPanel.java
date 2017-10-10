/**
 * Author: Brandon
 * Date Created: 8 Oct. 2017
 * File : SoundPropsPanel.java
 */
package elara.editor.ui.propertiespanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import elara.editor.debug.LogType;
import elara.editor.debug.StaticLogs;
import elara.project.EditingContext;
import elara.project.ProjectContext;

/**
 * SoundPropsPanel
 *
 * Description:
 */
public class SoundPropsPanel extends JPanel 
	implements ActionListener, ChangeListener
{
	private static final long serialVersionUID = 1L;
	
	private EditingContext editingContext = EditingContext.editingContext();
	private ProjectContext projectContext = ProjectContext.projectContext();
	
	private Music music;
	
	
	/**
	 * Controls
	 */
	private JButton playBtn;
	private JSpinner volumeSpinner;
	private JCheckBox loopChkBox;

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
		
		playBtn = new JButton("Play");
		playBtn.addActionListener(this);
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
		
		volumeSpinner = new JSpinner();
		volumeSpinner.addChangeListener(this);
		volumeSpinner.setModel(new SpinnerNumberModel(50, 0, 100, 1));
		GridBagConstraints gbc_volumeSpinner = new GridBagConstraints();
		gbc_volumeSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_volumeSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_volumeSpinner.gridx = 1;
		gbc_volumeSpinner.gridy = 2;
		panel.add(volumeSpinner, gbc_volumeSpinner);
		
		JLabel lblLoop = new JLabel("Looped in-game:");
		GridBagConstraints gbc_lblLoop = new GridBagConstraints();
		gbc_lblLoop.anchor = GridBagConstraints.WEST;
		gbc_lblLoop.insets = new Insets(0, 0, 0, 5);
		gbc_lblLoop.gridx = 0;
		gbc_lblLoop.gridy = 3;
		panel.add(lblLoop, gbc_lblLoop);
		
		loopChkBox = new JCheckBox("");
		loopChkBox.addActionListener(this);
		GridBagConstraints gbc_loopChkBox = new GridBagConstraints();
		gbc_loopChkBox.anchor = GridBagConstraints.WEST;
		gbc_loopChkBox.gridx = 1;
		gbc_loopChkBox.gridy = 3;
		panel.add(loopChkBox, gbc_loopChkBox);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if (source == playBtn) {
			if (music == null) {
				try {
					music = new Music(projectContext.projectPath() + "/assets/sounds/" 
							+ editingContext.selectedSound().filename());
				} catch (SlickException e) {
					StaticLogs.debug.log(LogType.ERROR, "Could not load sound on play: " + projectContext.projectPath() + "/assets/sounds/" 
							+ editingContext.selectedSound().filename());
					JOptionPane.showMessageDialog(null, projectContext.projectPath() + "/assets/sounds/" 
							+ editingContext.selectedSound().filename(), "Failed to load sound", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if (music.playing()) {
				music.stop();
				playBtn.setText("Play");
			} else {
				music.play();
				playBtn.setText("Stop");
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
		
		if (source == volumeSpinner) {
			if (music != null) {
				music.setVolume((int)volumeSpinner.getValue() / 100.0f);
			}
		}
	}
}
