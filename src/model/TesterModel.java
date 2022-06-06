package model;

import java.util.HashMap;

import commands.ImageEditorCommand;
import image.ImageModel;

/**
 * A dummy model used for testing.
 *
 * @author emery
 * @created 2022-06-05
 */
public class TesterModel implements ImageEditorModel {
  @Override
  public String execute(ImageEditorCommand cmd, ImageModel image) throws IllegalArgumentException {
    return null;
  }

  @Override
  public ImageModel getImage(String name) throws IllegalArgumentException {
    return null;
  }
}
