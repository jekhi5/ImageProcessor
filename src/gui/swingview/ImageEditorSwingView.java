package gui.swingview;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import controller.ImageEditorSwingController;
import view.ImageEditorGUIView;
import view.ImageEditorView;

/**
 * A GUI view for the image editor, using Swing.
 */
public class ImageEditorSwingView implements ImageEditorGUIView {

  private final JFrame frame;
  private ImageEditorSwingController controller;
  private String curImageName;
  private final List<String> names;

  public ImageEditorSwingView() {
    this.frame = new JFrame("Image Editor");


    this.curImageName = "";
    frame.add(new MenuBar(curImageName, controller))
    this.names = new ArrayList<>();
  }

  /**
   * Creates a popup dialog box with the message.
   *
   * @param msg the message to render
   */
  @Override
  public void renderMessage(String msg) {
    new PopupDialog(msg);
  }

  @Override
  public void accept(ImageEditorSwingController controller) throws IllegalArgumentException {
    if (controller == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    this.controller = controller;
  }
}
