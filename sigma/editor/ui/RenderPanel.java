
package sigma.editor.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import sigma.editor.input.Mouse;
import sigma.editor.input.MouseState;

/**
 * Component which actually holds the level being rendered and everything
 * that will be in the game. The main editor area.
 * 
 * @author Brandon Quinn
 * @version 0.1
 * @since 12 Jun 2017
 */

public class RenderPanel extends JComponent implements
		KeyListener,
		MouseListener,
		MouseMotionListener
{
	private static final long serialVersionUID = 1L;

	/*
	 * Mouse cursor.
	 */
	private ImageIcon cursorImage;

	public RenderPanel()
	{
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);

		// load mouse cursor
		cursorImage = new ImageIcon("res\\icons\\cursor.png");

		// transparent image to replace mouse
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// blank cursor
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg,
				new Point(0, 0),
				"blank cursor");

		setCursor(blankCursor);
	}

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		drawDefaultBackground(g2d);

		g2d.setColor(Color.white);

		drawSelectionRectangle(g2d);
		drawMouseCursor(g2d);
	}

	/**
	 * Just draws a static background so as to not be boring.
	 * 
	 * This displays when there is no project loaded.
	 * 
	 * @param g2d
	 */
	private void drawDefaultBackground(Graphics2D g2d)
	{
		int interval = 50;

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(new Color(20, 20, 20));

		for (int x = 0; x < getWidth(); x += interval) {
			for (int y = 0; y < getHeight(); y += interval) {
				g2d.drawLine(x, 0, x, getHeight());
				g2d.drawLine(0, y, getWidth(), y);
			}
		}
	}

	private int startX = 0, startY = 0;
	private boolean startSet = false;

	/**
	 * Draws a selection rectangle in the render area when the mouse is 
	 * pressed down and held.
	 * @param g2d
	 */
	private void drawSelectionRectangle(Graphics2D g2d)
	{
		// get the starting position
		if (Mouse.isLeftButtonDown() && !startSet) {
			startX = Mouse.x;
			startY = Mouse.y;
			startSet = true;
		}

		// ready to draw rectangle
		if (Mouse.isLeftButtonDown() && startSet) {
			// draw border
			g2d.setColor(new Color(66, 138, 255, 180));
			g2d.drawRect(startX, startY, Mouse.x - startX, Mouse.y - startY);

			g2d.setColor(new Color(66, 138, 255, 100));
			// draw center
			g2d.fillRect(startX + 1, startY + 1, Mouse.x - startX - 1, Mouse.y - startY - 1);

			g2d.setColor(Color.WHITE);
		}

		if (!Mouse.isLeftButtonDown()) {
			startSet = false;
			startX = 0;
			startY = 0;
		}
	}

	/**
	 * Draw the mouse cursor to the component.
	 */
	public void drawMouseCursor(Graphics2D g2d)
	{
		g2d.drawImage(cursorImage.getImage(), Mouse.x - 8, Mouse.y - 8, null);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1) {
			Mouse.setLeftButtonState(MouseState.PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			Mouse.setMiddleButtonState(MouseState.PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Mouse.setRightButtonState(MouseState.PRESSED);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1) {
			Mouse.setLeftButtonState(MouseState.NOT_PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			Mouse.setMiddleButtonState(MouseState.NOT_PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Mouse.setRightButtonState(MouseState.NOT_PRESSED);
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1) {
			Mouse.setLeftButtonState(MouseState.PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			Mouse.setMiddleButtonState(MouseState.PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Mouse.setRightButtonState(MouseState.PRESSED);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1) {
			Mouse.setLeftButtonState(MouseState.NOT_PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			Mouse.setMiddleButtonState(MouseState.NOT_PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Mouse.setRightButtonState(MouseState.NOT_PRESSED);
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Create key events
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Create key events
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Create key events
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		Mouse.x = e.getX();
		Mouse.y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		Mouse.x = e.getX();
		Mouse.y = e.getY();
	}
}
