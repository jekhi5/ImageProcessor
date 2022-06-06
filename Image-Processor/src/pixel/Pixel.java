package pixel;

/**
 * A {@code Pixel} represents the color values of a given pixel. Certain pixels simply have
 * {@code red}, {@code green}, and {@code blue} to determine the color. Others, however, have
 * additional increments such as {@code alpha} to determine opacity. All images are made of
 * {@code Pixel}s. All components are on a scale from 0-255. 0 being the lowest contribution to the
 * pixel, 255 being the highest contribution to the pixel.
 */
public interface Pixel {

  /**
   * To get the {@code red} component of {@code this} pixel.
   *
   * @return the {@code red} value of this pixel
   */
  int getRed();

  /**
   * To get the {@code green} component of {@code this} pixel.
   *
   * @return the {@code green} value of this pixel
   */
  int getGreen();

  /**
   * To get the {@code blue} component of {@code this} pixel.
   *
   * @return the {@code blue} value of this pixel
   */
  int getBlue();

  /**
   * To get the {@code alpha} component of {@code this} pixel.
   *
   * @return the {@code alpha} value of this pixel
   */
  int getAlpha();

  /**
   * To set the {@code red} component of {@code this} pixel to the given value.
   *
   * @param newRed is the new value of for the red component
   * @throws IllegalArgumentException if the given value is negative or larger than 255
   */
  void setRed(int newRed) throws IllegalArgumentException;

  /**
   * To set the {@code green} component of {@code this} pixel to the given value.
   *
   * @param newGreen is the new value of for the green component
   * @throws IllegalArgumentException if the given value is negative or larger than 255
   */
  void setGreen(int newGreen) throws IllegalArgumentException;

  /**
   * To set the {@code blue} component of {@code this} pixel to the given value.
   *
   * @param newBlue is the new value of for the blue component
   * @throws IllegalArgumentException if the given value is negative or larger than 255
   */
  void setBlue(int newBlue) throws IllegalArgumentException;

  /**
   * To set the {@code alpha} component of this pixel to the given value only if the type of pixel
   * supports this operation.
   *
   * @param newAlpha is the new value of for the alpha component
   * @throws IllegalArgumentException if the given value is negative or larger than 255
   * @throws IllegalCallerException   if an attempt is made to set the alpha component of a pixel
   *                                  that does not support this feature
   */
  void setAlpha(int newAlpha) throws IllegalArgumentException, IllegalCallerException;

}
