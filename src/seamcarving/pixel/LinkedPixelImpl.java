package seamcarving.pixel;

/**
 * An implementation of {@code LinkedPixel}.
 */
public class LinkedPixelImpl implements LinkedPixel {

  private LinkedPixel up;
  private LinkedPixel right;
  private LinkedPixel down;
  private LinkedPixel left;

  /**
   * To construct a LinkedPixelImpl with only border pixels surrounding it
   */
  public LinkedPixelImpl() {
    this.up = new BorderPixel();
    this.right = new BorderPixel();
    this.down = new BorderPixel();
    this.left = new BorderPixel();
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
  public void linkRight(LinkedPixel newRight) {
    this.right = newRight;
    newRight.setLeft(this);
  }

  @Override
  public void linkDown(LinkedPixel newDown) {
    this.down = newDown;
    newDown.setUp(this);
  }

  @Override
  public void setLeft(LinkedPixel newLeft) throws IllegalStateException {
    if (!newLeft.getRight().equals(this)) {
      throw new IllegalStateException("The given newLeft must hold this pixel as its right " +
              "pixel.");
    }

    this.left = newLeft;
  }

  @Override
  public void setUp(LinkedPixel newUp) throws IllegalStateException {
    if (!newUp.getDown().equals(this)) {
      throw new IllegalStateException("The given newUp must hold this pixel as its down " +
              "pixel.");
    }

    this.up = newUp;
  }
}
