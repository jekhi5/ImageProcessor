package gui.swingview;


import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import controller.ImageEditorSwingController;
import view.ImageEditorGUIView;

/**
 * A menu listener specifically for commands like save and load which require files.
 */
public class FileChooserMenuListener extends SimpleMenuListener {
  private final String name;
  private final ImageEditorGUIView view;

  /**
   * Creates a new SimpleMenuListener.
   *
   * @param controller the controller
   * @param name       the image name to act upon
   * @param view the view
   * @throws IllegalArgumentException if any argument is null
   */
  public FileChooserMenuListener(ImageEditorSwingController controller, String name, ImageEditorGUIView view)
          throws IllegalArgumentException {
    super(controller);
    if (name == null || view == null) {
      throw new IllegalArgumentException("Can't have null args!");
    }
    this.name = name;
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int i;
    if (e.getActionCommand().equalsIgnoreCase("load")) {
      i = f.showOpenDialog(new JFrame());
    } else {
      i = f.showSaveDialog(new JFrame());
    }


    if (i == JFileChooser.APPROVE_OPTION) {
      String path = f.getSelectedFile().getAbsolutePath();
      if (e.getActionCommand().equalsIgnoreCase("load")) {
        controller.runCommand("load " + path + " " + path);
        view.addImage(path);
        view.setCurrentImage(path);
      } else {
        controller.runCommand("save " + path + " " + name + " y");
      }
    }
  }
}
