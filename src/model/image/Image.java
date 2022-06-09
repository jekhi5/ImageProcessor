package model.image;

import model.ImageEditorModel;
import model.pixel.Pixel;

/**
 * An image that will be used with the {@link ImageEditorModel}.
 *
 * @author Jacob Kline
 * @created 06/06/2022
 */
public interface Image extends Iterable<Pixel> {

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
  Image getCopy() throws IllegalArgumentException;

  /**
   * To get a string representation of this image in a PPM format.
   *
   * @return the PPM string
   */
  String toString();
}
