import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import model.image.Image;
import model.image.BetterImage;
import utilities.ImageFactory;
import utilities.ImageSaver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * Tests for ImageSaver
 */
public class ImageSaverTest {
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
  public void writeNullBI() throws IOException {
    ImageSaver.write(null, "png", new File("test" + SEP + "testOut" + SEP + "temp.png"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeNullType() throws IOException {
    ImageSaver.write(bi, null, new File("test" + SEP + "testOut" + SEP + "temp.png"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeNullFile() throws IOException {
    ImageSaver.write(bi, "png", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeBadType() throws IOException {
    ImageSaver.write(bi, "webm", new File("test" + SEP + "testOut" + SEP + "temp.png"));
  }

  @Test(expected = IOException.class)
  public void writeBadPath() throws IOException {
    ImageSaver.write(bi, "png", new File("test" + SEP + "bungus" + SEP + "temp.png"));
  }

  @Test
  public void writePPM() throws IOException {
    String path = "test" + SEP + "testOut" + SEP + "temp.ppm";
    ImageSaver.write(bi, "ppm", new File(path));
    assertEquals(img, ImageFactory.createImage(path));
  }

  @Test
  public void writePNG() throws IOException {
    String path = "test" + SEP + "testOut" + SEP + "temp.png";
    ImageSaver.write(bi, "png", new File(path));
    assertEquals(img, ImageFactory.createImage(path));
  }

  @Test
  public void writeBMP() throws IOException {
    String path = "test" + SEP + "testOut" + SEP + "temp.bmp";
    ImageSaver.write(bi, "bmp", new File(path));
    assertEquals(img, ImageFactory.createImage(path));
  }

  @Test
  public void writeJPG() throws IOException {
    String path = "test" + SEP + "testOut" + SEP + "temp.jpg";
    ImageSaver.write(bi, "jpg", new File(path));
    assertNotEquals(img, ImageFactory.createImage(path));
  }
}