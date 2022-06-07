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
    ImageEditorController c = new ImageEditorTextController(m, v, new InputStreamReader(System.in));
    c.launch();
  }
}
