import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.image.PixelIterator;
import model.pixel.Pixel;
import model.pixel.PixelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The test class for the {@link PixelIterator}.
 */
public class PixelIteratorTest {

  @Test
  public void hasNext_EmptyList() {
    // Pixel iterator with no Pixels inside of it.
    // Therefore, hasNext() should always return false.
    PixelIterator p = new PixelIterator(new ArrayList<>());
    assertFalse(p.hasNext());
  }

  @Test
  public void hasNext() {
    // Pixel iterator with 3 Pixels inside of it.
    // we can call next() thrice before hasNext() returns false.
    List<List<Pixel>> lst = new ArrayList<>();
    for (int i = 0; i < 3; i += 1) {
      List<Pixel> sublist = new ArrayList<>();
      sublist.add(new PixelImpl(0, 0, 0, 0));
      lst.add(sublist);
    }
    PixelIterator p = new PixelIterator(lst);
    assertTrue(p.hasNext());
    p.next();
    assertTrue(p.hasNext());
    p.next();
    assertTrue(p.hasNext());
    p.next();
    assertFalse(p.hasNext());
  }

  @Test(expected = IllegalStateException.class)
  public void next_NoHasNext() {
    PixelIterator p = new PixelIterator(new ArrayList<>());
    assertFalse(p.hasNext());
    p.next();
  }

  @Test
  public void next() {
    List<List<Pixel>> lst = new ArrayList<>();
    for (int i = 0; i < 3; i += 1) {
      List<Pixel> sublist = new ArrayList<>();
      sublist.add(new PixelImpl(i, 0, 0, 0));
      lst.add(sublist);
    }
    PixelIterator p = new PixelIterator(lst);

    for (int i = 0; i < 3; i += 1) {
      Pixel pix = p.next();
      assertEquals(i, pix.getRed());
    }
  }
}