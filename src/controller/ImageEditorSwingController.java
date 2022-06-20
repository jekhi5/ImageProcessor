package controller;

/**
 * A controller interface that allows compatibility between a Swing GUI view and model.
 * Allows the view to collect user input and passes it to the model.
 */
public interface ImageEditorSwingController {

  /**
   * Runs the given command on the model, and gives output results to the view.
   * @param command the command as a String, in the form it would be used in interactive scripting.
   */
  void runCommand(String command);

}
