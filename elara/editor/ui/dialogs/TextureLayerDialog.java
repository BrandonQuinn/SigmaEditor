/**
 * Author: Brandon
 * Date Created: 7 Oct. 2017
 * File : TextureLayerDialog.java
 */
package elara.editor.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.DefaultIcons;
import elara.project.EditingContext;
import elara.project.ProjectContext;

/**
 * TextureLayerDialog
 *
 * Description: Displays a list of texture layers and
 * gives controls to modfy them like creation or deletion.
 */
public class TextureLayerDialog extends JFrame 
	implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private EditingContext editCon = EditingContext.editingContext();
	private ProjectContext projectContext = ProjectContext.projectContext();
			
	private final JPanel contentPanel = new JPanel();

	/**
	 * Buttons
	 */
	private JButton newLayerBtn;
	private JButton deleteLayerBtn;
	
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private JLabel selectedLayerLabel;
	
	/**
	 * Create the dialog.
	 */
	public TextureLayerDialog()
	{
		setTitle("Texture Layers");
		setBounds(100, 100, 262, 360);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane);
		
		list = new JList<String>();
		listModel = new DefaultListModel<String>();

		loadListModel();
		
		list.setModel(listModel);
		list.addListSelectionListener(this);
		scrollPane.setViewportView(list);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		scrollPane.setColumnHeaderView(toolBar);
		
		newLayerBtn = new JButton(DefaultIcons.newLayerIcon);
		newLayerBtn.addActionListener(this);
		toolBar.add(newLayerBtn);
		
		deleteLayerBtn = new JButton(DefaultIcons.deleteLayerIcon);
		deleteLayerBtn.addActionListener(this);
		toolBar.add(deleteLayerBtn);
		
		selectedLayerLabel = new JLabel("Selected Layer: none");
		toolBar.add(selectedLayerLabel);
		
		// setup defaults
		Integer selectedIndex = editCon.getSelectedGroundLayerIndex();
		if (selectedIndex != null && selectedIndex  > -1) {
			list.setSelectedIndex(selectedIndex);
			selectedLayerLabel.setText("Selected Layer: Layer " + (selectedIndex + 1));
		}
	}

	private void loadListModel()
	{
		listModel.clear();
		// load up the layers
		ArrayList<BufferedImage> imageList = editCon.groundTextures();
		for (int i = 0; i < imageList.size(); i++) {
			listModel.addElement("Layer " + (i + 1));
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		editCon.setSelectedGroundTextureLayer(list.getSelectedIndex());
		if (list.getSelectedIndex() == -1) {
			selectedLayerLabel.setText("Selected Layer: none");
			return;
		}
		
		selectedLayerLabel.setText("Selected Layer: " + (list.getSelectedIndex() + 1));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == newLayerBtn) {
			
			if (!projectContext.isProjectLoaded()) {
				JOptionPane.showMessageDialog(this, "No project loaded.", "No project loaded", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			editCon.addGoundTextureLayer();
			loadListModel();
			
			// ensure one is always selected to we don't have the user needed to keep reselecting
			list.setSelectedIndex(editCon.groundTextures().size() - 1);
		} else if (source == deleteLayerBtn) {
			if (!projectContext.isProjectLoaded()) {
				JOptionPane.showMessageDialog(this, "No project loaded.", "No project loaded", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			int selectedIndex = list.getSelectedIndex();
			
			if (selectedIndex == -1) {
				JOptionPane.showMessageDialog(this, "No layer selected.", "No layer selected", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			editCon.deleteGroundLayer(selectedIndex);
			loadListModel();
			
			if (editCon.groundTextures().size() == 0) {
				selectedLayerLabel.setText("Selected Layer: none");
			}
			
			// ensure one is always selected to we don't have the user needed to keep reselecting
			list.setSelectedIndex(editCon.groundTextures().size() - 1);
		}
	}
}
