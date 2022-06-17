package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import model.v2.ImageFactory;

/**
 * Opens an image from a PPM file (specified by path) into the editor, where further commands can be
 * run on it. The given name is used to refer to it within the editor. All args are case-sensitive.
 *
 * <p>Command syntax: {@code load <image-path> <image-name>}
 */
public class LoadImage extends AbstractCommand {
  /**
   * Creates a new {@code LoadImage} with the given number of arguments.
   *
   * @param in the {@link Scanner}
   * @throws IllegalArgumentException if {@code numArgs} is negative
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public LoadImage(Scanner in)
          throws IllegalStateException, IllegalArgumentException {
    super(in, 2);
  }

  @Override
  public String apply(ImageEditorModel model) throws IllegalArgumentException {
    checkNullModel(model);

    Image img;
    try {
      img = ImageFactory.createImage(args[0]);
    } catch (IllegalArgumentException e) {
      return "Load failed: " + e.getMessage();
    }

    model.addImage(args[1], img);
    return "Successfully loaded image \"" + args[1] + "\" from " + args[0] + "!";
  }
}
