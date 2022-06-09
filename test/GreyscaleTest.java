import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

import commands.Grayscale;
import controller.ImageEditorController;
import controller.ImageEditorTextController;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import view.ImageEditorTextView;
import view.ImageEditorView;

public class GreyscaleTest {


  // Testing constructor w/ null scanner
  @Test(expected = IllegalArgumentException.class)
  public void constructor_NullScanner() {
    new Grayscale(null);
  }

  // Testing apply w/ null model
  @Test(expected = IllegalArgumentException.class)
  public void apply_NullModel() {
    new Grayscale(new Scanner(System.in)).apply(null);
  }

  // Testing apply w/ valid model
  @Test
  public void apply() {
    ImageEditorModel model = new BasicImageEditorModel();
    ImageEditorView view = new ImageEditorTextView();
    Reader reader = new StringReader("load res/CheckeredBlackBottom_3x4.ppm checkered\n" +
            "flip vertical checkered checkered_vertical_flip\n" +
            "flip horizontal checkered checkered_horizontal_flip\n" +
            "gray red checkered checkered_red\n" +
            "gray green checkered checkered_green\n" +
            "gray blue checkered checkered_blue\n" +
            "gray value checkered checkered_value\n" +
            "gray intensity checkered checkered_intensity\n" +
            "gray luma checkered checkered_luma\n" +
            "brighten 100 checkered checkered_brighten_100\n" +
            "darken 100 checkered checkered_darken_100\n" +
            "save testOut/checkered_vertical_flip.ppm checkered_vertical_flip y\n" +
            "save testOut/checkered_horizontal_flip.ppm checkered_horizontal_flip y\n" +
            "save testOut/checkered_red.ppm checkered_red y\n" +
            "save testOut/checkered_green.ppm checkered_green y\n" +
            "save testOut/checkered_blue.ppm checkered_blue y\n" +
            "save testOut/checkered_value.ppm checkered_value y\n" +
            "save testOut/checkered_intensity.ppm checkered_intensity y\n" +
            "save testOut/checkered_luma.ppm checkered_luma y\n" +
            "save testOut/checkered_brighten_100.ppm checkered_brighten_100 y\n" +
            "save testOut/checkered_darken_100.ppm checkered_darken_100 y");
    ImageEditorController controller = new ImageEditorTextController(model, view, reader);
    controller.launch();

    //Scanner scanner = new Scanner(new StringReader(""))
  }

}