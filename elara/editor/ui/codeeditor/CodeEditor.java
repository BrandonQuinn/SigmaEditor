/**
 * Author: Brandon
 * Date Created: 15 Oct. 2017
 * File : CodeEditor.java
 */
package elara.editor.ui.codeeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import elara.assets.DefaultIcons;
import elara.assets.Script;
import elara.editor.debug.ElaraException;
import elara.project.Assets;
import elara.project.ProjectContext;
import elara.project.ProjectManager;

/**
 * CodeEditor
 *
 * Description: A small, lightweight code editor built in
 * that can be used to edit gameplay code.
 */
public class CodeEditor extends JFrame
	implements ActionListener,
	ListSelectionListener,
	KeyListener
{
	private static final long serialVersionUID = 1L;
	
	private ProjectManager projMan = ProjectManager.manager();
	private ProjectContext projCon = ProjectContext.projectContext();

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	private RSyntaxDocument document = new RSyntaxDocument(RSyntaxDocument.SYNTAX_STYLE_JAVA);
	private RSyntaxTextArea syntaxTextArea = new RSyntaxTextArea(document);
	private RTextScrollPane textScrollPane = new RTextScrollPane(syntaxTextArea);
	
	private DefaultListModel<String> scriptListModel = new DefaultListModel<String>();
	private JList<String> scriptList = new JList<String>(scriptListModel);
	
	private JSplitPane scriptCodeSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	/**
	 * Stores the current state of the source code for each scripts.
	 * This could end up being very space inefficient. We'll see.
	 */
	private ArrayList<String> scriptBuffer = new ArrayList<String>();
	
	private int currentSelection = -1;
	
	/**
	 * Menus and menu items
	 */
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newJava = new JMenuItem("New Java Script...");
	private JMenuItem newKotlin = new JMenuItem("New Kotlin Script...");
	private JMenuItem saveScript = new JMenuItem("Save");
	private JMenuItem deleteScript = new JMenuItem("Delete");
	
	public CodeEditor()
	{
		setTitle("Script Editor");
		setIconImage(DefaultIcons.windowIcon.getImage());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(toolkit.getScreenSize().width / 2, 
				(int)(toolkit.getScreenSize().height / 1.5f));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		// event listeners
		syntaxTextArea.addKeyListener(this);
		newJava.addActionListener(this);
		newKotlin.addActionListener(this);
		scriptList.addListSelectionListener(this);
		saveScript.addActionListener(this);
		deleteScript.addActionListener(this);
		
		syntaxTextArea.setCurrentLineHighlightColor(new Color(240, 240, 240));
		syntaxTextArea.setFadeCurrentLineHighlight(true);
		syntaxTextArea.setMarginLineEnabled(true);
		syntaxTextArea.setMarginLineColor(new Color(240, 240, 255));
		
		scriptCodeSplitPane.setDividerLocation(150);
		scriptCodeSplitPane.add(scriptList, JSplitPane.LEFT);
		scriptCodeSplitPane.add(textScrollPane, JSplitPane.RIGHT);
		add(scriptCodeSplitPane, BorderLayout.CENTER);
		
		fileMenu.add(newJava);
		fileMenu.add(newKotlin);
		fileMenu.add(saveScript);
		fileMenu.add(deleteScript);
		menuBar.add(fileMenu);
		add(menuBar, BorderLayout.NORTH);
		
		// add scripts to model
		for (int i = 0; i < projCon.scriptList().size(); i++) {
			String str = (String) projCon.scriptList().toArray()[i];
			scriptListModel.addElement(str);
			
			Script script = Assets.readScript(str);
			scriptBuffer.add(i, script.getContent());
		}
	}

	/**
	 * Handle events
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if (source == newJava || source == newKotlin) {
			String scriptName = "";
			while (!scriptName.matches("[A-Za-z]+")) {
				scriptName = JOptionPane.showInputDialog(this, "Enter script name..."
						+ "\n(Must be only letters)", "New Script", 
						JOptionPane.PLAIN_MESSAGE);
				
				// option pane returns null on cancel
				if (scriptName == null) {
					return;
				}
				
				// nope, try again...
				if (!scriptName.matches("[A-Za-z]+"))
					continue;
				
				// TODO Script templates with required methods
				try {
					if (source == newJava) {
						projMan.addScript(scriptName + ".java");
						scriptListModel.addElement(scriptName + ".java");
						
					} else if(source == newKotlin) {
						projMan.addScript(scriptName + ".kt");
						scriptListModel.addElement(scriptName + ".kt");
					}
					
					scriptBuffer.add("");
					break;
				} catch ( ElaraException  e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), e.toString(), 
							JOptionPane.ERROR_MESSAGE);
					scriptName = "";
				}
			}
		} else if (source == saveScript) {
			int index = scriptList.getSelectedIndex();
			if (index != -1) {
					//Assets.saveScript(scriptListModel.getElementAt(index), 
					//		syntaxTextArea.getText());
			}
		} else if (source == deleteScript) {
			int index = scriptList.getSelectedIndex();
			if (index != -1) {
				try {
					String script = scriptListModel.get(index);
					//Assets.deleteScript(script);
					projCon.deleteScript(script);
					projMan.deleteScript(script);
					scriptListModel.remove(index);
				} catch (ElaraException e) {
					JOptionPane.showMessageDialog(this, "Failed to delete script: " 
							+ e.getMessage(), "Exception", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Figure out which script is selected.
	 */
	@Override
	public void valueChanged(ListSelectionEvent lse)
	{
		int index = scriptList.getSelectedIndex();
		
		if (index != -1) {
			String newStr = syntaxTextArea.getText();
			
			if (currentSelection != -1) {
				scriptBuffer.set(currentSelection, newStr);
			}
			
			syntaxTextArea.setText(scriptBuffer.get(index));
			currentSelection = index;
			
		}
	}

	/** (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Create indicator when changes are made
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
