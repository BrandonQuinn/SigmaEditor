
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
import elara.assets.AssetSelector;
import elara.assets.DefaultIcons;
import elara.assets.Sound;
import elara.assets.SpawnPoint;
import elara.assets.Texture;
import elara.editor.imageprocessing.ImageProcessor;
import elara.editor.input.InputManager;
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

	private EditingContext editCon = EditingContext.editingContext();
	private GameModel gameModel = GameModel.gameModel();
	private InputManager inMan = InputManager.inputManager();

	private MainWindow mainWindow;

	/*
	 * Mouse cursor.
	 */
	private ImageIcon cursorImage;
	
	/*
	 * Selection rectangle
	 */
	private SelectionRectangle selectionRectangle 
		= new SelectionRectangle();

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
		gameModel.draw(editCon.xOffset(), editCon.yOffset(), g2d);
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
		g2d.drawString("Tool: " + editCon.state().toString() + " | "
				+ "Selected Texture Layer: " + (editCon.getSelectedGroundLayerIndex() + 1)
				+ " | Offset x:" + editCon.xOffset() + ", y: " + editCon.yOffset()
				+ " | Memory Usage: " + Runtime.getRuntime().totalMemory()/1000000 + "MB/" 
				+ Runtime.getRuntime().maxMemory()/1000000 + "MB", 5, 15);
		
		g2d.drawString("Frame Time: " + RenderStats.frameTime + "ms", 5, 30);
	}

	/**
	 * Handles any changes to input that need to be made
	 */
	private void handleInput()
	{
		inMan.pollInput();
		
		// handle space bar pressed and change state only when we are just enterting this state
		if ((Keyboard.SPACE_BAR == KeyState.PRESSED)
				&& editCon.state() != EditingState.MOVE_WORLD) {
			editCon.assignState(EditingState.MOVE_WORLD);
		}
		
		if (Keyboard.S == KeyState.PRESSED) {
			editCon.assignState(EditingState.SELECT);
		}
		
		// state controlled input, no privatisation here!
		switch (editCon.state()) {
			case SELECT:
			break;
			
			case TEXTURE_PAINT:
				// comma and period keys control opacity
				if (Keyboard.PERIOD == KeyState.PRESSED) {
					editCon.assignTextureBrushOpacity(editCon.textureBrushOpacity() + 0.01f);
					mainWindow.evaluateState();
				}
				
				if (Keyboard.COMMA == KeyState.PRESSED) {
					editCon.assignTextureBrushOpacity(editCon.textureBrushOpacity() - 0.01f);
					mainWindow.evaluateState();
				}
			break;
			
			case MOVE_WORLD:
			break;
			
			case ADD_SOUND:
			break;

			case ADD_SPAWN_POINT:
			break;
			
			default:
			break;
			
		}
	}
	
	/*	 
	 * All this stuff is essentially anything that we may not 
	 * want to be recreating all the time in the loop. 
	 */

	private Integer moveStartX = null;
	private Integer moveStartY = null;
	
	BufferedImage paintTexture;
	BufferedImage buffIm;
	Graphics2D buffG;
	BufferedImage newImage;
	Graphics2D ng;
	int paintx = 0;
	int painty = 0;
	
	BufferedImage placeDecalImage = null;
	Texture previousDecal = null;
	
	/**
	 * Basically switch throw all the editing states and make the
	 * edits
	 * 
	 * @param g2d
	 */
	private void handleEditingState(Graphics2D g2d)
	{
		switch (editCon.state()) {
			case SELECT:

				// click and hold down on non-selection
				if (Mouse.isLeftButtonDown() && !selectionRectangle.isDrawn() && !AssetSelector.isMouseOnSelection()) {
					// deselect all
					AssetSelector.deselectAll();
					// check selection at this point
					AssetSelector.checkSelections(Mouse.x, Mouse.y);
				}
				
				// click and hold on selection
				if (Mouse.isLeftButtonDown() && !selectionRectangle.isDrawn() && AssetSelector.isMouseOnSelection()) {
					AssetSelector.moveSelection();
				} else {
					// we are not moving our selection, so draw the selection rectangle
					AssetSelector.checkSelections(selectionRectangle);
					selectionRectangle.draw(g2d);
					
				}
				
				if (!Mouse.isLeftButtonDown() && !selectionRectangle.isDrawn()) {
					AssetSelector.resetMouseStart();
				}
				
				if (Mouse.isLeftButtonClicked()) {
					mainWindow.evaluateState();
				}
				
			break;
	
			case TEXTURE_PAINT:
				
				if (Keyboard.BRACKET_LEFT == KeyState.PRESSED) {
					editCon.assignBrushSize(editCon.brushSize() - 0.01f);
				}
				
				if (Keyboard.BRACKET_RIGHT == KeyState.PRESSED) {
					editCon.assignBrushSize(editCon.brushSize() + 0.01f);
				}
				
				if (Mouse.isLeftButtonDown() && editCon.getSelectedGroundLayerIndex() != -1) {
					paintTexture = editCon.selectedTexture().image();
					
					// only change the buffered image if a different one has been selected
					if (buffIm != gameModel.groundTextureLayers().get(editCon.getSelectedGroundLayerIndex())) {
						buffIm = gameModel.groundTextureLayers().get(editCon.getSelectedGroundLayerIndex());
						buffG = buffIm.createGraphics();
					}
					
					int newImgWidth = (int)(paintTexture.getWidth() * editCon.brushSize());
					int newImgHeight = (int)(paintTexture.getHeight() * editCon.brushSize());
					
					newImage = new BufferedImage(
							newImgWidth, newImgHeight, 
							BufferedImage.TYPE_INT_ARGB);
					ng = newImage.createGraphics();
					ng.drawImage(paintTexture, 0, 0, null);
					
					paintx = (Mouse.x - (newImgWidth >> 1)) + editCon.xOffset() * -1;
					painty = (Mouse.y - (newImgHeight >> 1)) + editCon.yOffset() * -1;
					
					// create a new image which handles tiling
					if (editCon.tiledPaintingEnabled()) {
						// number of times to repeat the texture
						int numTexRpt = (int) (editCon.brushSize() + 1);
						
						for (int xt = 0; xt < numTexRpt+1; xt++) {
							for (int yt = 0; yt < numTexRpt+1; yt++) {
							// left to right
							ng.drawImage(paintTexture, 
									(0 - (paintx % paintTexture.getWidth()) + (paintTexture.getWidth() * xt)), 
									(0 - (painty % paintTexture.getHeight()) + (paintTexture.getHeight() * yt)),
									null);
							}
						}
					}
	
					// BRUSH TYPE
					switch (editCon.selectedBrushFilter()) {
						case RADIAL_FALLOFF:
							
							newImage = ImageProcessor.radialAlphaFalloff(newImage);
							
						break;
						
						case NONE:
							// Do nothing
						break;
						
						default:
						break;
					}
					
					BufferedImage subBuffIm;
					
					// BLEND MODE
					switch (editCon.selectedBlendMode()) {
						case OVERLAP:
							// Do nothing
						break;
					
						case MULTIPLY:
							
							subBuffIm = ImageProcessor.subImg(buffIm, paintx, painty, 
									newImgWidth, newImgHeight);
							newImage = ImageProcessor.multiply(newImage, subBuffIm);
						
						break;
						
						case OVERLAY:
							
							subBuffIm = ImageProcessor.subImg(buffIm, paintx, painty, 
									newImgWidth, newImgHeight);
							newImage = ImageProcessor.overlay(newImage, subBuffIm);
							
						break;
						
						case SCREEN:
						break;
						
						default:
						break;
					}
					
					newImage = ImageProcessor.setOpacity(newImage, editCon.textureBrushOpacity());
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
	
				/* detect a change and add it, what a might fine mess, but it works
				 * This code is complicated because it's blocking the user from moving
				 * too for left, right, above or bellow the game world. It's limited
				 * to half the width or high, so at least half of the size of the editor
				 * window will always have the actual game or level visible. Can't go farther.
				 */
				
				if (Mouse.x - moveStartX != 0 
						&& editCon.xOffset() <= (getWidth() >> 1) &&
						editCon.xOffset() >= -1 * gameModel.worldWidthPixels() + (getWidth() >> 1)) {
					editCon.addToXOffset(Mouse.x - moveStartX);
					moveStartX = Mouse.x;
				} else if (editCon.xOffset() >= (getWidth() >> 1)) {
					editCon.addToXOffset(-editCon.xOffset() + (getWidth() >> 1));
				} else if (editCon.xOffset() <= -1 * gameModel.worldWidthPixels() + (getWidth() >> 1)) {
					editCon.addToXOffset(-editCon.xOffset() + -1 * gameModel.worldWidthPixels() + (getWidth() >> 1));
				}
				
				if (Mouse.y - moveStartY != 0 
						&& editCon.xOffset() <= (getHeight()) &&
						editCon.yOffset() >= -1 * gameModel.worldHeightPixels() + (getHeight() >> 1)) {
					editCon.addToYOffset(Mouse.y - moveStartY);
					moveStartY = Mouse.y;
				} else if (editCon.yOffset() >= (getHeight() >> 1)) {
					editCon.addToYOffset(-editCon.yOffset() + (getHeight() >> 1));
				} else if (editCon.yOffset() <= -1 * gameModel.worldHeightPixels() + (getHeight() >> 1)) {
					editCon.addToYOffset(-editCon.yOffset() + -1 * gameModel.worldHeightPixels() + (getHeight() >> 1));
				}
				
				if (Keyboard.SPACE_BAR == KeyState.RELEASED) {
					moveStartX = null;
					moveStartY = null;
					Keyboard.SPACE_BAR = KeyState.NOT_PRESSED;	
					editCon.assignState(editCon.previousState());
				}
				
			break;
			
			case ADD_SOUND:
				
				// check if clicked and then add a new sound the curretly selected layer
				if (Mouse.isLeftButtonClicked() && gameModel.assetLayers().size() != 0) {
					Sound s = new Sound(editCon.selectedSound());
					s.setPosition(new Vector2f(Mouse.x - editCon.xOffset(), 
							Mouse.y - editCon.yOffset()));
					editCon.selectedAssetLayer().addSound(s);
					mainWindow.evaluateState();
				}
					
			break;
			
			case ADD_SPAWN_POINT:
				
				// check if clicked and then add a new spawn point to the currently selected layer
				if (Mouse.isLeftButtonClicked() && gameModel.assetLayers().size() != 0) {
					SpawnPoint sp = new SpawnPoint(editCon.selectedSpawnPoint());
					sp.setPosition(new Vector2f(Mouse.x - editCon.xOffset(), 
							Mouse.y - editCon.yOffset()));
					editCon.selectedAssetLayer().addSpawnPoint(sp);
					mainWindow.evaluateState();
				}
				
			break;
			
			case DECAL_PLACEMENT:
				
				// you spin me right 'round baby right 'round
				if (Mouse.isMiddleButtonDown()) {
					editCon.assignDecalRotation(editCon.decalRotation() + 0.2);
				}
				
				if (Mouse.isLeftButtonClicked()) {
					Texture decal = editCon.selectedDecal();
					int groundLayerIndex = editCon.getSelectedGroundLayerIndex();
					if (groundLayerIndex != -1) {
						BufferedImage texLayer 
							= gameModel.groundTextureLayers().get(groundLayerIndex);
						
						// get the positon to place the decal.
						paintx = (Mouse.x - (decal.image().getWidth() >> 1)) + editCon.xOffset() * -1;
						painty = (Mouse.y - (decal.image().getHeight() >> 1)) + editCon.yOffset() * -1;

						/*
						 * Since the image is going to be rotated and we have create a new image to
						 * house the rotated decal, then its width needs to be the length of the diagnal, not
						 * the regular width since, naturally if it's rotated 45 degrees then the diagnal is
						 * going across the width and will overun. Thus, the width of the new image needs to be
						 * the length of the diagnal.
						 */
						
						int hypotenuse = (int) Math.sqrt((decal.image().getWidth() << 1) * (decal.image().getHeight() << 1));
						
						placeDecalImage = new BufferedImage(hypotenuse, 
								hypotenuse, 
								BufferedImage.TYPE_INT_ARGB);
						
						// create the new image we are going to place and rotate it
						Graphics2D pgi = placeDecalImage.createGraphics();
						pgi.rotate(Math.toRadians(editCon.decalRotation()), placeDecalImage.getWidth() >> 1, placeDecalImage.getHeight() >> 1);
						pgi.drawImage(decal.image(), (placeDecalImage.getWidth() >> 1) - (decal.image().getWidth() >> 1), 
								(placeDecalImage.getHeight() >> 1) - (decal.image().getHeight() >> 1), null);
						
						Graphics2D dest = texLayer.createGraphics();
						dest.drawImage(placeDecalImage, (paintx + (decal.image().getHeight() >> 1)) - (placeDecalImage.getWidth() >> 1), 
								(painty + (decal.image().getWidth() >> 1)) - (placeDecalImage.getHeight() >> 1), null);
					}
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
		g2d.fillRect(editCon.xOffset(),
				editCon.yOffset(),
				gameModel.worldWidthPixels(),
				gameModel.worldHeightPixels());

		g2d.setColor(new Color(50, 50, 50));

		// draw a simple grid
		for (int x = 0; x < gameModel.worldWidthPixels() + 1; x += GameModel.GRID_SIZE) {
			for (int y = 0; y < gameModel.worldHeightPixels() + 1; y += GameModel.GRID_SIZE) {

				g2d.drawLine(x + editCon.xOffset(), 
						0 + editCon.yOffset(),
						x + editCon.xOffset(), 
						gameModel.worldHeightPixels() + editCon.yOffset());

				g2d.drawLine(0 + editCon.xOffset(), 
						y + editCon.yOffset(), 
						gameModel.worldWidthPixels() + editCon.xOffset(), 
						y + editCon.yOffset());
			}
		}
	}

	/**
	 * Draw the mouse cursor to the component.
	 */
	public void drawMouseCursor(Graphics2D g2d)
	{
		switch (editCon.state()) {
			case SELECT:
				
				g2d.drawImage(cursorImage.getImage(), Mouse.x - 8, Mouse.y - 8, null);
				
			break;
	
			case TEXTURE_PAINT:
				
				BufferedImage selectedImage = editCon.selectedTexture().image();
				g2d.setColor(new Color(255, 255, 255));
				g2d.setStroke(new BasicStroke(2.0f));
				g2d.drawOval((int)(Mouse.x - ((selectedImage.getWidth() * editCon.brushSize()) / 2)), 
					(int)(Mouse.y - ((selectedImage.getHeight() * editCon.brushSize()) / 2)), 
					(int)(selectedImage.getWidth() * editCon.brushSize()), 
					(int)(selectedImage.getHeight() * editCon.brushSize()));
				
			break;
	
			case MOVE_WORLD:
				
				g2d.drawImage(DefaultIcons.moveWorldIcon.getImage(), 
						Mouse.x - DefaultIcons.moveWorldIcon.getIconWidth() / 2, 
						Mouse.y - DefaultIcons.moveWorldIcon.getIconHeight() / 2, null);
				
			break;
			
			case ADD_SOUND:
				
				g2d.drawImage(DefaultIcons.soundIcon.getImage(),
					Mouse.x  - (DefaultIcons.soundIcon.getIconWidth() >> 1), 
					Mouse.y - (DefaultIcons.soundIcon.getIconHeight() >> 1), null);
				
			break;
			
			case ADD_SPAWN_POINT:
				
				SpawnPoint sp = editCon.selectedSpawnPoint();
				ImageIcon chosenIcon = DefaultIcons.spawnIcon;
				
				if (sp != null) {
					switch (sp.team()) {
						case 1:
							chosenIcon = DefaultIcons.spawnIcon1;
						break;
						
						case 2:
							chosenIcon = DefaultIcons.spawnIcon2;
						break;
						
						case 3:
							chosenIcon = DefaultIcons.spawnIcon3;
						break;
					}
					
					g2d.drawImage(chosenIcon.getImage(),
							Mouse.x  - (chosenIcon.getIconWidth() >> 1), 
							Mouse.y - (chosenIcon.getIconHeight() >> 1), null);
				}

			break;
	
			case DECAL_PLACEMENT:

				g2d.rotate(Math.toRadians(editCon.decalRotation()), Mouse.x, Mouse.y);
				
				g2d.drawImage(editCon.selectedDecal().image(), 
						Mouse.x - (editCon.selectedDecal().image().getWidth() >> 1), 
						Mouse.y - (editCon.selectedDecal().image().getHeight() >> 1), null);

				g2d.rotate(Math.toRadians(-editCon.decalRotation()), Mouse.x, Mouse.y);
				
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
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			Mouse.setLeftButtonState(MouseState.NOT_PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			Mouse.setMiddleButtonState(MouseState.NOT_PRESSED);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Mouse.setRightButtonState(MouseState.NOT_PRESSED);
		}
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
			if (Mouse.isLeftButtonDown()) {
				Mouse.setLeftButtonState(MouseState.CLICKED);
			} else {
				Mouse.setLeftButtonState(MouseState.NOT_PRESSED);
			}
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			if (Mouse.isMiddleButtonDown()) {
				Mouse.setMiddleButtonState(MouseState.CLICKED);
			} else {
				Mouse.setMiddleButtonState(MouseState.NOT_PRESSED);
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (Mouse.isRightButtonDown()) {
				Mouse.setRightButtonState(MouseState.CLICKED);
			} else {
				Mouse.setRightButtonState(MouseState.NOT_PRESSED);
			}
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
			case KeyEvent.VK_S:
				Keyboard.S = KeyState.PRESSED;
			break;
			case KeyEvent.VK_BRACERIGHT:
				Keyboard.BRACKET_RIGHT = KeyState.PRESSED;
			break;
			case KeyEvent.VK_BRACELEFT:
				Keyboard.BRACKET_LEFT = KeyState.PRESSED;
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
			case KeyEvent.VK_S:
				Keyboard.S = KeyState.RELEASED;
			break;
			case KeyEvent.VK_BRACERIGHT:
				Keyboard.BRACKET_RIGHT = KeyState.RELEASED;
			break;
			case KeyEvent.VK_BRACELEFT:
				Keyboard.BRACKET_LEFT = KeyState.RELEASED;
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
			case KeyEvent.VK_S:
				Keyboard.S = KeyState.TYPED;
			break;
			case KeyEvent.VK_BRACERIGHT:
				Keyboard.BRACKET_RIGHT = KeyState.TYPED;
			break;
			case KeyEvent.VK_BRACELEFT:
				Keyboard.BRACKET_LEFT = KeyState.TYPED;
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
	}
}
