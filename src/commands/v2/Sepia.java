package commands.v2;

import java.util.Scanner;

import commands.AbstractCommand;
import model.ImageEditorModel;
import model.image.Image;
import model.v2.kernels.AbstractMatrixOperator;
import model.v2.kernels.Transformer;

/**
 * A command to apply a Sepia filter to an image.
 * {@code sepia <original-image-name> <new-image-name>}
 */
public class Sepia extends AbstractCommand {


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

    AbstractMatrixOperator.MatrixOperatorBuilder tb = new Transformer.TransformerBuilder()
            .valueAt(0, 0, 0.393)
            .valueAt(0, 1, 0.769)
            .valueAt(0, 2, 0.189)

            .valueAt(1, 0, 0.349)
            .valueAt(1, 1, 0.686)
            .valueAt(1, 2, 0.168)

            .valueAt(2, 0, 0.272)
            .valueAt(2, 1, 0.534)
            .valueAt(2, 2, 0.131);

    applyPixelOperator(model, orig, tb.build(), args[1]);

    return "Sepia successful!";
  }
}
