package gui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the place that the user selected image is displayed.
 */
public class ImageViewPort extends JPanel {


  /**
   * To construct a view port of the given width and height with the given image as the display.
   *
   * @param width     the width of the view port
   * @param height    the height of the view port
   * @param toDisplay the image to display in the view port
   */
  public ImageViewPort(int width, int height, java.awt.Image toDisplay) {
    super();
    java.awt.Image image;
    if (toDisplay == null) {
      try {
        File imgNotFoundImage = new File("res" + System.getProperty("file.separator") +
                "Image_Not_Found.png");
        image = ImageIO.read(imgNotFoundImage);
      } catch (IOException e) {
        throw new RuntimeException("The \"Image_Not_Found.png\" image was not found in the " +
                "res folder!");
      }
    } else {
      image = toDisplay;
    }

    this.setMaximumSize(new Dimension(width, height));

    JLabel imageLabel = new JLabel(new ImageIcon(image));
    imageLabel.setMaximumSize(new Dimension(width, height));

    imageLabel.setAlignmentX(CENTER_ALIGNMENT);
    imageLabel.setAlignmentY(CENTER_ALIGNMENT);

    this.add(imageLabel);
  }
}
