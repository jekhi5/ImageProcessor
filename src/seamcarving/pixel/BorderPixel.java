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

  @Override
  public void linkRight(LinkedPixel newRight) {
    newRight.setLeft(this);
  }

  @Override
  public void linkDown(LinkedPixel newDown) {
    newDown.setUp(this);
  }

  // SHOULD BORDER PIXELS HOLD THESE VALUES AS FIELDS???
  @Override
  public void setLeft(LinkedPixel newLeft) throws IllegalStateException {
    // Does nothing
  }

  @Override
  public void setUp(LinkedPixel newUp) throws IllegalStateException {
    // Does nothing
  }
}
