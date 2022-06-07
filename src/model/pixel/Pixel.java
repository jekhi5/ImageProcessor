package model.pixel;

/**
 * A {@code Pixel} represents the color values of a given pixel. Certain pixels simply have
 * {@code red}, {@code green}, and {@code blue} to determine the color. Others, however, have
 * additional increments such as {@code alpha} to determine opacity. All images are made of
 * {@code Pixel}s. All components are on a scale from 0-255. 0 being the lowest contribution to the
 * pixel, 255 being the highest contribution to the pixel.
 *
 * @author Jacob Kline
 * @created 06/06/2022
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
}
