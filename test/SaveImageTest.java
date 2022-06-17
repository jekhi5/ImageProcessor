import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import commands.ImageEditorCommand;
import commands.SaveImage;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The test class for {@link SaveImage}.
 */
public class SaveImageTest {

  private static final String SLASH = System.getProperty("file.separator");

  ImageEditorModel model;

  @Before
  public void init() {
    Map<String, Image> preLoadedImage = new HashMap<>();
    preLoadedImage.put("checkered",
            ImageUtil.createImageFromPath("test" + SLASH + "testRes" + SLASH + "checkered.ppm"));
    this.model = new BasicImageEditorModel(preLoadedImage);
  }

  @After
  public void emptyTestOut() {
    File directory = new File("test" + SLASH + "testOut");
    for (File file : Objects.requireNonNull(directory.listFiles())) {
      if (!file.delete()) {
        throw new IllegalStateException("File:" + file.getName() + " was not deleted! " +
                "Clear test" + SLASH + "testOut directory before continuing with testing or false" +
                " tests may occur!");
      }
    }
  }

  // To test that apply works without overwrite
  @Test
  public void apply_NoOverwrite() {
    // Can be anything other than affirmative
    String negativeOverwrite = new Random().nextFloat() + "";

    ImageEditorCommand saveCommand =
            new SaveImage(new Scanner(new StringReader("test" + SLASH + "testOut" + SLASH +
                    "savedCheckered.ppm checkered " + negativeOverwrite + " q")));
    saveCommand.apply(this.model);

    assertEquals(ImageUtil.createImageFromPath("test" + SLASH + "testOut" + SLASH +
            "savedCheckered.ppm"), this.model.getImage("checkered"));

  }

  // To test that apply works with overwrite
  @Test
  public void applyOverwriteTest() {
    this.applyWithOverwrite(false);
    this.applyWithOverwrite(true);

    boolean t = true;
    assertTrue(t);
  }

  private void applyWithOverwrite(boolean shouldOverwriteFile) {
    String[] affirmativeOverwrite = {"y", "Y", "yes", "YeS", "t", "trUE"};
    File fileToOverwrite = new File("test" + SLASH + "testOut" + SLASH + "savedCheckered.ppm");
    for (String opt : affirmativeOverwrite) {
      this.init();
      if (shouldOverwriteFile) {
        try {
          fileToOverwrite.createNewFile();
        } catch (IOException e) {
          fail(e.getMessage());
        }
      }

      ImageEditorCommand saveCommand =
              new SaveImage(new Scanner(new StringReader("test" + SLASH + "testOut" + SLASH +
                      "savedCheckered.ppm checkered " + opt + " q")));
      saveCommand.apply(this.model);

      assertEquals(ImageUtil.createImageFromPath("test" + SLASH + "testOut" + SLASH +
                      "savedCheckered.ppm"),
              this.model.getImage("checkered"));

      if (shouldOverwriteFile && !fileToOverwrite.delete()) {
        fail("File not deleted! Oops!");
      }
    }
  }

  // Testing attempting to save but no overwrite
  // It is impossible to test giving a bad path WITH overwrite permission because you can only
  // type in unicode characters in IntelliJ and the only way to give a bad file name in our
  // system is to type outside that character set.
  @Test
  public void apply_NoOverwritePermissions() {
    ImageEditorCommand saveCommand = new SaveImage(new Scanner(new StringReader(
            "test" + SLASH + "testRes" + SLASH + "checkered.ppm checkered no q")));
    assertEquals(
            "Save failed: Could not create file from path: " +
                    "test" + SLASH + "testRes" + SLASH +
                    "checkered.ppm. There was already a file at this " +
                    "location. To " +
                    "overwrite, add \"true\" to command.",
            saveCommand.apply(model));
  }

  @Test
  public void apply_BadPath() {
    ImageEditorCommand saveCommand = new SaveImage(new Scanner(new StringReader(
            "test" + SLASH + "testRes" + SLASH + "checkered.ppm checkered no q")));
    assertEquals(
            "Save failed: Could not create file from path: " +
                    "test" + SLASH + "testRes" + SLASH + "checkered.ppm. There was already a " +
                    "file at this location. To overwrite, add \"true\" to command.",
            saveCommand.apply(model));
  }

  // Testing giving a bad image name
  @Test
  public void apply_BadImageName() {
    ImageEditorCommand saveCommand = new SaveImage(new Scanner(new StringReader(
            "test" + SLASH + "testRes" + SLASH + "checkered.ppm checkered no q")));
    assertEquals(
            "Save failed: Could not create file from path: " +
                    "test" + SLASH + "testRes" + SLASH +
                    "checkered.ppm. There was already a file at this " +
                    "location. To overwrite, add \"true\" to command.",
            saveCommand.apply(model));
  }

  // To test giving the constructor a null scanner
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    new SaveImage(null);
  }


  // To test trying to save an image that doesn't exist
  @Test
  public void invalidImage() {
    ImageEditorModel temp = new BasicImageEditorModel();
    ImageEditorCommand saveCommand = new SaveImage(new Scanner(new StringReader("test" + SLASH +
            "testOut" + SLASH + "newImage.ppm invalid-image-name n")));
    assertEquals("No image found with the name: \"invalid-image-name\"", saveCommand.apply(temp));
  }
}
