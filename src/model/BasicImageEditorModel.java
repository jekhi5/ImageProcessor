package model;

import java.util.Map;

import commands.ImageEditorCommand;
import image.ImageModel;

public class BasicImageEditorModel implements ImageEditorModel {

  Map<String, ImageModel> imageMap;

  @Override
  public String execute(ImageEditorCommand cmd, ImageModel image)
          throws IllegalArgumentException {
    // TODO: implement this! -Emery (:
    return null;
  }

  @Override
  public ImageModel getImage(String name) throws IllegalArgumentException {
    if (this.imageMap.containsKey(name)) {
      return this.imageMap.get(name);
    } else {
      throw new IllegalArgumentException("Error. The given name does not appear in the list of " +
              "images. Give: " + name);
    }
  }
}
