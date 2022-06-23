package gui.listeners;

import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.controller.ImageEditorSwingController;
import gui.view.ImageEditorGUIView;

/**
 * A menu action listener that prompts the user for 2 additional numerical inputs.
 */
public class ResizerListener extends SimpleMenuListener {


  private final String name;

  /**
   * Creates this menu listener with the given controller, view, and name.
   *
   * @param controller the controller
   * @param view       the view that this menu listener can communicate with
   * @param name       the image name to act upon
   * @throws IllegalArgumentException if any argument is null
   */
  public ResizerListener(ImageEditorSwingController controller,
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
    JTextField widthField = new JTextField("Width");
    JTextField heightField = new JTextField("Height");

    JPanel panel = new JPanel();
    panel.add(new JLabel("New Width:"));
    panel.add(widthField);
    panel.add(Box.createHorizontalStrut(15));
    panel.add(new JLabel("New Height:"));
    panel.add(heightField);


    int result = JOptionPane.showConfirmDialog(inputFrame, panel,
            "Please enter the new width and height (positive integers please!):",
            JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      String cmd = "resize " + widthField.getText() + " " + heightField.getText() +
              " " + name + name;
      controller.runCommand(cmd);
    }

    this.view.refreshImages();

  }
}
