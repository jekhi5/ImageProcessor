package gui.swingview;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import view.ImageEditorGUIView;

/**
 * Represents a bar where the user can scroll through the loaded images and select the one they
 * would like to work on.
 */
public class ImageSelectorBar extends JPanel {

  private static final int BUTTON_WIDTH = 75;
  private static final int BUTTON_HEIGHT = 75;
  private final JScrollPane scrollPane;
  private static final java.awt.Image
          NOT_FOUND_IMAGE;

  static {
    try {
      NOT_FOUND_IMAGE =
              ImageIO.read(new File(
                              "res" + System.getProperty("file.separator") + "Image_Not_Found.png"))
                      .getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_DEFAULT);
    } catch (IOException e) {
      throw new RuntimeException("The \"Image_Not_Found.png\" image was not found in the " +
              "res folder!");
    }
  }

  /**
   * To construct this JComponent with 0 images.
   */
  public ImageSelectorBar(int width, int height) {
    super();
    this.scrollPane = new JScrollPane();
    this.scrollPane.setPreferredSize(new Dimension(width, height));
    this.scrollPane.setLayout(new ScrollPaneLayout());
    //this.scrollPane.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    this.setPreferredSize(new Dimension(width, height));
    super.add(this.scrollPane);
  }

  @Override
  public Component add(Component component) {
    this.scrollPane.add(component);
    return this;
  }

  /**
   * Represents a single image editor button.
   */
  public static class ImageSelectorButton extends JButton {

    /**
     * To construct a button with the given image as an icon and the given name as the text for the
     * button. The dimensions are the given width by the static height of all buttons.
     *
     * @param displayName the display name of the image
     * @param originalImage the originally sized image that will be resized for the button
     * @param view the view that this button will appear in
     * @param nameInEditor the name of this image in the editor
     */
    protected ImageSelectorButton(String displayName, java.awt.Image originalImage,
                                  ImageEditorGUIView view, String nameInEditor) {
      super();
      if (view == null) {
        throw new IllegalArgumentException("The view cannot be null!");
      }

      if (originalImage == null) {
        super.setIcon(new ImageIcon(NOT_FOUND_IMAGE));
      } else {
        java.awt.Image resizedImage = originalImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT
                , Image.SCALE_DEFAULT);
        super.setIcon(new ImageIcon(resizedImage));
      }

      super.setText(Objects.requireNonNullElse(displayName, "Image"));

      this.addActionListener(new ButtonListener(view));
      this.setActionCommand(nameInEditor);
      this.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
      this.setAlignmentX(Component.LEFT_ALIGNMENT);
      this.setHorizontalTextPosition(CENTER);
      this.setVerticalTextPosition(TOP);
    }
  }


}
