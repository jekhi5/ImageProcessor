import org.junit.Test;

import java.util.Random;

import model.pixel.Pixel;
import model.pixel.PixelBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

/**
 * Tests for {@link model.pixel.PixelBuilder}.
 */
public abstract class PixelBuilderTest {

  protected PixelBuilder builder;
  protected Random rand;

  /**
   * Creates a new PixelBuilderTest.
   *
   * @param builder builder
   */
  public PixelBuilderTest(PixelBuilder builder) {
    this.builder = builder;
    this.rand = new Random(1);
  }

  @Test
  public void red() {
    for (int i = 0; i < 1000; i += 1) {
      int amt = Math.abs(rand.nextInt()) % 255;
      builder.red(amt);
      assertEquals(amt, builder.build().getRed());
    }
  }

  @Test
  public void green() {
    for (int i = 0; i < 1000; i += 1) {
      int amt = Math.abs(rand.nextInt()) % 255;
      builder.green(amt);
      assertEquals(amt, builder.build().getGreen());
    }
  }

  @Test
  public void blue() {
    for (int i = 0; i < 1000; i += 1) {
      int amt = Math.abs(rand.nextInt()) % 255;
      builder.blue(amt);
      assertEquals(amt, builder.build().getBlue());
    }
  }

  @Test
  public void alpha() {
    for (int i = 0; i < 1000; i += 1) {
      int amt = Math.abs(rand.nextInt()) % 255;
      builder.alpha(amt);
      assertEquals(amt, builder.build().getAlpha());
    }
  }

  @Test
  public void build() {
    for (int i = 0; i < 1000; i += 1) {
      int r = Math.abs(rand.nextInt()) % 255;
      int g = Math.abs(rand.nextInt()) % 255;
      int b = Math.abs(rand.nextInt()) % 255;
      int a = Math.abs(rand.nextInt()) % 255;
      builder.red(r);
      builder.green(g);
      builder.blue(b);
      builder.alpha(a);

      assertEquals(r, builder.build().getRed());
      assertEquals(g, builder.build().getGreen());
      assertEquals(b, builder.build().getBlue());
      assertEquals(a, builder.build().getAlpha());
    }
  }

  @Test
  public void build_NoAliasing() {
    Pixel p1 = builder.build();
    Pixel p2 = builder.build();
    assertEquals(p1, p2);
    assertNotSame(p1, p2);
  }

  @Test
  public void invalidRed() {
    try {
      builder.red(256);
      builder.build();
      fail();
    } catch (IllegalArgumentException e) {
      // pass
    }
    try {
      builder.red(-1);
      builder.build();
      fail();
    } catch (IllegalArgumentException e) {
      // pass
    }
  }

  @Test
  public void invalidGreen() {
    try {
      builder.green(256);
      builder.build();
      fail();
    } catch (IllegalArgumentException e) {
      // pass
    }
    try {
      builder.green(-1);
      builder.build();
      fail();
    } catch (IllegalArgumentException e) {
      // pass
    }
  }

  @Test
  public void invalidBlue() {
    try {
      builder.blue(256);
      builder.build();
      fail();
    } catch (IllegalArgumentException e) {
      // pass
    }
    try {
      builder.blue(-1);
      builder.build();
      fail();
    } catch (IllegalArgumentException e) {
      // pass
    }
  }

  @Test
  public void invalidAlpha() {
    try {
      builder.alpha(256);
      builder.build();
      fail();
    } catch (IllegalArgumentException e) {
      // pass
    }
    try {
      builder.alpha(-1);
      builder.build();
      fail();
    } catch (IllegalArgumentException e) {
      // pass
    }
  }

  @Test
  public abstract void buildDefault();
}