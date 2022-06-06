package model;

import java.util.HashMap;
import java.util.Map;

import commands.ImageEditorCommand;
import model.image.Image;

/**
 * To represent a basic image the only supports simple pixels. IE, ones with no toggle-able alpha
 * value.
 *
 * @author Jacob Kline
 * @created 06/06/2022
 */
public class BasicImageEditorModel implements ImageEditorModel {

  private final Map<String, Image> images;

  public BasicImageEditorModel() {
    this.images = new HashMap<>();
  }

  public BasicImageEditorModel(Map<String, Image> images) {
    this.images = new HashMap<>(images);
  }

  @Override
  public String execute(ImageEditorCommand cmd) throws IllegalArgumentException {
    return cmd.apply(this.images);
  }

//  @Override
//  public Image getImage(String name) throws IllegalArgumentException {
//    if (this.images.containsKey(name)) {
//      return this.images.get(name);
//    } else {
//      throw new IllegalArgumentException("Error. The given name could not be found. Give: " + name);
//    }
//  }
}
