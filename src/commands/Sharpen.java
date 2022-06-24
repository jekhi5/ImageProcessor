package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.kernels.FilterKernel;
import model.kernels.PixelOperator;
import utilities.ImageFactory;

/**
 * A command that sharpens an image. Command syntax:
 * {@code sharpen <original-image-name> <new-image-name>}
 */
public class Sharpen extends AbstractCommand {

  private static final PixelOperator FILTER_KERNEL = new FilterKernel.KernelBuilder()
          .size(5)
          .valueAt(0, 0, -0.125)
          .valueAt(0, 1, -0.125)
          .valueAt(0, 2, -0.125)
          .valueAt(0, 3, -0.125)
          .valueAt(0, 4, -0.125)

          .valueAt(1, 0, -0.125)
          .valueAt(1, 1, 0.25)
          .valueAt(1, 2, 0.25)
          .valueAt(1, 3, 0.25)
          .valueAt(1, 4, -0.125)

          .valueAt(2, 0, -0.125)
          .valueAt(2, 1, 0.25)
          .valueAt(2, 2, 1)
          .valueAt(2, 3, 0.25)
          .valueAt(2, 4, -0.125)

          .valueAt(3, 0, -0.125)
          .valueAt(3, 1, 0.25)
          .valueAt(3, 2, 0.25)
          .valueAt(3, 3, 0.25)
          .valueAt(3, 4, -0.125)

          .valueAt(4, 0, -0.125)
          .valueAt(4, 1, -0.125)
          .valueAt(4, 2, -0.125)
          .valueAt(4, 3, -0.125)
          .valueAt(4, 4, -0.125).build();

  /**
   * To construct a Sharpen command that takes in two additional user inputted arguments.
   *
   * @param in the scanner connected to the user
   * @throws IllegalArgumentException if {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public Sharpen(Scanner in) {
    super(in, 2);
  }

  @Override
  public String apply(ImageEditorModel model) {
    checkNullModel(model);

    Image orig = handleGettingImage(model);

    if (orig == null) {
      return "Sharpen failed: invalid image \"" + args[0] + "\".";
    }

    applyPixelOperator(model, orig, FILTER_KERNEL, args[1]);

    return "Sharpen successful!";
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
      return "Sharpen failed: invalid mask path \"" + pathToMask + "\".";
    }

    Image orig = handleGettingImage(model);

    if (orig == null) {
      return "Sharpen failed: invalid image \"" + args[0] + "\"";
    }

    if (orig.getWidth() != maskImage.getWidth() || orig.getHeight() != maskImage.getHeight()) {
      return "Sharpen failed: the mask's width and height does not match that of the " +
              "original image.";
    }

    model.addImage(args[1], applyMaskWithKernel(orig, maskImage, FILTER_KERNEL));

    return "Sharpen successful!";
  }
}
