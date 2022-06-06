package pixel;

/**
 * This represents a simple pixel. It is made up of the red, green, and blue components. Its opacity
 * is always 100, and it is not possible to modify the alpha component as it is always 100.
 */
public class SimplePixel extends APixel {
  /**
   * To construct a Pixel with the given values for each component.
   *
   * @param red   is the value for the red component
   * @param green is the value for the green component
   * @param blue  is the value for the blue component
   * @throws IllegalArgumentException if any of the given values is negative or larger than 255
   */
  public SimplePixel(int red, int green, int blue) throws IllegalArgumentException {
    super(red, green, blue, 255);
  }

  @Override
  public void setAlpha(int newAlpha) throws IllegalCallerException {
    throw new IllegalCallerException("Error. Unable to modify alpha component of Simple Pixel: " +
            "Operation not supported!");
  }
}
