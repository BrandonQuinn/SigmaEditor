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
 * 
 * TODO Check performance of DefaultIcons static operations
 */
public class DefaultIcons
{
	public static final BufferedImage BLANK_ICON 
		= new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	
	static {
		// create the BLANK_ICON as completely magenta
		WritableRaster raster = BLANK_ICON.getRaster();
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				raster.setPixel(x, y, new int[] {255, 0, 255, 255});
			}
		}
	}
	
	public static ImageIcon soundIcon = new ImageIcon("res/icons/sound.png");
	public static ImageIcon newLayerIcon = new ImageIcon("res/icons/newLayer.png");
	public static ImageIcon deleteLayerIcon = new ImageIcon("res/icons/deleteLayer.png");
	public static ImageIcon textureLayersIcon = new ImageIcon("res/icons/textureLayers.png");
	public static ImageIcon moveWorldIcon = new ImageIcon("res/icons/moveWorldTool.png");
	
	// check if icons exist otherwise set them to the blank
	static {
		File testFile = new File("res/icons/sound.png");
		if (!testFile.exists()) {
			soundIcon.setImage(BLANK_ICON);
		}
		
		testFile = new File("res/icons/newLayer.png");
		if (!testFile.exists()) {
			newLayerIcon.setImage(BLANK_ICON.getScaledInstance(16, 16, BufferedImage.SCALE_DEFAULT));
		}
		
		testFile = new File("res/icons/deleteLayer.png");
		if (!testFile.exists()) {
			deleteLayerIcon.setImage(BLANK_ICON.getScaledInstance(16, 16, BufferedImage.SCALE_DEFAULT));
		}
		
		testFile = new File("res/icons/textureLayers.png");
		if (!testFile.exists()) {
			textureLayersIcon.setImage(BLANK_ICON.getScaledInstance(16, 16, BufferedImage.SCALE_DEFAULT));
		}
		
		testFile = new File("res/icons/moveWorldTool.png");
		if (!testFile.exists()) {
			moveWorldIcon.setImage(BLANK_ICON.getScaledInstance(16, 16, BufferedImage.SCALE_DEFAULT));
		}
	}
}
