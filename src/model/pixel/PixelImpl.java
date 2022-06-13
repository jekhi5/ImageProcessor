package model.pixel;

/**
 * This represents a pixel. It is made up of the red, green, and blue components. Its opacity is
 * determined by the user at creation.
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
  public PixelImpl(Pixel pixel) throws IllegalArgumentException {
    if (pixel == null) {
      throw new IllegalArgumentException("Pixel cannot be null.");
    }
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

  @Override
  public int hashCode() {
    int result = 17;

    result = 31 * result + this.red;
    result = 31 * result + this.green;
    result = 31 * result + this.blue;
    result = 31 * result + this.alpha;

    return result;
  }

  /**
   * Two {@code PixelImpl}s are .equal() IFF:
   *
   * <ol>
   *   <li> they are both of type {@code PixelImpl} AND</li>
   *   <li> each field of pixel {@code A} has an equal value to the same field of pixel {@code B}
   *   </li>
   * </ol>
   *
   * @param other is the other object to compare
   * @return if this object is equal to the other object
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Pixel) {
      return this.getRed() == ((Pixel) other).getRed()
              && this.getGreen() == ((Pixel) other).getGreen()
              && this.getBlue() == ((Pixel) other).getBlue()
              && this.getAlpha() == ((Pixel) other).getAlpha();
    } else {
      return false;
    }
  }

  /**
   * A builder for {@link PixelImpl}.
   */
  public static class PixelImplBuilder extends PixelBuilder {

    @Override
    public Pixel build() {
      return new PixelImpl(red, green, blue, alpha);
    }
  }

}
