import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import model.image.Image;
import model.v2.BetterImage;
import model.v2.ImageFactory;
import model.v2.ImageSaver;

import static org.junit.Assert.assertEquals;

/**
 * Tests for ImageFactory.
 */
public class ImageFactoryTest {

  static final String SEP = System.getProperty("file.separator");
  Image img;
  BufferedImage bi;

  @Before
  public void init() {
    bi = new BufferedImage(2, 3, BufferedImage.TYPE_INT_ARGB);
    bi.setRGB(0, 0, new Color(255, 0, 0, 255).getRGB());
    bi.setRGB(1, 0, new Color(255, 0, 0, 255).getRGB());
    bi.setRGB(0, 1, new Color(0, 255, 0, 255).getRGB());
    bi.setRGB(1, 1, new Color(0, 255, 0, 255).getRGB());
    bi.setRGB(0, 0, new Color(0, 0, 255, 255).getRGB());
    bi.setRGB(0, 0, new Color(0, 0, 255, 255).getRGB());
    img = new BetterImage(bi);
  }

  @After
  public void purgeTestOut() {

  }

  @Test(expected = IllegalArgumentException.class)
  public void nullPath() {
    ImageFactory.createImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badPath() {
    ImageFactory.createImage("bungus" + SEP + "test.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void badExtension() {
    ImageFactory.createImage("res" + SEP + "example.webm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void fileNotFound() {
    ImageFactory.createImage("res" + SEP + "bungus.ppm");
  }

  @Test
  public void loadPPM() throws IOException {
    ImageSaver.write(bi, "ppm", new File("test" + SEP + "testOut" + SEP + "test.ppm"));
    Image img2 = ImageFactory.createImage("test" + SEP + "testOut" + SEP + "test.ppm");
    assertEquals(img, img2);
  }

  @Test
  public void loadPNG() throws IOException {
    ImageSaver.write(bi, "png", new File("test" + SEP + "testOut" + SEP + "test.png"));
    Image img2 = ImageFactory.createImage("test" + SEP + "testOut" + SEP + "test.png");
    assertEquals(img, img2);
  }

  @Test
  public void loadJPG() throws IOException {
    ImageSaver.write(bi, "jpg", new File("test" + SEP + "testOut" + SEP + "test.jpg"));
    Image img2 = ImageFactory.createImage("test" + SEP + "testOut" + SEP + "test.jpg");
    assertEquals(img, img2);
  }

  @Test
  public void loadBMP() throws IOException {
    ImageSaver.write(bi, "bmp", new File("test" + SEP + "testOut" + SEP + "test.bmp"));
    Image img2 = ImageFactory.createImage("test" + SEP + "testOut" + SEP + "test.bmp");
    assertEquals(img, img2);
  }
}