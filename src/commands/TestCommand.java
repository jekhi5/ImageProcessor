package commands;

import java.util.Scanner;

import model.ImageEditorModel;
import model.image.Image;

/**
 * A test command. It takes in two arguments and has the view print them out.
 *
 * @author emery
 * @created 2022-06-05
 */
public class TestCommand extends AbstractCommand {

  public TestCommand(Scanner in) {
    super(in, 1);

  }

  @Override
  public String apply(ImageEditorModel model) {
    // get the image
    Image orig;
    try {
      orig = model.getImageAt(args[0]);
    } catch (IllegalArgumentException e) {
      return "Debug failed: invalid image \"" + args[0] + "\".";
    }

    return args[0] + ": width" + orig.getWidth() + ", height " + orig.getHeight();
  }
}
