package gui.swingview;

import java.awt.*;
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
    this.names = new ArrayList<>();
    this.curImageName = "No_Image_Loaded";
    frame.setMinimumSize(new Dimension(400, 300));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
//    frame.setVisible(true);
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
    frame.setJMenuBar(new MenuBar(curImageName, controller, this));
    frame.setVisible(true);
  }

  @Override
  public void setCurrentImage(String nameInEditor) throws IllegalArgumentException {
    if (nameInEditor == null) {
      throw new IllegalArgumentException("Name can't be null");
    }
    if (!this.names.contains(nameInEditor)) {
      throw new IllegalArgumentException("Invalid name: " + nameInEditor);
    }
    this.curImageName = nameInEditor;
    this.frame.setJMenuBar(new MenuBar(this.curImageName, this.controller, this));
    this.frame.revalidate();
  }

  @Override
  public void addImage(String nameInEditor) throws IllegalArgumentException {
    try {
      this.controller.getImage(nameInEditor);
      this.names.add(nameInEditor);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
