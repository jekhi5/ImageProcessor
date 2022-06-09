package view;

import java.io.IOException;

/**
 * An implementation of {@link ImageEditorView}. It can write text to its output stream, which can
 * be specified by the user, or is {@link System}{@code .out} by default.
 *
 * @author emery
 * @created 2022-06-05
 */
public class ImageEditorTextView implements ImageEditorView {
  private final Appendable out;

  /**
   * Creates a new {@code ImageEditorTextView} outputting to {@link System}{@code .out}.
   */
  public ImageEditorTextView() {
    this(System.out);
  }

  /**
   * Creates a new {@code ImageEditorTextView} with a given output stream.
   *
   * @param out the output stream
   * @throws IllegalArgumentException if {@code out} is {@code null}
   */
  public ImageEditorTextView(Appendable out) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("View can't have a null output.");
    }
    this.out = out;
  }

  @Override
  public void renderMessage(String msg) throws IOException {
    out.append(msg);
  }
}
