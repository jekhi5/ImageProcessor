package gui.swingview;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Represents the place that the user selected image is displayed.
 */
public class ImageViewPort extends JComponent {

  private int width;
  private int height;

  public ImageViewPort(int width, int height, java.awt.Image toDisplay) {
    super();
    this.width = width;
    this.height = height;
    this.setPreferredSize(new Dimension(this.width, this.height));

    JLabel imageLabel = new JLabel();
    imageLabel.setPreferredSize(new Dimension(this.width, this.height));
    imageLabel.setIcon(
            new ImageIcon(new ImageIcon(toDisplay).getImage().getScaledInstance(this.width,
                    this.height, Image.SCALE_DEFAULT)));
    imageLabel.setAlignmentX(CENTER_ALIGNMENT);
    imageLabel.setAlignmentY(CENTER_ALIGNMENT);
    this.add(imageLabel);
    this.setVisible(true);
  }
}
