import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Scanner;

import commands.ImageEditorCommand;
import commands.LoadImage;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * Tests for LoadImage.
 */
public class LoadTest {
  ImageEditorModel model;
  static String SLASH = System.getProperty("file.separator");

  @Before
  public void init() {
    model = new BasicImageEditorModel();
  }

  @After
  public void unit() {
    model = new BasicImageEditorModel();
  }

  @Test
  public void load() {
    ImageEditorCommand l =
            new LoadImage(new Scanner(
                    new StringReader("test" + SLASH + "testRes" + SLASH + "checkered.ppm c")));


    assertEquals("Successfully loaded image \"c\" from test" + SLASH +
                    "testRes" + SLASH + "checkered.ppm!",
            l.apply(model));
    assertEquals(ImageUtil.createImageFromPath("test" + SLASH + "testRes" + SLASH + "checkered" +
                    ".ppm"),
            model.getImage("c"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadNullModel() {
    ImageEditorCommand l =
            new LoadImage(new Scanner(
                    new StringReader("test" + SLASH + "testRes" + SLASH + "checkered.ppm c")));
    l.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadNullScanner() {
    new LoadImage(null);
  }

  @Test
  public void badPath() {
    ImageEditorCommand l =
            new LoadImage(new Scanner(new StringReader("test" + SLASH + "testRes" + SLASH +
                    "bungus.ppm " +
                    "bungus")));
    assertEquals("File test" + SLASH + "testRes" + SLASH + "bungus.ppm not found!", l.apply(model));
  }

  @Test
  public void overwritingLoad() {
    ImageEditorCommand l1 =
            new LoadImage(new Scanner(
                    new StringReader("test" + SLASH + "testRes" + SLASH + "checkered.ppm c")));
    ImageEditorCommand l2 =
            new LoadImage(new Scanner(
                    new StringReader(
                            "test" + SLASH + "testRes" + SLASH + "FullyBlue_3x3.ppm c")));

    l1.apply(model);
    assertEquals(ImageUtil.createImageFromPath("test" + SLASH + "testRes" + SLASH + "checkered" +
                    ".ppm"),
            model.getImage("c"));
    l2.apply(model);
    assertEquals(ImageUtil.createImageFromPath("test" + SLASH + "testRes" + SLASH +
                    "FullyBlue_3x3.ppm"),
            model.getImage("c"));
  }
}
