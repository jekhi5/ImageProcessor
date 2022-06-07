import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import controller.ImageEditorController;
import controller.ImageEditorTextController;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;
import view.ImageEditorTextView;
import view.ImageEditorView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link controller.ImageEditorTextController}
 *
 * @author emery and Jacob Kline
 * @created 2022-06-05
 */
public class ImageEditorTextControllerTest {

  ImageEditorModel model;
  ImageEditorView view;
  ImageEditorController controller;
  Appendable log;

  String initialMessage =
          "Welcome to ImageEditor! Please enter a command:\n> ";
  String finalMessage = "> Thanks for using ImageEditor!\n";

  String loadingLemonChiffonMsg =
          initialMessage + "addImage(lemon-image,\n" + ImageUtil.createImageFromPath("res" +
                  "/LemonChiffon_1x1.ppm").toString() +
                  ")\nSuccessfully loaded image \"lemon-image\" from res/LemonChiffon_1x1.ppm!\n";
  String loadChiffon = "load res/LemonChiffon_1x1.ppm lemon ";

  @Before
  public void init() {
    log = new StringBuilder();
    model = new MockModel(log);
    view = new ImageEditorTextView(log);
  }

  // Testing constructor with null model
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullModel() {
    new ImageEditorTextController(null, new ImageEditorTextView(), new StringReader(""));
  }

  // Testing constructor with null view
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullView() {
    new ImageEditorTextController(new BasicImageEditorModel(), null, new StringReader(""));
  }

  // Testing constructor with null readable
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullReadable() {
    new ImageEditorTextController(new BasicImageEditorModel(), new ImageEditorTextView(),
            null);
  }

  // Testing immediately quitting
  @Test
  public void quittingBeforeAnything() {
    Reader reader = new StringReader("q");
    controller = new ImageEditorTextController(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(initialMessage + finalMessage, this.log.toString());
  }

  // Testing loading an image and not overwriting the name of another image already in the model
  @Test
  public void loadingImageNoOverwrite() {
    Reader reader = new StringReader(loadChiffon + "q");
    controller = new ImageEditorTextController(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(loadingLemonChiffonMsg + finalMessage, this.log.toString());
  }

  // Testing loading an image and overwriting the name of another image
  @Test
  public void loadingImageWithOverwrite() {
    Reader reader = new StringReader(loadChiffon + "load res/CheckeredBlackBottom_3x4.ppm image Q");
    controller = new ImageEditorTextController(model, view, reader);
    Image image2 = ImageUtil.createImageFromPath("res/CheckeredBlackBottom_3x4.ppm");

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(loadingLemonChiffonMsg + "> addImage(image,\n" + image2.toString() +
            ")\nSuccessfully loaded image \"image\" from res/CheckeredBlackBottom_3x4.ppm!\n" +
            finalMessage, this.log.toString());
  }

  // Testing Greyscale using "greyscale red"
  @Test
  public void greyScale() {
    String[] spellings = {"Grayscale", "GrAyScAle", "greyscale", "gREYSCALE", "grey", "GREY",
            "gray", "GrAY"};
    String[] typesOfGreyscale = {"red", "ReD", "green", "GReeN", "blue", "BlUE", "value", "VaLuE"
            , "intensity", "INtENsITy", "luma", "Luma"};

    for (int spelling = 0; spelling < spellings.length; spelling += 1) {
      for (int type = 0; type < typesOfGreyscale.length; type += 1) {
        this.init();
        assertEquals("", this.log.toString());
        runGreyscale(spellings[spelling], typesOfGreyscale[type]);
        assertEquals(loadingLemonChiffonMsg, this.log.toString());
      }
    }
  }


  public void runGreyscale(String spelling, String typeOfGreyscale) {
    Reader reader = new StringReader(loadChiffon + spelling + " " + typeOfGreyscale + " image q");
    controller = new ImageEditorTextController(model, view, reader);

    controller.launch();
  }

  // Testing greyscale using the 4 different ways of writing it and also all different ways of
  //    greyscaling
  //    red, green, blue, value, intensity, luma
  // For every command show one argument being wrong in each location (can be same method)
  // Testing hanging input
  // Testing quitting (all types)
  // Testing get next command
  // Testing transmit (both overloads)

  // TODO: Integration tests


  private static class MockModel implements ImageEditorModel {

    Appendable log;

    public MockModel(Appendable log) {
      this.log = log;
    }


    @Override
    public Image getImage(String name) throws IllegalArgumentException {
      try {
        this.log.append("getImage(").append(name).append(")").append("\n");
      } catch (IOException e) {
        this.log = new StringBuilder("ERROR APPENDING IN getImage()!!");
      }
      return null;
    }

    @Override
    public void addImage(String name, Image image) throws IllegalArgumentException {
      try {
        this.log.append("addImage(").append(name).append(",\n").append(image.toString()).append(")")
                .append("\n");
      } catch (IOException e) {
        this.log = new StringBuilder("ERROR APPENDING IN addImage()!!");
      }
    }
  }
}