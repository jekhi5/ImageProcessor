package commands.v2;

import java.util.Scanner;

import commands.AbstractCommand;
import model.ImageEditorModel;
import model.image.Image;
import model.v2.kernels.AbstractMatrixOperator;
import model.v2.kernels.Transformer;

/**
 * Represents a generic gray-scaling operation which performs a single luma calculation across each
 * pixel. {@code generic-grayscale <original-image-name> <new-image-name>}
 */
public class GenericGrayscale extends AbstractCommand {

  /**
   * To construct a GenericGreyscale command.
   *
   * @param in the scanner connected to the user
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
