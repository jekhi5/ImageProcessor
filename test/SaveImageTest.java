import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;
import java.util.Random;

import commands.SaveImage;
import controller.ImageEditorController;
import controller.ImageEditorTextController;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import utilities.ImageUtil;
import view.ImageEditorTextView;
import view.ImageEditorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SaveImageTest {

  private static final String SLASH = System.getProperty("file.separator");

  ImageEditorModel model;
  ImageEditorView view;

  @Before
  public void init() {
    this.model = new BasicImageEditorModel();
    view = new ImageEditorTextView(new StringBuilder());
    Reader reader = new StringReader("load testRes" + SLASH + "checkered.ppm checkered");
    ImageEditorController controller = new ImageEditorTextController(model, view, reader);
    controller.launch();
  }

  @After
  public void emptyTestOut() {
    File directory = new File("testOut");
    for (File file : Objects.requireNonNull(directory.listFiles())) {
      if (!file.delete()) {
        throw new IllegalStateException("Error. File:" + file.getName() + " was not deleted! " +
                "Clear testOut directory before continuing with testing or false tests may occur!");
      }
    }
  }

  // To test that apply works without overwrite
  @Test
  public void apply_NoOverwrite() {
    // Can be anything other than affirmative
    String negativeOverwrite = new Random().nextFloat() + "";

    Reader reader = new StringReader(
            "save testOut" + SLASH + "savedCheckered.ppm checkered " + negativeOverwrite + " q");
    ImageEditorController controller = new ImageEditorTextController(this.model, this.view, reader);
    controller.launch();

    assertEquals(ImageUtil.createImageFromPath("testOut" + SLASH + "savedCheckered.ppm"),
            this.model.getImage("checkered"));

  }

  // To test that apply works with overwrite
  @Test
  public void applyOverwriteTest() {
    this.apply_WithOverwrite(false);
    this.apply_WithOverwrite(true);
  }

  private void apply_WithOverwrite(boolean shouldOverwriteFile) {
    String[] affirmativeOverwrite = {"y", "Y", "yes", "YeS", "t", "trUE"};
    File fileToOverwrite = null;
    for (String opt : affirmativeOverwrite) {
      if (shouldOverwriteFile) {
        try {
          fileToOverwrite = new File("testOut" + SLASH + "savedCheckered.ppm");
          if (!fileToOverwrite.createNewFile()) {
            fail("File not created. Oops!");
          }
        } catch (IOException e) {
          fail(e.getMessage());
        }
      }


      Reader reader = new StringReader(
              "save testOut" + SLASH + "savedCheckered.ppm checkered " + opt + " q");
      ImageEditorController controller =
              new ImageEditorTextController(this.model, this.view, reader);
      controller.launch();

      assertEquals(ImageUtil.createImageFromPath("testOut" + SLASH + "savedCheckered.ppm"),
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
