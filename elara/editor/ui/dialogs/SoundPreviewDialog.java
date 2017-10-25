/**
 * Author: Brandon
 * Date Created: 7 Oct. 2017
 * File : SoundPreviewDialog.java
 */
package elara.editor.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import elara.assets.Sound;
import elara.editor.debug.LogType;
import elara.editor.debug.ElaraException;
import elara.editor.debug.StaticLogs;
import elara.project.ProjectContext;

/**
 * SoundPreviewDialog
 *
 * Description:
 */
public class SoundPreviewDialog extends JDialog
	implements ActionListener, ChangeListener
{
	private static final long serialVersionUID = 1L;
	
	private ProjectContext projectContext = ProjectContext.projectContext();
	
	private Music playableSound;
	
	/**
	 * Controls
	 */
	private JButton playBtn;
	private JButton stopBtn;
	private JSlider volumeSlider;
	private JLabel lblVolume;
	private JLabel posLbl;
	private JSlider positionSlider;
	
	/**
	 * Create the dialog.
	 * @throws ElaraException 
	 */
	public SoundPreviewDialog(Sound sound) throws ElaraException
	{
		try {
			playableSound = new Music(projectContext.projectPath() + "/assets/sounds/" + sound.filename());
		} catch (SlickException e) {
			StaticLogs.debug.log(LogType.ERROR, "Failed to load audio file in preview dialog: " 
					+ projectContext.projectPath() + "/assets/sounds/" + sound.filename());
			throw new ElaraException("Failed to load audio file in preview dialog: " 
					+ projectContext.projectPath() + "/assets/sounds/" + sound.filename());
		}
		
		setBounds(100, 100, 281, 181);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{133, 136, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				playBtn = new JButton("Play");
				playBtn.addActionListener(this);
				GridBagConstraints gbc_btnPlayer = new GridBagConstraints();
				gbc_btnPlayer.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnPlayer.insets = new Insets(0, 0, 5, 5);
				gbc_btnPlayer.gridx = 0;
				gbc_btnPlayer.gridy = 0;
				panel.add(playBtn, gbc_btnPlayer);
			}
			{
				stopBtn = new JButton("Stop");
				stopBtn.addActionListener(this);
				GridBagConstraints gbc_btnStop = new GridBagConstraints();
				gbc_btnStop.insets = new Insets(0, 0, 5, 0);
				gbc_btnStop.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnStop.gridx = 1;
				gbc_btnStop.gridy = 0;
				panel.add(stopBtn, gbc_btnStop);
			}
			{
				lblVolume = new JLabel("Volume");
				GridBagConstraints gbc_lblVolume = new GridBagConstraints();
				gbc_lblVolume.insets = new Insets(0, 0, 5, 5);
				gbc_lblVolume.gridx = 0;
				gbc_lblVolume.gridy = 1;
				panel.add(lblVolume, gbc_lblVolume);
			}
			{
				volumeSlider = new JSlider();
				volumeSlider.setMaximum(100);
				volumeSlider.setMinimum(0);
				volumeSlider.addChangeListener(this);
				GridBagConstraints gbc_volumeSlider = new GridBagConstraints();
				gbc_volumeSlider.insets = new Insets(0, 0, 5, 0);
				gbc_volumeSlider.fill = GridBagConstraints.HORIZONTAL;
				gbc_volumeSlider.gridwidth = 2;
				gbc_volumeSlider.gridx = 0;
				gbc_volumeSlider.gridy = 2;
				panel.add(volumeSlider, gbc_volumeSlider);
			}
			{
				posLbl = new JLabel("Position");
				GridBagConstraints gbc_posLbl = new GridBagConstraints();
				gbc_posLbl.insets = new Insets(0, 0, 5, 5);
				gbc_posLbl.gridx = 0;
				gbc_posLbl.gridy = 3;
				panel.add(posLbl, gbc_posLbl);
			}
			{
				positionSlider = new JSlider();
				positionSlider.setMaximum(100);
				positionSlider.setMinimum(0);
				positionSlider.addChangeListener(this);
				GridBagConstraints gbc_positionSlider = new GridBagConstraints();
				gbc_positionSlider.fill = GridBagConstraints.HORIZONTAL;
				gbc_positionSlider.gridwidth = 2;
				gbc_positionSlider.insets = new Insets(0, 0, 0, 5);
				gbc_positionSlider.gridx = 0;
				gbc_positionSlider.gridy = 4;
				panel.add(positionSlider, gbc_positionSlider);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == playBtn) {
			playableSound.play();
		} else if (source == stopBtn) {
			playableSound.stop();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent ce)
	{
		Object source = ce.getSource();
		
		if (source == volumeSlider) {
			playableSound.setVolume(volumeSlider.getValue() / 100.0f);
		} else if (source == positionSlider) {
			playableSound.setPosition(positionSlider.getValue());
		}
		
	}
}
