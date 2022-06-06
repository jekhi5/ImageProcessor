package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;

/**
 * Converts an image from a PPM file (specified by path) into an {@link Image}. It is added to the
 * provided {@code Map}, and is thus accessible by the model. The given name is used to refer to it
 * within the editor.
 *
 * </p>
 *
 * Command syntax: {@code load <image-path> <image-name>}
 *
 * @author emery
 * @created 2022-06-06
 */
public class LoadImage extends AbstractCommand {
  /**
   * Creates a new {@code LoadImage} with the given number of arguments.
   *
   * @param in      the {@link Scanner}
   * @param numArgs the number of arguments
   * @throws IllegalArgumentException if {@code numArgs} is negative
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public LoadImage(Scanner in, int numArgs)
          throws IllegalStateException, IllegalArgumentException {
    super(in, numArgs);
  }

  @Override
  public String apply(ImageEditorModel model) {
    // TODO: Discuss this method!!!
    // this method is very sus: It requires images be an alias of the model in order to work.
    // Options:
    // 1) go with the aliasing
    // 2) have this be a unique command similar to "quit" which works at the ImageEditorModel level
    // and not at the Image level.
    return "broken command lol";
  }
}
