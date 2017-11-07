/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 4 Nov. 2017
 * File : FalloffGraph.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.editor.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import javax.swing.JPanel;

/*****************************************************************
 *
 * FalloffGraph
 *
 * Description: Used to edit a graph which describes the
 * behviour of things like sound and light.
 * 
 * NOTE(brandon) Continue work on falloff graph
 * 
 *****************************************************************/

public class FalloffGraph extends JPanel
	implements MouseMotionListener,
		MouseListener
{
	private static final long serialVersionUID = 1L;
	
	private final int HANDLE_SIZE = 10;
	
	private Type type = Type.LINEAR;
	private Line2D line = new Line2D.Float();
	private QuadCurve2D quadCurve = new QuadCurve2D.Float();
	private CubicCurve2D cubicCurve = new CubicCurve2D.Float();
	
	private Point lineStart = new Point();
	private Point lineEnd = new Point();
	
	private int gridSizeWidth = 0;
	private int gridSizeHeight = 0;
	
	private int mousedx = 0;
	private int mousedy = 0;
	private int mousex = 0;
	private int mousey = 0;
	
	private boolean insideStart = false;
	private boolean insideEnd = false;
	private boolean leftMouseDown = false;
	
	enum Type
	{
		LINEAR,
		QUAD,
		CUBIC
	}

	public FalloffGraph()
	{
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setColor(new Color(200, 200, 200));

		// draw a simple grid background
		gridSizeWidth = (getWidth() - 1) / 10;
		
		// draw vertical dotted lines
		for (int x = 0; x < getWidth(); x += gridSizeWidth) {
			for (int y = 0; y < getHeight(); y += 4) {
				g2d.drawLine(x, y, x, y + 1);
			}
		}
		
		gridSizeHeight = (getHeight() - 1) / 10;
		
		// draw horizontal dotted lines
		for (int y = 0; y < getHeight(); y += gridSizeHeight) {
			for (int x = 0; x < getWidth(); x += 4) {
				g2d.drawLine(x, y, x + 1, y);
			}
		}
		
		g2d.setColor(new Color(41, 126, 196));
		g2d.setStroke(new BasicStroke(2.0f));
		
		switch (type) {
			case LINEAR:
				lineEnd.x = getWidth();
				lineEnd.y = getHeight();
				
				line.setLine(lineStart, lineEnd);
				
				Rectangle startRect = new Rectangle(lineStart.x - HANDLE_SIZE / 2, 
						lineStart.y - HANDLE_SIZE / 2,
						HANDLE_SIZE, HANDLE_SIZE);
				g2d.fillRect(startRect.x, startRect.y, startRect.width, startRect.height);
				
				Rectangle endRect = new Rectangle(lineEnd.x - HANDLE_SIZE / 2, 
						lineEnd.y - HANDLE_SIZE / 2,
						HANDLE_SIZE, HANDLE_SIZE);
				g2d.fillRect(endRect.x, endRect.y, endRect.width, endRect.height);
				
				if ((startRect.contains(new Point(mousex, mousey)) && leftMouseDown) 
						|| (leftMouseDown && insideStart)) {
					insideStart = true;
					if (mousex != 0 || mousey != 0) {
						lineStart.x = mousex;
						lineStart.y = mousey;
						mousedx = 0;
						mousedy = 0;
					}
				}
				
				if ((endRect.contains(new Point(mousex, mousey)) && leftMouseDown) 
						|| (leftMouseDown && insideEnd)) {
					insideEnd = true;
					if (mousex != 0 || mousey != 0) {
						lineEnd.x = mousex;
						lineEnd.y = mousey;
						mousedx = 0;
						mousedy = 0;
					}
				}
				
				g2d.draw(line);
			break;
			case QUAD:
				g2d.draw(quadCurve);
			break;
			case CUBIC:
				
			break;
		}
		
		repaint();
	}

	int sx = 0, sy = 0;
	@Override
	public void mouseDragged(MouseEvent me)
	{
		mousex = me.getX();
		mousey = me.getY();
		
		mousedx = me.getX() - sx;
		mousedy = me.getY() - sy;
	}

	@Override
	public void mouseMoved(MouseEvent me)
	{
		mousex = me.getX();
		mousey = me.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		leftMouseDown = false;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		leftMouseDown = false;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		mousedx = 0;
		mousedy = 0;
		sx = e.getX();
		sy = e.getY();
		leftMouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		sx = 0; sy = 0;
		leftMouseDown = false;
		insideStart = false; insideEnd = false;
	}
}
