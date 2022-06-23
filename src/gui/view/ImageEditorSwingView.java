package gui.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import gui.Histogram;
import gui.HistogramFactory;
import gui.ImageSelectorBar;
import gui.ImageSelectorBar.ImageSelectorButton;
import gui.ImageViewPort;
import gui.MenuBar;
import gui.PopupDialog;
import gui.controller.ImageEditorSwingController;
import utilities.ImageUtil;

/**
 * A GUI view for the image editor, using Swing.
 */
public class ImageEditorSwingView implements ImageEditorGUIView {

  private final JFrame frame;
  private final List<String> names;
  private final GridBagLayout layout;
  private JComponent selectorBar;
  private JComponent viewPort;
  private ImageEditorSwingController controller;
  private String curImageName;

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

    this.frame.setJMenuBar(new MenuBar(this.curImageName, this.controller, this));

    this.refreshViewPort();
  }

  @Override
  public void addImage(String nameInEditor) throws IllegalArgumentException {
    try {
      this.names.add(0, nameInEditor);
      this.setCurrentImage(nameInEditor);

      this.refreshButtons();

    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public void refreshImages() {
    this.refreshButtons();
    this.refreshViewPort();
    HistogramFactory.createHistogram(ImageUtil.toBufferedImage(controller.getImage(curImageName)));
  }

  private void refreshViewPort() {
    this.frame.remove(this.viewPort);

    this.viewPort = new JScrollPane(
            new ImageViewPort(this.viewPort.getWidth(), this.viewPort.getHeight(),
                    ImageUtil.toBufferedImage(this.controller.getImage(this.curImageName))));

    layout.addLayoutComponent(this.viewPort,
            new GridBagConstraints(1, 0, 1, 1, 10, 1, GridBagConstraints.PAGE_START,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    this.frame.add(this.viewPort);
    this.frame.revalidate();
  }

  private void refreshButtons() {
    this.frame.remove(this.selectorBar);

    JComponent newSelectorBar =
            new ImageSelectorBar(this.selectorBar.getWidth(), this.selectorBar.getHeight());

    for (String nameInEditor : this.names) {
      java.awt.Image newImage = ImageUtil.toBufferedImage(this.controller.getImage(nameInEditor));
      JComponent newButton =
              new ImageSelectorButton(nameInEditor.substring(
                      nameInEditor.lastIndexOf(System.getProperty("file.separator")) + 1), newImage,
                      this, nameInEditor);
      newSelectorBar.add(newButton);
    }

    this.selectorBar = new JScrollPane(newSelectorBar);

    layout.addLayoutComponent(this.selectorBar,
            new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.frame.add(this.selectorBar);
    this.frame.revalidate();
  }
}
