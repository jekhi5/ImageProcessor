package commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import model.ImageEditorModel;
import model.image.BetterImage;
import model.image.Image;
import model.pixel.Pixel;

/**
 * Represents a resizing command that takes four arguments and maps an image of a certain size and
 * converts it into one of a different size.
 * {@code downsize <new-width> <new-height> <original-image-name> <new-image-name>}
 */
public class Downsize extends AbstractCommand {

  private static final int RED = 1;
  private static final int GREEN = 2;
  private static final int BLUE = 3;
  private static final int ALPHA = 4;

  private Image originalImage;


  /**
   * Creates a new Downsizing command which takes in 4 arguments
   *
   * @param in the {@link java.util.Scanner}
   * @throws IllegalArgumentException if {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting 4 inputs.
   */
  public Downsize(Scanner in)
          throws IllegalStateException, IllegalArgumentException {
    super(in, 4);
  }

  @Override
  public String apply(ImageEditorModel model) {
    checkNullModel(model);

    Image originalImage;
    try {
      originalImage = model.getImage(args[2]);

    } catch (IllegalArgumentException e) {
      return "Downsize failed: invalid image \"" + args[2] + "\".";
    }

    this.originalImage = originalImage;

    BufferedImage newBufferedImage;
    try {
      int newWidth = Integer.parseInt(args[0]);
      int newHeight = Integer.parseInt(args[1]);
      if (newWidth > originalImage.getWidth() || newHeight > originalImage.getHeight()) {
        return "Downsize failed: the new width and height must be less than or equal to the " +
                "current images size. Current width: " + originalImage.getWidth() +
                ". Current height: " + originalImage.getHeight();
      }

      newBufferedImage =
              new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    } catch (NumberFormatException e) {
      return "Downsize failed: the new width and height of the image must both be positive " +
              "integers. Given width: " + args[0] + ". Given height: " + args[1];
    }


    int newWidth = newBufferedImage.getWidth();
    int newHeight = newBufferedImage.getHeight();
    int oldWidth = originalImage.getWidth();
    int oldHeight = originalImage.getHeight();

    for (int y = 0; y < newHeight; y += 1) {
      for (int x = 0; x < newWidth; x += 1) {

        double oldX = (((double) x / newWidth) * oldWidth);
        double oldY = (((double) y / newHeight) * oldHeight);
        Color newRGB;
        try {
          newRGB =
                  new Color(this.getNewColor(oldX, oldY, RED), this.getNewColor(oldX, oldY, GREEN),
                          this.getNewColor(oldX, oldY, BLUE), this.getNewColor(oldX, oldY, ALPHA));
          newBufferedImage.setRGB(x, y, newRGB.getRGB());
        } catch (IllegalArgumentException e) {
          return "Downsize failed: Failed to parse coordinates: (" + oldX + ", " + oldY + ").";
        }
      }
    }

    Image newImage = new BetterImage(newBufferedImage);

    model.addImage(args[3], newImage);

    return "Downsize successful!";

  }

  private int getNewColor(double x, double y, int component) {

    double xCeil = Math.ceil(y);
    double yCeil = Math.ceil(x);

    if (xCeil == y) {
      xCeil += 1;
    }

    if (yCeil == x) {
      yCeil += 1;
    }

    Pixel A = this.originalImage.getPixelAt((int) Math.floor(y), (int) Math.floor(x));
    Pixel B = this.originalImage.getPixelAt((int) xCeil, (int) Math.floor(x));
    Pixel C = this.originalImage.getPixelAt((int) Math.floor(y), (int) yCeil);
    Pixel D = this.originalImage.getPixelAt((int) xCeil, (int) yCeil);

    int a;
    int b;
    int c;
    int d;

    switch (component) {
      case RED:
        try {
          a = A.getRed();
          b = B.getRed();
          c = C.getRed();
          d = D.getRed();
          break;
        } catch (IllegalArgumentException e) {
          return -1;
        }
      case GREEN:
        try {
          a = A.getGreen();
          b = B.getGreen();
          c = C.getGreen();
          d = D.getGreen();
          break;
        } catch (IllegalArgumentException e) {
          return -1;
        }
      case BLUE:
        try {
          a = A.getBlue();
          b = B.getBlue();
          c = C.getBlue();
          d = D.getBlue();
          break;
        } catch (IllegalArgumentException e) {
          return -1;
        }
      case ALPHA:
        try {
          a = A.getAlpha();
          b = B.getAlpha();
          c = C.getAlpha();
          d = D.getAlpha();
          break;
        } catch (IllegalArgumentException e) {
          return -1;
        }
      default:
        throw new IllegalArgumentException("Invalid downsize component: " + component + ". Must " +
                "be one of 1->RED, 2->GREEN, 3->BLUE, or 4->ALPHA.");
    }

    int m = (int) ((b * (y - Math.floor(y))) + (a * (xCeil - y)));
    int n = (int) ((d * (y - Math.floor(y))) + (c * (xCeil - y)));

    return (int) ((n * (x - Math.floor(x))) + (m * (yCeil - x)));
  }
}
