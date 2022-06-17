package model.image;

import java.io.IOException;

import model.ImageEditorModel;
import model.pixel.Pixel;

/**
 * An image that will be used with the {@link ImageEditorModel}.
 */
public interface Image {

  /**
   * To get a {@code Pixel} at the given coordinates.
   *
   * @param row the row of the requested pixel
   * @param col the column of the requested pixel
   * @return the {@code Pixel} at the given coordinates
   * @throws IllegalArgumentException if the coordinates are at an invalid location
   */
  Pixel getPixelAt(int row, int col) throws IllegalArgumentException;

  /**
   * To set a {@code Pixel} at the given coordinates to the given pixel.
   *
   * @param row      the row of the pixel to be set
   * @param col      the column of the pixel to be set
   * @param newPixel the new pixel
   * @return the pixel that occupied (row, col) before it was set
   * @throws IllegalArgumentException if the coordinates are out of bounds
   */
  Pixel setPixelAt(int row, int col, Pixel newPixel) throws IllegalArgumentException;

  /**
   * To get the width of this image in pixels.
   *
   * @return the width of this image
   */
  int getWidth();

  /**
   * To get the width of this image in pixels.
   *
   * @return the width of this image
   */
  int getHeight();

  /**
   * To get a copy of this image in the same format.
   *
   * @return the new image
   */
  Image getCopy();

  /**
   * Saves this image to the given path. This method used to be {@code toSavableText} because we
   * were under the assumption that all image formats could, like PPM images, be saved to a file as
   * text.
   *
   * @param path            the path of the new image
   * @param shouldOverwrite should the new file overwrite any existing file?
   * @throws java.io.IOException      if there is an issue with saving the file on the system end
   * @throws IllegalArgumentException if the given path is not a valid path (is a directory, is
   *                                  null, etc.)
   */
  void saveToPath(String path, boolean shouldOverwrite)
          throws IOException, IllegalArgumentException;
}
