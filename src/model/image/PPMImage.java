package model.image;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
   * @throws IllegalArgumentException if the pixel array is null, empty, or non-rectangular.
   */
  public PPMImage(List<List<Pixel>> pixelArray) throws IllegalArgumentException {
    if (pixelArray == null) {
      throw new IllegalArgumentException("Error. The given Pixel array cannot be null.");
    } else if (pixelArray.size() == 0) {
      throw new IllegalArgumentException("Error. The given Pixel array cannot be empty.");
    } else {
      this.pixelArray = pixelArray;
      if (!this.isRectangular()) {
        throw new IllegalArgumentException("Error. PPM images must be rectangular.");
      }
    }
  }

  @Override
  public Iterator<Pixel> iterator() {
    return new PixelIterator(new ArrayList<>(this.pixelArray));
  }

  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.pixelArray.size() || col < 0 ||
            col >= this.pixelArray.get(row).size()) {
      throw new IllegalArgumentException("Error. The given coordinates: (" + row + ", " + col +
              "). is out of bounds.");
    } else {
      return new PixelImpl(this.pixelArray.get(row).get(col));
    }
  }

  @Override
  public Pixel setPixelAt(int row, int col, Pixel newPixel) throws IllegalArgumentException {
    if (row < 0 || row >= this.pixelArray.size() || col < 0 ||
            col >= this.pixelArray.get(row).size()) {
      throw new IllegalArgumentException("Error. The given coordinates were out of bounds. Given:" +
              " (" + row + ", " + col + ").");
    }

    if (newPixel == null) {
      throw new IllegalArgumentException("Error. Cannot set a null pixel.");
    }

    Pixel priorPixel = this.pixelArray.get(row).get(col);

    this.pixelArray.get(row).set(col, newPixel);
    return new PixelImpl(priorPixel);
  }

  @Override
  public int getWidth() {
    return this.pixelArray.get(0).size();
  }

  @Override
  public int getHeight() {
    // In the parsing of the PPM file we check to see if the image has a width or height of 0, so
    // it's ok to use .get(0) here because we know there is at least 1 column.
    return this.pixelArray.size();
  }

  @Override
  public Image getCopy() {
    List<List<Pixel>> newPixelArray = new ArrayList<>();

    for (List<Pixel> row : this.pixelArray) {
      List<Pixel> curRow = new ArrayList<>();
      for (Pixel pixel : row) {
        curRow.add(new PixelImpl(pixel));
      }
      newPixelArray.add(curRow);
    }

    return new PPMImage(newPixelArray);
  }

  @Override
  public String toSavableText() {
    List<String> result = new ArrayList<>();
    result.add("P3");

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    result.add("# This image was created by the the Jacob Kline and Emery Jacobowitz's Image " +
            "Editor on: " + dtf.format(now));

    result.add(this.pixelArray.get(0).size() + " " + this.pixelArray.size());

    // The maxValue will go here

    int highestValue = -1;
    for (List<Pixel> row : this.pixelArray) {
      for (Pixel pix : row) {
        int pixRed = pix.getRed();
        int pixGreen = pix.getGreen();
        int pixBlue = pix.getBlue();

        highestValue = Math.max(highestValue, (Math.max(Math.max(pixRed, pixGreen), pixBlue)));

        result.add(pixRed + "");
        result.add(pixGreen + "");
        result.add(pixBlue + "");
      }
    }

    result.add(3, highestValue + "");

    return String.join("\n", result) + "\n";
  }

  @Override
  public int hashCode() {
    return this.pixelArray.hashCode();
  }

  /**
   * Two {@code PPMImage}s are equal IFF:
   *
   * <ol>
   *   <li> the width and height of image {@code A} are the same as the width and height of image
   *   {@code B}</li>
   *   AND
   *   <li> each {@link Pixel} of image {@code A} are .equals() to the corresponding
   *   {@link Pixel} in image {@code B}
   *   </li>
   * </ol>
   *
   * @param other is the other object to compare
   * @return if this object is equal to the other object
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof PPMImage) {
      if (this.getWidth() != ((PPMImage) other).getWidth() ||
              this.getHeight() != ((PPMImage) other).getHeight()) {
        return false;
      } else {

        for (int row = 0; row < this.getHeight(); row += 1) {
          for (int col = 0; col < this.getWidth(); col += 1) {
            if (!this.getPixelAt(row, col).equals(((PPMImage) other).getPixelAt(row, col))) {
              return false;
            }
          }
        }

        return true;
      }
    } else {
      return false;
    }
  }

  private boolean isRectangular() {
    for (List<Pixel> row : this.pixelArray) {
      if (row.size() != this.pixelArray.get(0).size()) {
        return false;
      }
    }
    return true;
  }
}
