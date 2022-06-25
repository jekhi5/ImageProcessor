package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.view.ImageEditorGUIView;

/**
 * Represents a listener for a button.
 */
public class ButtonListener implements ActionListener {

  private final ImageEditorGUIView view;

  /**
   * Constructs a button listener with the given view.
   *
   * @param view the view that this button will communicate with
   */
  public ButtonListener(ImageEditorGUIView view) {
    if (view == null) {
      throw new IllegalArgumentException("Controller cannot be null.");
    }

    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.view.setCurrentImage(e.getActionCommand());
  }
}
