package model.pixel;

/**
 * A Builder for {@link Pixel}s that can specify red, green, blue, and alpha channels.
 *
 * @author emery
 * @created 2022-06-06
 */
public abstract class PixelBuilder {
  protected int red;
  protected int green;
  protected int blue;
  protected int alpha;

  protected PixelBuilder() {
    this.red = 0;
    this.green = 0;
    this.blue = 0;
    this.alpha = 255;
  }


  /**
   * Sets the red channel of this builder.
   *
   * @param r red amount
   * @author emery
   * @created 2022-06-06
   */
  public void red(int r) {
    red = r;
  }

  /**
   * Sets the green channel of this builder.
   *
   * @param g green amount
   * @author emery
   * @created 2022-06-06
   */
  public void green(int g) {
    green = g;
  }

  /**
   * Sets the blue channel of this builder.
   *
   * @param b blue amount
   * @author emery
   * @created 2022-06-06
   */
  public void blue(int b) {
    blue = b;
  }

  /**
   * Sets the alpha channel of this builder.
   *
   * @param a opacity
   * @author emery
   * @created 2022-06-06
   */
  public void alpha(int a) {
    alpha = a;
  }

  /**
   * Builds this builder into a {@link Pixel}, returning an instance of a concrete {@link Pixel}
   * implementation with the desired settings.
   *
   * @return a {@link Pixel}
   * @author emery
   * @created 2022-06-06
   */
  public abstract Pixel build();
}
