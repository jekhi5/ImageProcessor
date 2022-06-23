package commands;

import java.util.Scanner;
import java.util.function.Function;

import model.ImageEditorModel;
import model.image.Image;
import model.kernels.PixelOperator;
import model.pixel.Pixel;

/**
 * Represents an abstract command. All commands extend this abstract class because they all have
 * some sort of similar operation.
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

    // If it takes in no arguments, then there doesn't need to be a scanner
    if (numArgs > 0 && in == null) {
      throw new IllegalArgumentException("If inputs are expected, the scanner cannot be null!");
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

  protected static void applyPixelOperatorToPixel(int row, int col,
                                                  Image imageCopy, PixelOperator op) {
    Pixel newPixel = op.resultAt(row, col, imageCopy);
    imageCopy.setPixelAt(row, col, newPixel);
  }

  protected static void applyPixelOperator(ImageEditorModel model,
                                           Image orig,
                                           PixelOperator op,
                                           String newImageName) {
    Image newImg = orig.getCopy();

    for (int r = 0; r < orig.getHeight(); r++) {
      for (int c = 0; c < orig.getWidth(); c++) {
        applyPixelOperatorToPixel(r, c, newImg, op);
      }
    }
    model.addImage(newImageName, newImg);
  }

  @Override
  public abstract String apply(ImageEditorModel model);

  protected void applyToEachPixel(Image img, Function<Pixel, Pixel> func) {
    for (int r = 0; r < img.getHeight(); r += 1) {
      for (int c = 0; c < img.getWidth(); c += 1) {
        this.applyToSinglePixel(r, c, img, func);
      }
    }
  }

  protected static Image applyMaskWithKernel(Image orig, Image maskImage,
                                          PixelOperator pixelOperator) {
    Image imageCopy = orig.getCopy();
    for (int row = 0; row < orig.getHeight(); row += 1) {
      for (int col = 0; col < orig.getWidth(); col += 1) {
        if (maskImage.getPixelAt(row, col).getRed() == 0 &&
                maskImage.getPixelAt(row, col).getGreen() == 0 &&
                maskImage.getPixelAt(row, col).getBlue() == 0) {
          applyPixelOperatorToPixel(row, col, imageCopy, pixelOperator);
        }
      }
    }

    return imageCopy;
  }

  protected void applyToSinglePixel(int row, int col, Image img, Function<Pixel, Pixel> func) {
    Pixel p = img.getPixelAt(row, col);
    img.setPixelAt(row, col, func.apply(p));
  }

  protected void checkNullModel(ImageEditorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null!");
    }
  }


  protected void checkNullMask(String mask) throws IllegalArgumentException {
    if (mask == null) {
      throw new IllegalArgumentException("Mask can't be null!");
    }
  }

  protected Image handleGettingImage(ImageEditorModel model) {
    try {
      return model.getImage(args[0]);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
