package seamcarving.pixel;

import model.pixel.Pixel;
import model.pixel.PixelImpl;

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

  @Override
  public boolean isBorderPixel() {
    return false;
  }

  @Override
  public Pixel getPixelDelegate() {
    return new PixelImpl(pixelDelegate);
  }

  @Override
  public double getEnergy() {
    double brightnessA = this.getUp().getLeft().getBrightness();
    double brightnessB = this.getUp().getBrightness();
    double brightnessC = this.getUp().getRight().getBrightness();
    double brightnessD = this.getLeft().getBrightness();
    // We are E
    double brightnessF = this.getRight().getBrightness();
    double brightnessG = this.getDown().getLeft().getBrightness();
    double brightnessH = this.getDown().getBrightness();
    double brightnessI = this.getDown().getRight().getBrightness();

    double horizBrightness = (brightnessA + (2 * brightnessD) + brightnessG)
            - (brightnessC + (2 * brightnessF) + brightnessI);
    double vertBrightness = (brightnessA + (2 * brightnessB) + brightnessC)
            - (brightnessG + (2 * brightnessH) + brightnessI);

    double resultingEnergy = Math.sqrt(Math.pow(horizBrightness, 2)
            + Math.pow(vertBrightness, 2));

    return resultingEnergy;
  }

  @Override
  public double getBrightness() {
    double totalRGBValues = this.pixelDelegate.getRed() + this.pixelDelegate.getBlue()
            + this.pixelDelegate.getGreen();
    return (totalRGBValues / 3.0) / 255.0;
  }


}
