package sigma.editor.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 8 Jun 2017
 */

public class CanvasPanel extends JPanel implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private enum MouseState {
		DOWN,
		UP
	}
	
	private MouseState leftMouseState = MouseState.UP;
	private int lmClickLocationX = -1;
	private int lmClickLocationY = -1;
	
	public CanvasPanel() {
		this.addMouseListener(this);
		this.addKeyListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {		
		Graphics2D g2d = (Graphics2D) g;
		
		// black background underneath everything
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		if (leftMouseState == MouseState.DOWN) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(lmClickLocationX, lmClickLocationY, getMousePosition().x, getMousePosition().y);
			System.out.println("test");
		} else {
			lmClickLocationX = -1;
			lmClickLocationY = -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		
	}

	@Override
	public void mouseExited(MouseEvent me) {
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		lmClickLocationX = me.getX();
		lmClickLocationY = me.getY();
		leftMouseState = MouseState.DOWN;
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		leftMouseState = MouseState.UP;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		
	}
}
