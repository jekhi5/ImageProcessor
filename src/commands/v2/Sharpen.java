package commands.v2;

import java.util.Scanner;

import commands.AbstractCommand;
import model.ImageEditorModel;
import model.image.Image;
import model.v2.kernels.FilterKernel;

/**
 * A command that sharpens an image. Command syntax:
 * {@code sharpen <original-image-name> <new-image-name>}
 */
public class Sharpen extends AbstractCommand {

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

    Image newImg = orig.getCopy();

    FilterKernel.KernelBuilder kb = new FilterKernel.KernelBuilder()
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
            .valueAt(4, 4, -0.125);


    AbstractCommand.applyKernelStaticallyAcrossAll(model, orig, newImg, kb.build(), args[1]);

    return "Sharpen successful!";

  }
}
