package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.kernels.PixelOperator;
import model.kernels.Transformer;
import utilities.ImageFactory;

/**
 * A command to apply a Sepia filter to an image.
 * {@code sepia <original-image-name> <new-image-name>}
 */
public class Sepia extends AbstractCommand {

  private static final PixelOperator TRANSFORMER = new Transformer.TransformerBuilder()
          .valueAt(0, 0, 0.393)
          .valueAt(0, 1, 0.769)
          .valueAt(0, 2, 0.189)

          .valueAt(1, 0, 0.349)
          .valueAt(1, 1, 0.686)
          .valueAt(1, 2, 0.168)

          .valueAt(2, 0, 0.272)
          .valueAt(2, 1, 0.534)
          .valueAt(2, 2, 0.131).build();

  /**
   * To construct a Sepia command that takes in two additional user inputted arguments.
   *
   * @param in the scanner connected to the user
   * @throws IllegalArgumentException if {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public Sepia(Scanner in) {
    super(in, 2);
  }

  @Override
  public String apply(ImageEditorModel model) {
    checkNullModel(model);

    Image orig = handleGettingImage(model);
    if (orig == null) {
      return "Sepia failed: invalid image \"" + args[0] + "\"";
    }

    applyPixelOperator(model, orig, TRANSFORMER, args[1]);

    return "Sepia successful!";
  }

  @Override
  public String applyMask(ImageEditorModel model, String pathToMask)
          throws IllegalArgumentException, UnsupportedOperationException {
    checkNullModel(model);
    checkNullMask(pathToMask);

    Image maskImage;
    try {
      maskImage = ImageFactory.createImage(pathToMask);
    } catch (IllegalArgumentException e) {
      return "Sepia failed: invalid mask path \"" + pathToMask + "\".";
    }

    Image orig = handleGettingImage(model);

    if (orig == null) {
      return "Sepia failed: invalid image \"" + args[0] + "\"";
    }

    if (orig.getWidth() != maskImage.getWidth() || orig.getHeight() != maskImage.getHeight()) {
      return "Sepia failed: the mask's width and height does not match that of the " +
              "original image (" + orig.getWidth() + "x" + orig.getHeight() + ").";
    }

    model.addImage(args[1], applyMaskWithKernel(orig, maskImage, TRANSFORMER));

    return "Sepia successful!";
  }
}
