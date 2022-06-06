package controller;

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

  /**
   * Creates a new {@code ImageEditorTextController} with given model, view, and input stream.
   *
   * @param model the model
   * @param view the view
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
  }

  @Override
  public void launch() {

  }
}
