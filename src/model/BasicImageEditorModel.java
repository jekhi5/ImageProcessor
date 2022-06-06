package model;

import commands.ImageEditorCommand;
import image.ImageModel;

public class BasicImageEditorModel implements ImageEditorModel {
  @Override
  public String execute(ImageEditorCommand cmd, ImageModel image) throws IllegalArgumentException {
    return null;
  }

  @Override
  public ImageModel getImage(String name) throws IllegalArgumentException {
    return null;
  }
}
