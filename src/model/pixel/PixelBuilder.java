package model.pixel;

/**
 * A Builder for {@link Pixel}s that can specify red, green, blue, and alpha channels.
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
   * @return this {@code PixelBuilder}
   * @author emery
   * @created 2022-06-06
   */
  public PixelBuilder red(int r) {
    red = r;
    return this;
  }

  /**
   * Sets the green channel of this builder.
   *
   * @param g green amount
   * @return this {@code PixelBuilder}
   * @author emery
   * @created 2022-06-06
   */
  public PixelBuilder green(int g) {
    green = g;
    return this;
  }

  /**
   * Sets the blue channel of this builder.
   *
   * @param b blue amount
   * @return this {@code PixelBuilder}
   * @author emery
   * @created 2022-06-06
   */
  public PixelBuilder blue(int b) {
    blue = b;
    return this;
  }

  /**
   * Sets the alpha channel of this builder.
   *
   * @param a opacity
   * @return this {@code PixelBuilder}
   * @author emery
   * @created 2022-06-06
   */
  public PixelBuilder alpha(int a) {
    alpha = a;
    return this;
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
