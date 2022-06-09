import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.image.Image;
import model.image.PPMImage;
import model.pixel.Pixel;
import model.pixel.PixelImpl;
import utilities.ImageUtil;

import static org.junit.Assert.*;

/**
 * Tests for ImageUtil.
 */
public class ImageUtilTest {

  private static final String sep = System.getProperty("file.separator");
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
    File testOut = new File("testOut");
    for (File f : Objects.requireNonNull(testOut.listFiles())) {
      f.delete();
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
    ImageUtil.createImageFromPath("testRes" + sep + "bungus.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadImage_BadExtension() {
    ImageUtil.createImageFromPath("testRes" + sep + "dummy.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadImage_BadPath() {
    // this test fails if you create a directory called "bungus" for some reason
    ImageUtil.createImageFromPath("bungus" + sep + "FullyBlue_3x3.ppm");
  }

  @Test
  public void createImageFromPathReadingPPM() {
    Image imgLoaded = ImageUtil.createImageFromPath("testRes" + sep + "FullyBlue_3x3.ppm");
    assertEquals(blue, imgLoaded);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveImage_NullImage() {
    ImageUtil.saveImage(null, "testRes" + sep + "out.ppm", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveImage_NullPath() {
    ImageUtil.saveImage(blue, null, true);
  }

  @Test
  public void saveImage() {
    ImageUtil.saveImage(red, "testOut" + sep + "red.ppm", true);
    Image readImage = ImageUtil.createImageFromPath("testOut" + sep + "red.ppm");
    assertEquals(red, readImage);
  }

  @Test
  public void saveImage_OverwriteTrue() {
    ImageUtil.saveImage(red, "testOut" + sep + "img.ppm", true);
    ImageUtil.saveImage(blue, "testOut" + sep + "img.ppm", true);
    assertEquals(blue, ImageUtil.createImageFromPath("testOut" + sep + "img.ppm"));
  }

  @Test
  public void saveImage_OverwriteFalse() {
    ImageUtil.saveImage(red, "testOut" + sep + "img.ppm", true);
    try {
      ImageUtil.saveImage(blue, "testOut" + sep + "img.ppm", false);
    } catch (IllegalArgumentException e) {
      assertEquals(red, ImageUtil.createImageFromPath("testOut" + sep + "img" + ".ppm"));
    }
  }
}