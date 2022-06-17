package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;

/**
 * Represents a command that modifies the lighting of an image like in {@link Brighten} and
 * {@link Darken}.
 */
public abstract class AdjustLightCommand extends AbstractCommand {


  /**
   * Creates a new lighting based command with the given number of arguments.
   *
   * @param in      the {@link Scanner}
   * @param numArgs the number of arguments
   * @throws IllegalArgumentException if {@code numArgs} is negative, or {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public AdjustLightCommand(Scanner in, int numArgs)
          throws IllegalStateException, IllegalArgumentException {
    super(in, numArgs);
  }

  public String apply(ImageEditorModel model) {
    checkNullModel(model);

    String commandName = this.getName();

    // get the image
    Image orig;
    try {
      orig = model.getImage(args[1]);
    } catch (IllegalArgumentException e) {
      return commandName + " failed: invalid image \"" + args[1] + "\".";
    }

    int amount;
    try {
      amount = Integer.parseInt(args[0]);
      if (amount < 0) {
        return commandName + " failed: amount must be positive, was: " + amount;
      }
    } catch (NumberFormatException e) {
      return commandName + " failed: amount must be a positive integer!";
    }

    applyToEachPixel(orig, p -> {
      PixelBuilder builder = new PixelImpl.PixelImplBuilder();
      builder.red(this.performOperation(p.getRed(), amount));
      builder.green(this.performOperation(p.getGreen(), amount));
      builder.blue(this.performOperation(p.getBlue(), amount));
      return builder.build();
    });

    // put orig back into the ImageEditor as a new image.
    // This breaks if ImageEditor.getImageAt() returns an alias instead of a deep copy.
    model.addImage(args[2], orig);

    // Success!
    return commandName + " successful!";
  }

  /**
   * To perform the necessary lighting operation on the given component value
   *
   * @param compValue   the value of the component to use
   * @param amtToAdjust is the amount to adjust the given component value by
   * @return the resulting value of that component after performing the operation
   */
  protected abstract int performOperation(int compValue, int amtToAdjust);

  /**
   * To get the String representation of the type of command. To be used in the Error messages.
   *
   * @return the name of the command as a String
   */
  protected abstract String getName();
}
