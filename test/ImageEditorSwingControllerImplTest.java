import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import commands.ImageEditorCommand;
import controller.ImageEditorSwingController;
import controller.ImageEditorSwingControllerImpl;
import gui.swingview.ImageEditorSwingView;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.BetterImage;
import model.image.Image;
import view.ImageEditorGUIView;

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

  @Before
  public void init() {
    viewLog = new StringBuilder();
    mockView = new ImageEditorGUIView() {
      @Override
      public void accept(ImageEditorSwingController controller) throws IllegalArgumentException {
        viewLog.append("controller accepted.").append(System.lineSeparator());
      }

      @Override
      public void setCurrentImage(String nameInEditor) throws IllegalArgumentException {
        viewLog.append("set name to ").append(nameInEditor).append(System.lineSeparator());
      }

      @Override
      public void addImage(String nameInEditor) throws IllegalArgumentException {
        viewLog.append("added image ").append(nameInEditor).append(System.lineSeparator());
      }

      @Override
      public void refreshImages() {
        viewLog.append("refreshed").append(System.lineSeparator());
      }

      @Override
      public void renderMessage(String msg) throws IOException {
        viewLog.append("rendered: ").append(msg).append(System.lineSeparator());
      }
    };

    mockModel = new ImageEditorModel() {
      @Override
      public Image getImage(String name) throws IllegalArgumentException {
        modelLog.append("got ").append(name).append(System.lineSeparator());
        return new BetterImage(new BufferedImage(1, 1, 2));
      }

      @Override
      public void addImage(String name, Image image) throws IllegalArgumentException {
        modelLog.append("added ").append(name).append(System.lineSeparator());
      }

      @Override
      public String execute(ImageEditorCommand cmd) throws IllegalArgumentException {
        modelLog.append("executed command").append(System.lineSeparator());
        return "executed command";
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
    assertEquals("", viewLog.toString());
  }

  @Test
  public void getImage() {
    ImageEditorModel model = new BasicImageEditorModel();
    model.addImage("a", new BetterImage(new BufferedImage(1, 1, 2)));
  }
}