package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;

public abstract class AdjustLightCommand extends AbstractCommand {

  /**
   * Creates a new AdjustLightCommand command with the given scanner.
   *
   * @param in      the {@link Scanner}
   * @throws IllegalArgumentException if {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public AdjustLightCommand(Scanner in)
          throws IllegalStateException, IllegalArgumentException {
    super(in, 3);
  }

  public String apply(ImageEditorModel model) {
    checkNullModel(model);

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
      return "Brighten failed: amount must be a positive integer!";
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
