import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import commands.ImageEditorCommand;
import commands.SameImageCommand;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * Tests for SameImage.
 */
public class SameImageCommandTest {
  static String slash = System.getProperty("file.separator");

  ImageEditorModel m;

  @Before
  public void init() {
    Map<String, Image> hm = new HashMap<>();
    hm.put("a", ImageUtil.createImageFromPath("test" + slash + "testRes" + slash + "FullyBlue_3x3" +
            ".ppm"));
    hm.put("b", ImageUtil.createImageFromPath("test" + slash + "testRes" + slash +
            "FullyBlue_3x3.ppm"));
    hm.put("c",
            ImageUtil.createImageFromPath("test" + slash + "testRes" + slash + "checkered.ppm"));
    m = new BasicImageEditorModel(hm);
  }

  @Test
  public void sameImages() {
    ImageEditorCommand s1 = new SameImageCommand(new Scanner(new StringReader("a b")));
    assertEquals("Images \"a\" and \"b\" are the same, with width 3px and height 3px.",
            s1.apply(m));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    ImageEditorCommand s1 = new SameImageCommand(new Scanner(new StringReader("a b")));
    s1.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullScanner() {
    ImageEditorCommand s1 = new SameImageCommand(null);
  }

  @Test
  public void differentImages() {
    ImageEditorCommand s1 = new SameImageCommand(new Scanner(new StringReader("a c")));
    assertEquals("Images \"a\" and \"c\" are different.",
            s1.apply(m));
  }

  @Test
  public void invalidImage1() {
    ImageEditorCommand s1 = new SameImageCommand(new Scanner(new StringReader("q c")));
    assertEquals("", s1.apply(m));
  }
}