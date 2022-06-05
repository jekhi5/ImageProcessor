package view;

import java.io.IOException;

/**
 * An implementation of {@link ImageEditorTextView}. It can write text to its output stream, which
 * can be specified by the user, or is {@link System}{@code .out} by default.
 *
 * @author emery
 * @created 2022-06-05
 */
public class ImageEditorTextViewImpl implements ImageEditorTextView {
  private Appendable out;

  /**
   * Creates a new {@code ImageEditorTextViewImpl} outputting to {@link System}{@code .out}.
   */
  public ImageEditorTextViewImpl() {
    this(System.out);
  }

  /**
   * Creates a new {@code ImageEditorTextViewImpl} with a given output stream.
   * @param out the output stream
   */
  public ImageEditorTextViewImpl(Appendable out) {
    this.out = out;
  }

  @Override
  public void renderMessage(String msg) throws IOException {
    out.append(msg);
  }
}
