import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import model.image.Image;
import model.image.PPMImage;
import model.pixel.PixelImpl;
import model.kernels.FilterKernel;
import model.kernels.PixelOperator;

import static org.junit.Assert.assertEquals;

/**
 * The testing class for FilterKernels.
 */
public class FilterKernelTest {
  PixelOperator op;
  Image img;

  @Before
  public void init() {
    op = new FilterKernel.KernelBuilder()
            .size(3)
            .valueAt(0, 0, 1.0)
            .valueAt(0, 1, 1.0)
            .valueAt(0, 2, 1.0)

            .valueAt(1, 0, 1.0)
            .valueAt(1, 1, 1.0)
            .valueAt(1, 2, 1.0)

            .valueAt(2, 0, 1.0)
            .valueAt(2, 1, 1.0)
            .valueAt(2, 2, 1.0)
            .build();

    img = new PPMImage(
            Arrays.asList(
                    Arrays.asList(new PixelImpl(1, 1, 1, 1),
                            new PixelImpl(1, 1, 1, 1),
                            new PixelImpl(1, 1, 1, 1)),
                    Arrays.asList(new PixelImpl(1, 1, 1, 1),
                            new PixelImpl(1, 1, 1, 1),
                            new PixelImpl(1, 1, 1, 1)),
                    Arrays.asList(new PixelImpl(1, 1, 1, 1),
                            new PixelImpl(1, 1, 1, 1),
                            new PixelImpl(1, 1, 1, 1))));
  }

  // Value at without setting size
  @Test(expected = IllegalStateException.class)
  public void notSettingSize() {
    new FilterKernel.KernelBuilder().valueAt(1, 2, 3);
  }

  // Testing value at with index out of bounds
  @Test(expected = IllegalArgumentException.class)
  public void valueAtOutOfBounds() {
    new FilterKernel.KernelBuilder().size(1).valueAt(5424, -5432, 0.0);
  }

  // Testing size with negative
  @Test(expected = IllegalArgumentException.class)
  public void negSize() {
    new FilterKernel.KernelBuilder().size(-4);
  }

  // Testing size with even
  @Test(expected = IllegalArgumentException.class)
  public void evenSize() {
    new FilterKernel.KernelBuilder().size(2);
  }

  // Testing out of bounds
  @Test(expected = IllegalArgumentException.class)
  public void outOfBounds() {
    op.resultAt(100, -24, img);
  }

  // Testing null image
  @Test(expected = IllegalArgumentException.class)
  public void nullImage() {
    op.resultAt(0, 0, null);
  }

  // Testing that building a KernelBuilder makes the correct FilterKernel
  @Test
  public void build() {
    // Corner:
    assertEquals(new PixelImpl(4, 4, 4, 1), op.resultAt(0, 0, img));

    // Edge:
    assertEquals(new PixelImpl(6, 6, 6, 1), op.resultAt(0, 1, img));

    // Center:
    assertEquals(new PixelImpl(9, 9, 9, 1), op.resultAt(1, 1, img));
  }
}