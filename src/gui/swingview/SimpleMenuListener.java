package gui.swingview;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import controller.ImageEditorSwingController;
import model.image.Image;
import model.pixel.Pixel;

/**
 * An event listener for mouse events, specialized for one-click menu bar items. On click, sends the
 * given command to the controller.
 */
public class SimpleMenuListener implements ActionListener {
  protected final ImageEditorSwingController controller;

  /**
   * Creates a new SimpleMenuListener.
   *
   * @param controller the controller
   * @throws IllegalArgumentException if any argument is null
   */
  public SimpleMenuListener(ImageEditorSwingController controller) throws IllegalArgumentException {
    if (controller == null) {
      throw new IllegalArgumentException("Can't have null values");
    }
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().contains("hgram")) {
      try {
        generateHistogram(e.getActionCommand().split(" ")[1]);
      } catch (IllegalArgumentException ex) {
        new PopupDialog("Can't generate histogram - no image loaded.");
      }
    } else {
      controller.runCommand(e.getActionCommand());
    }
  }

  private void generateHistogram(String name) throws IllegalArgumentException {
    new Histogram(toBufferedImage(controller.getImage(name)));
  }

  private static BufferedImage toBufferedImage(Image image) {
    BufferedImage bi =
            new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int r = 0; r < image.getHeight(); r++) {
      for (int c = 0; c < image.getWidth(); c++) {
        Pixel p = image.getPixelAt(r, c);
        Color color = new Color(p.getRed(), p.getGreen(), p.getBlue());
        bi.setRGB(c, r, color.getRGB());
      }
    }
    return bi;
  }
}
