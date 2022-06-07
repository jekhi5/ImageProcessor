import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import model.image.PPMImage;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicImageEditorModelTest {

  Image fullyRedPPM;
  Image fullyGreenPPM;
  Image fullyBluePPM;
  Image checkeredBlackRight;
  Image checkeredBlackBottom;
  ImageEditorModel model;

  @Before
  public void init() {
    fullyRedPPM = ImageUtil.createImageFromPath("res/FullyRed_3x3.ppm");
    fullyGreenPPM = ImageUtil.createImageFromPath("res/FullyGreen_3x3.ppm");
    fullyBluePPM = ImageUtil.createImageFromPath("res/FullyBlue_3x3.ppm");
    checkeredBlackRight = ImageUtil.createImageFromPath("res/CheckeredBlackRight_3x4" +
            ".ppm");
    checkeredBlackBottom = ImageUtil.createImageFromPath("res/CheckeredBlackBottom_3x4" +
            ".ppm");
    model = new BasicImageEditorModel();
  }

  private void populateModel() {
    Map<String, Image> map = new HashMap<>();
    map.put("r", fullyRedPPM);
    map.put("g", fullyGreenPPM);
    map.put("b", fullyBluePPM);
    model = new BasicImageEditorModel(map);
  }

  // Testing giving a null argument to constructor
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullMap() {
    new BasicImageEditorModel(null);
  }

  // Testing constructing with valid map
  @Test
  public void testingConstructor_ValidMap() {
    populateModel();
    assertEquals(fullyRedPPM, model.getImage("r"));
    assertEquals(fullyGreenPPM, model.getImage("g"));
    assertEquals(fullyBluePPM, model.getImage("b"));
  }

  @Test
  public void zeroArgConstructor() {
    ImageEditorModel m = new BasicImageEditorModel();
    m.addImage("r", fullyRedPPM);
    assertEquals(fullyRedPPM, m.getImage("r"));
  }

  /*
   edge cases for constructor:
   - null arguments
   - showing making copies
   - working successfully

   edge cases for getImage:
   - working successfully
   - empty images
   - name not found
   - null name
   - demonstrating non-mutability

   edge cases for addImage:
   - working normally
   - null image
   - null name
   - overwriting an existing image with the same name
   - demonstrating non-mutability

   */

  @Test(expected = IllegalArgumentException.class)
  public void getImage_NoImages (){
    model.getImage("UwU what's this?");
  }

  @Test(expected = IllegalArgumentException.class)
  public void getImage_InvalidName (){
    populateModel();
    model.getImage("a");
  }

  @Test(expected = IllegalArgumentException.class)
  public void getImage_InvalidNameCaseSensitive (){
    populateModel();
    model.getImage("R");
  }

  @Test(expected = IllegalArgumentException.class)
  public void getImage_NullName (){
    populateModel();
    model.getImage(null);
  }

  @Test
  public void getImage_ReturnsCopy () {
    Map<String, Image> map = new HashMap<>();
    map.put("r", fullyRedPPM);
    model = new BasicImageEditorModel(map);

    Image img = model.getImage("r");
    assertTrue(img.equals(fullyRedPPM));
    assertFalse(img == fullyRedPPM);
  }

  @Test
  public void getImage_IntendedFunctionality() {
    populateModel();

    assertEquals(fullyRedPPM, model.getImage("r"));
    assertEquals(fullyBluePPM, model.getImage("b"));
    assertEquals(fullyGreenPPM, model.getImage("g"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addImage_NullName() {
    model.addImage(null, checkeredBlackBottom);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addImage_NullImage() {
    model.addImage("OwO", null);
  }

  @Test
  public void addImage_StoresCopy() {
    model.addImage("r", fullyRedPPM);
    Image img = model.getImage("r");
    assertTrue(img.equals(fullyRedPPM));
    assertFalse(img == fullyRedPPM);
  }

  @Test
  public void addImage_OverwriteName() {
    populateModel();
    assertEquals(fullyRedPPM, model.getImage("r"));
    model.addImage("r", checkeredBlackBottom);
    assertEquals(checkeredBlackBottom, model.getImage("r"));
  }

  @Test
  public void addImage_OverwriteName_CaseSensitive() {
    populateModel();
    assertEquals(fullyRedPPM, model.getImage("r"));
    model.addImage("R", checkeredBlackBottom);
    assertEquals(fullyRedPPM, model.getImage("r"));
    assertEquals(checkeredBlackBottom, model.getImage("R"));
  }

  @Test
  public void addImage_IntendedFunctionality() {
    model.addImage("r", fullyRedPPM);
    model.addImage("g", fullyGreenPPM);
    model.addImage("b", fullyBluePPM);
    assertEquals(fullyRedPPM, model.getImage("r"));
    assertEquals(fullyBluePPM, model.getImage("b"));
    assertEquals(fullyGreenPPM, model.getImage("g"));
  }
}