package gui.swingview;


import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Represents a bar where the user can scroll through the loaded images and select the one they
 * would like to work on.
 */
public class ImageViewerBar extends JPanel {

  private static final int BUTTON_WIDTH = 75;
  private static final int BUTTON_HEIGHT = 75;

  /**
   * To construct this JComponent with 0 images.
   */
  public ImageViewerBar(int width, int height) {
    super();
    this.setPreferredSize(new Dimension(width, height));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
  }

  /**
   * Represents a single image editor button.
   */
  public static class ImageSelectorButton extends JButton {

    /**
     * To construct a button with the given image as an icon and the given name as the text for the
     * button. The dimensions are the given width by the static height of all buttons.
     *
     * @param name          the name of the image
     * @param resizedImage  the resized image to appear on the icon
     * @param originalImage the non-resized image that will appear in the
     *                      {@link gui.swingview.ImageViewPort}
     * @throws IllegalArgumentException if either the name or image is null
     */
    protected ImageSelectorButton(String name, java.awt.Image resizedImage)
            throws IllegalArgumentException {
      super(name, new ImageIcon(resizedImage));

      if (name == null) {
        throw new IllegalArgumentException("Name nor image can be null!");
      }

      //this.addActionListener(new ButtonListener());
      this.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
      this.setAlignmentX(Component.LEFT_ALIGNMENT);
      this.setHorizontalTextPosition(CENTER);
      this.setVerticalTextPosition(TOP);
    }
  }


}
