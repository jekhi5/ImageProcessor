package model;

import commands.ImageEditorCommand;
import model.image.Image;

/**
 * This interface represents the actual model of editing the images. It houses the functionality to
 * control how pixels get modified, and in what way. It supports editing images with and without
 * alpha-modification capabilities (jpegs vs PNGs)
 */
public interface ImageEditorModel {

  /**
   * To get the image with the given name.
   *
   * @param name the name of the image
   * @return the image with the given name
   * @throws IllegalArgumentException if there is no image with the given name
   */
  Image getImage(String name) throws IllegalArgumentException;

  /**
   * To add the given {@code Image} into the editor with the given name. Will overwrite any images
   * that share the name.
   *
   * @param name  the name of the new image
   * @param image the image to add
   * @throws IllegalArgumentException if either argument is null
   */
  void addImage(String name, Image image) throws IllegalArgumentException;


  /**
   * Executes the given {@link ImageEditorCommand} on this model.
   *
   * @param cmd the command to execute
   * @return the result message of the command's execution
   * @throws IllegalArgumentException if the command is null
   */
  String execute(ImageEditorCommand cmd) throws IllegalArgumentException;
}
