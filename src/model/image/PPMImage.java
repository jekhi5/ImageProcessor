package model.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.pixel.Pixel;
import model.pixel.PixelImpl;
import model.v2.ImageSaver;
import utilities.ImageUtil;

/**
 * Represents a PPM Image. The text of the image is converted to an array of pixels that can be
 * individually modified.
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
  public void saveToPath(String path, boolean shouldOverwrite)
          throws IOException, IllegalArgumentException {
    if (path == null) {
      throw new IllegalArgumentException("Can't save to null path");
    }
    File f = new File(path);

    if (f.exists() && f.isDirectory()) {
      throw new IllegalArgumentException("Error. Could not create file from path: " + path +
              ". This is a directory.");
    } else if (f.exists() && shouldOverwrite) {
      boolean wasSuccessfullyDeleted = f.delete();

      if (!wasSuccessfullyDeleted) {
        throw new IOException("Error. Cannot delete file at path: " + f.getPath());
      }
    } else if (f.exists() && !shouldOverwrite) {
      throw new IllegalArgumentException(
              "Error. Could not create file from path: " + path + ". There was " +
                      "already a file at this location. To overwrite, add \"true\" to command.");
    }

    ImageSaver.write(this.toBufferedImage(), ImageUtil.getSuffix(path), f);
  }


  @Override
  public int hashCode() {
    return this.pixelArray.hashCode();
  }

  /**
   * Two {@code PPMImage}s are equal IFF.
   *
   * <ol>
   *   <li> the width and height of image {@code A} are the same as the width and height of image
   *   {@code B}. AND</li>
   *   <li> each {@link Pixel} of image {@code A} are .equals() to the corresponding
   *   {@link Pixel} in image {@code B}.
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


  private BufferedImage toBufferedImage() {
    BufferedImage bi =
            new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int r = 0; r < this.getHeight(); r++) {
      for (int c = 0; c < this.getWidth(); c++) {
        Pixel p = this.getPixelAt(r, c);
        Color color = new Color(p.getRed(), p.getGreen(), p.getBlue(), p.getAlpha());
        bi.setRGB(c, r, color.getRGB());
      }
    }

    return bi;
  }
}