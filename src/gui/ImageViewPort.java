package gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

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
//      try {
//        String jarPath = ImageViewPort.class
//                .getProtectionDomain()
//                .getCodeSource()
//                .getLocation()
//                .toURI()
//                .getPath();
//
//        File imgNotFoundImage =
//                new File(jarPath + System.getProperty("file" +
//                        ".separator") +
//                        "Image_Not_Found.png");
//        System.out.println(imgNotFoundImage.getPath());
//        image = ImageIO.read(imgNotFoundImage);
        image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
//      } catch (IOException e) {
//        throw new RuntimeException("The \"Image_Not_Found.png\" image was not found in the " +
//                "res folder!");
//        throw new RuntimeException(e);
//      } catch (URISyntaxException e) {
//        throw new RuntimeException(e);
//      }
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
