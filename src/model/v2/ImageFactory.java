package model.v2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.image.Image;

/**
 * A factory that for creating an {@link model.image.Image}, with implementation type determined at
 * runtime.
 */
public class ImageFactory {

  /**
   * Creates a new {@link model.image.Image}, generated from the given path.
   * @param path the path of the image file
   * @return a new {@link model.image.Image}.
   * @throws IllegalArgumentException if the path is invalid
   */
  public static Image createImage(String path) throws IllegalArgumentException {
    if (path == null) {
      throw new IllegalArgumentException("Path can't be null.");
    }

    File f = new File(path);
    try {
      BufferedImage img = ImageIO.read(f);
      return new BetterImage(img);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid path: " + e.getMessage());
    }
  }
}
