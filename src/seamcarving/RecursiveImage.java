package seamcarving;

import java.io.IOException;

import model.image.Image;
import model.pixel.Pixel;

/**
 * This is an image whose pixels are linked to one another directly rather than in an ArrayList.
 */
public class RecursiveImage implements Image {




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
