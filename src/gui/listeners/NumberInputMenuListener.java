package gui.listeners;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.controller.ImageEditorSwingController;
import gui.view.ImageEditorGUIView;

/**
 * A menu action listener that prompts the user for an additional numerical input.
 */
public class NumberInputMenuListener extends SimpleMenuListener {
  private final String name;

  /**
   * Creates a new SimpleMenuListener.
   *
   * @param controller the controller
   * @param name       the image name to act upon
   * @throws IllegalArgumentException if any argument is null
   */
  public NumberInputMenuListener(ImageEditorSwingController controller,
                                 ImageEditorGUIView view, String name)
          throws IllegalArgumentException {
    super(controller, view);
    if (name == null) {
      throw new IllegalArgumentException("Can't have null args!");
    }
    this.name = name;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JFrame inputFrame = new JFrame("Input needed!");
    String input;
    input = JOptionPane.showInputDialog(inputFrame,
            "Enter amount to " + e.getActionCommand());

    if (input != null && !input.equals("")) {
      String cmd = e.getActionCommand() + " " + input + " " + name + " " + name;
      controller.runCommand(cmd);
    }

    this.view.refreshImages();
  }

}
