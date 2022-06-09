package model.image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.pixel.Pixel;

/**
 * An iterator over a list of {@link Pixel}s.
 *
 * @author Jacob Kline
 * @created 06/06/2022
 */
public class PixelIterator implements Iterator<Pixel> {

  private final List<Pixel> pixels;
  private int index;

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
