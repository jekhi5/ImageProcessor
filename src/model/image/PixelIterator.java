package model.image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.pixel.Pixel;

/**
 * An iterator over a list of {@link Pixel}s.  We initially thought this would be more helpful;,
 * however we decided to change our design.  We are keeping this in because it might come in handy
 * in the next iteration of this project.
 *
 * @author Jacob Kline
 * @created 06/06/2022
 */
public class PixelIterator implements Iterator<Pixel> {

  private final List<Pixel> pixels;
  private int index;

  /**
   * To construct a Pixel iterator that will iterate over the given Pixel array.
   *
   * @param image the pixel array of the image to be iterated over
   */
  public PixelIterator(List<List<Pixel>> image) {
    pixels = new ArrayList<>();
    for (List<Pixel> row : image) {
      pixels.addAll(row);
    }
  }

  @Override
  public boolean hasNext() {
    return index < pixels.size();
  }

  @Override
  public Pixel next() throws IllegalStateException {
    if (this.hasNext()) {
      return this.pixels.get(this.index++);
    } else {
      throw new IllegalStateException("Error. Attempting to get next value when no next value " +
              "exists!");
    }
  }
}
