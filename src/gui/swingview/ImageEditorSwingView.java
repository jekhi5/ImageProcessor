package gui.swingview;

import javax.swing.JFrame;

import controller.ImageEditorSwingController;
import view.ImageEditorView;

/**
 * A GUI view for the image editor, using Swing.
 */
public class ImageEditorSwingView implements ImageEditorView {

  private final JFrame frame;
  private ImageEditorSwingController controller;

  public ImageEditorSwingView() {
    this.frame = new JFrame("Image Editor");
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


}
