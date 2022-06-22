package gui.swingview;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * A bar graph visualization of the colors in an image.
 */
public class Histogram extends JFrame {

  /**
   * Creates a new Histogram from the image.
   * @param image the image
   */
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

    HistogramBox box = new HistogramBox(red, green, blue, new int[256]);
    this.add(box);

    this.setMinimumSize(new Dimension(570, 325));
    this.setResizable(false);
    this.setVisible(true);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.pack();
    this.setLocationRelativeTo(null);
  }

  /**
   * The main content panel which contains the histogram.
   * This is what the graphics will act on.
   */
  private class HistogramBox extends JComponent {
    int[] red;
    int[] green;
    int[] blue;
    int[] alpha;

    /**
     * Creates a new HistogramBox.
     * @param red red
     * @param green green
     * @param blue blue
     * @param alpha alpha
     */
    public HistogramBox(int[] red, int[] green, int[] blue, int[] alpha) {
      super();
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      this.setMinimumSize(new Dimension(610, 325));
    }

    @Override
    public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      super.paintComponent(g);

      int maxValue = findMax(red, green, blue);
      // x/max = y/256
      // 256x = max*y
      //y = 256x / max


      int x = 15;
      for(int i = 0; i < 255; i++) {
        g2.setColor(Color.RED);
        g2.drawLine(x, 265 - red[i] * 256 / maxValue, x + 2, 265 - red[i + 1] * 256 / maxValue);

        g2.setColor(Color.GREEN);
        g2.drawLine(x, 265 - green[i] * 256 / maxValue, x + 2, 265 - green[i + 1] * 256 / maxValue);

        g2.setColor(Color.BLUE);
        g2.drawLine(x, 265 - blue[i] * 256 / maxValue, x + 2, 265 - blue[i + 1] * 256 / maxValue);
        x += 2;
      }


      g2.setColor(Color.BLACK);
      g2.drawLine(15, 10, 15, 266);
      g2.drawLine(15, 266, 530, 266);
      g2.drawString("0", 5, 276);
      g2.drawString("255", 520, 276);
      g2.drawString(Integer.toString(maxValue), 5, 10);
    }

    private int findMax(int[]... arrs) {
      int max = 0;
      for(int i = 0; i < 256; i++) {
        for(int[] arr : arrs) {
          max = Math.max(arr[i], max);
        }
      }
      return max;
    }
  }
}
