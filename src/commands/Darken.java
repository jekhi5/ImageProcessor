package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;

/**
 * A command that darkens the original image by a given amount, storing the resultant image under a
 * new name. It should be given a positive integer value between 0 and 255, which is subtracts from
 * each color component of each pixel in the image. Darkening cannot reduce values below 0.
 *
 * <p>Command syntax: {@code darken <amount> <original-image-name> <new-image-name>}.
 */
public class Darken extends AdjustLightCommand {


  /**
   * Creates a new {@code Darken}.
   *
   * @param in the {@link Scanner}
   * @throws IllegalStateException if {@code in} runs out of inputs before collecting
   *                               {@code numArgs} inputs.
   */
  public Darken(Scanner in) throws IllegalStateException {
    super(in, 3);
  }

  @Override
  protected int performOperation(int compValue, int amtToAdjust) {
    return Math.max(compValue - amtToAdjust, 0);
  }

  @Override
  protected String getName() {
    return "Darken";
  }


}
