package view;

import java.io.IOException;

/**
 * A simple view for the image editor. The only functionality is writing text to an output stream.
 */
public interface ImageEditorView {

  /**
   * Appends the given message to the output stream.
   *
   * @param msg the message
   * @throws IOException if appending to the output stream fails
   */
  void renderMessage(String msg) throws IOException;
}
