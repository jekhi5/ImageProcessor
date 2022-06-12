package model.v2;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelImpl;

/**
 * An improved implementation of {@link Image}. Uses a {@link java.awt.image.BufferedImage}
 * instead of a 2D list, and can work with more file types than just .ppm.
 *
 */
public class BetterImage implements Image {
  private final BufferedImage image;

  /**
   * Creates a new {@code BetterImage} from the given {@link BufferedImage};
   * @param image the {@link BufferedImage}
   * @throws IllegalArgumentException if {@code image} is null.
   */
  public BetterImage(BufferedImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image can't be null.");
    }
    this.image = image;
  }


  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {

    // thanks to icza and laplasz on StackOverflow for teaching us how to deal with this output
    //https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
    int rgba = image.getRGB(col, row);
    int blue = rgba & 0xff;
    int green = (rgba & 0xff00) >> 8;
    int red = (rgba & 0xff0000) >> 16;
    int alpha = (rgba & 0xff000000) >>> 24;
    Pixel p = new PixelImpl.PixelImplBuilder()
            .red(red)
            .blue(blue)
            .green(green)
            .alpha(alpha)
            .build();
    return p;
  }

  @Override
  public Pixel setPixelAt(int row, int col, Pixel newPixel) throws IllegalArgumentException {
    Pixel p = getPixelAt(row, col);

    byte red = (byte) newPixel.getRed();
    byte green = (byte) newPixel.getGreen();
    byte blue = (byte) newPixel.getBlue();
    byte alpha = (byte) newPixel.getAlpha();
    int rgba = alpha + red + green + blue;

    image.setRGB(col, row, rgba);
    return p;
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
  public Image getCopy() throws IllegalArgumentException {
    return null;
  }

  @Override
  public String toSavableText() {
    return null;
  }

  @Override
  public Iterator<Pixel> iterator() {
    return null;
  }
}
