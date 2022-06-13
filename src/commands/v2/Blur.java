package commands.v2;

import java.util.Scanner;

import commands.AbstractCommand;
import model.ImageEditorModel;
import model.image.Image;
import model.v2.KernelImpl;

/**
 * A command that blurs an image. Command syntax:
 * {@code blur <original-image-name> <new-image-name>}
 */
public class Blur extends AbstractCommand {

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

    Image newImg = orig.getCopy();

    KernelImpl.KernelBuilder kb = new KernelImpl.KernelBuilder()
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

    AbstractCommand.applyKernelStaticallyAcrossAll(model, orig, newImg, kb, args[1]);

    return "Blur successful!";
  }
}