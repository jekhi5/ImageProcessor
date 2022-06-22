package gui.swingview;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * A bar graph visualization of the colors in an image.
 */
public class Histogram extends JFrame {

  public Histogram(BufferedImage image) {
    super("ImageProcessor - Histogram");
    // create boxes for each color channel that we care about
    int[] red = new int[256];
    int[] green = new int[256];
    int[] blue = new int[256];

    // loop through the image to populate the boxes
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Color c = new Color(image.getRGB(x, y));
        // each box should be the number of pixels with that color equal to that index
        red[c.getRed()]++;
        green[c.getGreen()]++;
        blue[c.getBlue()]++;
      }
    }

    JPanel redGraph = new JPanel();
    redGraph.setAlignmentY(Component.TOP_ALIGNMENT);
    JPanel greenGraph = new JPanel();
    JPanel blueGraph = new JPanel();
    redGraph.setLayout(new BoxLayout(redGraph, BoxLayout.X_AXIS));
    for (int i = 0; i <= 255; i++) {
      JPanel redBar = new JPanel();
      JPanel greenBar = new JPanel();
      JPanel blueBar = new JPanel();

      redBar.setAlignmentX(Component.LEFT_ALIGNMENT);
      redBar.setAlignmentY(Component.CENTER_ALIGNMENT);
      redBar.setMaximumSize(new Dimension(-5, red[i]));
      redBar.setBackground(new Color(255, 0, 0));
      redGraph.add(redBar);

      greenBar.setAlignmentX(Component.LEFT_ALIGNMENT);
      greenBar.setAlignmentY(Component.CENTER_ALIGNMENT);
      greenBar.setMaximumSize(new Dimension(0, green[i]));
      greenBar.setBackground(Color.GREEN);
      greenGraph.add(greenBar);

      blueBar.setAlignmentX(Component.LEFT_ALIGNMENT);
      blueBar.setAlignmentY(Component.CENTER_ALIGNMENT);
      blueBar.setMaximumSize(new Dimension(0, blue[i]));
      blueBar.setBackground(Color.BLUE);
      blueGraph.add(blueBar);
    }
    this.getContentPane().add(redGraph);
//    this.getContentPane().add(greenGraph);
//    this.getContentPane().add(blueGraph);

    this.setVisible(true);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//    this.pack();
    this.setLocationRelativeTo(null);
  }
}
