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

  //TODO: Support only rectangular images

  /**
   * To get a {@code Pixel} at the given coordinates.
   *
   * @param row the row of the requested pixel
   * @param col the column of the requested pixel
   * @return the {@code Pixel} at the given coordinates
   * @throws IllegalArgumentException if the coordinates are at an invalid location
   */
  Pixel getPixelAt(int row, int col) throws IllegalArgumentException;
}
