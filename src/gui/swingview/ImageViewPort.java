package gui.swingview;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the place that the user selected image is displayed.
 */
public class ImageViewPort extends JPanel {
  public ImageViewPort(int width, int height, java.awt.Image toDisplay) {
    super();
    this.setPreferredSize(new Dimension(width, height));

    JLabel imageLabel = new JLabel(new ImageIcon(toDisplay));

    imageLabel.setPreferredSize(new Dimension(width, height));

    imageLabel.setAlignmentX(CENTER_ALIGNMENT);
    imageLabel.setAlignmentY(CENTER_ALIGNMENT);
    this.add(imageLabel);
  }
}
