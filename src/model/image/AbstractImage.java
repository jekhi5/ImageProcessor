package model.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import utilities.ImageSaver;
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

  @Override
  public int hashCode() {
    int hash = 0;
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        hash += getPixelAt(row, col).hashCode() * row % (col + 1);
      }
    }
    return hash;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Image) {
      if (this.getWidth() != ((Image) o).getWidth() ||
              this.getHeight() != ((Image) o).getHeight()) {
        return false;
      }
      for (int row = 0; row < getHeight(); row++) {
        for (int col = 0; col < getWidth(); col++) {
          if (!getPixelAt(row, col).equals(((Image) o).getPixelAt(row, col))) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }
}
