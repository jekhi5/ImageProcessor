package pixel;

/**
 * This represents an alpha pixel. It is made up of the red, green, and blue components. Its opacity
 * is determined by the user and can be modified at any time by setting the {@code alpha}
 * component.
 */
public class AlphaPixel extends APixel {
  /**
   * To construct a Pixel with the given values for each component.
   *
   * @param red   is the value for the red component
   * @param green is the value for the green component
   * @param blue  is the value for the blue component
   * @param alpha is the value for the alpha component
   * @throws IllegalArgumentException if any of the given values is negative or larger than 255
   */
  public AlphaPixel(int red, int green, int blue, int alpha) throws IllegalArgumentException {
    super(red, green, blue, alpha);
  }

  @Override
  public void setAlpha(int newAlpha) {
    if (newAlpha < 0 || newAlpha > 255) {
      throw new IllegalArgumentException("Error. Given value must be between 0-255 (inclusive). " +
              "Given: " + newAlpha);
    } else {
      this.alpha = newAlpha;
    }
  }
}
