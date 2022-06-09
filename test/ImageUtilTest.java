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
  private Image red;

  @Before
  public void init() {
    List<List<Pixel>> grid = new ArrayList<>();
    List<List<Pixel>> grid2 = new ArrayList<>();
    Pixel b = new PixelImpl(0, 0, 255, 255);
    Pixel r = new PixelImpl(255, 0, 0, 255);
    grid.add(List.of(b, b, b));
    grid.add(List.of(b, b, b));
    grid.add(List.of(b, b, b));
    blue = new PPMImage(grid);
    grid2.add(List.of(r, r, r));
    grid2.add(List.of(r, r, r));
    grid2.add(List.of(r, r, r));
    red = new PPMImage(grid2);
  }

  @After
  public void purgeTestOut() {
    File testOut = new File("test" + SLASH + "testOut");
    for (File f : Objects.requireNonNull(testOut.listFiles())) {
      if (!f.delete()) {
        throw new IllegalStateException("Error. File:" + f.getName() + " was not deleted! " +
                "Clear test" + SLASH + "testOut directory before continuing with testing or false" +
                " tests may " +
                "occur!");
      }
    }
  }

  /*
  Edge case:
  loadImage:
  -correct
  -bad name
  -unsupported type

  save image:
  -bad path
  -overwrite true
  -overwrite false
  -null image
   */

  @Test(expected = IllegalArgumentException.class)
  public void loadImage_BadName() {
    // this test fails if you create a file called "bungus.ppm"
    ImageUtil.createImageFromPath("testRes" + SLASH + "bungus.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadImage_BadExtension() {
    ImageUtil.createImageFromPath("testRes" + SLASH + "dummy.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadImage_BadPath() {
    // this test fails if you create a directory called "bungus" for some reason
    ImageUtil.createImageFromPath("bungus" + SLASH + "FullyBlue_3x3.ppm");
  }

  @Test
  public void createImageFromPathReadingPPM() {
    Image imgLoaded = ImageUtil.createImageFromPath("testRes" + SLASH + "FullyBlue_3x3.ppm");
    assertEquals(blue, imgLoaded);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveImage_NullImage() {
    ImageUtil.saveImage(null, "testRes" + SLASH + "out.ppm", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveImage_NullPath() {
    ImageUtil.saveImage(blue, null, true);
  }

  @Test
  public void saveImage() {
    ImageUtil.saveImage(red, "test" + SLASH + "testOut" + SLASH + "red.ppm", true);
    Image readImage = ImageUtil.createImageFromPath("test" + SLASH + "testOut" + SLASH + "red.ppm");
    assertEquals(red, readImage);
  }

  @Test
  public void saveImage_OverwriteTrue() {
    ImageUtil.saveImage(red, "test" + SLASH + "testOut" + SLASH + "img.ppm", true);
    ImageUtil.saveImage(blue, "test" + SLASH + "testOut" + SLASH + "img.ppm", true);
    assertEquals(blue,
            ImageUtil.createImageFromPath("test" + SLASH + "testOut" + SLASH + "img.ppm"));
  }

  @Test
  public void saveImage_OverwriteFalse() {
    ImageUtil.saveImage(red, "test" + SLASH + "testOut" + SLASH + "img.ppm", true);
    try {
      ImageUtil.saveImage(blue, "test" + SLASH + "testOut" + SLASH + "img.ppm", false);
    } catch (IllegalArgumentException e) {
      assertEquals(red,
              ImageUtil.createImageFromPath("test" + SLASH + "testOut" + SLASH + "img.ppm"));
    }
  }
}