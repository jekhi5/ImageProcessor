package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.kernels.FilterKernel;
import model.kernels.PixelOperator;
import utilities.ImageFactory;

/**
 * A command that blurs an image. Command syntax:
 * {@code blur <original-image-name> <new-image-name>}
 */
public class Blur extends AbstractCommand {

  private static final PixelOperator FILTER_KERNEL = new FilterKernel.KernelBuilder()
          .size(3)
          .valueAt(0, 0, 0.0625)
          .valueAt(0, 1, 0.125)
          .valueAt(0, 2, 0.0625)

          .valueAt(1, 0, 0.125)
          .valueAt(1, 1, 0.25)
          .valueAt(1, 2, 0.125)

          .valueAt(2, 0, 0.0625)
          .valueAt(2, 1, 0.125)
          .valueAt(2, 2, 0.0625).build();

  /**
   * To construct a Blur command.
   *
   * @param in the scanner connected to the user
   * @throws IllegalArgumentException if {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public Blur(Scanner in) {
    super(in, 2);
  }

  @Override
  public String apply(ImageEditorModel model) {
    checkNullModel(model);

    // get the image
    Image orig = handleGettingImage(model);
    if (orig == null) {
      return "Blur failed: invalid image \"" + args[0] + "\".";
    }

    AbstractCommand.applyPixelOperator(model, orig, FILTER_KERNEL, args[1]);

    return "Blur successful!";
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
      return "Blur failed: invalid mask path \"" + pathToMask + "\".";
    }

    Image orig = handleGettingImage(model);

    if (orig == null) {
      return "Blur failed: invalid image \"" + args[0] + "\"";
    }

    if (orig.getWidth() != maskImage.getWidth() || orig.getHeight() != maskImage.getHeight()) {
      return "Blur failed: the mask's width and height does not match that of the " +
              "original image.";
    }

    model.addImage(args[2], applyMaskWithKernel(orig, maskImage, FILTER_KERNEL));

    return "Blur successful!";
  }
}
