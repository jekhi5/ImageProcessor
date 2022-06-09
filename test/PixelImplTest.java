import org.junit.Test;

import model.pixel.Pixel;
import model.pixel.PixelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;


/**
 * The test class for {@link PixelImpl}.
 */
public class PixelImplTest {

  Pixel fullyVisibleWhite;
  Pixel fullyVisibleBlack;

  public PixelImplTest() {
    this.fullyVisibleWhite = new PixelImpl(255, 255, 255, 255);
    this.fullyVisibleBlack = new PixelImpl(0, 0, 0, 255);
  }

  @Test
  public void testGetRed() {
    assertEquals(255, this.fullyVisibleWhite.getRed());
    assertEquals(0, this.fullyVisibleBlack.getRed());
  }

  @Test
  public void testGetGreen() {
    assertEquals(255, this.fullyVisibleWhite.getGreen());
    assertEquals(0, this.fullyVisibleBlack.getGreen());
  }

  @Test
  public void testGetBlue() {
    assertEquals(255, this.fullyVisibleWhite.getBlue());
    assertEquals(0, this.fullyVisibleBlack.getBlue());
  }

  @Test
  public void testGetAlpha() {
    assertEquals(255, this.fullyVisibleWhite.getAlpha());
    assertEquals(255, this.fullyVisibleBlack.getAlpha());
  }

  @Test(expected = IllegalArgumentException.class)
  public void badRedValue_TooHigh() {
    new PixelImpl(400, 5, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badRedValue_TooLow() {
    new PixelImpl(-32, 5, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGreenValue_TooHigh() {
    new PixelImpl(5, 400, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGreenValue_TooLow() {
    new PixelImpl(5, -32, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badBlueValue_TooHigh() {
    new PixelImpl(5, 5, 400, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badBlueValue_TooLow() {
    new PixelImpl(5, 5, -32, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badAlphaValue_TooHigh() {
    new PixelImpl(5, 5, 5, 400);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badAlphaValue_TooLow() {
    new PixelImpl(5, 5, 5, -32);
  }

  @Test
  public void pixelConstructor() {
    Pixel p1 = new PixelImpl(1, 2, 3, 4);
    Pixel p2 = new PixelImpl(p1);

    assertEquals(p1, p2);
    assertNotSame(p1, p2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullPixelConstructor() {
    new PixelImpl(null);
  }

  @Test
  public void testEquals() {
    assertNotEquals(fullyVisibleBlack, fullyVisibleWhite);
    assertNotEquals(fullyVisibleWhite, fullyVisibleBlack);

    assertEquals(fullyVisibleWhite, fullyVisibleWhite);
    Pixel anotherWhite = new PixelImpl(255, 255, 255, 255);
    assertEquals(fullyVisibleWhite, anotherWhite);
    assertEquals(anotherWhite, fullyVisibleWhite);

    assertEquals(fullyVisibleWhite.hashCode(), anotherWhite.hashCode());
    assertNotEquals(fullyVisibleWhite.hashCode(), fullyVisibleBlack.getAlpha());
    assertFalse(fullyVisibleBlack.equals("banana"));
  }

  @Test
  public void testEqualsNonPixel() {
    assertNotEquals(null, fullyVisibleBlack);
    assertNotEquals("uwu", fullyVisibleWhite);
    assertNotEquals(1, fullyVisibleWhite);
  }

  @Test
  public void testHashCode() {
    assertEquals(23549777, fullyVisibleWhite.hashCode());
    assertEquals(15700112, fullyVisibleBlack.hashCode());
  }
}
