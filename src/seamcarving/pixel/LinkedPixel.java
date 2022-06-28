package seamcarving.pixel;

/**
 * Represents a pixel that connects to all other pixels in an image.
 */
public interface LinkedPixel {

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
}
