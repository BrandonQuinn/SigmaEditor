/**
 * Author: Brandon
 * Date Created: 11 Oct. 2017
 * File : ImageProcessingEvent.java
 */
package elara.editor.imageprocessing;

/**
 * ImageProcessingEvent
 *
 * Description: Represents the required functions
 * needed to run an image processing algorithm
 * on the image processing thread.
 */
public interface ImageProcessingEvent
{
	/**
	 * Runs the algorithms needed to process the images.
	 * This is all handled in the implementing classes. Of course.
	 */
	public void processImages();
}
