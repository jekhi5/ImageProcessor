package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;

/**
 * A command that flips an image in the horizontal or vertical dimension.
 *
 * <p>
 * <p>
 * Command syntax: {@code flip <mode> <original-image-name> <new-image-name>}.
 *
 * @author emery
 * @created 2022-06-06
 */
public class Flip extends AbstractCommand {
  /**
   * Creates a new {@code Flip} with the given number of arguments.
   *
   * @param in the {@link Scanner}
   * @throws IllegalArgumentException if {@code numArgs} is negative
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public Flip(Scanner in)
          throws IllegalStateException, IllegalArgumentException {
    super(in, 3);
  }

  private static void swapPixels(Image img, int r1, int c1, int r2, int c2) {
    img.setPixelAt(r1, c1, img.setPixelAt(r2, c2, img.getPixelAt(r1, c1)));
  }

  @Override
  public String apply(ImageEditorModel model) {
    // get the image
    Image orig;
    try {
      orig = model.getImageAt(args[1]);
    } catch (IllegalArgumentException e) {
      return "Flip failed: invalid image \"" + args[1] + "\".";
    }

    int horizBound = orig.getWidth();
    int vertBound = orig.getHeight();

    // set the bounds, so that we don't undo our flipping by flipping the other halfback to normal
    if (args[0].equalsIgnoreCase("horizontal")) {
      horizBound = orig.getWidth() / 2;
    } else if (args[0].equalsIgnoreCase("vertical")) {
      vertBound = orig.getHeight() / 2;
    } else {
      return "Flip failed: invalid mode \"" + args[0] + "\".";
    }

    // loop through every pixel and swap them!
    for (int r = 0; r < vertBound; r += 1) {
      for (int c = 0; c < horizBound; c += 1) {
        //System.out.println("r: " + r + ", c: " + c);

        if (args[0].equalsIgnoreCase("horizontal")) {
          swapPixels(orig, r, c, r, orig.getWidth() - 1 - c);
          //System.out.println(String.format("r1: %d, c1: %d, r2: %d, c2: %d", r, c, r, orig.getWidth() - 1 - c));
        } else {
          swapPixels(orig, r, c, orig.getHeight() - 1 - r, c);
        }
      }
    }

    // put orig back into the ImageEditor as a new image.
    // This breaks if ImageEditor.getImageAt() returns an alias instead of a deep copy.
    model.addImage(args[2], orig);

    // success!
    return "Flip successful!";
  }
}
