package sigma.editor.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonIOException;

import sigma.editor.control.conf.EntityConfLoader;
import sigma.editor.model.conf.EntityConfModel;

import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 11 Jun 2017
 */

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JComboBox<String> comboBox;
	/**
	 * Get the default toolkit to resize the application.
	 */
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Sigma Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 2);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewProject = new JMenuItem("New Project...");
		mnFile.add(mntmNewProject);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JSplitPane leftSplitPane = new JSplitPane();
		splitPane.setLeftComponent(leftSplitPane);
		leftSplitPane.setContinuousLayout(true);
		leftSplitPane.setResizeWeight(0.5);
		leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanel leftSideNorthPanel = new JPanel();
		leftSplitPane.setLeftComponent(leftSideNorthPanel);
		leftSideNorthPanel.setLayout(new BorderLayout(0, 0));
		
		comboBox = new JComboBox<String>();
		leftSideNorthPanel.add(comboBox, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		leftSideNorthPanel.add(scrollPane, BorderLayout.CENTER);
		
		JList entityList = new JList();
		entityList.setVisibleRowCount(5);
		scrollPane.setViewportView(entityList);
		
		JPanel leftSideSouthPanel = new JPanel();
		leftSideSouthPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		leftSplitPane.setRightComponent(leftSideSouthPanel);
		leftSideSouthPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblProperties = new JLabel("Properties");
		lblProperties.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftSideSouthPanel.add(lblProperties, BorderLayout.NORTH);
		
		JPanel propertiesPanel = new JPanel();
		leftSideSouthPanel.add(propertiesPanel, BorderLayout.CENTER);
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.9);
		panel.add(splitPane_1, BorderLayout.CENTER);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.5);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_2);
	}

}
