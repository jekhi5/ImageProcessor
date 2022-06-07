package model.pixel;

/**
 * This represents a pixel. It is made up of the red, green, and blue components. Its opacity is
 * determined by the user at creation.
 *
 * @author Jacob Kline
 * @created 06/06/2022
 */
public class PixelImpl implements Pixel {
  private final int red;
  private final int green;
  private final int blue;
  private final int alpha;

  /**
   * To construct a Pixel with the given values for each component.
   *
   * @param red   is the value for the red component
   * @param green is the value for the green component
   * @param blue  is the value for the blue component
   * @param alpha is the value for the alpha component
   * @throws IllegalArgumentException if any of the given values is negative or larger than 255
   */
  public PixelImpl(int red, int green, int blue, int alpha) throws IllegalArgumentException {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255 || alpha < 0 ||
            alpha > 255) {
      throw new IllegalArgumentException("Error. All components must be between 0-255 (inclusive)" +
              ". Given: [(red)" + red + ", (green)" + green + ", (blue)" + blue + ", (alpha)" +
              alpha + "].");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  /**
   * To construct a copy of the given pixel.
   *
   * @param pixel the pixel whose values will be copied into this pixel
   */
  public PixelImpl(Pixel pixel) {
    this.red = pixel.getRed();
    this.green = pixel.getGreen();
    this.blue = pixel.getBlue();
    this.alpha = pixel.getAlpha();
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getAlpha() {
    return this.alpha;
  }

  /**
   * A builder for {@link PixelImpl}.
   *
   * @author emery
   * @created 2022-06-06
   */
  public static class PixelImplBuilder extends PixelBuilder {

    @Override
    public Pixel build() {
      return new PixelImpl(red, green, blue, alpha);
    }
  }
}
