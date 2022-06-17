import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelImpl;
import model.v2.BetterImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Tests for BetterImage.
 */
public class BetterImageTest {

  Image img;
  BufferedImage bi;

  @Before
  public void init() {
    bi = new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB);
    bi.setRGB(0, 0, new Color(0, 0, 0, 0).getRGB());
    bi.setRGB(1, 0, new Color(0, 1, 0, 0).getRGB());
    bi.setRGB(0, 1, new Color(0, 1, 0, 0).getRGB());
    bi.setRGB(1, 1, new Color(1, 1, 0, 0).getRGB());
    bi.setRGB(0, 0, new Color(0, 2, 0, 0).getRGB());
    bi.setRGB(0, 0, new Color(1, 2, 0, 0).getRGB());
    img = new BetterImage(bi);
  }

  /*
  - save as each type (PNG, PPM, BMP, JPG)
  - save to directory
  - save to invalid filetype
  - save without overwriting
  - save with overwrite
   */

  @Test(expected = IllegalArgumentException.class)
  public void nullConstr() {
    new BetterImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPixelOOB() {
    img.getPixelAt(-1, 12);
  }

  @Test
  public void getPixel() {
    for (int r = 0; r < img.getHeight(); r++) {
      for (int c = 0; c < img.getWidth(); c++) {
        Color clr = new Color(bi.getRGB(c, r), true);
        Pixel p = new PixelImpl(clr.getRed(), clr.getGreen(), clr.getBlue(), clr.getAlpha());
        Pixel p2 = img.getPixelAt(r, c);
        assertEquals(p, p2);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPixelNull() {
    img.setPixelAt(0, 0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPixelOOB() {
    img.setPixelAt(-1, 12, new PixelImpl(1, 1, 1, 1));
  }

  @Test
  public void setPixel() {
    for (int r = 0; r < img.getHeight(); r++) {
      for (int c = 0; c < img.getWidth(); c++) {
        Pixel p = new PixelImpl(255, 255, 255, 255);
        img.setPixelAt(r, c, p);
        assertEquals(p, img.getPixelAt(r, c));
        assertNotSame(p, img.getPixelAt(r, c));
      }
    }
  }

  @Test
  public void getWidthAndHeight() {
    for (int r = 1; r < 100; r++) {
      for (int c = 1; c < 100; c++) {
        BufferedImage newBI = new BufferedImage(c, r, BufferedImage.TYPE_INT_ARGB);
        Image newImg = new BetterImage(newBI);
        assertEquals(r, newImg.getHeight());
        assertEquals(c, newImg.getWidth());
      }
    }
  }


}