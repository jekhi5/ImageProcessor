import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import commands.ImageEditorCommand;
import gui.controller.ImageEditorSwingController;
import gui.controller.ImageEditorSwingControllerImpl;
import gui.view.ImageEditorSwingView;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.BetterImage;
import model.image.Image;
import gui.view.ImageEditorGUIView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the gui controller.
 * This is the controller features implementation, so it is really the only thing we can test.
 */
public class ImageEditorSwingControllerImplTest {

  ImageEditorGUIView mockView;
  StringBuilder viewLog;
  ImageEditorModel mockModel;
  StringBuilder modelLog;
  static String n = System.lineSeparator();

  @Before
  public void init() {
    viewLog = new StringBuilder();
    modelLog = new StringBuilder();
    mockView = new ImageEditorGUIView() {
      @Override
      public void accept(ImageEditorSwingController controller) throws IllegalArgumentException {
        viewLog.append("controller accepted.").append(n);
      }

      @Override
      public void setCurrentImage(String nameInEditor) throws IllegalArgumentException {
        viewLog.append("set name to ").append(nameInEditor).append(n);
      }

      @Override
      public void addImage(String nameInEditor) throws IllegalArgumentException {
        viewLog.append("added image ").append(nameInEditor).append(n);
      }

      @Override
      public void refreshImages() {
        viewLog.append("refreshed").append(n);
      }

      @Override
      public void renderMessage(String msg) throws IOException {
        viewLog.append("rendered: ").append(msg).append(n);
      }
    };

    mockModel = new ImageEditorModel() {
      final ImageEditorModel del = new BasicImageEditorModel();
      @Override
      public Image getImage(String name) throws IllegalArgumentException {
        modelLog.append("got ").append(name).append(n);
        return del.getImage(name);
      }

      @Override
      public void addImage(String name, Image image) throws IllegalArgumentException {
        modelLog.append("added ").append(name).append(n);
        del.addImage(name, image);
      }

      @Override
      public String execute(ImageEditorCommand cmd) throws IllegalArgumentException {
        String str = del.execute(cmd);
        modelLog.append("executed command: ").append(str).append(n);
        return str;
      }
    };
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    new ImageEditorSwingControllerImpl(null, mockView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullView() {
    new ImageEditorSwingControllerImpl(new BasicImageEditorModel(), null);
  }

  @Test
  public void runCommand() {
    ImageEditorSwingController c = new ImageEditorSwingControllerImpl(new BasicImageEditorModel(), mockView);
    c.runCommand("load bungus.png b");
    assertEquals("controller accepted." + n +
            "rendered: Load failed: Invalid path: Can't read input file!" + n, viewLog.toString());
  }

  @Test
  public void getImage() {
    mockModel.addImage("a", new BetterImage(new BufferedImage(1, 1, 2)));
    Image img = new ImageEditorSwingControllerImpl(mockModel, mockView).getImage("a");
    assertEquals(new BetterImage(new BufferedImage(1, 1, 2)), img);
    assertEquals("added a" +n + "got a" + n, modelLog.toString());
  }
}