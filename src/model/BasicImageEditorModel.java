package model;

import java.util.Map;

import commands.ImageEditorCommand;
import image.ImageModel;

public class BasicImageEditorModel implements ImageEditorModel {

  Map<String, ImageModel> imageMap;

  @Override
<<<<<<< HEAD:src/model/BasicImageEditorModel.java
  public String execute(ImageEditorCommand cmd) throws IllegalArgumentException {
=======
  public void execute(ImageEditorCommand cmd, String imageName) throws IllegalArgumentException {
>>>>>>> model:Image-Processor/src/model/BasicImageEditorModel.java

    // TODO: implement this! -Emery (:
    return null;
  }
}
