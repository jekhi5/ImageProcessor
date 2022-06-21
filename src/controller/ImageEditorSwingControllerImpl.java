package controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import commands.Blur;
import commands.Brighten;
import commands.Convert;
import commands.Darken;
import commands.Flip;
import commands.GenericGrayscale;
import commands.Grayscale;
import commands.HelpCommand;
import commands.ImageEditorCommand;
import commands.ImageEqualsCommand;
import commands.LoadImage;
import commands.SaveImage;
import commands.Sepia;
import commands.Sharpen;
import model.ImageEditorModel;
import view.ImageEditorView;

/**
 * An implementation of {@link ImageEditorSwingController}.
 */
public class ImageEditorSwingControllerImpl
        implements ImageEditorSwingController {

  private final ImageEditorModel model;
  private final ImageEditorView view;
  private final Map<String, Function<Scanner, ImageEditorCommand>> commands;

  /**
   * Creates a new Swing controller.
   *
   * @param model the model
   * @param view  the view
   * @throws IllegalArgumentException if either is null
   */
  public ImageEditorSwingControllerImpl(ImageEditorModel model, ImageEditorView view) {
    this.model = model;
    this.view = view;
    this.commands = new HashMap<>();

    // Add all new commands here:
    commands.put("same", s -> new ImageEqualsCommand(s));
    commands.put("grayscale", s -> new Grayscale(s));
    commands.put("greyscale", s -> new Grayscale(s));
    commands.put("gray", s -> new Grayscale(s));
    commands.put("grey", s -> new Grayscale(s));
    commands.put("load", s -> new LoadImage(s));
    commands.put("save", s -> new SaveImage(s));
    commands.put("flip", s -> new Flip(s));
    commands.put("brighten", s -> new Brighten(s));
    commands.put("darken", s -> new Darken(s));
    commands.put("blur", s -> new Blur(s));
    commands.put("sharpen", s -> new Sharpen(s));
    commands.put("sepia", s -> new Sepia(s));
    commands.put("generic-grayscale", s -> new GenericGrayscale(s));
    commands.put("help", s -> new HelpCommand());
    commands.put("h", s -> new HelpCommand());
    commands.put("convert", s -> new Convert(s));
  }


  @Override
  public void runCommand(String command) {
    StringReader reader = new StringReader(command);
    Scanner in = new Scanner(reader);

    String cmdString = "";
    if (in.hasNext()) {
      cmdString = in.next();
    } else {
      transmit("Error: missing input!");
      return;
    }

    if (commands.containsKey(cmdString)) {
      ImageEditorCommand cmd = commands.get(cmdString).apply(in);
      transmit(model.execute(cmd));
    } else {
      transmit("Error: Invalid command");
    }
  }

  private void transmit(String msg) {
    try {
      view.renderMessage(msg);
    } catch (IOException ignored) {
    }
  }
}
