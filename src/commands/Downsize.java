package commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import model.ImageEditorModel;
import model.image.BetterImage;
import model.image.Image;
import model.pixel.Pixel;

/**
 * Represents a downsizing command that takes four arguments and maps an image of a certain size and
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


    Image originalImage = model.getImage(args[2]);

    if (originalImage == null) {
      return "Blur failed: invalid image \"" + args[2] + "\".";
    }

    this.originalImage = originalImage;


    Image newImage;
    try {
      newImage =
              new BetterImage(new BufferedImage(Integer.parseInt(args[0]),
                      Integer.parseInt(args[1]),
                      BufferedImage.TYPE_INT_ARGB));
    } catch (NumberFormatException e) {
      return "Downsize failed: the new width and height of the image must both be positive " +
              "integers. Given width: " + args[0] + ". Given height: " + args[1];
    }


    int newWidth = newImage.getWidth();
    int newHeight = newImage.getHeight();
    int oldWidth = originalImage.getWidth();
    int oldHeight = originalImage.getHeight();

    for (int row = 0; row < newHeight; row += 1) {
      for (int col = 0; col < newWidth; col += 1) {
        float newX = col;
        float newY = row;

        float oldX = (newX / newWidth) * oldWidth;
        float oldY = (newY / newHeight) * oldHeight;

        Color newRGB =
                new Color(this.getNewColor(oldX, oldY, RED), this.getNewColor(oldX, oldY, GREEN),
                        this.getNewColor(oldX, oldY, BLUE), this.getNewColor(oldX, oldY, ALPHA));

        
      }
    }

  }

  private int getNewColor(float x, float y, int component) {
    Pixel A = this.originalImage.getPixelAt((int) Math.floor(x), (int) Math.floor(y));
    Pixel B = this.originalImage.getPixelAt((int) Math.ceil(x), (int) Math.floor(y));
    Pixel C = this.originalImage.getPixelAt((int) Math.floor(x), (int) Math.ceil(y));
    Pixel D = this.originalImage.getPixelAt((int) Math.ceil(x), (int) Math.ceil(y));

    int a;
    int b;
    int c;
    int d;

    switch (component) {
      case RED:
        a = A.getRed();
        b = B.getRed();
        c = C.getRed();
        d = D.getRed();
        break;
      case GREEN:
        a = A.getGreen();
        b = B.getGreen();
        c = C.getGreen();
        d = D.getGreen();
        break;
      case BLUE:
        a = A.getBlue();
        b = B.getBlue();
        c = C.getBlue();
        d = D.getBlue();
        break;
      case ALPHA:
        a = A.getAlpha();
        b = B.getAlpha();
        c = C.getAlpha();
        d = D.getAlpha();
        break;
      default:
        throw new IllegalArgumentException("Invalid downsize component: " + component + ". Must " +
                "be one of 1->RED, 2->GREEN, 3->BLUE, or 4->ALPHA.");
    }

    int m = (int) ((b * (x - Math.floor(x)) + (a * (Math.ceil(x) - x))));
    int n = (int) ((d * (x - Math.floor(x)) + (c * (Math.ceil(x) - x))));

    return (int) ((n * (y - Math.floor(y))) + (m * (Math.ceil(y) - y)));
  }
}
