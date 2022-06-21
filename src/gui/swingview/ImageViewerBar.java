package gui.swingview;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import model.image.Image;
import model.pixel.Pixel;

/**
 * Represents a bar where the user can scroll through the loaded images and select the one they
 * would like to work on.
 */
public class ImageViewerBar extends JComponent implements ImageEditorSwingFeature {

  private final List<ImageSelectorButton> buttons;
  private int width;
  private int height;

  /**
   * To construct this JComponent with 0 images.
   */
  public ImageViewerBar(int width, int height) {
    super();
    this.buttons = new ArrayList<>();
    this.width = width;
    this.height = height;
  }

  /**
   * To add an image button to this component.
   *
   * @param name the name of the image in the editor
   * @param img  the {@code Image} to add
   * @throws java.lang.IllegalArgumentException if either argument is null
   */
  public void addImageToBar(String name, Image img) throws IllegalArgumentException {
    this.buttons.add(new ImageSelectorButton(name, this.toBufferedImage(img), this.width));
  }

  private BufferedImage toBufferedImage(Image img) {
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

  public void display(boolean isCollapsed) {
    int height = this.height;
    int width;
    if (isCollapsed) {
      width = 0;
    } else {
      width = this.width;
    }

    for (ImageSelectorButton button : this.buttons) {
      this.add(button);
    }


  }

  /**
   * Represents a single image editor button.
   */
  private static class ImageSelectorButton extends JButton {

    private static final int HEIGHT_OF_BUTTON = 200;
    private Dimension dimensions;

    /**
     * To construct a button with the given image as an icon and the given name as the text for the
     * button. The dimensions are the given width by the static height of all buttons.
     *
     * @param name  the name of the image
     * @param img   the image to be on the button
     * @param width the width of the button
     * @throws IllegalArgumentException if either the name or image is null
     */
    protected ImageSelectorButton(String name, java.awt.Image img, int width)
            throws IllegalArgumentException {
      super(name, new ImageIcon(img)); // Handles null catching for image

      if (name == null) {
        throw new IllegalArgumentException("Name cannot be null!");
      }

      this.addActionListener(new ButtonListener());
      this.dimensions = new Dimension(width, HEIGHT_OF_BUTTON);
      this.setPreferredSize(new Dimension(width, HEIGHT_OF_BUTTON));
    }

    /**
     * To set the dimensions of this button.
     *
     * @param dimensions the dimensions that this button will be
     */
    protected void setDimensions(Dimension dimensions) {
      this.dimensions = dimensions;
    }
  }


}
