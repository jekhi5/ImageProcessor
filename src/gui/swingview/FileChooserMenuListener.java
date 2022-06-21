package gui.swingview;


import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import controller.ImageEditorSwingController;

/**
 * A menu listener specifically for commands like save and load which require files.
 */
public class FileChooserMenuListener extends SimpleMenuListener {
  private final String name;

  /**
   * Creates a new SimpleMenuListener.
   *
   * @param controller the controller
   * @param name       the image name to act upon
   * @throws IllegalArgumentException if any argument is null
   */
  public FileChooserMenuListener(ImageEditorSwingController controller, String name)
          throws IllegalArgumentException {
    super(controller);
    if (name == null) {
      throw new IllegalArgumentException("Can't have null args!");
    }
    this.name = name;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("lol");
    JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int i;
    if (e.getActionCommand().equalsIgnoreCase("load")) {
      i = f.showOpenDialog(new JFrame());
    } else {
      i = f.showSaveDialog(new JFrame());
    }
    System.out.println("lol");
    if (i == JFileChooser.APPROVE_OPTION) {
      String path = f.getSelectedFile().getAbsolutePath();
      if (e.getActionCommand().equalsIgnoreCase("load")) {
        controller.runCommand("load " + path + " " + path);
      } else {
        controller.runCommand("save " + path + " " + name + " y");
      }

    }

  }
}
