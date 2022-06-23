import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import controller.ImageEditorTextController;
import controller.ImageEditorTextControllerImpl;
import gui.controller.ImageEditorSwingController;
import gui.controller.ImageEditorSwingControllerImpl;
import gui.view.ImageEditorGUIView;
import gui.view.ImageEditorSwingView;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import view.ImageEditorTextView;

/**
 * A class to run a ImageEditor.
 */
public class Runner {

  /**
   * The main method that runs the program.
   *
   * @param args are the command line arguments
   */
  public static void main(String[] args) {
    Readable input;

    ImageEditorModel m = new BasicImageEditorModel();

    if (args.length == 0) {
      ImageEditorGUIView v = new ImageEditorSwingView();
      ImageEditorSwingController c = new ImageEditorSwingControllerImpl(m, v);
    } else if (args[0].equals("-text")) {
      ImageEditorTextView v = new ImageEditorTextView();
      ImageEditorTextController c =
              new ImageEditorTextControllerImpl(m, v, new InputStreamReader(System.in));
      c.launch();
    } else if (args[0].equals("-file")) {
      try {
        ImageEditorTextView v = new ImageEditorTextView();
        ImageEditorTextController c =
                new ImageEditorTextControllerImpl(m, v, new FileReader(args[1]));
        c.launch();
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
