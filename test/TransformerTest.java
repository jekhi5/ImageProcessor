import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import model.image.Image;
import model.image.PPMImage;
import model.pixel.PixelImpl;
import model.v2.kernels.PixelOperator;
import model.v2.kernels.Transformer;

import static org.junit.Assert.*;

/**
 * Test class for a Transformer.
 */
public class TransformerTest {
  PixelOperator op;
  Image img;

  @Before
  public void init() {
    op = new Transformer.TransformerBuilder()
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



  // Size throws error
  @Test(expected = UnsupportedOperationException.class)
  public void sizeOnTransformerBuilder() {
    new Transformer.TransformerBuilder().size(5);
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
    assertEquals(new PixelImpl(3, 3, 3, 1), op.resultAt(0, 0, img));

    // Edge:
    assertEquals(new PixelImpl(3, 3, 3, 1), op.resultAt(0, 1, img));

    // Center:
    assertEquals(new PixelImpl(3, 3, 3, 1), op.resultAt(1, 1, img));
  }
}