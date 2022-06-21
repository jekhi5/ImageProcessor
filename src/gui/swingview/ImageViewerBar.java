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
    this.setPreferredSize(new Dimension(width, height));
  }

  /**
   * To add an image button to this component.
   *
   * @param name the name of the image in the editor
   * @param img  the {@code Image} to add
   * @throws java.lang.IllegalArgumentException if either argument is null
   */
  public void addImageToBar(String name, Image img) throws IllegalArgumentException {
    ImageIcon icon = new ImageIcon(toBufferedImage(img));
    java.awt.Image resizedImage = icon.getImage().getScaledInstance(this.width,
            ImageSelectorButton.HEIGHT_OF_BUTTON, java.awt.Image.SCALE_DEFAULT);
    this.buttons.add(new ImageSelectorButton(name, resizedImage, this.width));
  }

  public static BufferedImage toBufferedImage(Image img) {
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

  public void render(boolean isCollapsed) {
    int width;
    if (isCollapsed) {
      width = 0;
    } else {
      width = this.width;
    }

    //this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    for (ImageSelectorButton button : this.buttons) {
      button.setDimensions(width);
      this.add(button);
      button.setVisible(true);
    }

    this.setVisible(true);
  }

  /**
   * Represents a single image editor button.
   */
  public static class ImageSelectorButton extends JButton {

    public static final int HEIGHT_OF_BUTTON = 20;
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
      this.setPreferredSize(this.dimensions);
    }

    /**
     * To set the dimensions of this button.
     *
     * @param width the new width of the button (the height is locked)
     */
    protected void setDimensions(int width) {
      this.dimensions = new Dimension(width, HEIGHT_OF_BUTTON);
      this.setPreferredSize(this.dimensions);
    }
  }


}
