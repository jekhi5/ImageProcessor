package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;

/**
 * A command that darkens the original image by a given amount, storing the resultant image under a
 * new name. It should be given a positive integer value between 0 and 255, which is subtracts from
 * each color component of each pixel in the image. Darkening cannot reduce values below 0.
 * <p>
 * Command syntax: {@code darken <amount> <original-image-name> <new-image-name>}.
 *
 * @author emery
 * @created 2022-06-07
 */
public class Darken extends AbstractCommand {


  /**
   * Creates a new {@code Darken}.
   *
   * @param in the {@link Scanner}
   * @throws IllegalStateException if {@code in} runs out of inputs before collecting
   *                               {@code numArgs} inputs.
   */
  public Darken(Scanner in) throws IllegalStateException {
    super(in, 3);
  }

  @Override
  public String apply(ImageEditorModel model) {
    // get the image
    Image orig = null;
    try {
      orig = model.getImageAt(args[1]);
    } catch (IllegalArgumentException e) {
      return "Darken failed: invalid image \"" + args[1] + "\".";
    }

    int amount;
    try {
      amount = Integer.parseInt(args[0]);
      if (amount < 0) {
        return "Darken failed: amount must be positive, was: " + amount;
      }
    } catch (NumberFormatException e) {
      return e.getMessage();
    }

    applyToEachPixel(orig, p -> {
      PixelBuilder builder = new PixelImpl.PixelImplBuilder();
      builder.red(Math.max(p.getRed() - amount, 0));
      builder.green(Math.max(p.getGreen() - amount, 0));
      builder.blue(Math.max(p.getBlue() - amount, 0));
      return builder.build();
    });

    // Success!
    return "Darken successful!";
  }
}
