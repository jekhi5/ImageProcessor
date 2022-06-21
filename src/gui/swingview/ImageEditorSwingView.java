package gui.swingview;

import javax.swing.JFrame;

import view.ImageEditorView;

/**
 * A GUI view for the image editor, using Swing.
 */
public class ImageEditorSwingView extends JFrame implements ImageEditorView {


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
