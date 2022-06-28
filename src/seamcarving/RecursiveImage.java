package seamcarving;

import java.io.IOException;
import java.util.List;

import model.image.Image;
import model.pixel.Pixel;
import seamcarving.pixel.PixelGrid;

/**
 * This is an image whose pixels are linked to one another directly rather than in an ArrayList.
 */
public class RecursiveImage implements Image {

  private final PixelGrid grid;

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
  }


  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Pixel setPixelAt(int row, int col, Pixel newPixel) throws IllegalArgumentException {
    return null;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
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
