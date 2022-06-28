package seamcarving.pixel;

import model.pixel.Pixel;

/**
 * Represents a pixel that connects to all other pixels in an image.
 */
public interface LinkedPixel {

  //TODO: There is leakage in all of these methods. This needs to be addressed so that we return
  // a copy of the requested neighbor that is not aliased. However, this data is cyclic so I'm
  // not sure how to exactly do that...

  /**
   * Gets the pixel immediately above this pixel.
   *
   * @return the {@code LinkedPixel} immediately above this {@code LinkedPixel}. If this
   *         {@code LinkedPixel} is a {@link seamcarving.pixel.BorderPixel}, returns this.
   */
  LinkedPixel getUp();

  /**
   * Gets the pixel immediately to the right of this pixel.
   *
   * @return the {@code LinkedPixel} immediately to the right of this {@code LinkedPixel}. If this
   *         {@code LinkedPixel} is a {@link seamcarving.pixel.BorderPixel}, returns this.
   */
  LinkedPixel getRight();

  /**
   * Gets the pixel immediately below this pixel.
   *
   * @return the {@code LinkedPixel} immediately below this {@code LinkedPixel}. If this
   *         {@code LinkedPixel} is a {@link seamcarving.pixel.BorderPixel}, returns this.
   */
  LinkedPixel getDown();

  /**
   * Gets the pixel immediately to the left this pixel.
   *
   * @return the {@code LinkedPixel} immediately to the left this {@code LinkedPixel}. If this
   *         {@code LinkedPixel} is a {@link seamcarving.pixel.BorderPixel}, returns this.
   */
  LinkedPixel getLeft();

  /**
   * Sets this pixel's left pixel to the given {@code LinkedPixel}, and sets that pixel's right
   * pixel to this. If this pixel is a border pixel, only sets the given pixel's right pixel to
   * this.
   *
   * @param newLeft the pixel to the left of this pixel
   */
  void linkLeft(LinkedPixel newLeft);

  /**
   * Sets this pixel's up pixel to the given {@code LinkedPixel}, and sets that pixel's down pixel
   * to this. If this pixel is a border pixel, only sets the given pixel's down pixel to this.
   *
   * @param newUp the pixel to the up of this pixel
   */
  void linkUp(LinkedPixel newUp);

  /**
   * Sets this pixel's right pixel to the given {@code LinkedPixel}.
   *
   * @param newRight the pixel to the right of this pixel
   * @throws java.lang.IllegalStateException if the left of the given pixel is not this pixel
   */
  void setRight(LinkedPixel newRight) throws IllegalStateException;

  /**
   * Sets this pixel's down pixel to the given {@code LinkedPixel}.
   *
   * @param newDown the pixel to the down of this pixel
   * @throws java.lang.IllegalStateException if the up of the given pixel is not this pixel
   */
  void setDown(LinkedPixel newDown) throws IllegalStateException;

  /**
   * Returns whether this {@code LinkedPixel} is a {@link BorderPixel}.
   *
   * @return a boolean representing this
   */
  boolean isBorderPixel();

  /**
   * Gets the PixelDelegate of this LinkedPixel.
   *
   * @return this's PixelDelegate
   */
  Pixel getPixelDelegate();

  /**
   * Gets the energy of this {@code LinkedPixel} as described in the following formula: Assume this
   * 3x3 grid of {@code LinkedPixel}:
   *
   * A B C D E F G H I
   *
   * The Energy of pixel E is calculated as follows ("br" stands for brightness):
   *
   *
   * 𝐻𝑜𝑟𝑖𝑧𝐸𝑛𝑒𝑟𝑔𝑦(𝐸) = (𝑏𝑟(𝐴) + 2𝑏𝑟(𝐷) + 𝑏𝑟(𝐺)) − (𝑏𝑟(𝐶) + 2𝑏𝑟(𝐹) +
   * 𝑏𝑟(𝐼)) 𝑉𝑒𝑟𝑡𝐸𝑛𝑒𝑟𝑔𝑦(𝐸) = (𝑏𝑟(𝐴) + 2𝑏𝑟(𝐵) + 𝑏𝑟(𝐶)) − (𝑏𝑟(𝐺) + 2𝑏𝑟(𝐻)
   * + 𝑏𝑟(𝐼)) 𝐸𝑛𝑒𝑟𝑔𝑦(𝐸) = SQRT(𝐻𝑜𝑟𝑖𝑧𝐸𝑛𝑒𝑟𝑔𝑦(𝐸)^2 + 𝑉𝑒𝑟𝑡𝐸𝑛𝑒𝑟𝑔𝑦(𝐸)^2)
   *
   * Important: for pixels on the edge of the image, pretend the image is surrounded by a 1-pixel
   * border of black pixels
   *
   * This formula was taken from:
   * <a href="https://course.ccs.neu.edu/cs2510a/assignment9.html#:~:text=Next%2C%20the%20horizontal,of%20black%20pixels">...</a>.
   *
   * @return the energy of this {@code LinkedPixel} as a double
   */
  double getEnergy();

  /**
   * Gets the brightness of this {@code LinkedPixel}. The brightness is determined as the average of
   * the red, green, and blue components of this {@code LinkedPixel}'s delegate.
   *
   * @return the brightness of this {@code LinkedPixel} as a double
   */
  double getBrightness();
}
