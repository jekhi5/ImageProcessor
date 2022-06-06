import org.junit.Test;

import model.pixel.APixel;

import static org.junit.Assert.*;

public abstract class APixelTest {

  APixel fullyVisibleWhite;
  APixel fullyVisibleBlack;

  public APixelTest(APixel fullyVisibleWhite, APixel fullyVisibleBlack) {
    this.fullyVisibleWhite = fullyVisibleWhite;
    this.fullyVisibleBlack = fullyVisibleBlack;
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

  @Test
  public void testSetRed() {
    assertEquals(255, this.fullyVisibleWhite.getRed());
    assertEquals(0, this.fullyVisibleBlack.getRed());

    this.fullyVisibleWhite.setRed(17);
    this.fullyVisibleBlack.setRed(65);

    assertEquals(17, this.fullyVisibleWhite.getRed());
    assertEquals(65, this.fullyVisibleBlack.getRed());
  }

  @Test
  public void testSetGreen() {
    assertEquals(255, this.fullyVisibleWhite.getGreen());
    assertEquals(0, this.fullyVisibleBlack.getGreen());

    this.fullyVisibleWhite.setGreen(17);
    this.fullyVisibleBlack.setGreen(65);

    assertEquals(17, this.fullyVisibleWhite.getGreen());
    assertEquals(65, this.fullyVisibleBlack.getGreen());
  }

  @Test
  public void testSetBlue() {
    assertEquals(255, this.fullyVisibleWhite.getBlue());
    assertEquals(0, this.fullyVisibleBlack.getBlue());

    this.fullyVisibleWhite.setBlue(17);
    this.fullyVisibleBlack.setBlue(65);

    assertEquals(17, this.fullyVisibleWhite.getBlue());
    assertEquals(65, this.fullyVisibleBlack.getBlue());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testingGivingValueHigherThan255_Red() {
    this.fullyVisibleWhite.setRed(5784);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testingGivingValuelessThan0_Red() {
    this.fullyVisibleWhite.setRed(-54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testingGivingValueHigherThan255_Green() {
    this.fullyVisibleWhite.setGreen(5784);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testingGivingValuelessThan0_Green() {
    this.fullyVisibleWhite.setGreen(-54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testingGivingValueHigherThan255_Blue() {
    this.fullyVisibleWhite.setBlue(5784);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testingGivingValuelessThan0_Blue() {
    this.fullyVisibleWhite.setBlue(-54);
  }
}