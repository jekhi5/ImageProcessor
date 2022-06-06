package model.image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.pixel.Pixel;
import utilities.ImageUtil;

/**
 * Represents a PPM Image. The text of the image is converted to an array of pixels that can be
 * individually modified.
 *
 * @author Jacob Kline
 * @created 06/06/2022
 */
public class PPMImage implements Image {

  private final List<List<Pixel>> pixelArray;

  /**
   * To construct a PPM image based on the text of the image.
   *
   * @param fileName the path to the file of the PPM Image as a string
   * @throws IllegalArgumentException if the given file name is either not a valid file path or the
   *                                  path does not lead to a PPM Image
   */
  public PPMImage(String fileName) {
    this.pixelArray = new ArrayList<>(ImageUtil.readPPM(fileName));
  }

  @Override
  public Iterator<Pixel> iterator() {
    return new PixelIterator(this.pixelArray);
  }

  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row > this.pixelArray.size() || col < 0 ||
            col > this.pixelArray.get(row).size()) {
      throw new IllegalArgumentException("Error. The given coordinates: (" + row + ", " + col +
              "). is out of bounds.");
    } else {
      return this.pixelArray.get(row).get(col);
    }
  }
}
