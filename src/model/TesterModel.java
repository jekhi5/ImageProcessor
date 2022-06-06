package model;

import java.util.HashMap;

import commands.ImageEditorCommand;

/**
 * A dummy model used for testing.
 *
 * @author emery
 * @created 2022-06-05
 */
public class TesterModel implements ImageEditorModel {
  @Override
  public String execute(ImageEditorCommand cmd) throws IllegalArgumentException {
    return cmd.apply(this);
  }
}
