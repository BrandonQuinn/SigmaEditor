
package elara.editor.ui;

import java.awt.BasicStroke;
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
import elara.assets.DefaultIcons;
import elara.editor.input.KeyState;
import elara.editor.input.Keyboard;
import elara.editor.input.Mouse;
import elara.editor.input.MouseState;
import elara.project.EditingContext;
import elara.project.EditingContext.EditingState;
import elara.project.GameModel;

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

	private EditingContext editingContext = EditingContext.editingContext();
	private GameModel gameModel = GameModel.gameModel();

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

		gameModel.draw(editingContext.xOffset(), editingContext.yOffset(), g2d);

		handleInput();
		handleEditingState(g2d);
		drawDebugInfo(g2d);
	}

	/**
	 * Displays debug info at the top of the render window
	 */
	private void drawDebugInfo(Graphics2D g2d)
	{
		g2d.setColor(Color.WHITE);
		g2d.drawString("Tool: " + editingContext.state().toString() + " | "
				+ "Selected Texture Layer: " + (editingContext.getSelectedGroundLayerIndex() + 1)
				+ " | Offset x:" + editingContext.xOffset() + ", y: " + editingContext.yOffset(), 5, 15);
	}

	/**
	 * Handles any changes to input that need to be made
	 */
	private void handleInput()
	{
		// handle space bar pressed and change state only when we are just enterting this state
		if (Keyboard.SPACE_BAR == KeyState.PRESSED 
				&& editingContext.state() != EditingState.MOVE_WORLD) {
			editingContext.assignState(EditingState.MOVE_WORLD);
		}
	}

	private Integer moveStartX = null;
	private Integer moveStartY = null;

	/**
	 * Basically switch throw all the editing states and make the
	 * edits
	 * 
	 * @param g2d
	 */
	private void handleEditingState(Graphics2D g2d)
	{
		switch (editingContext.state()) {
		case SELECT:
			drawSelectionRectangle(g2d);
		break;

		case TEXTURE_PAINT:
			if (Mouse.isLeftButtonDown() && editingContext.getSelectedGroundLayerIndex() != -1) {
				BufferedImage paintTexture = editingContext.selectedTexture().image();
				BufferedImage buffIm = gameModel.groundTextureLayers().get(editingContext.getSelectedGroundLayerIndex());
				Graphics2D big = (Graphics2D) buffIm.getGraphics();
				
				int paintx = (Mouse.x - (paintTexture.getWidth() >> 1)) + editingContext.xOffset() * -1;
				int painty = (Mouse.y - (paintTexture.getHeight() >> 1)) + editingContext.yOffset() * -1;
				
				BufferedImage newImage 
					= new BufferedImage(paintTexture.getWidth(), paintTexture.getHeight(), 
							BufferedImage.TYPE_INT_ARGB);
				
				// create a new image which handles tiling
				if (editingContext.tiledPaintingEnabled()) {
					Graphics2D p2d = newImage.createGraphics();
					
					p2d.drawImage(editingContext.selectedTexture().image(), 
							0 - (paintx % newImage.getWidth()), 
							0 - (painty % newImage.getHeight()), null);
						
					p2d.drawImage(editingContext.selectedTexture().image(), 
							(0 - (paintx % newImage.getWidth()) + newImage.getWidth()), 
							0 - (painty % newImage.getHeight()), null);
						
					System.out.println(paintx % newImage.getWidth());
					System.out.println((0 - (paintx % newImage.getWidth()) + newImage.getWidth()));
					
					p2d.drawImage(editingContext.selectedTexture().image(), 
							(0 - (paintx % newImage.getWidth()) + newImage.getWidth()), 
							(0 - (painty % newImage.getHeight()) + newImage.getHeight()), null);
						
					p2d.drawImage(editingContext.selectedTexture().image(), 
							0 - (paintx % newImage.getWidth()), 
							(0 - (painty % newImage.getHeight()) + newImage.getHeight()), null);
					
					g2d.drawImage(newImage,
							Mouse.x - (newImage.getWidth() / 2), Mouse.y - (newImage.getHeight() / 2), null);
					
					big.drawImage(newImage, paintx, painty, null);
				} else {
					big.drawImage(paintTexture, paintx, painty, null);
				}
		        
		        
			}
			
		break;

		case MOVE_WORLD:
			if (moveStartX == null) {
				moveStartX = Mouse.x;
			}
			if (moveStartY == null) {
				moveStartY = Mouse.y;
			}

			// TODO prevent moving world too far

			// detect a change and add it
			if (Mouse.x - moveStartX != 0) {
				editingContext.addToXOffset(Mouse.x - moveStartX);
				moveStartX = Mouse.x;
			}

			if (Mouse.y - moveStartY != 0) {
				editingContext.addToYOffset(Mouse.y - moveStartY);
				moveStartY = Mouse.y;
			}

			if (Keyboard.SPACE_BAR == KeyState.RELEASED) {
				moveStartX = null;
				moveStartY = null;
				Keyboard.SPACE_BAR = KeyState.NOT_PRESSED;

				editingContext.assignState(editingContext.previousState());
			}
		break;
		}

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
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		g2d.setColor(new Color(10, 10, 10));
		g2d.fillRect(editingContext.xOffset(),
				editingContext.yOffset(),
				gameModel.worldWidthPixels(),
				gameModel.worldHeightPixels());

		g2d.setColor(new Color(50, 50, 50));

		for (int x = 0; x < gameModel.worldWidthPixels() + 1; x += GameModel.GRID_SIZE) {
			for (int y = 0; y < gameModel.worldHeightPixels() + 1; y += GameModel.GRID_SIZE) {

				g2d.drawLine(x + editingContext.xOffset(), 0 + editingContext.yOffset(),
						x + editingContext.xOffset(), gameModel.worldHeightPixels() + editingContext.yOffset());

				g2d.drawLine(0 + editingContext.xOffset(), y + editingContext.yOffset(), 
						gameModel.worldWidthPixels() + editingContext.xOffset(), y + editingContext.yOffset());
			}
		}
	}

	private int startX = 0, startY = 0;
	private boolean startSet = false;

	/**
	 * Draws a selection rectangle in the render area when the mouse is
	 * pressed down and held.
	 * 
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
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.setColor(new Color(42, 216, 30, 180));
			g2d.drawRect(startX, startY, Mouse.x - startX, Mouse.y - startY);

			g2d.setStroke(new BasicStroke(0.0f));
			g2d.setColor(new Color(87, 206, 78, 100));
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
		switch (editingContext.state()) {
		case SELECT:
			g2d.drawImage(cursorImage.getImage(), Mouse.x - 8, Mouse.y - 8, null);
		break;

		case TEXTURE_PAINT:
			BufferedImage selectedImage = editingContext.selectedTexture().image();

			int hypotenuse = (int) Math.sqrt(Math.pow(selectedImage.getWidth(), 2) + Math.pow(selectedImage.getHeight(), 2));
			int paintx = (Mouse.x - (selectedImage.getWidth() >> 1)) + editingContext.xOffset() * -1;
			int painty = (Mouse.y - (selectedImage.getHeight() >> 1)) + editingContext.yOffset() * -1;
			
			BufferedImage newImage 
				= new BufferedImage(selectedImage.getWidth(), selectedImage.getHeight(), 
						BufferedImage.TYPE_INT_ARGB);
			
			g2d.setColor(new Color(86, 247, 217));
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.drawOval(Mouse.x - (hypotenuse / 2), Mouse.y - (hypotenuse / 2), hypotenuse, hypotenuse);
			
			// create a new image which handles tiling
			if (editingContext.tiledPaintingEnabled()) {
				Graphics2D paintg2d = newImage.createGraphics();
				
				paintg2d.drawImage(editingContext.selectedTexture().image(), 
					0 - (paintx % selectedImage.getWidth()), 
					0 - (painty % selectedImage.getHeight()), null);
				
				paintg2d.drawImage(editingContext.selectedTexture().image(), 
						(0 - (paintx % selectedImage.getWidth()) + selectedImage.getWidth()), 
						0 - (painty % selectedImage.getHeight()), null);
				
				System.out.println(paintx % selectedImage.getWidth());
				System.out.println((0 - (paintx % selectedImage.getWidth()) + selectedImage.getWidth()));
				
				paintg2d.drawImage(editingContext.selectedTexture().image(), 
						(0 - (paintx % selectedImage.getWidth()) + selectedImage.getWidth()), 
						(0 - (painty % selectedImage.getHeight()) + selectedImage.getHeight()), null);
					
				paintg2d.drawImage(editingContext.selectedTexture().image(), 
						0 - (paintx % selectedImage.getWidth()), 
						(0 - (painty % selectedImage.getHeight()) + selectedImage.getHeight()), null);
				
				g2d.drawImage(newImage,
						Mouse.x - (selectedImage.getWidth() / 2), Mouse.y - (selectedImage.getHeight() / 2), null);
			} else {
				g2d.drawImage(selectedImage,
						Mouse.x - (selectedImage.getWidth() / 2), Mouse.y - (selectedImage.getHeight() / 2), null);
			}
		break;

		case MOVE_WORLD:
			g2d.drawImage(DefaultIcons.moveWorldIcon.getImage(), Mouse.x - 8, Mouse.y - 8, null);
		break;

		default:
			g2d.drawImage(cursorImage.getImage(), Mouse.x - 8, Mouse.y - 8, null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1) {
			Mouse.setLeftButtonState(MouseState.CLICKED);
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			Mouse.setMiddleButtonState(MouseState.CLICKED);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Mouse.setRightButtonState(MouseState.CLICKED);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		grabFocus();
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
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_SPACE:
			Keyboard.SPACE_BAR = KeyState.PRESSED;
		break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_SPACE:
			Keyboard.SPACE_BAR = KeyState.RELEASED;
		break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_SPACE:
			Keyboard.SPACE_BAR = KeyState.TYPED;
		break;
		}
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