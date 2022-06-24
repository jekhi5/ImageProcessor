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
public class FileChooserMenuListener extends SimpleMenuListener {
  private final String name;

  /**
   * Creates a new SimpleMenuListener.
   *
   * @param controller the controller
   * @param name       the image name to act upon
   * @param view       the view
   * @throws IllegalArgumentException if any argument is null
   */
  public FileChooserMenuListener(ImageEditorSwingController controller, String name,
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
    if (e.getActionCommand().equalsIgnoreCase("save")) {
      i = f.showSaveDialog(new JFrame());
    } else {
      i = f.showOpenDialog(new JFrame());
    }


    if (i == JFileChooser.APPROVE_OPTION) {
      String path = f.getSelectedFile().getAbsolutePath();
      String commandName;

      try {
        commandName = e.getActionCommand().toLowerCase().substring(0,
                e.getActionCommand().indexOf(" "));
      } catch (IndexOutOfBoundsException j) {
        commandName = e.getActionCommand().toLowerCase();
      }

      switch (commandName) {
        case "load":
          String nameInEditor = this.getNameInEditor(path);

          controller.runCommand("load " + path + " " + nameInEditor);
          view.addImage(nameInEditor);
          view.setCurrentImage(nameInEditor);
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
            typeOfMaskCommand = restOfCommand.substring(1/*the leading space*/, restOfCommand
                    .substring(1/*not counting the first space*/).indexOf(" ") + 1/*because
                    we're starting one behind*/);
          } catch (IndexOutOfBoundsException j) {
            throw new IllegalArgumentException("Cannot get rest of command!");
          }

          this.handleCommand(typeOfMaskCommand, path, restOfCommand);
          break;
        default:
          throw new IllegalArgumentException("Unsupported File Chooser Action");
      }
    }

    this.view.refreshImages();
  }

  private void handleCommand(String typeOfMaskCommand, String path, String restOfCommand) {
    if (typeOfMaskCommand.equalsIgnoreCase("darken") || typeOfMaskCommand.equalsIgnoreCase(
            "brighten")) {
      ActionListener niml = new NumberInputMenuListener(this.controller, this.view, this.name);
      niml.actionPerformed(
              new ActionEvent(new Object(), ActionEvent.ACTION_FIRST, typeOfMaskCommand));
    } else {
      controller.runCommand("mask-command " + path + " " + restOfCommand);
    }
  }


  private String getNameInEditor(String initialName) {
    String result = initialName;

    try {
      controller.getImage(result);
      result += "_";
    } catch (IllegalArgumentException ignored) {
      return result;
    }
    return this.getNameInEditor(result);
  }
}
