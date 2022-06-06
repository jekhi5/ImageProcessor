package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import commands.ImageEditorCommand;
import commands.TestCommand;
import model.ImageEditorModel;
import view.ImageEditorView;

/**
 * An {@link ImageEditorController} that reads input in the form of text.
 *
 * @author emery
 * @created 2022-06-05
 */
public class ImageEditorTextController implements ImageEditorController {
  private final ImageEditorModel model;
  private final ImageEditorView view;
  private final Readable input;
  private final Scanner in;
  private final Map<String, Function<Scanner, ImageEditorCommand>> commands;
  private final List<String> quitAliases;

  /**
   * Creates a new {@code ImageEditorTextController} with given model, view, and input stream.
   *
   * @param model the model
   * @param view  the view
   * @param input the input stream
   * @throws IllegalArgumentException if any argument is {@code null}
   */
  public ImageEditorTextController(ImageEditorModel model, ImageEditorView view, Readable input)
          throws IllegalArgumentException {
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("Controller can't have null arguments.");
    }

    this.model = model;
    this.view = view;
    this.input = input;
    this.in = new Scanner(input);
    this.commands = new HashMap<>();
    this.quitAliases = Arrays.asList("q", "quit", "exit");

    commands.put("test", s -> new TestCommand(in));
  }

  @Override
  public void launch() throws IllegalStateException {
    boolean hasQuit = false;

    transmit("Welcome to ImageEditor! Please enter a command:");

    // main controller loop
    while (!hasQuit) {

      // get input
      String cmdString = getNextCommand();

      // quit if necessary
      if (quitAliases.contains(cmdString)) {
        hasQuit = true;
      } else {
        // we don't have to worry about a null value
        // because getNextCommand ensures that cmdString is a valid command.
        Function<Scanner, ImageEditorCommand> cmdFunc = commands.get(cmdString);
        ImageEditorCommand cmd = cmdFunc.apply(in);

        transmit(model.execute(cmd, model.getImage(/*TODO: What name are we giving it?*/)));
      }
    }

    // if we run out of inputs without quitting, throw an exception.
    // Otherwise, display a pleasant message.
    if (!hasQuit) {
      throw new IllegalStateException("Controller ran out of inputs!");
    } else {
      in.close();
      transmit("Thanks for using ImageEditor!");
    }
  }

  // gets the next valid command.
  private String getNextCommand() throws IllegalStateException {
    while (in.hasNext()) {
      transmit("> ", false);

      String attempt = in.next().toLowerCase();

      if (commands.containsKey(attempt) || quitAliases.contains(attempt)) {
        return attempt;
      } else {
        transmit("Invalid command: \"" + attempt + "\". Please try again.");
      }
    }
    // this return statement is only reached if input runs out of inputs.
    // therefore, we just return a command to quit the editor.
    return quitAliases.get(0);
  }

  private void transmit(String msg) throws IllegalStateException {
    transmit(msg, true);
  }

  private void transmit(String msg, boolean newLineAfter) {
    try {
      view.renderMessage(msg);
      if (newLineAfter) {
        view.renderMessage(System.lineSeparator());
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
