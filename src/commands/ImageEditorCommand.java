package commands;

import model.ImageEditorModel;

/**
 * Commands represent a string of edits to an image that result in an overarching complete edit. For
 * example, in order to greyscale an image according to the red-level, ALL the pixels in the image
 * must be updated. This can be accomplished by allowing a single command to handle the entire
 * operation. This is both to make the code easier to read, and more user-friendly.
 */
public interface ImageEditorCommand {
  /**
   * To execute {@code this} command on the given model. This method performs a series of edits on
   * the given model.
   *
   * @param model the model containing the image(s) which this command should be applied to.
   * @return a message indicating the completion of this command.
   * @throws IllegalArgumentException if the given model is null
   */
  String apply(ImageEditorModel model) throws IllegalArgumentException;

  /**
   * To apply the given mask on the image specified in this command.
   *
   * @param model      the model that this image resides
   * @param pathToMask the path in the file system to the mask that will be applied to this image
   * @return a message indicating the completion of this command.
   * @throws java.lang.IllegalArgumentException      if the given model is null or the path is
   *                                                 invalid
   * @throws java.lang.UnsupportedOperationException if a mask command is not offered by this
   *                                                 command
   */
  String applyMask(ImageEditorModel model, String pathToMask) throws IllegalArgumentException,
          UnsupportedOperationException;
}
