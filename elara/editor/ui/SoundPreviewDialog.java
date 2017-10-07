/**
 * Author: Brandon
 * Date Created: 7 Oct. 2017
 * File : SoundPreviewDialog.java
 */
package elara.editor.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.newdawn.slick.SlickException;
import elara.assets.Sound;
import elara.project.ProjectContext;

/**
 * SoundPreviewDialog
 *
 * Description:
 */
public class SoundPreviewDialog extends JDialog
	implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private ProjectContext projectContext = ProjectContext.projectContext();
	
	private org.newdawn.slick.Sound playableSound;
	
	/**
	 * Controls
	 */
	private JButton playBtn;
	private JButton stopBtn;
	
	/**
	 * Create the dialog.
	 */
	public SoundPreviewDialog(Sound sound)
	{
		try {
			playableSound = new org.newdawn.slick.Sound(projectContext.projectPath() + "/assets/sounds/" + sound.filename());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setBounds(100, 100, 281, 142);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				playBtn = new JButton("Play");
				playBtn.addActionListener(this);
				GridBagConstraints gbc_btnPlayer = new GridBagConstraints();
				gbc_btnPlayer.insets = new Insets(0, 0, 0, 5);
				gbc_btnPlayer.gridx = 0;
				gbc_btnPlayer.gridy = 0;
				panel.add(playBtn, gbc_btnPlayer);
			}
			{
				stopBtn = new JButton("Stop");
				stopBtn.addActionListener(this);
				GridBagConstraints gbc_btnStop = new GridBagConstraints();
				gbc_btnStop.gridx = 1;
				gbc_btnStop.gridy = 0;
				panel.add(stopBtn, gbc_btnStop);
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

}
