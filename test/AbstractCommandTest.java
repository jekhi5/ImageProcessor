import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commands.ImageEditorCommand;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for all commands.
 */
public abstract class AbstractCommandTest {

  protected static final String slash = System.getProperty("file.separator");
  protected static final Image CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered.ppm");
  protected static final Image RED_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_red.ppm");
  protected static final Image GREEN_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_green.ppm");
  protected static final Image BLUE_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_blue.ppm");
  protected static final Image VALUE_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_value.ppm");
  protected static final Image INTENSITY_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_intensity.ppm");
  protected static final Image LUMA_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_luma.ppm");
  protected static final Image BRIGHTEN_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_brighten_100.ppm");
  protected static final Image DARKEN_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_darken_150.ppm");
  protected static final Image HORIZONTAL_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_horizontal_flip.ppm");
  protected static final Image VERTICAL_CHECKERED =
          ImageUtil.createImageFromPath("testRes" + slash + "checkered_vertical_flip.ppm");
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
  }

  @Before
  public void init() {
    Map<String, Image> editorImages = new HashMap<>();
    editorImages.put("checkered", ImageUtil.createImageFromPath("testRes" + slash + "checkered" +
            ".ppm"));

    model = new BasicImageEditorModel(editorImages);
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
