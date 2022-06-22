package gui.swingview;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import controller.ImageEditorSwingController;
import gui.swingview.ImageSelectorBar.ImageSelectorButton;
import model.image.Image;
import utilities.ImageUtil;
import view.ImageEditorGUIView;

/**
 * A GUI view for the image editor, using Swing.
 */
public class ImageEditorSwingView implements ImageEditorGUIView {

  private final JFrame frame;
  private JComponent viewPort;
  private final JComponent selectorBar;
  private ImageEditorSwingController controller;
  private String curImageName;
  private final List<String> names;

  public ImageEditorSwingView() {
    this.frame = new JFrame("Image Editor");
    this.frame.setSize(750, 750);

    this.names = new ArrayList<>();
    this.curImageName = "No_Image_Loaded";

    this.selectorBar = new ImageSelectorBar(this.frame.getWidth() / 6, this.frame.getHeight());
    this.viewPort =
            new ImageViewPort(this.frame.getWidth() - 200, this.frame.getHeight() - 200, null);

    initializeBoard();
  }

  private void initializeBoard() {

    this.frame.setMinimumSize(new Dimension(400, 300));
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setLocationRelativeTo(null);
    this.frame.setLayout(null);


    this.frame.setMenuBar(new java.awt.MenuBar());

    this.selectorBar.setAlignmentX(Component.LEFT_ALIGNMENT);
    this.selectorBar.setAlignmentY(Component.CENTER_ALIGNMENT);

    this.viewPort.setLocation(this.frame.getWidth() / 5, this.frame.getHeight() / 5);

    this.frame.add(this.selectorBar);
    this.frame.add(this.viewPort);
    this.frame.setVisible(true);
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

    Image toDisplay = this.controller.getImage(nameInEditor);
    this.frame.remove(this.viewPort);

    this.viewPort = new ImageViewPort(this.frame.getWidth() - 200,
            this.frame.getHeight() - 200,
            ImageUtil.toBufferedImage(toDisplay));
    this.viewPort.setLocation(this.frame.getWidth() / 5, this.frame.getHeight() / 5);

    this.frame.add(this.viewPort);

    this.frame.setJMenuBar(new MenuBar(this.curImageName, this.controller, this));
    this.frame.revalidate();
  }

  @Override
  public void addImage(String nameInEditor) throws IllegalArgumentException {
    try {
      this.names.add(nameInEditor);
      this.setCurrentImage(nameInEditor);

      String displayName;
      try {
        displayName = nameInEditor.substring(
                nameInEditor.lastIndexOf(System.getProperty("line.separator") + 1,
                        nameInEditor.lastIndexOf(".")));
      } catch (IndexOutOfBoundsException e) {
        displayName = "UnknownName";
      }

      ImageSelectorButton newButton = new ImageSelectorButton(displayName,
              ImageUtil.toBufferedImage(this.controller.getImage(nameInEditor)), this,
              nameInEditor);

      this.selectorBar.add(newButton);
      this.frame.revalidate();
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
