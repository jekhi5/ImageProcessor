package view;

import java.io.IOException;

/**
 * A simple view for the image editor. The only functionality is writing text to an output stream.
 *
 * @author emery
 * @created 2022-06-05
 */
public interface ImageEditorView {

  /**
   * Appends the given message to the output stream.
   *
   * @param msg the message
   * @throws IOException if appending to the output stream fails
   * @author emery
   * @created 2022-06-05
   */
  void renderMessage(String msg) throws IOException;
}
