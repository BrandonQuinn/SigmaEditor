
package elara.editor.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import org.joml.Vector2f;
import elara.assets.DefaultIcons;
import elara.assets.Sound;
import elara.editor.imageprocessing.ImageProcessor;
import elara.editor.input.KeyState;
import elara.editor.input.Keyboard;
import elara.editor.input.Mouse;
import elara.editor.input.MouseState;
import elara.editor.rendering.RenderStats;
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
		MouseMotionListener,
		MouseWheelListener
{
	private static final long serialVersionUID = 1L;

	private EditingContext editingContext = EditingContext.editingContext();
	private GameModel gameModel = GameModel.gameModel();

	private MainWindow mainWindow;

	/*
	 * Mouse cursor.
	 */
	private ImageIcon cursorImage;

	public RenderPanel(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);

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
		
		long time = System.currentTimeMillis();
		
		drawDefaultBackground(g2d);
		setup(g2d);
		gameModel.draw(editingContext.xOffset(), editingContext.yOffset(), g2d);
		handleInput();
		drawMouseCursor(g2d);
		handleEditingState(g2d);
		
		drawDebugInfo(g2d);

		RenderStats.frameTime = System.currentTimeMillis() - time;
	}
	
	/**
	 * Prepare the renderer
	 * @param g2d
	 */
	private void setup(Graphics2D g2d) 
	{
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Tahoma", Font.BOLD, 12));
	}

	/**
	 * Displays debug info at the top of the render window
	 */
	private void drawDebugInfo(Graphics2D g2d)
	{
		g2d.setColor(Color.WHITE);
		g2d.drawString("Tool: " + editingContext.state().toString() + " | "
				+ "Selected Texture Layer: " + (editingContext.getSelectedGroundLayerIndex() + 1)
				+ " | Offset x:" + editingContext.xOffset() + ", y: " + editingContext.yOffset()
				+ " | Memory Usage: " + Runtime.getRuntime().totalMemory()/1000000 + "MB/" + Runtime.getRuntime().maxMemory()/1000000 + "MB", 5, 15);
		
		g2d.drawString("Frame Time: " + RenderStats.frameTime + "ms", 5, 30);
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
		
		// state controlled input, no privatisation here!
		switch (editingContext.state()) {
			case SELECT:
			break;
			
			case TEXTURE_PAINT:
				// comma and period keys control opacity
				if (Keyboard.PERIOD == KeyState.PRESSED) {
					editingContext.assignTextureBrushOpacity(editingContext.textureBrushOpacity() + 0.01f);
					mainWindow.evaluateState();
				}
				
				if (Keyboard.COMMA == KeyState.PRESSED) {
					editingContext.assignTextureBrushOpacity(editingContext.textureBrushOpacity() - 0.01f);
					mainWindow.evaluateState();
				}
			break;
			
			case MOVE_WORLD:
			break;
			
			case ADD_SOUND:
			break;
			
			default:
			break;
			
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
				BufferedImage buffIm = gameModel.groundTextureLayers()
						.get(editingContext.getSelectedGroundLayerIndex());
				
				Graphics2D buffG = buffIm.createGraphics();
				
				BufferedImage newImage = new BufferedImage(
						paintTexture.getWidth(), paintTexture.getHeight(), 
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D ng = newImage.createGraphics();
				ng.drawImage(paintTexture, 0, 0, null);

				int paintx = (Mouse.x - (paintTexture.getWidth() >> 1)) + editingContext.xOffset() * -1;
				int painty = (Mouse.y - (paintTexture.getHeight() >> 1)) + editingContext.yOffset() * -1;
				
				// create a new image which handles tiling
				if (editingContext.tiledPaintingEnabled()) {
					ng.drawImage(paintTexture, 
							0 - (paintx % newImage.getWidth()), 
							0 - (painty % newImage.getHeight()),
							null);
					
					ng.drawImage(paintTexture, 
							(0 - (paintx % newImage.getWidth()) + newImage.getWidth()), 
							0 - (painty % newImage.getHeight()),
							null);
					
					ng.drawImage(paintTexture, 
							(0 - (paintx % newImage.getWidth()) + newImage.getWidth()), 
							(0 - (painty % newImage.getHeight()) + newImage.getHeight()),
							null);
					
					ng.drawImage(paintTexture, 
							0 - (paintx % newImage.getWidth()), 
							(0 - (painty % newImage.getHeight()) + newImage.getHeight()),
							null);
				}
				
				// BRUSH TYPE
				switch (editingContext.selectedBrushFilter()) {
					case RADIAL_FALLOFF:
						newImage = ImageProcessor.radialAlphaFalloff(newImage);
					break;
					
					case NONE:
						// Do nothing
					break;
					
					default:
					break;
				}
				
				// BLEND MODE
				switch (editingContext.selectedBlendMode()) {
					case OVERLAP:
						// Do nothing
					break;
				
					case MULTIPLY:
						
						// FIXME out of bounds, blend mode
						newImage = ImageProcessor.multiply(newImage, 
								buffIm.getSubimage(paintx, painty, newImage.getWidth(), newImage.getHeight()));
					break;
					
					case OVERLAY:
						newImage = ImageProcessor.overlay(newImage, 0, 0, 
								buffIm, paintx, painty, 
								newImage.getWidth(), newImage.getHeight());
					break;
					
					case SCREEN:
					break;
					
					default:
					break;
				}
				
				newImage = ImageProcessor.setOpacity(newImage, editingContext.textureBrushOpacity());
				buffG.drawImage(newImage, paintx, painty, null);
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
		
		case ADD_SOUND:
				if (Mouse.isLeftButtonClicked() && gameModel.assetLayers().size() != 0) {
					Sound s = new Sound(editingContext.selectedSound());
					s.setPosition(new Vector2f(Mouse.x - editingContext.xOffset(), 
							Mouse.y - editingContext.yOffset() - DefaultIcons.soundIcon.getIconHeight()));
					editingContext.selectedAssetLayer().addSound(s);
				}
			break;
			
		default:
			break;
		}
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
	 * FIXME Selection only works in 1 direction
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
				g2d.setColor(new Color(255, 255, 255));
				g2d.setStroke(new BasicStroke(2.0f));
				g2d.drawOval(Mouse.x - (selectedImage.getWidth() >> 1), Mouse.y - (selectedImage.getHeight() >> 1), 
						selectedImage.getWidth(), selectedImage.getHeight());
			break;
	
			case MOVE_WORLD:
				g2d.drawImage(DefaultIcons.moveWorldIcon.getImage(), 
						Mouse.x - DefaultIcons.moveWorldIcon.getIconWidth() / 2, 
						Mouse.y - DefaultIcons.moveWorldIcon.getIconHeight() / 2, null);
			break;
			
			case ADD_SOUND:
				g2d.drawImage(DefaultIcons.soundIcon.getImage(),
					Mouse.x  - DefaultIcons.soundIcon.getIconWidth() / 2, 
					Mouse.y - DefaultIcons.soundIcon.getIconHeight() / 2, null);
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
			case KeyEvent.VK_PERIOD:
				Keyboard.PERIOD = KeyState.PRESSED;
			break;
			case KeyEvent.VK_COMMA:
				Keyboard.COMMA = KeyState.PRESSED;
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
			case KeyEvent.VK_PERIOD:
				Keyboard.PERIOD = KeyState.RELEASED;
			break;
			case KeyEvent.VK_COMMA:
				Keyboard.COMMA = KeyState.RELEASED;
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
			case KeyEvent.VK_PERIOD:
				Keyboard.PERIOD = KeyState.TYPED;
			break;
			case KeyEvent.VK_COMMA:
				Keyboard.COMMA = KeyState.TYPED;
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

	/* (non-Javadoc)
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		
	}
}
