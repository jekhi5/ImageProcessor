package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.kernels.PixelOperator;
import model.kernels.Transformer;
import utilities.ImageFactory;

/**
 * Represents a generic gray-scaling operation which performs a single luma calculation across each
 * pixel. {@code generic-grayscale <original-image-name> <new-image-name>}
 */
public class GenericGrayscale extends AbstractCommand {

  private static final PixelOperator TRANSFORMER = new Transformer.TransformerBuilder()
          .valueAt(0, 0, 0.2126)
          .valueAt(0, 1, 0.7152)
          .valueAt(0, 2, 0.0722)

          .valueAt(1, 0, 0.2126)
          .valueAt(1, 1, 0.7152)
          .valueAt(1, 2, 0.0722)

          .valueAt(2, 0, 0.2126)
          .valueAt(2, 1, 0.7152)
          .valueAt(2, 2, 0.0722).build();

  /**
   * To construct a GenericGreyscale command that takes in 2 additional user given arguments.
   *
   * @param in the scanner connected to the user
   * @throws IllegalArgumentException if {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public GenericGrayscale(Scanner in) {
    super(in, 2);
  }

  @Override
  public String apply(ImageEditorModel model) {
    checkNullModel(model);

    Image orig = handleGettingImage(model);

    if (orig == null) {
      return "Generic Grayscale failed: invalid image \"" + args[0] + "\"";
    }

    applyPixelOperator(model, orig, TRANSFORMER, args[1]);

    return "Generic Grayscale successful!";
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
      return "Generic Grayscale failed: invalid mask path \"" + pathToMask + "\".";
    }

    Image orig = handleGettingImage(model);

    if (orig == null) {
      return "Generic Grayscale failed: invalid image \"" + args[0] + "\"";
    }

    if (orig.getWidth() != maskImage.getWidth() || orig.getHeight() != maskImage.getHeight()) {
      return "Generic Grayscale failed: the mask's width and height does not match that of the " +
              "original image (" + orig.getWidth() + "x" + orig.getHeight() + ").";
    }

    model.addImage(args[1], applyMaskWithKernel(orig, maskImage, TRANSFORMER));

    return "Generic Grayscale successful!";
  }
}
