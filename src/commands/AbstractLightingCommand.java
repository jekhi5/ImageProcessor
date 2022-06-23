package commands;

import java.util.Scanner;
import java.util.function.Function;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;
import utilities.ImageFactory;

/**
 * Represents a command that modifies the lighting of an image like in {@link Brighten} and
 * {@link Darken}.
 */
public abstract class AbstractLightingCommand extends AbstractCommand {

  /**
   * Creates a new lighting based command with the given number of arguments.
   *
   * @param in      the {@link Scanner}
   * @param numArgs the number of arguments
   * @throws IllegalArgumentException if {@code numArgs} is negative, or {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public AbstractLightingCommand(Scanner in, int numArgs)
          throws IllegalStateException, IllegalArgumentException {
    super(in, numArgs);

  }

  @Override
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

  @Override
  public String applyMask(ImageEditorModel model, String pathToMask)
          throws IllegalArgumentException,
          UnsupportedOperationException {
    checkNullModel(model);
    checkNullMask(pathToMask);

    String commandName = this.getName();

    Image maskImage;
    try {
      maskImage = ImageFactory.createImage(pathToMask);
    } catch (IllegalArgumentException e) {
      return commandName + " failed: invalid mask path \"" + pathToMask + "\".";
    }

    // get the image
    Image orig;
    try {
      orig = model.getImage(args[1]);
    } catch (IllegalArgumentException e) {
      return commandName + " failed: invalid image \"" + args[1] + "\".";
    }

    if (orig.getWidth() != maskImage.getWidth() || orig.getHeight() != maskImage.getHeight()) {
      return commandName + "failed: the mask's width and height does not match that of the " +
              "original image.";
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

    for (int row = 0; row < orig.getHeight(); row += 1) {
      for (int col = 0; col < orig.getWidth(); col += 1) {

        if (maskImage.getPixelAt(row, col).getRed() == 0 &&
                maskImage.getPixelAt(row, col).getGreen() == 0 &&
                maskImage.getPixelAt(row, col).getBlue() == 0) {
          applyToSinglePixel(row, col, orig, p -> {
            PixelBuilder builder = new PixelImpl.PixelImplBuilder();
            builder.red(this.performOperation(p.getRed(), amount));
            builder.green(this.performOperation(p.getGreen(), amount));
            builder.blue(this.performOperation(p.getBlue(), amount));
            return builder.build();
          });
        }
      }
    }

    // put orig back into the ImageEditor as a new image.
    // This breaks if ImageEditor.getImageAt() returns an alias instead of a deep copy.
    model.addImage(args[2], orig);

    // Success!
    return commandName + " successful!";

  }

  /**
   * To perform the necessary lighting operation on the given component value.
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
