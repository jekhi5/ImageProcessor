package model.v2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelImpl;

/**
 * An improved implementation of {@link Image}. Uses a {@link java.awt.image.BufferedImage}
 * instead of a 2D list, and can work with more file types than just .ppm.
 *
 */
public class BetterImage implements Image {
  private final BufferedImage image;

  /**
   * Creates a new {@code BetterImage} from the given {@link BufferedImage};
   * @param image the {@link BufferedImage}
   * @throws IllegalArgumentException if {@code image} is null.
   */
  public BetterImage(BufferedImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image can't be null.");
    }
    this.image = image;
  }
  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (Math.min(row, col) < 0 || row >= getHeight() || col >= getWidth()) {
      throw new IllegalArgumentException("Invalid position: " + row + ", " + col);
    }
    int argb = image.getRGB(col, row);
    // we use java.awt.Color to do the bitwise math for us (:
    Color c = new Color(argb, true);
    int blue = c.getBlue();
    int green = c.getGreen();
    int red = c.getRed();
    int alpha = c.getAlpha();
    return new PixelImpl.PixelImplBuilder()
            .red(red)
            .blue(blue)
            .green(green)
            .alpha(alpha)
            .build();
  }

  @Override
  public Pixel setPixelAt(int row, int col, Pixel newPixel) throws IllegalArgumentException {
    if (Math.min(row, col) < 0 || row >= getHeight() || col >= getWidth()) {
      throw new IllegalArgumentException("Invalid position: " + row + ", " + col);
    }
    Pixel p = getPixelAt(row, col);
    Color c = new Color(newPixel.getRed(), newPixel.getGreen(), newPixel.getBlue(), newPixel.getAlpha());
    image.setRGB(col, row, c.getRGB());
    return p;
  }

  @Override
  public int getWidth() {
    return image.getWidth();
  }

  @Override
  public int getHeight() {
    return image.getHeight();
  }

  @Override
  public Image getCopy() {
    // how to deep copy a BufferedImage from hyper-neutrino on StackOverflow:
    // https://stackoverflow.com/a/31226502
    BufferedImage img = new BufferedImage(getWidth(), getHeight(), image.getType());
    for (int r = 0; r < image.getHeight(); r++) {
      for(int c = 0; c < image.getWidth(); c++) {
        img.setRGB(c, r, image.getRGB(c, r));
      }
    }
    return new BetterImage(img);
  }

  @Override
  public String toSavableText() {
    File outputfile = new File("testOut" + System.getProperty("file.separator") + "new.png");
    try {
      ImageIO.write(image, "png", outputfile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return "placeholder";
  }

  @Override
  public Iterator<Pixel> iterator() {
    return null;
  }
}
