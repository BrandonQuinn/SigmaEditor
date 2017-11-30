/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 30 Nov. 2017
 * File : ErrorListRenderer.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.editor.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import elara.editor.debug.Log;

/*****************************************************************
 *
 * ErrorListRenderer
 *
 * Description:
 *
 *****************************************************************/

public class ErrorListRenderer extends JLabel
	implements ListCellRenderer<Log>
{
	private static final long serialVersionUID = 1L;

	public ErrorListRenderer()
	{
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Log> list,
			Log log,
			int index,
			boolean isSelected,
			boolean hasCellFocus)
	{
		setText(log.getMessage());

		// assign colour based on type of log
		switch (log.getType()) {
		 	case INFO:
				setBackground(new Color(232, 243, 249));
				setForeground(Color.BLACK);
			break;
			case WARNING:
				setBackground(new Color(226, 183, 81));
				setForeground(Color.BLACK);
			break;
			case ERROR:
				setBackground(new Color(150, 33, 33));
				setForeground(Color.WHITE);
			break;
		}
		
		setBorder(new EmptyBorder(2, 5, 2, 5));

		return this;
	}
}
