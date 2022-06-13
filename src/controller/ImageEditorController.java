package controller;

/**
 * Takes in user input in some way, and uses it to run the image editor. The controller facilitates
 * the transfer of data between an {@link view.ImageEditorView} and an
 * {@link model.ImageEditorModel}.
 */
public interface ImageEditorController {

  /**
   * Launches the image editor controller.
   *
   * @throws IllegalStateException if the view has an issue transmitting data
   */
  void launch() throws IllegalStateException;
}
