package gui.swingview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import controller.ImageEditorSwingController;

/**
 * An event listener for mouse events, specialized for one-click menu bar items.
 * On click, sends the given command to the controller.
 */
public class SimpleMenuListener implements ActionListener {
  protected final ImageEditorSwingController controller;

  /**
   * Creates a new SimpleMenuListener.
   *
   * @param controller the controller
   * @throws IllegalArgumentException if any argument is null
   */
  public SimpleMenuListener(ImageEditorSwingController controller) throws IllegalArgumentException {
    if (controller == null) {
      throw new IllegalArgumentException("Can't have null values");
    }
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("hgram")) {
      generateHistogram();
    } else {
      controller.runCommand(e.getActionCommand());
    }
  }

  private void generateHistogram() {
    // TODO: figure out histograms!
  }
}
