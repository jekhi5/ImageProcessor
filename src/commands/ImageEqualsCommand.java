package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;

/**
 * A debug command. With overwriting and image manipulation, it can be difficult to tell if you've
 * made a change to an image or not. This command allows the user to "debug," by comparing two
 * images.
 *
 * <p>Command syntax: {@code same <image-name> <image-name>}
 */
public class ImageEqualsCommand extends AbstractCommand {

  /**
   * Creates a new {@code DebugCommand}.
   *
   * @param in the scanner
   * @throws IllegalArgumentException if {@code in} is null
   */
  public ImageEqualsCommand(Scanner in) throws IllegalArgumentException {
    super(in, 2);
  }

  @Override
  public String apply(ImageEditorModel model) throws IllegalArgumentException {
    checkNullModel(model);

    // get the images
    Image img1;
    try {
      img1 = model.getImage(args[0]);
    } catch (IllegalArgumentException e) {
      return "Equality check failed: invalid image \"" + args[0] + "\".";
    }
    Image img2;
    try {
      img2 = model.getImage(args[1]);
    } catch (IllegalArgumentException e) {
      return "Equality check failed: invalid image \"" + args[1] + "\".";
    }

    // generate the result string
    String out = "Images \"" + args[0] + "\" and \"" + args[1] + "\" are ";
    if (img1.equals(img2)) {
      out += "the same, with width " + img1.getWidth() + "px and height " + img1.getHeight() +
              "px.";
    } else {
      out += "different.";
    }

    return out;
  }
}
