package seamcarving.pixel;

/**
 * Represents a {@code LinkedPixel} that is outside the actual image. All pixels on the border refer
 * to this border.
 */
public class BorderPixel implements LinkedPixel {


  @Override
  public LinkedPixel getUp() {
    return this;
  }

  @Override
  public LinkedPixel getRight() {
    return this;
  }

  @Override
  public LinkedPixel getDown() {
    return this;
  }

  @Override
  public LinkedPixel getLeft() {
    return this;
  }
}
