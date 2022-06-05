package model;

import commands.ImageEditorCommand;

public class BasicImageEditorModel implements ImageEditorModel {


  @Override
  public void execute(ImageEditorCommand cmd) throws IllegalArgumentException {
    cmd.execute(this);
  }
}
