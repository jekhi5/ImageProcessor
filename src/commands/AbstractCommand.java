package commands;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.Pixel;

/**
 * An abstract command.
 *
 * @author emery
 * @created 2022-06-05
 */
public abstract class AbstractCommand implements ImageEditorCommand {
  protected final String[] args;

  /**
   * Creates a new {@code AbstractCommand} with the given number of arguments.
   *
   * @param in      the {@link Scanner}
   * @param numArgs the number of arguments
   * @throws IllegalArgumentException if {@code numArgs} is negative
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public AbstractCommand(Scanner in, int numArgs)
          throws IllegalStateException, IllegalArgumentException {
    if (numArgs < 0) {
      throw new IllegalArgumentException("Commands must expect a non-negative number of inputs.");
    }
    args = new String[numArgs];
    for (int i = 0; i < numArgs; i += 1) {
      if (in.hasNext()) {
        args[i] = in.next();
      } else {
        throw new IllegalStateException("Scanner ran out of inputs.");
      }
    }
  }

  @Override
  public abstract String apply(ImageEditorModel model);

  protected void applyToEachPixel(Image img, Function<Pixel, Pixel> func) {
    for (int r = 0; r < img.getWidth(); r += 1) {
      for (int c = 0; c < img.getHeight(); c += 1) {
        Pixel p =  img.getPixelAt(r, c);
        img.setPixelAt(r, c, func.apply(p));
      }
    }
  }
}
