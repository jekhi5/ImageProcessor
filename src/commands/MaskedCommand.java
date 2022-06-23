package commands;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import model.ImageEditorModel;

/**
 * Represents a masked based command. Any command that modifies a currently loaded image (sans flip)
 * can be run with a masked command by providing the necessary mask.
 * {@code mask-command <mask-image> <command name> <...all arguments associated with that
 * command...>}
 */
public class MaskedCommand implements ImageEditorCommand {

  private final ImageEditorCommand cmd;
  private final String pathToMask;


  /**
   * Creates a new Masked Command which takes in the scanner.
   *
   * @param in the {@link java.util.Scanner}
   * @throws IllegalArgumentException        if {@code in} is null
   * @throws java.lang.IllegalStateException if {@code in} runs out of inputs
   */
  public MaskedCommand(Scanner in,
                       Map<String, Function<Scanner, ImageEditorCommand>> availableCommands)
          throws IllegalArgumentException, IllegalStateException {
    if (in == null) {
      throw new IllegalArgumentException("If inputs are expected, the scanner cannot be null!");
    }

    String nameOfCommand = this.handleNext(in);

    this.pathToMask = this.handleNext(in);

    if (availableCommands.containsKey(nameOfCommand)) {
      this.cmd = availableCommands.get(nameOfCommand).apply(in);
    } else {
      throw new IllegalArgumentException("Command not found: " + nameOfCommand);
    }
  }

  private String handleNext(Scanner in) {
    if (in.hasNext()) {
      return in.next();
    } else {
      throw new IllegalStateException("The scanner ran out of input!");
    }
  }

  @Override
  public String apply(ImageEditorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null!");
    }

    return cmd.applyMask(model, this.pathToMask);
  }

  @Override
  public String applyMask(ImageEditorModel model, String pathToMask)
          throws IllegalArgumentException, UnsupportedOperationException {
    throw new UnsupportedOperationException("You cannot apply a mask on a mask command!");
  }
}
