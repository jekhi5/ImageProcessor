package gui.swingview;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Represents the place that the user selected image is displayed.
 */
public class ImageViewPort extends JPanel {


  public ImageViewPort(int width, int height, java.awt.Image toDisplay) {
    super();
    java.awt.Image image = null;
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

    this.setPreferredSize(new Dimension(width, height));

    JLabel imageLabel = new JLabel(new ImageIcon(image));

    imageLabel.setAlignmentX(CENTER_ALIGNMENT);
    imageLabel.setAlignmentY(CENTER_ALIGNMENT);

    JScrollPane scrollPane = new JScrollPane(imageLabel);
    scrollPane.setPreferredSize(new Dimension(width, height));

    this.add(scrollPane);
  }
}
