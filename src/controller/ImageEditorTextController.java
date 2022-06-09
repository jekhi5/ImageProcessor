package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import commands.Brighten;
import commands.Darken;
import commands.Flip;
import commands.Grayscale;
import commands.ImageEditorCommand;
import commands.LoadImage;
import commands.SameImageCommand;
import commands.SaveImage;
import model.ImageEditorModel;
import view.ImageEditorView;

// TODO: Make fun name
// TODO: "help" command
// TODO: file-by-file cleanup (remove System.out.println(), check for leakage, remove warnings, etc)
// TODO: run coverage on all tests
// TODO: See how to handle the image-creation-time in the tests
// TODO: Handle testCommand/debugCommand

/**
 * An {@link ImageEditorController} that reads input in the form of text.
 *
 * @author emery
 * @created 2022-06-05
 */
public class ImageEditorTextController implements ImageEditorController {
  private static final List<String> QUIT_ALIASES = Arrays.asList("q", "quit", "exit");
  private static final String USER_INPUT_SYMBOL = "> ";
  private final ImageEditorModel model;
  private final ImageEditorView view;
  private final Scanner in;
  private final Map<String, Function<Scanner, ImageEditorCommand>> commands;

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
    this.in = new Scanner(input);
    this.commands = new HashMap<>();

    // Add all new commands here:
    commands.put("debug", s -> new SameImageCommand(s));

    // different misspellings of "grayscale"
    commands.put("grayscale", s -> new Grayscale(s));
    commands.put("greyscale", s -> new Grayscale(s));
    commands.put("gray", s -> new Grayscale(s));
    commands.put("grey", s -> new Grayscale(s));

    commands.put("load", s -> new LoadImage(s));
    commands.put("save", s -> new SaveImage(s));
    commands.put("flip", s -> new Flip(s));
    commands.put("brighten", s -> new Brighten(s));
    commands.put("darken", s -> new Darken(s));
  }

  @Override
  public void launch() throws IllegalStateException {
    boolean hasQuit = false;

    this.transmit("Welcome to ImageEditor! Please enter a command:");
    //TODO: create a help menu and allow the user to display it with the "help" command

    // main controller loop
    while (!hasQuit) {
      // if we run out of inputs without quitting, quit..
      // Otherwise, display a pleasant message.

      // get input
      String cmdString = getNextCommand();

      // quit if necessary
      if (QUIT_ALIASES.contains(cmdString)) {
        hasQuit = true;
      } else {
        // we don't have to worry about a null value
        // because getNextCommand ensures that cmdString is a valid command.
        Function<Scanner, ImageEditorCommand> cmdFunc = commands.get(cmdString);
        try {
          ImageEditorCommand cmd = cmdFunc.apply(in);
          // we could have simply called cmd.execute(model);
          // however, we felt that it was better for the model to be the agent in this thematic
          // relationship. This way, the commands are data passed from the controller to the model,
          // and not bridges between the two in and of themselves.
          this.transmit(model.execute(cmd));

        } catch (IllegalStateException e) {
          // if we run out of arguments WITHIN A COMMAND, we want to note that before quitting.
          this.transmit("Error: Insufficient command input. Quitting...");
          hasQuit = true;
        }

      }
    }

    in.close();
    this.transmit("Thanks for using ImageEditor!");
  }

  // gets the next valid command.
  private String getNextCommand() throws IllegalStateException {
    this.transmit(USER_INPUT_SYMBOL, false);
    while (in.hasNext()) {
      String attempt = in.next().toLowerCase();
      if (commands.containsKey(attempt) || QUIT_ALIASES.contains(attempt)) {
        return attempt;
      } else {
        this.transmit("Invalid command: \"" + attempt + "\". Please try again.");
        this.transmit(USER_INPUT_SYMBOL, false);
      }
    }
    // this return statement is only reached if input runs out of inputs.
    // therefore, we just return a command to quit the editor.
    return QUIT_ALIASES.get(0);
  }

  private void transmit(String msg) throws IllegalStateException {
    this.transmit(msg, true);
  }

  private void transmit(String msg, boolean newLineAfter) throws IllegalStateException {
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
