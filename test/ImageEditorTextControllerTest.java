import org.junit.Test;

import java.io.StringReader;

import controller.ImageEditorController;
import controller.ImageEditorTextController;
import model.BasicImageEditorModel;
import view.ImageEditorTextView;

import static org.junit.Assert.*;

/**
 * Tests for {@link controller.ImageEditorTextController}
 *
 * @author emery
 * @created 2022-06-05
 */
public class ImageEditorTextControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    ImageEditorController c =
            new ImageEditorTextController(null, new ImageEditorTextView(), new StringReader(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullView() {
    ImageEditorController c =
            new ImageEditorTextController(new BasicImageEditorModel(), null, new StringReader(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullReadable() {
    ImageEditorController c =
            new ImageEditorTextController(new BasicImageEditorModel(), new ImageEditorTextView(),
                    null);
  }
}