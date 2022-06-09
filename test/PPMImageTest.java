import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import model.image.Image;
import model.image.PPMImage;
import model.image.PixelIterator;
import model.pixel.Pixel;
import model.pixel.PixelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests for PPMImage.
 *
 * @author emery
 * @created 2022-06-08
 */
public class PPMImageTest {

  private Pixel tl;
  private Pixel tm;
  private Pixel tr;
  private Pixel bl;
  private Pixel bm;
  private Pixel br;
  private List<List<Pixel>> grid;

  @Before
  public void init() {
    this.tl = new PixelImpl(0, 0, 0, 0);
    this.tm = new PixelImpl(0, 1, 0, 0);
    this.tr = new PixelImpl(0, 2, 0, 0);
    this.bl = new PixelImpl(1, 0, 0, 0);
    this.bm = new PixelImpl(1, 1, 0, 0);
    this.br = new PixelImpl(1, 2, 0, 0);
    grid = new ArrayList<>();
    grid.add(Arrays.asList(tl, tm, tr));
    grid.add(Arrays.asList(bl, bm, br));
  }

  @Test
  public void Constructor() {
  }

  @Test(expected = IllegalArgumentException.class)
  public void Constructor_EmptyGrid() {
    new PPMImage(new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void Constructor_NullGrid() {
    new PPMImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void Constructor_NonRect() {
    List<List<Pixel>> nonRectList = new ArrayList<>();
    nonRectList.add(List.of(tl));
    nonRectList.add(Arrays.asList(tm, tr));
    new PPMImage(nonRectList);
  }

  @Test
  public void iterator() {
    Iterator<Pixel> iter1 = new PixelIterator(grid);
    Iterator<Pixel> iter2 = new PPMImage(grid).iterator();

    for (int i = 0; i < 6; i += 1) {
      assertTrue(iter1.hasNext());
      assertTrue(iter2.hasNext());
      assertEquals(iter1.next(), iter2.next());
    }
    assertFalse(iter1.hasNext());
    assertFalse(iter2.hasNext());
  }

  @Test
  public void getPixelAt() {
    Image img = new PPMImage(grid);
    for (int r = 0; r < grid.size(); r += 1) {
      for (int c = 0; c < grid.get(r).size(); c += 1) {
        assertEquals(grid.get(r).get(c), img.getPixelAt(r, c));
        assertNotSame(grid.get(r).get(c), img.getPixelAt(r, c));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPixelAt_InvalidRowNeg() {
    Image img = new PPMImage(grid);
    img.getPixelAt(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPixelAt_InvalidRowPos() {
    Image img = new PPMImage(grid);
    img.getPixelAt(2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPixelAt_InvalidColNeg() {
    Image img = new PPMImage(grid);
    img.getPixelAt(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPixelAt_InvalidColPos() {
    Image img = new PPMImage(grid);
    img.getPixelAt(0, 3);
  }

  @Test
  public void setPixelAt() {
    Pixel newPixel = new PixelImpl(255, 255, 255, 255);
    Image img = new PPMImage(grid);
    for (int r = 0; r < img.getHeight(); r += 1) {
      for (int c = 0; c < img.getWidth(); c += 1) {
        Pixel oldPixel = img.getPixelAt(r, c);
        assertEquals(oldPixel, img.setPixelAt(r, c, newPixel));
        assertEquals(newPixel, img.getPixelAt(r, c));
        assertNotSame(newPixel, img.getPixelAt(r, c));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPixelAt_NullPixel() {
    Image img = new PPMImage(grid);
    img.setPixelAt(0, 0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPixelAt_InvalidRowNeg() {
    Image img = new PPMImage(grid);
    img.setPixelAt(-1, 0, tl);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPixelAt_InvalidRowPos() {
    Image img = new PPMImage(grid);
    img.setPixelAt(2, 0, tl);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPixelAt_InvalidColNeg() {
    Image img = new PPMImage(grid);
    img.setPixelAt(0, -1, tl);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPixelAt_InvalidColPos() {
    Image img = new PPMImage(grid);
    img.setPixelAt(0, 3, tl);
  }


  @Test
  public void getWidth() {
    List<List<Pixel>> lst = new ArrayList<>();
    lst.add(new ArrayList<>());
    for (int i = 1; i <= 500; i += 1) {
      lst.get(0).add(new PixelImpl(0, 0, 0, 0));
      Image img = new PPMImage(lst);
      assertEquals(i, img.getWidth());
    }
  }

  @Test
  public void getHeight() {
    List<List<Pixel>> lst = new ArrayList<>();
    for (int i = 1; i <= 500; i += 1) {
      lst.add(List.of(new PixelImpl(255, 255, 255, 255)));
      Image img = new PPMImage(lst);
      assertEquals(i, img.getHeight());
    }
  }

  @Test
  public void getCopy() {
    Image img = new PPMImage(grid);
    Image copy = img.getCopy();
    assertEquals(img, copy);
    assertNotSame(img, copy);
  }

  @Test
  public void testToPPMText() {
    String output = "P3\n# This image was created by the the Jacob Kline and Emery Jacobowitz's" +
            " Image Editor on: 06/08/2022 13:08:10\n" +
            "3 2\n2\n0\n0\n0\n0\n1\n0\n0\n2\n0\n1\n0\n0\n1\n1\n0\n1\n2\n0\n";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    String timestamp = output.substring(91, 110);



    assertEquals(output.substring(0, 91), new PPMImage(grid).toPPMText().substring(0, 91));
    assertEquals(output.substring(110),
            new PPMImage(grid).toPPMText().substring(110));
  }

  @Test
  public void testHashCode() {
    List<List<Pixel>> lst = new ArrayList<>();
    for (int i = 1; i <= 500; i += 1) {
      lst.add(List.of(new PixelImpl(255, 255, 255, 255)));
      Image img = new PPMImage(lst);
      assertEquals(lst.hashCode(), img.hashCode());
    }

  }

  @Test
  public void testEquals() {
    List<List<Pixel>> grid2 = new ArrayList<>();
    grid2.add(Arrays.asList(tr, tm, tl));
    grid2.add(Arrays.asList(br, bm, bl));

    Image i1 = new PPMImage(grid);
    Image i2 = new PPMImage(grid);

    Image i3 = new PPMImage(grid2);

    assertEquals(i1, i2);
    assertNotSame(i1, i2);

    assertEquals(i2, i1);
    assertNotSame(i2, i1);


    assertEquals(i1, i1);
    assertSame(i1, i1);

    assertEquals(i1.hashCode(), i1.hashCode());
    assertEquals(i1.hashCode(), i2.hashCode());

    assertNotEquals(i1, i3);
    assertNotEquals(i1.hashCode(), i3.hashCode());

    assertNotEquals(i3, i1);
    assertNotEquals(i3.hashCode(), i1.hashCode());
  }
}