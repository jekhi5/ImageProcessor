package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import commands.Help;
import gui.ColoredPopup;
import gui.HistogramFactory;
import gui.PopupDialog;
import gui.controller.ImageEditorSwingController;
import gui.view.ImageEditorGUIView;
import utilities.ImageUtil;

/**
 * An event listener for mouse events, specialized for one-click menu bar items. On click, sends the
 * given command to the controller.
 */
public class SimpleMenuListener implements ActionListener {

  protected final ImageEditorSwingController controller;
  protected final ImageEditorGUIView view;

  /**
   * Creates a new SimpleMenuListener.
   *
   * @param controller the controller
   * @throws IllegalArgumentException if any argument is null
   */
  public SimpleMenuListener(ImageEditorSwingController controller, ImageEditorGUIView view)
          throws IllegalArgumentException {
    if (controller == null || view == null) {
      throw new IllegalArgumentException("Can't have null values");
    }

    this.controller = controller;
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().contains("help")) {
      JFrame helpFrame = new JFrame("Help Menu");

      JTextPane textPane = new ColoredPopup(new Help().apply(null));

      helpFrame.setSize(1000, 1000);
      helpFrame.setLocationRelativeTo(null);
      helpFrame.add(new JScrollPane(textPane));
      helpFrame.setVisible(true);

    } else if (e.getActionCommand().contains("hgram")) {
      try {
        generateHistogram(e.getActionCommand().split(" ")[1]);
      } catch (IllegalArgumentException ex) {
        new PopupDialog("Can't generate histogram - no image loaded.");
      }
    } else {
      controller.runCommand(e.getActionCommand());
      this.view.refreshImages();
    }
  }

  private void generateHistogram(String name) throws IllegalArgumentException {
    HistogramFactory.createHistogram(ImageUtil.toBufferedImage((controller.getImage(name))));
  }
}
