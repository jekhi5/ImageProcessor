package controller;

/**
 * Takes in user input in some way, and uses it to run the image editor. The controller facilitates
 * the transfer of data between an {@link view.ImageEditorView} and an
 * {@link model.ImageEditorModel}.
 *
 * @author emery
 * @created 2022-06-05
 */
public interface ImageEditorController {

  /**
   * Launches the image editor controller.
   *
   * @author emery
   * @created 2022-06-05
   */
  void launch();
}
