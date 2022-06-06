import org.junit.Test;

import pixel.AlphaPixel;

import static org.junit.Assert.assertEquals;

public class AlphaPixelTest extends APixelTest {


  public AlphaPixelTest() {
    super(new AlphaPixel(255, 255, 255, 255), new AlphaPixel(0, 0, 0, 255));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testingGivingValueHigherThan255_Alpha() {
    this.fullyVisibleWhite.setAlpha(5784);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testingGivingValuelessThan0_Alpha() {
    this.fullyVisibleWhite.setAlpha(-54);
  }

  @Test
  public void testingSetAlpha() {
    assertEquals(255, this.fullyVisibleWhite.getAlpha());
    assertEquals(255, this.fullyVisibleBlack.getAlpha());

    this.fullyVisibleWhite.setAlpha(17);
    this.fullyVisibleBlack.setAlpha(65);

    assertEquals(17, this.fullyVisibleWhite.getAlpha());
    assertEquals(65, this.fullyVisibleBlack.getAlpha());
  }

  @Test(expected = IllegalArgumentException.class)
  public void badRedValue_TooHigh() {
    new AlphaPixel(400, 5, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badRedValue_TooLow() {
    new AlphaPixel(-32, 5, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGreenValue_TooHigh() {
    new AlphaPixel(5, 400, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGreenValue_TooLow() {
    new AlphaPixel(5, -32, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badBlueValue_TooHigh() {
    new AlphaPixel(5, 5, 400, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badBlueValue_TooLow() {
    new AlphaPixel(5, 5, -32, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badAlphaValue_TooHigh() {
    new AlphaPixel(5, 5, 5, 400);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badAlphaValue_TooLow() {
    new AlphaPixel(5, 5, 5, -32);
  }
}
