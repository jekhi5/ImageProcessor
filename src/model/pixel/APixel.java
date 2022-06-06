package model.pixel;

/**
 * This represents an abstract Pixel that is made up of Red, Green, Blue, and Alpha components. The
 * client can get all the components and set each of them (the ability to set the alpha component is
 * dependent on the type of pixel).
 *
 * @author Jacob Kline
 * @created 06/06/2022
 */
public abstract class APixel implements Pixel {
  protected int red;
  protected int green;
  protected int blue;
  protected int alpha;

  /**
   * To construct a Pixel with the given values for each component.
   *
   * @param red   is the value for the red component
   * @param green is the value for the green component
   * @param blue  is the value for the blue component
   * @param alpha is the value for the alpha component
   * @throws IllegalArgumentException if any of the given values is negative or larger than 255
   */
  public APixel(int red, int green, int blue, int alpha) throws IllegalArgumentException {
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
  public void setRed(int newRed) throws IllegalArgumentException {
    if (newRed < 0 || newRed > 255) {
      throw new IllegalArgumentException("Error. Given value must be between 0-255 (inclusive). " +
              "Given: " + newRed);
    } else {
      this.red = newRed;
    }
  }

  @Override
  public void setGreen(int newGreen) throws IllegalArgumentException {
    if (newGreen < 0 || newGreen > 255) {
      throw new IllegalArgumentException("Error. Given value must be between 0-255 (inclusive). " +
              "Given: " + newGreen);
    } else {
      this.green = newGreen;
    }
  }

  @Override
  public void setBlue(int newBlue) throws IllegalArgumentException {
    if (newBlue < 0 || newBlue > 255) {
      throw new IllegalArgumentException("Error. Given value must be between 0-255 (inclusive). " +
              "Given: " + newBlue);
    } else {
      this.blue = newBlue;
    }
  }
}
