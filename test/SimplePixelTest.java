import org.junit.Test;

import model.pixel.SimplePixel;

public class SimplePixelTest extends APixelTest {

  public SimplePixelTest() {
    super(new SimplePixel(255, 255, 255), new SimplePixel(0, 0, 0));
  }


  @Test(expected = IllegalCallerException.class)
  public void testSetAlpha() {
    this.fullyVisibleWhite.setAlpha(65);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badRedValue_TooHigh() {
    new SimplePixel(400, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badRedValue_TooLow() {
    new SimplePixel(-32, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGreenValue_TooHigh() {
    new SimplePixel(5, 400, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGreenValue_TooLow() {
    new SimplePixel(5, -32, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badBlueValue_TooHigh() {
    new SimplePixel(5, 5, 400);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badBlueValue_TooLow() {
    new SimplePixel(5, 5, -32);
  }
}
