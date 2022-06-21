import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import model.image.BetterImage;
import model.image.Image;
import utilities.ImageFactory;
import utilities.ImageSaver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

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
    bi.setRGB(0, 2, new Color(0, 0, 255, 255).getRGB());
    bi.setRGB(1, 2, new Color(0, 0, 255, 255).getRGB());
    img = new BetterImage(bi);
  }

  @After
  public void purgeTestOut() {
    File dir = new File("test" + SEP + "testOut");
    for (File f : Objects.requireNonNull(dir.listFiles())) {
      if (!f.delete()) {
        fail("couldn't remove files");
      }
    }
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
    // due to JPG conversion, this is image should not be the same.
    assertNotEquals(img, img2);
  }

  @Test
  public void loadBMP() throws IOException {
    ImageSaver.write(bi, "bmp", new File("test" + SEP + "testOut" + SEP + "test.bmp"));
    Image img2 = ImageFactory.createImage("test" + SEP + "testOut" + SEP + "test.bmp");
    assertEquals(img, img2);
  }

  // Testing giving an invalid non-ppm path
  @Test(expected = IllegalArgumentException.class)
  public void invalidPath() {
    ImageFactory.createImage("test" + SEP + "bungus" + SEP + " checkered.jpg");
  }

}