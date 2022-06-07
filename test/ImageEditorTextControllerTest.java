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
    Reader reader = new StringReader("load res/LemonChiffon_1x1.ppm lemon-image q");
    controller = new ImageEditorTextController(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(initialMessage + "addImage(lemon-image,\n" +
                    ImageUtil.readPPM("res/LemonChiffon_1x1.ppm").toString() + ")\n" +
                    "Successfully loaded image \"lemon-image\" from res/LemonChiffon_1x1.ppm!\n" +
                    finalMessage,
            this.log.toString());
  }

  // Testing loading an image and overwriting the name of another image
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