package commands;

import java.io.IOException;
import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;

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

//    boolean overwrite = args[2].equalsIgnoreCase("yes") || args[2].equalsIgnoreCase("y") ||
//            args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("t");
//
//    // actually attempt to save the image
//    try {
//      ImageUtil.saveImage(img, args[0], overwrite);
//    } catch (IllegalArgumentException e) {
//      return "Save failed: " + e.getMessage();
//    }

//    System.out.println(img.getPixelAt(0,0).getAlpha());
    img.toSavableText();

    // success!
    return "Image successfully saved to " + args[0];
  }
}
