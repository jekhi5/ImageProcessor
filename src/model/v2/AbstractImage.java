package model.v2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import model.image.Image;
import utilities.ImageUtil;

/**
 * A class that contains commonalities between all images. This is pretty much just save().
 */
public abstract class AbstractImage implements Image {
  @Override
  public void saveToPath(String path, boolean shouldOverwrite)
          throws IOException, IllegalArgumentException {
    if (path == null) {
      throw new IllegalArgumentException("Can't save to null path");
    }
    File f = new File(path);

    if (f.exists() && f.isDirectory()) {
      throw new IllegalArgumentException("Could not create file from path: " + path +
              ". This is a directory.");
    } else if (f.exists() && shouldOverwrite) {
      boolean wasSuccessfullyDeleted = f.delete();

      if (!wasSuccessfullyDeleted) {
        throw new IOException("Cannot delete file at path: " + f.getPath());
      }
    } else if (f.exists() && !shouldOverwrite) {
      throw new IllegalArgumentException(
              "Could not create file from path: " + path + ". There was " +
                      "already a file at this location. To overwrite, add \"true\" to command.");
    }

    ImageSaver.write(this.toBufferedImage(), ImageUtil.getSuffix(path), f);
  }

  protected abstract BufferedImage toBufferedImage();
}
