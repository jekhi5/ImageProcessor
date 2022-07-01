package seamcarving;

import model.pixel.Pixel;
import model.pixel.PixelImpl;

/**
 * Represents a {@code LinkedPixel} that is outside the actual image. All pixels on the border refer
 * to this border.
 */
class BorderPixel implements LinkedPixel {

  private final Pixel pixelDelegate;

  /**
   * Constructs a new BorderPixel whose delegate is a black {@link model.pixel.Pixel}.
   */
  public BorderPixel() {
    this.pixelDelegate = new PixelImpl.PixelImplBuilder()
            .red(0)
            .green(0)
            .blue(0)
            .alpha(0)
            .build();
  }

  @Override
  public LinkedPixel getUp() {
    return this;
  }

  @Override
  public LinkedPixel getRight() {
    return this;
  }

  @Override
  public void setRight(LinkedPixel newRight) throws IllegalStateException {
    // Does nothing
  }

  @Override
  public LinkedPixel getDown() {
    return this;
  }

  @Override
  public void setDown(LinkedPixel newDown) throws IllegalStateException {
    // Does nothing
  }

  @Override
  public LinkedPixel getLeft() {
    return this;
  }

  @Override
  public void linkLeft(LinkedPixel newLeft) {
    // Does nothing
  }

  @Override
  public void linkUp(LinkedPixel newUp) {
    // Does nothing
  }

  @Override
  public boolean isBorderPixel() {
    return true;
  }

  @Override
  public Pixel getPixelDelegate() {
    return new PixelImpl(pixelDelegate);
  }

  @Override
  public double getEnergy() {
    return Double.MAX_VALUE;
  }

  @Override
  public double getBrightness() {
    return 0;
  }
}
