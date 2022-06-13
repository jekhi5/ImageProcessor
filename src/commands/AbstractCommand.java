package commands;

import java.util.Scanner;
import java.util.function.Function;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelImpl;
import model.v2.KernelImpl;

/**
 * An abstract command.
 */
public abstract class AbstractCommand implements ImageEditorCommand {
  protected final String[] args;

  /**
   * Creates a new {@code AbstractCommand} with the given number of arguments.
   *
   * @param in      the {@link Scanner}
   * @param numArgs the number of arguments
   * @throws IllegalArgumentException if {@code numArgs} is negative, or {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public AbstractCommand(Scanner in, int numArgs)
          throws IllegalStateException, IllegalArgumentException {
    if (numArgs < 0) {
      throw new IllegalArgumentException("Commands must expect a non-negative number of inputs.");
    }
    if (in == null) {
      throw new IllegalArgumentException();
    }
    args = new String[numArgs];
    for (int i = 0; i < numArgs; i += 1) {
      if (in.hasNext()) {
        args[i] = in.next();
      } else {
        throw new IllegalStateException("Scanner ran out of inputs.");
      }
    }
  }

  @Override
  public abstract String apply(ImageEditorModel model);

  protected void applyToEachPixel(Image img, Function<Pixel, Pixel> func) {
    for (int r = 0; r < img.getHeight(); r += 1) {
      for (int c = 0; c < img.getWidth(); c += 1) {
        Pixel p = img.getPixelAt(r, c);
        img.setPixelAt(r, c, func.apply(p));
      }
    }
  }

  protected void checkNullModel(ImageEditorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null!");
    }
  }

  protected Image handleGettingImage(ImageEditorModel model) {
    try {
      return model.getImage(args[0]);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  protected static void applyKernelStaticallyAcrossAll(ImageEditorModel model, Image orig,
                                                       Image newImg,
                                                       KernelImpl.KernelBuilder kb,
                                                       String newImageName) {
    for (int r = 0; r < orig.getHeight(); r++) {
      for (int c = 0; c < orig.getWidth(); c++) {
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
    model.addImage(newImageName, newImg);
  }
}
