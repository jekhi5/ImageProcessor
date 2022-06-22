package gui.swingview;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

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
  private JComponent selectorBar;
  private final List<String> names;
  private JComponent viewPort;
  private ImageEditorSwingController controller;
  private String curImageName;
  private final GridBagLayout layout;

  public ImageEditorSwingView() {
    this.frame = new JFrame("Image Editor");
    this.frame.setSize(1000, 1000);
    this.layout = new GridBagLayout();


    this.names = new ArrayList<>();
    this.curImageName = "No_Image_Loaded";

    this.viewPort = new JScrollPane(new ImageViewPort(this.frame.getWidth() - 200,
            this.frame.getHeight(), null));
    this.selectorBar = new JScrollPane(new ImageSelectorBar(ImageSelectorBar.getButtonWidth() + 15,
            this.frame.getHeight()));

    layout.addLayoutComponent(this.selectorBar,
            new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    layout.addLayoutComponent(this.viewPort,
            new GridBagConstraints(1, 0, 1, 1, 10, 1, GridBagConstraints.PAGE_START,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.frame.setLayout(layout);
    initializeBoard();
    this.frame.setVisible(true);
  }

  private void initializeBoard() {

    this.frame.setMinimumSize(new Dimension(400, 300));
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setLocationRelativeTo(null);

    this.frame.setMenuBar(new java.awt.MenuBar());
    this.frame.getContentPane().add(this.selectorBar);
    this.frame.getContentPane().add(this.viewPort);
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

    this.viewPort = new JScrollPane(new ImageViewPort(this.frame.getWidth() - 200,
            this.frame.getHeight() - 200,
            ImageUtil.toBufferedImage(toDisplay)));
    this.viewPort.setLocation(this.frame.getWidth() / 5, this.frame.getHeight() / 5);

    layout.addLayoutComponent(this.viewPort,
            new GridBagConstraints(1, 0, 1, 1, 10, 1, GridBagConstraints.PAGE_START,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.frame.getContentPane().add(this.viewPort);

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

      JComponent newButton = new ImageSelectorButton(displayName,
              ImageUtil.toBufferedImage(this.controller.getImage(nameInEditor)), this,
              nameInEditor);

      JComponent newSelectorBar = new ImageSelectorBar(ImageSelectorBar.getButtonWidth() + 15,
              this.frame.getHeight() - 20);

      newSelectorBar.add(newButton);

      for (Component comp : this.selectorBar.getComponents()) {
        newSelectorBar.add(comp);
      }
      this.frame.remove(this.selectorBar);

      this.selectorBar = new JScrollPane(newSelectorBar);
      this.selectorBar.setAlignmentX(Component.LEFT_ALIGNMENT);
      this.selectorBar.setAlignmentY(Component.CENTER_ALIGNMENT);

      layout.addLayoutComponent(this.selectorBar,
              new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START,
                      GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      this.frame.add(this.selectorBar);
      this.frame.revalidate();
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
