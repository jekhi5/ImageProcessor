package model;

import java.util.HashMap;

import commands.ImageEditorCommand;
import model.image.Image;

/**
 * A dummy model used for testing.
 *
 * @author emery
 * @created 2022-06-05
 */
public class TesterModel implements ImageEditorModel {
  @Override
  public String execute(ImageEditorCommand cmd) throws IllegalArgumentException {
    return cmd.apply(new HashMap<>());
  }
}
