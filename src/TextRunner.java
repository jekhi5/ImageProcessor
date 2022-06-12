import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import controller.ImageEditorController;
import controller.ImageEditorTextController;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import view.ImageEditorTextView;

/**
 * A class to run a text-based version of ImageEditor.
 */
public class TextRunner {

  /**
   * The main method that runs the program.
   *
   * @param args are the command line arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      throw new RuntimeException("You need to specify the mode! Read the README!");
    }

    Readable input;
    if (args[0].equalsIgnoreCase("manual")) {
      input = new InputStreamReader(System.in);
    } else {
      try {
        input = new FileReader(args[0]);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }


    ImageEditorModel m = new BasicImageEditorModel();
    ImageEditorTextView v = new ImageEditorTextView();
    ImageEditorController c = new ImageEditorTextController(m, v, input);
    c.launch();
  }
}
