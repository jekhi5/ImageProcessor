package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.kernels.AbstractMatrixOperator;
import model.kernels.FilterKernel;

/**
 * A command that blurs an image. Command syntax:
 * {@code blur <original-image-name> <new-image-name>}
 */
public class Blur extends AbstractCommand {

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

    AbstractMatrixOperator.MatrixOperatorBuilder kb = new FilterKernel.KernelBuilder()
            .size(3)
            .valueAt(0, 0, 0.0625)
            .valueAt(0, 1, 0.125)
            .valueAt(0, 2, 0.0625)

            .valueAt(1, 0, 0.125)
            .valueAt(1, 1, 0.25)
            .valueAt(1, 2, 0.125)

            .valueAt(2, 0, 0.0625)
            .valueAt(2, 1, 0.125)
            .valueAt(2, 2, 0.0625);

    AbstractCommand.applyPixelOperator(model, orig, kb.build(), args[1]);

    return "Blur successful!";
  }
}