package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import commands.Blur;
import commands.Brighten;
import commands.Convert;
import commands.Darken;
import commands.Downsize;
import commands.Flip;
import commands.GenericGrayscale;
import commands.Grayscale;
import commands.Help;
import commands.ImageEditorCommand;
import commands.ImageEquals;
import commands.LoadImage;
import commands.SaveImage;
import commands.Sepia;
import commands.Sharpen;
import model.ImageEditorModel;
import view.ImageEditorView;

/**
 * An {@link ImageEditorTextController} that reads input in the form of text.
 */
public class ImageEditorTextControllerImpl implements ImageEditorTextController {
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
  public ImageEditorTextControllerImpl(ImageEditorModel model, ImageEditorView view, Readable input)
          throws IllegalArgumentException {
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("Controller can't have null arguments.");
    }

    this.model = model;
    this.view = view;
    this.in = new Scanner(input);
    this.commands = new HashMap<>();

    // Add all new commands here:
    commands.put("same", s -> new ImageEquals(s));

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

    // Added in Assignment 5:
    commands.put("blur", s -> new Blur(s));

    commands.put("sharpen", s -> new Sharpen(s));

    commands.put("sepia", s -> new Sepia(s));

    commands.put("generic-grayscale", s -> new GenericGrayscale(s));

    commands.put("help", s -> new Help());
    commands.put("h", s -> new Help());

    commands.put("convert", s -> new Convert(s));


    // Assignment 6 extra credit:
    commands.put("resize", s -> new Downsize(s));
  }

  @Override
  public void launch() throws IllegalStateException {
    boolean hasQuit = false;

    this.transmit("Welcome to ImageEditor! For information about the supported file types " +
            "or available commands enter \"help\" and press <enter>. Please enter a command:");

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
