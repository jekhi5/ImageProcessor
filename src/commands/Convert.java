package commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageFactory;

/**
 * Represents a command that will convert one file type to another file type.
 * {@code <path-to-image> <new-location-with-new-extension> <should-overwrite>}
 */
public class Convert extends AbstractCommand {
  /**
   * Creates a new convert command that takes in 3 arguments in the syntax above.
   *
   * @param in the {@link Scanner}
   * @throws IllegalArgumentException if {@code in} is null
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public Convert(Scanner in) throws IllegalStateException, IllegalArgumentException {
    super(in, 3);
  }

  @Override
  public String apply(ImageEditorModel model) {
    Image initialImage;
    try {
      initialImage = ImageFactory.createImage(args[0]);
    } catch (IllegalArgumentException e) {
      return "Convert failed: " + e.getMessage();
    }
    String destinationLocation = args[1];
    String[] overwriteAliases = {"y", "yes", "t", "true"};
    boolean shouldOverwrite =
            Arrays.stream(overwriteAliases).anyMatch(s -> s.equalsIgnoreCase(args[2]));

    try {
      initialImage.saveToPath(destinationLocation, shouldOverwrite);
    } catch (IOException e) {
      return "Convert failed: " + e.getMessage();
    }

    return "Convert successful!";

  }
}
