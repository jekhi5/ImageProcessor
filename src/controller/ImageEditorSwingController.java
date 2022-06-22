package controller;

import java.awt.image.BufferedImage;

import model.image.Image;

/**
 * A controller interface that allows compatibility between a Swing GUI view and model. Allows the
 * view to collect user input and passes it to the model.
 */
public interface ImageEditorSwingController {

  /**
   * Runs the given command on the model, and gives output results to the view.
   *
   * @param command the command as a String, in the form it would be used in interactive scripting.
   */
  void runCommand(String command);

  /**
   * Returns an Image of the given image name from the model.
   * @param name the name of the image
   * @return the desired Image
   * @throws IllegalArgumentException if the name doesn't exist.
   */
  Image getImage(String name) throws IllegalArgumentException;
}
