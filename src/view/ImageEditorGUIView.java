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
}
