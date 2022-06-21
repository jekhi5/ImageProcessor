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
  private final ImageEditorSwingController controller;

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
    controller.runCommand(e.getActionCommand());
    System.out.println(e.getActionCommand());
  }
}
