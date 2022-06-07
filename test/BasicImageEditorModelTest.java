import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;

public class BasicImageEditorModelTest {

  Image fullyRedPPM;
  Image fullyGreenPPM;
  Image fullyBluePPM;
  Image checkeredBlackRight;
  Image checkeredBlackBottom;
  ImageEditorModel model;

  @Before
  public void init() {
    fullyRedPPM = ImageUtil.createImageFromPath("resources/FullyRed_3x3.ppm");
    fullyGreenPPM = ImageUtil.createImageFromPath("resources/FullyGreen_3x3.ppm");
    fullyBluePPM = ImageUtil.createImageFromPath("resources/FullyBlue_3x3.ppm");
    checkeredBlackRight = ImageUtil.createImageFromPath("resources/CheckeredBlackRight_3x4" +
            ".ppm");
    checkeredBlackBottom = ImageUtil.createImageFromPath("resources/CheckeredBlackBottom_3x4" +
            ".ppm");
    model = new BasicImageEditorModel();
  }

  // Testing giving a null argument to constructor
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullMap() {
    new BasicImageEditorModel(null);
  }

  // Testing constructing with valid map
  @Test
  public void testingConstructor_ValidMap() {
    new BasicImageEditorModel(new HashMap<String, Image>());
  }



}