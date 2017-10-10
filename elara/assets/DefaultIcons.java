/**
 * Author: Brandon
 * Date Created: 6 Oct. 2017
 * File : DefaultIcons.java
 */
package elara.assets;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.swing.ImageIcon;

/**
 * DefaultIcons
 *
 * Description: A bunch of default icons which are loaded
 * for the editor. All static access.
 */
public class DefaultIcons
{
	public static final int[] BLANK_COLOR = new int[] {255, 0, 255, 255};
	
	public static final BufferedImage BLANK_ICON_16
		= new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	
	public static final BufferedImage BLANK_ICON_32
		= new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
	
	static {
		// create the blank icons as completely magenta
		WritableRaster r32 = BLANK_ICON_32.getRaster();
		WritableRaster r16 = BLANK_ICON_16.getRaster();
		for (int x = 0; x < 32; x++) {
			for (int y = 0; y < 32; y++) {
				r32.setPixel(x, y, BLANK_COLOR);
				
				if (x < 16 && y < 16)
					r16.setPixel(x, y, BLANK_COLOR);
			}
		}
	}
	
	/*
	 * 16x16 icons
	 */
	
	public static ImageIcon soundIcon;
	public static ImageIcon newLayerIcon;
	public static ImageIcon deleteLayerIcon;
	public static ImageIcon textureLayersIcon;
	public static ImageIcon moveWorldIcon;
	
	/*
	 * 32x32 icons
	 */
	
	public static ImageIcon selectToolIcon;
	public static ImageIcon moveToolIcon;
	public static ImageIcon rotateToolIcon;
	public static ImageIcon playIcon;
	
	// check if icons exist otherwise set them to the blank
	static {
		soundIcon = new ImageIcon("res/icons/sound.png");
		newLayerIcon = new ImageIcon("res/icons/newLayer.png");
		deleteLayerIcon = new ImageIcon("res/icons/deleteLayer.png");
		textureLayersIcon = new ImageIcon("res/icons/textureLayers.png");
		moveWorldIcon = new ImageIcon("res/icons/moveWorldTool.png");
		
		selectToolIcon = new ImageIcon("res/icons/selectToolIcon.png");
		moveToolIcon = new ImageIcon("res/icons/moveToolIcon.png");
		rotateToolIcon = new ImageIcon("res/icons/rotateToolIcon.png");
		playIcon = new ImageIcon("res/icons/playIcon.png");
		
		File testFile = new File("res/icons/sound.png");
		if (!testFile.exists()) {
			soundIcon.setImage(BLANK_ICON_32);
		}
		
		testFile = new File("res/icons/newLayer.png");
		if (!testFile.exists()) {
			newLayerIcon.setImage(BLANK_ICON_16);
		}
		
		testFile = new File("res/icons/deleteLayer.png");
		if (!testFile.exists()) {
			deleteLayerIcon.setImage(BLANK_ICON_16);
		}
		
		testFile = new File("res/icons/textureLayers.png");
		if (!testFile.exists()) {
			textureLayersIcon.setImage(BLANK_ICON_16);
		}
		
		testFile = new File("res/icons/moveWorldTool.png");
		if (!testFile.exists()) {
			moveWorldIcon.setImage(BLANK_ICON_16);
		}
		
		testFile = new File("res/icons/selectToolIcon.png");
		if (!testFile.exists()) {
			selectToolIcon.setImage(BLANK_ICON_32);
		}
		
		testFile = new File("res/icons/moveToolIcon.png");
		if (!testFile.exists()) {
			moveToolIcon.setImage(BLANK_ICON_32);
		}
		
		testFile = new File("res/icons/rotateToolIcon.png");
		if (!testFile.exists()) {
			rotateToolIcon.setImage(BLANK_ICON_32);
		}
		
		testFile = new File("res/icons/playIcon.png");
		if (!testFile.exists()) {
			playIcon.setImage(BLANK_ICON_32);
		}
	}
}
