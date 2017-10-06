/**
 * Author: Brandon
 * Date Created: 7 Oct. 2017
 * File : TextureLayerDialog.java
 */
package elara.editor.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import elara.assets.DefaultIcons;
import elara.editor.debug.SigmaException;
import elara.project.EditingContext;
import elara.project.GameModel;
import elara.project.ProjectContext;

/**
 * TextureLayerDialog
 *
 * Description: Displays a list of texture layers and
 * gives controls to modfy them like creation or deletion.
 */
public class TextureLayerDialog extends JDialog 
	implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private GameModel model = GameModel.gameModel();
	private EditingContext editingContext = EditingContext.editingContext();
	private ProjectContext projectContext = ProjectContext.projectContext();
			
	private final JPanel contentPanel = new JPanel();

	/**
	 * Buttons
	 */
	private JButton newLayerBtn;
	private JButton deleteLayerBtn;
	
	private DefaultListModel<String> listModel;
	private JList<String> list;
	
	/**
	 * Create the dialog.
	 */
	public TextureLayerDialog()
	{
		setTitle("Texture Layers");
		setBounds(100, 100, 333, 360);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
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
	}

	private void loadListModel()
	{
		listModel.clear();
		// load up the layers
		ArrayList<BufferedImage> imageList = model.groundTextureLayers();
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
		editingContext.setSelectedGroundTextureLayer(list.getSelectedIndex());
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
			
			try {
				model.addGoundTextureLayer();
			} catch (SigmaException e1) {
				JOptionPane.showMessageDialog(this, e1.message(), "Max layers reached!", 
						JOptionPane.INFORMATION_MESSAGE);
			}
			
			loadListModel();
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
			
			model.deleteGroundTextureLayer(selectedIndex);
			loadListModel();
		}
	}
}
