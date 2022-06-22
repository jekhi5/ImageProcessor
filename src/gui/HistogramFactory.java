package gui;

import java.awt.image.BufferedImage;

/**
 * A class to allow easy generation of histograms, and closing un-needed histograms.
 */
public class HistogramFactory {
  private static Histogram histogram;

  /**
   * Creates a new histogram from the given image, and closes the existing one.
   *
   * @param image the image
   */
  public static void createHistogram(BufferedImage image) {
    if (histogram != null) {
      histogram.dispose();
    }
    histogram = new Histogram(image);
  }
}
