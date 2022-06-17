import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commands.ImageEditorCommand;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import model.v2.ImageFactory;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for all {@link ImageEditorCommand}s.
 */
public abstract class AbstractCommandTest {

  protected static final String SLASH = System.getProperty("file.separator");
  protected static final Image CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered.ppm");
  protected static final Image RED_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered_red.ppm");
  protected static final Image GREEN_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered_green.ppm");
  protected static final Image BLUE_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered_blue.ppm");
  protected static final Image VALUE_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered_value.ppm");
  protected static final Image INTENSITY_CHECKERED =
          ImageFactory.createImage(
                  "test" + SLASH + "testRes" + SLASH + "checkered_intensity.ppm");
  protected static final Image LUMA_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered_luma.ppm");
  protected static final Image BRIGHTEN_CHECKERED =
          ImageFactory.createImage(
                  "test" + SLASH + "testRes" + SLASH + "checkered_brighten_100.ppm");
  protected static final Image DARKEN_CHECKERED =
          ImageFactory.createImage(
                  "test" + SLASH + "testRes" + SLASH + "checkered_darken_150.ppm");
  protected static final Image HORIZONTAL_CHECKERED =
          ImageFactory.createImage(
                  "test" + SLASH + "testRes" + SLASH + "checkered_horizontal_flip.ppm");
  protected static final Image VERTICAL_CHECKERED =
          ImageFactory.createImage(
                  "test" + SLASH + "testRes" + SLASH + "checkered_vertical_flip.ppm");
  protected static final Image BLUR_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered_blur.ppm");
  protected static final Image SHARPEN_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered_sharpen.ppm");
  protected static final Image SEPIA_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "checkered_sepia.ppm");
  protected static final Image GENERIC_GRAYSCALE_CHECKERED =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH +
                  "checkered_generic_grayscale.ppm");

  // The images in the test resources folder
  protected final Map<String, Image> testResourceImages;
  // This is all different forms of a given command (IE a red grayscale, a blue grayscale, etc)
  protected List<ImageEditorCommand> commandForms;
  // The types of operation performed in the same order as the given command forms
  protected List<String> orderOfTypes;
  // These commands were constructed to throw errors when attempting to apply to the model (bad
  // image, bad mode, etc)
  protected List<ImageEditorCommand> illegalForms;
  protected String successfulMessage;
  protected ImageEditorModel model;

  /**
   * To construct a test for an arbitrary command.
   *
   * @param commandForms      the different forms that the command could be used in (vertical, red,
   *                          100, etc)
   * @param orderOfTypes      the order that the types of commands are listed in
   * @param illegalForms      examples of illegal types that are not allowed to be used with the
   *                          command
   * @param successfulMessage the message that the command returns when the operation is fully
   *                          successful
   */
  public AbstractCommandTest(List<ImageEditorCommand> commandForms, List<String> orderOfTypes,
                             List<ImageEditorCommand> illegalForms, String successfulMessage) {
    this.commandForms = commandForms;
    this.orderOfTypes = orderOfTypes;
    this.illegalForms = illegalForms;
    this.successfulMessage = successfulMessage;

    testResourceImages = new HashMap<>();
    this.testResourceImages.put("checkered", CHECKERED);
    this.testResourceImages.put("red", RED_CHECKERED);
    this.testResourceImages.put("blue", BLUE_CHECKERED);
    this.testResourceImages.put("green", GREEN_CHECKERED);
    this.testResourceImages.put("value", VALUE_CHECKERED);
    this.testResourceImages.put("intensity", INTENSITY_CHECKERED);
    this.testResourceImages.put("luma", LUMA_CHECKERED);
    this.testResourceImages.put("100", BRIGHTEN_CHECKERED);
    this.testResourceImages.put("150", DARKEN_CHECKERED);
    this.testResourceImages.put("horizontal", HORIZONTAL_CHECKERED);
    this.testResourceImages.put("vertical", VERTICAL_CHECKERED);
    this.testResourceImages.put("blur", BLUR_CHECKERED);
    this.testResourceImages.put("sharpen", SHARPEN_CHECKERED);
    this.testResourceImages.put("sepia", SEPIA_CHECKERED);
    this.testResourceImages.put("generic-grayscale", GENERIC_GRAYSCALE_CHECKERED);

  }

  @Before
  public void init() {
    Map<String, Image> editorImages = new HashMap<>();
    editorImages.put("checkered",
            ImageUtil.createImageFromPath("test" + SLASH + "testRes" + SLASH + "checkered" +
                    ".ppm"));

    model = new BasicImageEditorModel(editorImages);
  }

  // Testing apply w/ null model
  @Test
  public void apply_NullModel() {
    for (ImageEditorCommand cmd : this.commandForms) {
      try {
        cmd.apply(null);
        fail("Should have thrown IllegalArgumentException but did not.");
      } catch (IllegalArgumentException e) {
        boolean t = true;
        assertTrue(t);
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
              this.testResourceImages.get(type.toLowerCase()));
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
