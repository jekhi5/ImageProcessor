package seamcarving.pixel;

import model.pixel.Pixel;

/**
 * An implementation of {@code LinkedPixel}.
 */
public class LinkedPixelImpl implements LinkedPixel {

  private LinkedPixel up;
  private LinkedPixel right;
  private LinkedPixel down;
  private LinkedPixel left;

  private final Pixel pixelDelegate;

  /**
   * To construct a LinkedPixelImpl with only border pixels surrounding it
   */
  public LinkedPixelImpl(Pixel pixelDelegate) {
    this.up = new BorderPixel();
    this.right = new BorderPixel();
    this.down = new BorderPixel();
    this.left = new BorderPixel();
    this.pixelDelegate = pixelDelegate;
  }

  @Override
  public LinkedPixel getUp() {
    return up;
  }

  @Override
  public LinkedPixel getRight() {
    return right;
  }

  @Override
  public LinkedPixel getDown() {
    return down;
  }

  @Override
  public LinkedPixel getLeft() {
    return left;
  }

  @Override
  public void linkLeft(LinkedPixel newLeft) {
    this.left = newLeft;
    newLeft.setRight(this);
  }

  @Override
  public void linkUp(LinkedPixel newUp) {
    this.up = newUp;
    newUp.setDown(this);
  }

  @Override
  public void setRight(LinkedPixel newRight) throws IllegalStateException {
    if (!newRight.getLeft().equals(this)) {
      throw new IllegalStateException("The given newRight must hold this pixel as its left " +
              "pixel.");
    }

    this.right = newRight;
  }

  @Override
  public void setDown(LinkedPixel newDown) throws IllegalStateException {
    if (!newDown.getUp().equals(this)) {
      throw new IllegalStateException("The given newDown must hold this pixel as its up " +
              "pixel.");
    }

    this.down = newDown;
  }
}
