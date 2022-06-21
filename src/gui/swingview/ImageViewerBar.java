package gui.swingview;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import model.image.Image;
import model.pixel.Pixel;

/**
 * Represents a bar where the user can scroll through the loaded images and select the one they
 * would like to work on.
 */
public class ImageViewerBar extends JComponent {

  private static final int BUTTON_WIDTH = 75;
  private static final int BUTTON_HEIGHT = 75;

  /**
   * To construct this JComponent with 0 images.
   */
  public ImageViewerBar(int width, int height) {
    super();
    this.setPreferredSize(new Dimension(width, height));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
  }

  //TODO: Deal with this

  /**
   * To get an image selector button with the given name and image
   *
   * @param name the name of the image to use on the button
   * @param img  the {@code Image} to add
   * @throws java.lang.IllegalArgumentException if either argument is null
   */
  public static Component getImageSelectorButton(String name, Image img)
          throws IllegalArgumentException {
    ImageIcon icon = new ImageIcon(toBufferedImage(img));
    java.awt.Image resizedImage = icon.getImage()
            .getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, java.awt.Image.SCALE_DEFAULT);
    return new ImageSelectorButton(name, resizedImage, toBufferedImage(img));
  }

  private static BufferedImage toBufferedImage(Image img) {
    BufferedImage result =
            new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

    for (int row = 0; row < img.getHeight(); row += 1) {
      for (int col = 0; col < img.getWidth(); col += 1) {
        Pixel curPixel = img.getPixelAt(row, col);
        Color rgb = new Color(curPixel.getRed(), curPixel.getGreen(), curPixel.getBlue(),
                curPixel.getAlpha());
        result.setRGB(col, row, rgb.getRGB());
      }
    }

    return result;
  }

  /**
   * Represents a single image editor button.
   */
  public static class ImageSelectorButton extends JButton {

    ImageIcon bigIcon;

    /**
     * To construct a button with the given image as an icon and the given name as the text for the
     * button. The dimensions are the given width by the static height of all buttons.
     *
     * @param name          the name of the image
     * @param resizedImage  the resized image to appear on the icon
     * @param originalImage the non-resized image that will appear in the
     *                      {@link gui.swingview.ImageViewPort}
     * @throws IllegalArgumentException if either the name or image is null
     */
    protected ImageSelectorButton(String name, java.awt.Image resizedImage,
                                  java.awt.Image originalImage) throws IllegalArgumentException {
      super(name, new ImageIcon(resizedImage));

      if (name == null || originalImage == null) {
        throw new IllegalArgumentException("Name nor image can be null!");
      }

      this.bigIcon = new ImageIcon(originalImage);

      //this.addActionListener(new ButtonListener());
      this.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
      this.setAlignmentX(Component.LEFT_ALIGNMENT);
      this.setHorizontalTextPosition(CENTER);
      this.setVerticalTextPosition(TOP);
    }
  }


}
