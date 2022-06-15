package commands.v2;

import java.util.Scanner;

import commands.AbstractCommand;
import model.ImageEditorModel;
import model.image.Image;
import model.v2.kernels.Transformer;

/**
 * Represents a generic gray-scaling operation which performs a single luma calculation across each
 * pixel.
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

    Transformer.TransformerBuilder tb = new Transformer.TransformerBuilder();
    for (int row = 0; row < 3; row += 1) {
      tb.valueAt(row, 0, 0.2126)
              .valueAt(row, 1, 0.7152)
              .valueAt(row, 2, 0.0722);
    }
    Transformer transformer = tb.build();

    for (int row = 0; row < orig.getHeight(); row += 1) {
      for (int col = 0; col < orig.getWidth(); col += 1) {
        orig.setPixelAt(row, col, transformer.resultAt(row, col, orig));
      }
    }

    return "Generic Grayscale successful!";
  }
}
