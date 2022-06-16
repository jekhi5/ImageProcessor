package commands;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;

/**
 * Saves an image currently open in the editor under the given name to the given file path. DOES NOT
 * enforce file types--saving an image to a different filetype than it was loaded from may result in
 * unreadable files. The {@code force} argument determines if this command should overwrite any file
 * already stored at the given path. Case-insensitive values of "yes", "y", "true", or "t" overwrite
 * older files, if any. All other arguments are case-sensitive.
 *
 * <p>Command syntax: {@code save <image-path> <image-name> <shouldOverwrite>}
 */
public class SaveImage extends AbstractCommand {

  /**
   * Creates a new {@code SaveImage} with the given number of arguments.
   *
   * @param in the {@link Scanner}
   * @throws IllegalArgumentException if {@code numArgs} is negative
   * @throws IllegalStateException    if {@code in} runs out of inputs before collecting
   *                                  {@code numArgs} inputs.
   */
  public SaveImage(Scanner in)
          throws IllegalStateException, IllegalArgumentException {
    super(in, 3);
  }

  @Override
  public String apply(ImageEditorModel model) throws IllegalArgumentException {
    checkNullModel(model);

    Image img;
    try {
      img = model.getImage(args[1]);
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }

    List<String> aliasesOW = List.of("yes", "y", "true", "t");
    boolean shouldOverwrite = aliasesOW.contains(args[2]);

    try {
      img.saveToPath(args[0], shouldOverwrite);
    } catch (IOException | IllegalArgumentException e) {
      return "Save failed: " + e.getMessage();
    }

    // success!
    return "Image successfully saved to " + args[0];
  }
}
