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

  /**
   * To construct a basic image editor model with no images in the editor.
   */
  public BasicImageEditorModel() {
    this.images = new HashMap<>();
  }

  /**
   * To construct a basic image editor model with the given map of images and their names in the
   * editor.
   *
   * @param images the map of names and images that this editor is working on
   * @throws IllegalArgumentException if the images map is null
   */
  public BasicImageEditorModel(Map<String, Image> images) throws IllegalArgumentException {
    if (images == null) {
      throw new IllegalArgumentException("Error. The given Map cannot be null.");
    } else {
      this.images = new HashMap<>(images);
      this.images.replaceAll((name, value/*unused*/) -> this.images.get(name).getCopy());
    }
  }


  @Override
  public Image getImage(String name) throws IllegalArgumentException {
    if (this.images.containsKey(name)) {
      return this.images.get(name).getCopy();
    } else {
      throw new IllegalArgumentException("Error. No image found with the name: \"" + name + "\"");
    }
  }

  @Override
  public void addImage(String name, Image image) throws IllegalArgumentException {
    if (name == null || image == null) {
      throw new IllegalArgumentException("Error. The given name nor image can be null.");
    } else {
      this.images.put(name, image.getCopy());
    }
  }

  @Override
  public String execute(ImageEditorCommand cmd) {
    if (cmd == null) {
      throw new IllegalArgumentException("Command can't be null.");
    }
    return cmd.apply(this);
  }
}
