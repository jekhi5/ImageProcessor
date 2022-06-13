package commands.v2;

import java.util.Scanner;
import java.util.function.Function;

import commands.AbstractCommand;
import model.ImageEditorModel;
import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelImpl;
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
    Image orig;
    try {
      orig = model.getImage(args[0]);
    } catch (IllegalArgumentException e) {
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

    for (int r = 0; r < orig.getHeight(); r++) {
      for (int c = 0; c < orig.getWidth(); c++) {
        Pixel p = orig.getPixelAt(r, c);
        int finalR = r;
        int finalC = c;
        Function<Function<Pixel, Integer>, Integer> op =
                f -> kb.channelFunc(f).build().resultAt(finalR, finalC, orig);

        Pixel newPixel = new PixelImpl.PixelImplBuilder()
                .red(op.apply(Pixel::getRed))
                .green(op.apply(Pixel::getGreen))
                .blue(op.apply(Pixel::getBlue))
                .alpha(op.apply(Pixel::getAlpha))
                .build();
        newImg.setPixelAt(r, c, newPixel);
      }
    }
    model.addImage(args[1], newImg);

    return "Blur successful!";
  }
}
