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
}
