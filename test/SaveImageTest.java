import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import commands.ImageEditorCommand;
import commands.SaveImage;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SaveImageTest {

  private static final String SLASH = System.getProperty("file.separator");

  ImageEditorModel model;

  @Before
  public void init() {
    this.model = new BasicImageEditorModel();
  }

  @After
  public void emptyTestOut() {
    File directory = new File("test" + SLASH + "testOut");
    for (File file : Objects.requireNonNull(directory.listFiles())) {
      if (!file.delete()) {
        throw new IllegalStateException("Error. File:" + file.getName() + " was not deleted! " +
                "Clear test" + SLASH + "testOut directory before continuing with testing or false" +
                " tests may " +
                "occur!");
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
                    "savedChecker.ppm " + negativeOverwrite + " q")));
    saveCommand.apply(this.model);

    assertEquals(ImageUtil.createImageFromPath("test" + SLASH + "testOut" + SLASH +
            "savedCheckered.ppm"), this.model.getImage("checkered"));

  }

  // To test that apply works with overwrite
  @Test
  public void applyOverwriteTest() {
    this.apply_WithOverwrite(false);
    this.apply_WithOverwrite(true);
  }

  private void apply_WithOverwrite(boolean shouldOverwriteFile) {
    String[] affirmativeOverwrite = {"y", "Y", "yes", "YeS", "t", "trUE"};
    File fileToOverwrite = new File("test" + SLASH + "testOut" + SLASH + "savedCheckered.ppm");
    for (String opt : affirmativeOverwrite) {
      this.init();
      if (shouldOverwriteFile) {
        try {
          if (!fileToOverwrite.createNewFile()) {
            fail("File not created. Oops!");
          }
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


  // To test giving the constructor a null scanner
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    new SaveImage(null);
  }


}
