package view;

import controller.ImageEditorSwingController;

/**
 * A view adapter interface for working with a GUI and controller features.
 * Adds the ability to accept a controller.
 */
public interface ImageEditorGUIView extends ImageEditorView {

  /**
   * Adds the given controller to this view to facilitate two-way communication.
   * @param controller the controller
   * @throws IllegalArgumentException if the controller is null
   */
  void accept(ImageEditorSwingController controller) throws IllegalArgumentException;

  /**
   * Changes the focused image in the editor to the given name.
   * @param nameInEditor the name of the image
   * @throws IllegalArgumentException if the name is null or not loaded in the view
   */
  void setCurrentImage(String nameInEditor) throws IllegalArgumentException;

  /**
   * Adds a new Image name to the view.
   * @param nameInEditor the name of the image
   * @throws IllegalArgumentException if the name is null, or not in the model
   */
  void addImage(String nameInEditor) throws IllegalArgumentException;
}
