package gui.swingview;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Represents the place that the user selected image is displayed.
 */
public class ImageViewPort extends JPanel {

  private final java.awt.Image image;
  private int width;
  private int height;

  private final JScrollPane scrollPane;


  public ImageViewPort(int width, int height, java.awt.Image toDisplay) {
    super();
    this.setPreferredSize(new Dimension(width, height));

    this.image = toDisplay;
    this.width = width;
    this.height = height;

    JLabel imageLabel = new JLabel(new ImageIcon(this.image));

    imageLabel.setAlignmentX(CENTER_ALIGNMENT);
    imageLabel.setAlignmentY(CENTER_ALIGNMENT);

    this.scrollPane = new JScrollPane(imageLabel);
    this.scrollPane.setPreferredSize(new Dimension(this.width, this.height));

    this.add(scrollPane);
  }
}
