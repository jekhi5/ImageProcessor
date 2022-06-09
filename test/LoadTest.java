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
  static String slash = System.getProperty("file.separator");

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
            new LoadImage(new Scanner(new StringReader("testRes" + slash + "checkered.ppm c")));


    assertEquals("Successfully loaded image \"c\" from testRes\\checkered.ppm!",
            l.apply(model));
    assertEquals(ImageUtil.createImageFromPath("testRes" + slash + "checkered.ppm"),
            model.getImage("c"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadNullModel() {
    ImageEditorCommand l =
            new LoadImage(new Scanner(new StringReader("testRes" + slash + "checkered.ppm c")));
    l.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadNullScanner() {
    new LoadImage(null);
  }

  @Test
  public void badPath() {
    ImageEditorCommand l =
            new LoadImage(new Scanner(new StringReader("testRes" + slash + "bungus.ppm bungus")));
    assertEquals("File testRes\\bungus.ppm not found!", l.apply(model));
  }

  @Test
  public void overwritingLoad() {
    ImageEditorCommand l1 =
            new LoadImage(new Scanner(new StringReader("testRes" + slash + "checkered.ppm c")));
    ImageEditorCommand l2 =
            new LoadImage(new Scanner(
                    new StringReader("testRes" + slash + "FullyBlue_3x3.ppm c")));

    l1.apply(model);
    assertEquals(ImageUtil.createImageFromPath("testRes" + slash + "checkered.ppm"),
            model.getImage("c"));
    l2.apply(model);
    assertEquals(ImageUtil.createImageFromPath("testRes" + slash + "FullyBlue_3x3.ppm"),
            model.getImage("c"));
  }
}
