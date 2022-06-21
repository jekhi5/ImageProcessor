package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.kernels.AbstractMatrixOperator;
import model.kernels.Transformer;

/**
 * Represents a generic gray-scaling operation which performs a single luma calculation across each
 * pixel. {@code generic-grayscale <original-image-name> <new-image-name>}
 */
public class GenericGrayscale extends AbstractCommand {

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

    AbstractMatrixOperator.MatrixOperatorBuilder tb = new Transformer.TransformerBuilder();

    for (int row = 0; row < 3; row += 1) {
      tb.valueAt(row, 0, 0.2126)
              .valueAt(row, 1, 0.7152)
              .valueAt(row, 2, 0.0722);
    }

    applyPixelOperator(model, orig, tb.build(), args[1]);

    return "Generic Grayscale successful!";
  }
}
