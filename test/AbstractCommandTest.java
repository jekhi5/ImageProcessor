import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commands.ImageEditorCommand;
import controller.ImageEditorController;
import controller.ImageEditorTextController;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;
import view.ImageEditorTextView;
import view.ImageEditorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class AbstractCommandTest {

  // This is all different forms of a given command (IE a red grayscale, a blue grayscale, etc)
  private final List<ImageEditorCommand> commandForms;

  // The types of operation performed in the same order as the given command forms
  private final List<String> orderOfTypes;

  // These commands were constructed to throw errors when attempting to apply to the model (bad
  // image, bad mode, etc)
  private final List<ImageEditorCommand> illegalForms;
  private final String successfulMessage;
  private ImageEditorModel model;

  private static final Image CHECKERED = ImageUtil.createImageFromPath("testRes/checkered.ppm");
  private static final Image RED_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_red.ppm");
  private static final Image GREEN_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_green.ppm");
  private static final Image BLUE_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_blue.ppm");
  private static final Image VALUE_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_value.ppm");
  private static final Image INTENSITY_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_intensity.ppm");
  private static final Image LUMA_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_luma.ppm");
  private static final Image BRIGHTEN_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_brighten_100.ppm");
  private static final Image DARKEN_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_darken_100.ppm");
  private static final Image HORIZONTAL_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_horizontal_flip.ppm");
  private static final Image VERTICAL_CHECKERED =
          ImageUtil.createImageFromPath("testRes/checkered_vertical_flip.ppm");

  // The images in the test resources folder
  private final Map<String, Image> testResourceImages;

  public AbstractCommandTest() {
    this.commandForms = this.getCommandForms();
    this.orderOfTypes = this.getOrderOfTypes();
    this.illegalForms = this.getIllegalForms();
    this.successfulMessage = this.getSuccessfulMessage();

    testResourceImages = new HashMap<>();
    this.testResourceImages.put("checkered", CHECKERED);
    this.testResourceImages.put("red", RED_CHECKERED);
    this.testResourceImages.put("blue", BLUE_CHECKERED);
    this.testResourceImages.put("green", GREEN_CHECKERED);
    this.testResourceImages.put("value", VALUE_CHECKERED);
    this.testResourceImages.put("intensity", INTENSITY_CHECKERED);
    this.testResourceImages.put("luma", LUMA_CHECKERED);
    this.testResourceImages.put("brighten", BRIGHTEN_CHECKERED);
    this.testResourceImages.put("darken", DARKEN_CHECKERED);
    this.testResourceImages.put("horizontal", HORIZONTAL_CHECKERED);
    this.testResourceImages.put("vertical", VERTICAL_CHECKERED);
  }

  protected abstract String getSuccessfulMessage();

  protected abstract List<ImageEditorCommand> getIllegalForms();

  protected abstract List<String> getOrderOfTypes();

  protected abstract List<ImageEditorCommand> getCommandForms();

  @Before
  public void init() {
    model = new BasicImageEditorModel();
    Appendable log = new StringBuilder();
    ImageEditorView view = new ImageEditorTextView(log);
    Reader reader = new StringReader("load testRes/checkered.ppm checkered q");
    ImageEditorController controller = new ImageEditorTextController(model, view, reader);
    controller.launch();
  }

  // Testing apply w/ null model
  @Test
  public void apply_NullModel() {
    for (ImageEditorCommand cmd : this.commandForms) {
      try {
        cmd.apply(null);
        fail("Error. Should have thrown IllegalArgumentException but did not.");
      } catch (IllegalArgumentException e) {
        assertTrue(true);
      }
    }
  }

  // Testing constructor w/ null scanner
  @Test(expected = IllegalArgumentException.class)
  public void constructor_NullScanner() {
    this.getNullCommand();
  }

  // Testing apply w/ valid model
  @Test
  public void apply() {
    for (int cmdIndex = 0; cmdIndex < this.commandForms.size(); cmdIndex += 1) {

      assertEquals(successfulMessage, this.commandForms.get(cmdIndex).apply(this.model));
      String type = this.orderOfTypes.get(cmdIndex);

      assertEquals(this.model.getImage(type.toLowerCase() + "_checkered"),
              this.testResourceImages.get(type));
    }
  }

  // Testing bad images and bad modes
  @Test
  public void apply_BadInput() {
    for (ImageEditorCommand cmd : this.illegalForms) {
      assertNotEquals(this.successfulMessage, cmd.apply(this.model));
    }
  }

  protected abstract void getNullCommand();
}
