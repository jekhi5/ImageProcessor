package model;

import commands.ImageEditorCommand;
import image.ImageModel;

/**
 * This interface represents the actual model of editing the images. It houses the functionality to
 * control how pixels get modified, and in what way. It supports editing images with and without
 * alpha-modification capabilities (jpegs vs PNGs)
 */
public interface ImageEditorModel {
  /**
   * To execute the given Image Editor Command. Based on the given command, this method will
   * determine its validity and execute the requested procedure on this models image.
   *
   * @param cmd   the command to be executed
   * @param image the image that the edits will be made on
   * @return a message indicating the status of the {@link ImageEditorCommand}'s execution
   * @throws IllegalArgumentException if the given command isn't valid for this model
   */
  String execute(ImageEditorCommand cmd, ImageModel image) throws IllegalArgumentException;

  /**
   * To get the {@code ImageModel} for the given name.
   *
   * @param name the name of the image
   * @return the ImageModel with that name
   * @throws IllegalArgumentException if the name is invalid
   */
  ImageModel getImage(String name) throws IllegalArgumentException;
}
