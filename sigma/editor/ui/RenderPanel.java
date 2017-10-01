
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

	private boolean mouseClicked = false;
	private boolean mouseEntered = false;
	private boolean mouseExited = false;
	private boolean mousePressed = false;
	private boolean keyPressed = false;
	private boolean mouseReleased = false;

	private int mouseX, mouseY;

	public RenderPanel()
	{
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);

		// load mouse cursor
		cursorImage = new ImageIcon("res\\icons\\cursor.png");

		// transparent image
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

	private void drawSelectionRectangle(Graphics2D g2d)
	{

		// get the starting position
		if (mousePressed && !startSet) {
			startX = mouseX;
			startY = mouseY;
			startSet = true;
		}

		// ready to draw rectangle
		if (mousePressed && startSet) {
			// draw border
			g2d.setColor(new Color(66, 138, 255, 98));
			g2d.drawRect(startX, startY, mouseX - startX, mouseY - startY);

			g2d.setColor(new Color(66, 138, 255, 50));
			// draw center
			g2d.fillRect(startX + 1, startY + 1, mouseX - startX - 1, mouseY - startY - 1);

			g2d.setColor(Color.WHITE);
		}

		if (mouseReleased) {
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
		g2d.drawImage(cursorImage.getImage(), mouseX - 8, mouseY - 8, null);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		mouseClicked = true;
		mousePressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		mouseEntered = true;
		mouseExited = false;
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		mouseExited = true;
		mouseEntered = false;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		mousePressed = true;
		mouseReleased = false;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		mouseReleased = true;
		mousePressed = false;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
