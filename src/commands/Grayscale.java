package commands;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;

/**
 * A command which produces a new image by applying a grayscale to an existing image. There are
 * several components that can be considered for this grayscale. These are: {@code red},
 * {@code green}, {@code blue}, {@code value}, {@code intensity}, and {@code luma}. These components
 * are case-insensitive.
 * <p>
 * Command syntax: {@code grayscale <component> <original-image-name> <new-image-name>}
 *
 * @author emery
 * @created 2022-06-06
 */
public class Grayscale extends AbstractCommand {

  /**
   * Creates a new {@code Grayscale} with the given number of arguments.
   *
   * @param in the {@link Scanner}
   * @throws IllegalArgumentException if {@code numArgs} is negative
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public Grayscale(Scanner in)
          throws IllegalStateException, IllegalArgumentException {
    super(in, 3);
  }

  @Override
  public String apply(ImageEditorModel model) {
    // get the image
    Image orig;
    try {
      orig = model.getImageAt(args[1]);
    } catch (IllegalArgumentException e) {
      return "Grayscale failed: invalid image \"" + args[1] + "\".";
    }

    // make sure the first argument is a valid mode
    List<String> modes = Arrays.asList("red", "green", "blue", "value", "intensity", "luma");
    if (!modes.contains(args[0].toLowerCase())) {
      return "Grayscale failed: invalid component \"" + args[0] + "\".";
    }

    // get the function to apply to the pixels in the image
    Function<Pixel, Pixel> op;
    PixelBuilder b = new PixelImpl.PixelImplBuilder();
    op = p -> {
      int val;
      switch (args[0].toLowerCase()) {
        case "red":
          val = p.getRed();
          break;
        case "green":
          val = p.getGreen();
          break;
        case "blue":
          val = p.getBlue();
          break;
        case "value":
          val = Math.max(p.getRed(), Math.max(p.getGreen(), p.getBlue()));
          break;
        case "intensity":
          val = (int) ((p.getRed() + p.getGreen() + p.getBlue()) / 3.0);
          break;
        case "luma":
          val = (int) (0.2126 * p.getRed() + 0.7152 * p.getGreen() + 0.0722 * p.getBlue());
          break;
        default:
          val = -1;
      }
      b.red(val);
      b.green(val);
      b.blue(val);
      b.alpha(p.getAlpha());
      return b.build();
    };

    // apply the function on each pixel in the image
    applyToEachPixel(orig, op);

    // put orig back into the ImageEditor as a new image.
    // This breaks if ImageEditor.getImageAt() returns an alias instead of a deep copy.
    model.addImage(args[2], orig);

    // success!
    return "Grayscale successful!";
  }
}
