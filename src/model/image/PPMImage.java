package model.image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.pixel.Pixel;
import model.pixel.PixelImpl;

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
   * To construct a PPMImage with the given array of pixels.
   *
   * @param pixelArray is the array of pixels of the image
   * @throws IllegalArgumentException if the pixel array is null
   */
  public PPMImage(List<List<Pixel>> pixelArray) throws IllegalArgumentException {
    if (pixelArray == null) {
      throw new IllegalArgumentException("Error. The given Pixel array cannot be null.");
    } else {
      this.pixelArray = pixelArray;
    }
  }

  @Override
  public Iterator<Pixel> iterator() {
    return new PixelIterator(new ArrayList<>(this.pixelArray));
  }

  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row > this.pixelArray.size() || col < 0 ||
            col > this.pixelArray.get(row).size()) {
      throw new IllegalArgumentException("Error. The given coordinates: (" + row + ", " + col +
              "). is out of bounds.");
    } else {
      return new PixelImpl(this.pixelArray.get(row).get(col));
    }
  }

  @Override
  public Pixel setPixelAt(int row, int col, Pixel newPixel) throws IllegalArgumentException {
    if (row < 0 || row > this.pixelArray.size() || col < 0 ||
            col > this.pixelArray.get(row).size()) {
      throw new IllegalArgumentException("Error. The given coordinates were out of bounds. Given:" +
              " (" + row + ", " + col + ").");
    }

    Pixel priorPixel = this.pixelArray.get(row).get(col);

    this.pixelArray.get(row).set(col, newPixel);
    return new PixelImpl(priorPixel);
  }

  @Override
  public int getWidth() {
    return this.pixelArray.size();
  }

  @Override
  public int getHeight() {
    // In the parsing of the PPM file we check to see if the image has a width or height of 0, so
    // it's ok to use .get(0) here because we know there is at least 1 column.
    return this.pixelArray.get(0).size();
  }

  @Override
  public Image getCopy() {
    List<List<Pixel>> newPixelArray = new ArrayList<>();

    for (List<Pixel> row : this.pixelArray) {
      List<Pixel> curRow = new ArrayList<>();
      for (Pixel pixel : row) {
        Pixel newPixel = new PixelImpl(pixel);
        curRow.add(newPixel);
      }
      newPixelArray.add(curRow);
    }

    return new PPMImage(newPixelArray);
  }
}
