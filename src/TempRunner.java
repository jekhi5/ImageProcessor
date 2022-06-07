import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import controller.ImageEditorController;
import controller.ImageEditorTextController;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import view.ImageEditorTextView;

/**
 * Temp class for manual testing.
 *
 * @author emery
 * @created 2022-06-05
 */
public class TempRunner {
  public static void main(String[] args) {
    ImageEditorModel m = new BasicImageEditorModel();
    ImageEditorTextView v = new ImageEditorTextView();
    Readable input = null;
    try {
      input = new FileReader("res/omniscript.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Readable input2 = new InputStreamReader(System.in);
    ImageEditorController c = new ImageEditorTextController(m, v, input);
    c.launch();
  }
}
