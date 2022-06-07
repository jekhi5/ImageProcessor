package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;

/**
 * A command that brightens the original image by a given amount, storing the resultant image under
 * a new name. It should be given a positive integer value between 0 and 255, which is adds to each
 * color component of each pixel in the image. Brightening cannot increase values above 255.
 * <p>
 * Command syntax: {@code darken <amount> <original-image-name> <new-image-name>}.
 *
 * @author emery
 * @created 2022-06-07
 */
public class Brighten extends AbstractCommand {


  /**
   * Creates a new {@code Brighten}.
   *
   * @param in the {@link Scanner}
   * @throws IllegalStateException if {@code in} runs out of inputs before collecting
   *                               {@code numArgs} inputs.
   */
  public Brighten(Scanner in) throws IllegalStateException {
    super(in, 3);
  }

  @Override
  public String apply(ImageEditorModel model) {
    // get the image
    Image orig;
    try {
      orig = model.getImage(args[1]);
    } catch (IllegalArgumentException e) {
      return "Brighten failed: invalid image \"" + args[1] + "\".";
    }

    int amount;
    try {
      amount = Integer.parseInt(args[0]);
      if (amount < 0) {
        return "Brighten failed: amount must be positive, was: " + amount;
      }
    } catch (NumberFormatException e) {
      return e.getMessage();
    }

    applyToEachPixel(orig, p -> {
      PixelBuilder builder = new PixelImpl.PixelImplBuilder();
      builder.red(Math.min(p.getRed() + amount, 255));
      builder.green(Math.min(p.getGreen() + amount, 255));
      builder.blue(Math.min(p.getBlue() + amount, 255));
      return builder.build();
    });

    // put orig back into the ImageEditor as a new image.
    // This breaks if ImageEditor.getImageAt() returns an alias instead of a deep copy.
    model.addImage(args[2], orig);

    // Success!
    return "Brighten successful!";
  }
}
