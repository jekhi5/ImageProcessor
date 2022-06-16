package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;

/**
 * A command that brightens the original image by a given amount, storing the resultant image under
 * a new name. It should be given a positive integer value between 0 and 255, which is adds to each
 * color component of each pixel in the image. Brightening cannot increase values above 255.
 *
 * <p>Command syntax: {@code brighten <amount> <original-image-name> <new-image-name>}.
 */
public class Brighten extends AdjustLightCommand {


  /**
   * Creates a new {@code Brighten}.
   *
   * @param in the {@link Scanner}
   * @throws IllegalStateException if {@code in} runs out of inputs before collecting
   *                               {@code numArgs} inputs.
   */
  public Brighten(Scanner in) throws IllegalStateException {
    super(in, 3);
  }

  @Override
  protected int performOperation(int compValue, int amtToAdjust) {
    return Math.min(compValue + amtToAdjust, 255);
  }

  @Override
  protected String getName() {
    return "Brighten";
  }


}
