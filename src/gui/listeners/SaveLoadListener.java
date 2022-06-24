package gui.listeners;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import gui.controller.ImageEditorSwingController;
import gui.view.ImageEditorGUIView;

/**
 * A menu listener specifically for commands like save and load which require files.
 */
public class SaveLoadListener extends SimpleMenuListener {
  private final String name;

  /**
   * Creates a new SimpleMenuListener.
   *
   * @param controller the controller
   * @param name       the image name to act upon
   * @param view       the view
   * @throws IllegalArgumentException if any argument is null
   */
  public SaveLoadListener(ImageEditorSwingController controller, String name,
                          ImageEditorGUIView view) throws IllegalArgumentException {
    super(controller, view);
    if (name == null) {
      throw new IllegalArgumentException("Can't have null args!");
    }
    this.name = name;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    FileFilter supportedTypes = new FileNameExtensionFilter("Supported Images", "jpg", "png",
            "bmp", "ppm");
    JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    f.setFileFilter(supportedTypes);
    int i;
    if (e.getActionCommand().equalsIgnoreCase("load")) {
      i = f.showOpenDialog(new JFrame());
    } else {
      i = f.showSaveDialog(new JFrame());
    }


    if (i == JFileChooser.APPROVE_OPTION) {
      String path = f.getSelectedFile().getAbsolutePath();
      String commandName = e.getActionCommand().toLowerCase().substring(0,
              e.getActionCommand().indexOf(" "));
      switch (commandName) {
        case "load":
          controller.runCommand("load " + path + " " + path);
          view.addImage(path);
          view.setCurrentImage(path);
          break;
        case "save":
          controller.runCommand("save " + path + " " + name + " y");
          break;
        case "mask-command":
          String restOfCommand;
          String typeOfMaskCommand;
          try {
            restOfCommand =
                    e.getActionCommand().substring(e.getActionCommand().indexOf(" "));
            typeOfMaskCommand = restOfCommand.substring(1/*The leading space*/,
                    restOfCommand.indexOf(" "));
          } catch (IndexOutOfBoundsException j) {
            throw new IllegalArgumentException("Cannot get rest of command!");
          }

          if (typeOfMaskCommand.equalsIgnoreCase("darken") || typeOfMaskCommand.equalsIgnoreCase(
                  "brighten")) {
            ActionListener niml = new NumberInputMenuListener(this.controller, this.view,
                    this.name);
            niml.actionPerformed(new ActionEvent(new Object(), ActionEvent.ACTION_FIRST,
                    "mask-command " + path + " " + typeOfMaskCommand));
          } else {
            controller.runCommand(path + " " + restOfCommand);
          }

          break;
      }
    }

    this.view.refreshImages();
  }
}
