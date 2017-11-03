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

import java.awt.Graphics;
import java.awt.Graphics2D;
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
 *****************************************************************/

public class FalloffGraph extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Type type = Type.LINEAR;
	private QuadCurve2D curve = new QuadCurve2D.Float();
	private Line2D line = new Line2D.Float();

	enum Type
	{
		LINEAR,
		QUAD,
		CUBIC
	}

	public FalloffGraph()
	{}

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(curve);
	}
}
