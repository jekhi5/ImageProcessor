import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.image.Image;
import model.image.PPMImage;
import model.pixel.Pixel;
import model.pixel.PixelImpl;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * Tests for ImageUtil.
 */
public class ImageUtilTest {

  private static final String SLASH = System.getProperty("file.separator");
  private Image blue;

  @Before
  public void init() {
    List<List<Pixel>> grid = new ArrayList<>();
    Pixel b = new PixelImpl(0, 0, 255, 255);
    grid.add(List.of(b, b, b));
    grid.add(List.of(b, b, b));
    grid.add(List.of(b, b, b));
    blue = new PPMImage(grid);
  }

  @After
  public void purgeTestOut() {
    File testOut = new File("test" + SLASH + "testOut");
    for (File f : Objects.requireNonNull(testOut.listFiles())) {
      if (!f.delete()) {
        throw new IllegalStateException("File:" + f.getName() + " was not deleted! " +
                "Clear test" + SLASH + "testOut directory before continuing with testing or false" +
                " tests may " +
                "occur!");
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadImage_BadName() {
    // this test fails if you create a file called "bungus.ppm"
    ImageUtil.createImageFromPath("test" + SLASH + "testRes" + SLASH + "bungus.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadImage_BadExtension() {
    ImageUtil.createImageFromPath("test" + SLASH + "testRes" + SLASH + "dummy.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadImage_BadPath() {
    // this test fails if you create a directory called "bungus" for some reason
    ImageUtil.createImageFromPath("bungus" + SLASH + "FullyBlue_3x3.ppm");
  }

  @Test
  public void createImageFromPathReadingPPM() {
    Image imgLoaded =
            ImageUtil.createImageFromPath("test" + SLASH + "testRes" + SLASH + "FullyBlue_3x3.ppm");
    assertEquals(blue, imgLoaded);
  }

  // Testing trying to read a file with no suffix
  @Test(expected = IllegalArgumentException.class)
  public void readNoSuffix() {
    ImageUtil.createImageFromPath("test" + SLASH + "LoadTest");
  }

  // Testing trying to read a fake .ppm file with wrong format
  @Test(expected = IllegalArgumentException.class)
  public void readFakePPM() {
    ImageUtil.createImageFromPath("res" + SLASH + "fakePPM.ppm");
  }

  // Testing trying to read a .ppm with a negative height and width
  @Test(expected = IllegalArgumentException.class)
  public void readNegativePPM() {
    ImageUtil.createImageFromPath("res" + SLASH + "negativePPM.ppm");
  }
}