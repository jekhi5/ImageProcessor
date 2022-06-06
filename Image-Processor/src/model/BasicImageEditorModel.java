package model;

import java.util.Map;

import commands.ImageEditorCommand;
import image.ImageModel;

public class BasicImageEditorModel implements ImageEditorModel {

  Map<String, ImageModel> imageMap;

  @Override
  public void execute(ImageEditorCommand cmd, String imageName) throws IllegalArgumentException {

  }
}
