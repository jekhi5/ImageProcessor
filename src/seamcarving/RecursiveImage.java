package seamcarving;

import java.io.IOException;
import java.util.List;

import model.image.AbstractImage;
import model.image.Image;
import model.pixel.Pixel;

/**
 * This is an image whose pixels are linked to one another directly rather than in an ArrayList.
 */
public class RecursiveImage extends AbstractImage {

  private final PixelGrid grid;
  private int width;
  private int height;

  /**
   * Constructs a RecursiveImage that has the same data as the given image.
   *
   * @param image the image
   * @throws java.lang.IllegalArgumentException if the given image is null, if either the width or
   *                                            height of the given image is less than or equal to
   *                                            0, or if the given image is not rectangular
   */
  public RecursiveImage(Image image) throws IllegalArgumentException {
    this.grid = new PixelGrid(image);
    this.width = image.getWidth();
    this.height = image.getHeight();
  }

  /**
   * Constructs a recursive image that has the same data as the given grid of pixels.
   *
   * @param pixelGrid the grid of pixels
   * @throws java.lang.IllegalArgumentException if the given grid is null, if either the width or
   *                                            height of the given grid is less than or equal to 0,
   *                                            or if the given grid is not rectangular
   */
  public RecursiveImage(List<List<Pixel>> pixelGrid) {
    this.grid = new PixelGrid(pixelGrid);
    this.width = pixelGrid.size();
    this.height = pixelGrid.get(0).size();
  }


  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    return grid.getPixelAt(row, col);
  }

  /**
   * This is an unsupported operation.
   *
   * @param row      the row of the pixel to be set
   * @param col      the column of the pixel to be set
   * @param newPixel the new pixel
   * @return will never return anything as it will always throw an exception
   * @throws IllegalArgumentException will always throw the error
   */
  @Override
  public Pixel setPixelAt(int row, int col, Pixel newPixel) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Cannot set pixel of a RecursiveImage!");
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public Image getCopy() {
    return null;
  }

  @Override
  public void saveToPath(String path, boolean shouldOverwrite)
          throws IOException, IllegalArgumentException {

  }
}
